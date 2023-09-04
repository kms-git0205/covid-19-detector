//Total_test.cpp
//main함수에서 실행되는 부분이 실제 동영상에서 실행된 코드이며, 나머지는 테스트코드입니다.
#include<sys/ioctl.h>
#include <stdint.h>
#include <iostream>
#include <cstring>
#include <fstream>
#include <chrono>
#include <thread>
#include "headers/MLX90640_API.h"
#include<string>
#include<ctime>
#include<sys/timeb.h>

#include<time.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#include<arpa/inet.h> 
#include<sys/socket.h>

#include<math.h>
#include<sys/time.h>

#include<sys/resource.h>
#include<wiringPi.h>

#define ANSI_COLOR_RED     "\x1b[31m"
#define ANSI_COLOR_GREEN   "\x1b[32m"
#define ANSI_COLOR_YELLOW  "\x1b[33m"
#define ANSI_COLOR_BLUE    "\x1b[34m"
#define ANSI_COLOR_MAGENTA "\x1b[35m"
#define ANSI_COLOR_CYAN    "\x1b[36m"
#define ANSI_COLOR_NONE    "\x1b[30m"
#define ANSI_COLOR_RESET   "\x1b[0m"

#define GPIO_TRIGGER 23	//초음파센서 입력 핀 2개 정의
#define GPIO_ECHO 24

//#define FMT_STRING "%+06.2f "		//이미지 출력 시, 숫자로 할지 문자로 할지 결정
#define FMT_STRING "\u2588\u2588"
#define TEMP_DETECT 37.5	//고열자 감지 온도
#define MP3_PLAY_CMD "mplayer ./stop-1_4.mp3"	//mp3파일 재생 명령어
#define MLX_I2C_ADDR 0x33

using namespace std;

void error_handling(char* message) {
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}

//서버통신 소켓 관련 변수
int sock;
struct sockaddr_in serv_adr;
socklen_t serv_adr_sz;
float distancee;


//스피커와 통신하는 소켓 관련 변수
int serv2_sock, clnt_sock;
struct sockaddr_in serv2_adr, clnt_adr;
socklen_t serv2_adr_sz, clnt_adr_sz;


//열화상카메라 관련 변수
int state = 0;
static uint16_t eeMLX90640[832];
float emissivity = 0.98;
uint16_t frame[834];
float eTa;
int subpage;
static float mlx90640To[768];
paramsMLX90640 mlx90640;


//fixed_temp관련 변수
int frames = 1;
float maxsum = 0;
float max2 = -1;
float min2 = 100;
float maxx = 0;


void Ultrasonic() {	//쓰레드 함수 : 초음파 센서
					//delay 마다 전역변수 거리값 distancee를 센서에서 읽어와서 갱신합니다.
	long start, stop;
	if (wiringPiSetup() == -1) { printf("wiringPiSetup() error\n"); exit(0); }

	pinMode(GPIO_TRIGGER, OUTPUT);	//pin 연결설정
	pinMode(GPIO_ECHO, INPUT);

	while (1)	//delay마다 거리값 갱신
	{
		digitalWrite(GPIO_TRIGGER, LOW);
		delay(50);
		digitalWrite(GPIO_TRIGGER, HIGH);
		delayMicroseconds(10);
		digitalWrite(GPIO_TRIGGER, LOW);
		while (digitalRead(GPIO_ECHO) == 0);
		start = micros();
		while (digitalRead(GPIO_ECHO) == 1);
		stop = micros();

		float tmp = (float)(stop - start) / 58.8235;

		if (tmp > 0 && tmp <= 200)
			distancee = tmp;
		delay(50);
	}

	return;
}

void  socket_connect(int argc, char* argv0, char* argv1, char* argv2) {		//소켓을 생성 및 연결
	//전역변수 sock에 새로운 소켓을 생성합니다. 이는 서버와 통신하게 됩니다.
	serv_adr_sz = sizeof(serv_adr);
	if (argc != 3) {
		printf("Usage : %s <IP> <port>\n", argv0);
		exit(1);
	}

	sock = socket(PF_INET, SOCK_STREAM, 0);
	if (sock == -1)
		error_handling("socket() error");

	memset(&serv_adr, 0, sizeof(serv_adr));
	serv_adr.sin_family = AF_INET;
	serv_adr.sin_addr.s_addr = inet_addr(argv1);
	serv_adr.sin_port = htons(atoi(argv2));

	if (connect(sock, (struct sockaddr*) & serv_adr, sizeof(serv_adr)) == -1) {
		error_handling("connect() error!");
	}
	else
		puts("Socket Connected..........");
}


void set_Thermal_camera() {		//열화상 카메라 세팅 -> 열화상카메라의 초기 설정입니다.
	
	printf("Starting...\n");
	MLX90640_SetDeviceMode(MLX_I2C_ADDR, 0);
	MLX90640_SetSubPageRepeat(MLX_I2C_ADDR, 0);
	MLX90640_SetRefreshRate(MLX_I2C_ADDR, 0b110);	//0b110 : 32fps, 0b111 : 64fps
	MLX90640_SetChessMode(MLX_I2C_ADDR);

	printf("Configured...\n");
	MLX90640_DumpEE(MLX_I2C_ADDR, eeMLX90640);
	MLX90640_ExtractParameters(eeMLX90640, &mlx90640);

	printf("EE Dumped...\n");
}

void set_Time(string& d) {	
	//d에 $202006ddhhmmss000 문자열 추가 -> 초기 문자열 포맷 생성
	//빈 문자열 d를 받아서 초기 시간데이터를 추가합니다.
	tm* tm;
	timeval tv;
	// 타임스탬프 선언

	gettimeofday(&tv, NULL);
	tv.tv_sec += 3600 * 8;
	settimeofday(&tv, NULL);
	tm = localtime(&tv.tv_sec);

	string hour = to_string(tm->tm_hour);
	string min = to_string(tm->tm_min);
	string sec = to_string(tm->tm_sec);

	int hours = tm->tm_hour;
	int mins = tm->tm_min;
	int secs = tm->tm_sec;

	if (hours >= 0 && hours <= 9) hour = "0" + to_string(tm->tm_hour);
	else hour = to_string(tm->tm_hour);

	if (mins >= 0 && mins <= 9) min = "0" + to_string(tm->tm_min);
	else min = to_string(tm->tm_min);

	if (secs >= 0 && secs <= 9) sec = "0" + to_string(tm->tm_sec);
	else sec = to_string(tm->tm_sec);

	string fl = to_string(tv.tv_usec).substr(0, 3);
	d += "$" + to_string(tm->tm_year + 1900) + "06" +to_string(tm->tm_mday);
	d += hour + min + sec + fl;
	//d : 202006ddhhmmss000
}

void get_frame_data() {	//프레임 픽셀 온도데이터 얻어오기 -> mlx90640To[]에 저장
						//한 프레임의 온도데이터를 전역변수 float mlx90640To[768]에 저장합니다.
	state = !state;
	MLX90640_GetFrameData(MLX_I2C_ADDR, frame);
	eTa = MLX90640_GetTa(frame, &mlx90640);
	subpage = MLX90640_GetSubPageNumber(frame);
	MLX90640_CalculateTo(frame, &mlx90640, emissivity, eTa, mlx90640To);
	MLX90640_BadPixelsCorrection((&mlx90640)->brokenPixels, mlx90640To, 1, &mlx90640);
	MLX90640_BadPixelsCorrection((&mlx90640)->outlierPixels, mlx90640To, 1, &mlx90640);
}

float get_fixed_plus() {	//거리에 따라 더해야 할 보정 값을 리턴합니다.
	float plusval = 0;
	if (distancee >= 10 && distancee < 20) plusval = 0.81;
	if (distancee >= 20 && distancee < 30) plusval = 0.48;
	if (distancee >= 30 && distancee < 40) plusval = 0.76;
	if (distancee >= 40 && distancee < 50) plusval = 1;
	if (distancee >= 50 && distancee < 60) plusval = 1.44;
	if (distancee >= 60 && distancee < 70) plusval = 1.7;
	if (distancee >= 70 && distancee < 80) plusval = 2.28;
	if (distancee >= 80 && distancee < 90) plusval = 3.04;
	if (distancee >= 90 && distancee < 100) plusval = 3.24;
	return plusval;
}

void Detecting(string& d) {	//고열자 온도 픽셀들에 대한 데이터를 d에 추가합니다.
	
	string tmp = d;	//별도로 출력할 문자열 -> Detecting시 온도값도 추가함

	for (int y = 0; y < 24; y++) {
		for (int x = 0; x < 32; x++) {
			float val = mlx90640To[32 * (y)+(31 - x)] + get_fixed_plus();

			if (isnan(val)) {
				printf("Nan 값! 계속 뜨면 재실행 해주세요\nnan값 : %f\n", val);
				continue;
			}
			if ((int)val > 50 || (int)val < 15) continue;	//15 ~ 50 밖의 범위는 고려하지 않았습니다.

			if ((int)val >= TEMP_DETECT) {	// 보낼 데이터 추가
				d += "|" + to_string(31 - x) + "," + to_string(y);
				tmp += "|" + to_string(31 - x) + "," + to_string(y) + "," + to_string(val);
			}
			else
				continue;

		}
	}
	d += "$";
	tmp += "$";
	if (tmp.length() > 19)
		printf("%s\n", tmp.c_str());

	std::this_thread::sleep_for(std::chrono::milliseconds(10));
}



void send_to_server(string& d) {	//문자열 d를 서버측으로 보냅니다.
		char* message = (char*)d.c_str();
		sendto(sock, message, strlen(message), 0,
			(struct sockaddr*) & serv_adr, sizeof(serv_adr));
}

void real_time_process() {	// 서버에 실시간으로 데이터를 처리하는 과정입니다.
	while (1) {		//실시간 처리 부분
	
		string d, tmp;	//d : 서버에 전송할 문자열 , tmp : 화면 출력을 위한 별도의 문자열
		get_frame_data();
		set_Time(d);		
		set_Time(tmp);		//프레임데이터를 얻어온 후, 초기문자열값 설정
		Detecting(d);		//고열 픽셀 감지
		if (d.length() > 19)	//초기문자열에 픽셀값이 추가되었을 경우, 서버로 전송
			send_to_server(d);	
	}
}


void camera_test() {	//카메라의 초기 nan값을 보정해주는 함수입니다.
	int flag = 1;
	while (flag) {
		flag = 0;
		for (int i = 0; i < 768; i++) { //모든 픽셀에 대한 nan값 판단
			if (isnan(mlx90640To[i]) || mlx90640To[i] >= 45 || mlx90640To[i] <= 20) {
				flag = 1;
				break;
			}
		}
		if (flag == 1) {
			printf("nan값 수정중\n");
			set_Thermal_camera();
			get_frame_data();
		}
	}
}



void real_cpp(int argc, char* argv0, char* argv1, char* argv2) {	//main에서 작동하는 real_cpp입니다.

	if (argc != 3) {
		printf("Usage : %s <IP> <port>\n", argv0);
		exit(1);
	}
	socket_connect(argc, argv0, argv1, argv2); //소켓 생성 및 연결

	std::thread t2(Ultrasonic);	//쓰레드 구현 : 초음파센서
	set_Thermal_camera();	//열화상 카메라 세팅
	camera_test();			//nan값 테스트 및 보정
	real_time_process();	//실시간 처리
}


float get_max_pixel() {	//프레임에서 최고온도 픽셀의 온도값을 반환합니다. 테스트를 위해 만들어졌습니다.
	float maxx = -1;

	for (int y = 0; y < 24; y++) {
		for (int x = 0; x < 32; x++) {
			float val = mlx90640To[32 * (y)+(31 - x)];
			if (val > 99.99) continue;
			if (val >= 25.0 && val <= 45 && val > maxx)
				maxx = val;
		}
	}
	return maxx;
}

void maxtemp_process() {	//maxtemp함수의 처리 과정입니다. 프레임 당 최고온도를 출력합니다.
	while (1) {
		get_frame_data();
		float maxx = get_max_pixel();
		std::cout << maxx << std::endl;
	}
}


void maxtemp_cpp() { //메인에서 작동하는 maxtemp_cpp함수입니다. 테스트를 위해 만들어졌습니다.
	set_Thermal_camera();	//열화상 카메라 세팅
	maxtemp_process();		//프레임마다 최고온도 출력
}

void soundplay_process() {	//프레임의 최고온도가 고열자 감지온도 이상이면, 경보울림
	while (1) {
		get_frame_data();
		float maxx = get_max_pixel();
		if (maxx >= TEMP_DETECT) {
			std::cout << maxx << std::endl;
			system(MP3_PLAY_CMD);
		}
	}
}

void soundplay_cpp() {	//특정온도 값 이상이 검출되면 mp3파일 재생
	set_Thermal_camera();	//열화상 카메라 세팅
	soundplay_process();
}


void img_process() {	//열상 이미지를 콘솔상에 시각적으로 볼 수 있도록 출력하는 함수입니다.
	while (1) {
		get_frame_data();
		for (int y = 0; y < 24; y++) {
			for (int x = 0; x < 32; x++) {
				float val = mlx90640To[32 * (y)+(31 - x)];
				if (val > 99.99) val = 99.99;

				if (val >= 33.0) {
					printf(ANSI_COLOR_RED FMT_STRING ANSI_COLOR_RESET, val);
				}
				else {
					printf(ANSI_COLOR_GREEN FMT_STRING ANSI_COLOR_RESET, val);
				}
			}
			std::cout << std::endl;
		}
		printf("\x1b[33A");
	}
}

void img_cpp() {		//실시간 열상 이미지 출력 - 두가지 색상
	set_Thermal_camera();	//열화상 카메라 세팅
	img_process();
}

void fixed_temp_process(char* constant) {	//보정값에 constant값 더해서 출력

	while (1) {
		get_frame_data();	//프레임 데이터 얻어오기

		maxx = get_max_pixel() + get_fixed_plus() + atof(constant);
		//maxx값을 프레임 최고온도 + 거리에따른 보정값 + 특정상수값 으로 설정합니다.

		if (max2 == min2) camera_test();	
		//max2와 min2가 같다는 것은, 모든 픽셀이 같은온도라는 뜻이므로 nan값입니다. 따라서 camera_test를 진행합니다.

		if (maxx >= 30) {	//max2, maxx, min2값들을 수정하면서 온도 평균을 누적시킵니다.
			if (maxx > max2) max2 = maxx;
			if (min2 > maxx) min2 = maxx;
			maxsum += maxx;
			frames++;
			std::cout << maxx << std::endl;
		}
		if (frames != 0) {	//누적된 최대온도 평균, 현재거리, 모든 누적된 온도의 최대, 최소값을 출력합니다.
			std::cout << "평균 : " << maxsum / frames << std::endl;
			std::cout << "거리 : " << distancee << std::endl;
			std::cout << "max : " << max2 << std::endl;
			std::cout << "min : " << min2 << std::endl;
		}
	}
}

void fixed_connect() {	//fixed_temp에서 사용될 쓰레드 -> 서버로부터 신호 수신시 온도 누적값 초기화

	//서버에서 신호를 받으면, 전역변수로 정의된 온도 관련된 값들을 초기화합니다.

	int readcnt;
	char buf[100];

	char tmp_char;
	long tmp_long = 0;

	while (1) {
		int readcnt = recvfrom(sock, buf, 100, 0,
			(struct sockaddr*) & serv_adr, &serv_adr_sz);

		//	if (readcnt == -1) error_handling("recvfrom() error");

		if (readcnt > 0) {	//신호를 받았을 경우
			frames = 1;
			maxsum = 0;
			max2 = -1;
			min2 = 100;

			if (ioctl(sock, FIONREAD, &tmp_long) != -1) {//나머지 쌓인 값을 버림
				for (int i = 0; i < tmp_long; i++)
					recv(sock, &tmp_char, sizeof(char), 0);
			}
		}
	}
}

void fixed_temp_cpp(int argc, char* argv0, char* argv1) {	//프레임당 최고온도를 보정해서 출력

	if (argc != 2) {
		printf("Usage : <%s> <constant>\n", argv0);
		exit(1);
	}

	set_Thermal_camera();	//열화상카메라 세팅

	camera_test();
	std::thread t1(Ultrasonic);	//쓰레드 : 초음파 센서
	std::thread t2(fixed_connect);	//쓰레드 : 서버에서 값 입력시 온도누적값 초기화

	socket_connect(3, "test", "127.0.0.1", "1515");	//자기 자신에게 연결
	fixed_temp_process(argv1);
}


int main(int argc, char* argv[]) {

	real_cpp(argc, argv[0], argv[1], argv[2]);
	
	//maxtemp_cpp();		-> 테스트를 하고자 할 때 주석을 제거합니다.
	//soundplay_cpp();
//	img_cpp();
//	fixed_temp_cpp(argc, argv[0], argv[1]);

	return 0;
}


