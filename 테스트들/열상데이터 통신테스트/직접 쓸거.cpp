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



#define ANSI_COLOR_RED     "\x1b[31m"
#define ANSI_COLOR_GREEN   "\x1b[32m"
#define ANSI_COLOR_YELLOW  "\x1b[33m"
#define ANSI_COLOR_BLUE    "\x1b[34m"
#define ANSI_COLOR_MAGENTA "\x1b[35m"
#define ANSI_COLOR_CYAN    "\x1b[36m"
#define ANSI_COLOR_NONE    "\x1b[30m"
#define ANSI_COLOR_RESET   "\x1b[0m"

//#define FMT_STRING "%+06.2f "
#define FMT_STRING "\u2588\u2588"

#define MLX_I2C_ADDR 0x33

#define TEMP_DETECT 36


using namespace std;

void error_handling(char *message) {
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}


int main(int argc, char*argv[]) {
	int sock;

	struct sockaddr_in serv_adr;

	if (argc != 3) {
		printf("Usage : %s <IP> <port>\n", argv[0]);
		exit(1);
	}

	sock = socket(PF_INET, SOCK_STREAM, 0);
	if (sock == -1)
		error_handling("socket() error");

	memset(&serv_adr, 0, sizeof(serv_adr));
	serv_adr.sin_family = AF_INET;
	serv_adr.sin_addr.s_addr = inet_addr(argv[1]);
	serv_adr.sin_port = htons(atoi(argv[2]));

	if (connect(sock, (struct sockaddr*)&serv_adr, sizeof(serv_adr)) == -1) {
		error_handling("connect() error!");
	}
	else
		puts("Socket Connected..........");
	//소켓 생성 및 연결 끝////////////////////////////////////////////////////




	tm *tm;
	timeval tv;
	// 타임스탬프 선언





	//열화상 데이터 추출 시작
	int state = 0;
	printf("Starting...\n");
	static uint16_t eeMLX90640[832];
	float emissivity = 0.98;
	uint16_t frame[834];
	float eTa;

	std::fstream fs;

	MLX90640_SetDeviceMode(MLX_I2C_ADDR, 0);
	MLX90640_SetSubPageRepeat(MLX_I2C_ADDR, 0);
	MLX90640_SetRefreshRate(MLX_I2C_ADDR, 0b110);
	MLX90640_SetChessMode(MLX_I2C_ADDR);

	printf("Configured...\n");

	paramsMLX90640 mlx90640;
	MLX90640_DumpEE(MLX_I2C_ADDR, eeMLX90640);
	MLX90640_ExtractParameters(eeMLX90640, &mlx90640);

	printf("EE Dumped...\n");
	int subpage;

	static float mlx90640To[768];
	int i = 0;



	char *message;



	int framenum = 1;

	while (1) {		//실시간 처리 부분

		state = !state;
		MLX90640_GetFrameData(MLX_I2C_ADDR, frame);
		eTa = MLX90640_GetTa(frame, &mlx90640);
		subpage = MLX90640_GetSubPageNumber(frame);
		MLX90640_CalculateTo(frame, &mlx90640, emissivity, eTa, mlx90640To);
		MLX90640_BadPixelsCorrection((&mlx90640)->brokenPixels, mlx90640To, 1, &mlx90640);
		MLX90640_BadPixelsCorrection((&mlx90640)->outlierPixels, mlx90640To, 1, &mlx90640);



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

		if (hours >= 0 && hours <= 9) {
			hour = "0" + to_string(tm->tm_hour);
		}
		else
			hour = to_string(tm->tm_hour);

		if (mins >= 0 && mins <= 9) {
			min = "0" + to_string(tm->tm_min);
		}
		else
			min = to_string(tm->tm_min);

		if (secs >= 0 && secs <= 9) {
			sec = "0" + to_string(tm->tm_sec);
		}
		else
			sec = to_string(tm->tm_sec);
		string fl = to_string(tv.tv_usec).substr(0, 3);
	

		string d = "$" + to_string(tm->tm_year + 1900) + "05" + to_string(tm->tm_mday);
		d += hour + min + sec + fl;



		int flag = 0;

		for (int x = 0; x < 32; x++) {
			for (int y = 0; y < 24; y++) {
				float val = mlx90640To[32 * (y) + x];

				if (isnan(val)) {
					printf("Nan 값! 계속 뜨면 재실행 해주세요\n");
					continue;
				}
				if ((int)val > 60 || (int)val < 0) continue;


				if ((int)val >= TEMP_DETECT) {	// 보낼 데이터 추가

					d += "|" + to_string(x) + "x" + to_string(y) + "_" + to_string(val);
					flag = 1;
				}
				else
					continue;


			}
		}

		d += "$";
		std::this_thread::sleep_for(std::chrono::milliseconds(10));
		if (flag == 1) {
			cout << d << endl;
			message = (char*)d.c_str();
			sendto(sock, message, strlen(message), 0,
				(struct sockaddr*)&serv_adr, sizeof(serv_adr));

		}
	}

	return 0;
}
