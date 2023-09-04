#include <stdint.h>
#include <iostream>
#include <cstring>
#include <fstream>
#include <chrono>
#include <thread>
#include "headers/MLX90640_API.h"

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<sys/socket.h>

#include<math.h>


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
	//MLX90640_SetSubPage(MLX_I2C_ADDR, 0);
	printf("Configured...\n");

	paramsMLX90640 mlx90640;
	MLX90640_DumpEE(MLX_I2C_ADDR, eeMLX90640);
	MLX90640_ExtractParameters(eeMLX90640, &mlx90640);

	printf("EE Dumped...\n");

	int subpage;
	static float mlx90640To[768];
	int i = 0;
	//	std::this_thread::sleep_for(std::chrono::milliseconds(1000));
	



	int framenum = 1;
	float data[3];
	while (1) {		//실시간 처리 부분
		state = !state;
		//printf("State: %d \n", state);
		MLX90640_GetFrameData(MLX_I2C_ADDR, frame);
		// MLX90640_InterpolateOutliers(frame, eeMLX90640);
		eTa = MLX90640_GetTa(frame, &mlx90640);
		subpage = MLX90640_GetSubPageNumber(frame);
		MLX90640_CalculateTo(frame, &mlx90640, emissivity, eTa, mlx90640To);
		MLX90640_BadPixelsCorrection((&mlx90640)->brokenPixels, mlx90640To, 1, &mlx90640);
		MLX90640_BadPixelsCorrection((&mlx90640)->outlierPixels, mlx90640To, 1, &mlx90640);
		//printf("Subpage: %d\n", subpage);
		// MLX90640_SetSubPage(MLX_I2C_ADDR,!subpage);
		


		for (int x = 0; x < 32; x++) {
			for (int y = 0; y < 24; y++) {
				float val = mlx90640To[32 * (23 - y) + x];
				
				if (isnan(val)) {
					printf("Nan 값! 계속 뜨면 재실행 해주세요\n");
					continue;
				}
				if ((int)val > 60 || (int)val < 0) continue;
				
				
				if ((int)val >= TEMP_DETECT) {	// 데이터 보내기
					printf("%d번째 데이터 입력(x, y, val) : %d, %d, %.2f\n", i++, x, y, val);
					data[0] = x; data[1] = y; data[2] = val;
					write(sock, &data, sizeof(data));
				}
				else {
					continue;
				}
				

			}
			printf("\n");
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(16));
		printf("%d번째 프레임\n", framenum++);
	}
	return 0;
}


