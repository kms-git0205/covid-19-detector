
//UIclient.cpp - 스피커와 통신테스트가 양호한지 테스트하기 위한 파일입니다. / UI쪽에서 스피커로 신호를 보내는 것을 시뮬레이션 했습니다.

#include<sys/ioctl.h>
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<sys/socket.h>

#include<string>
#include<cstring>
#include<fstream>
#include<stdint.h>

#define BUF_SIZE 5000

using namespace std;


void error_handling(char *message);

int sock;
struct sockaddr_in serv_adr;
socklen_t serv_adr_sz;

int main(int argc, char* argv[]) {

	////소켓 생성 및 연결
	serv_adr_sz = sizeof(serv_adr);

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

	if (connect(sock, (struct sockaddr*) & serv_adr, sizeof(serv_adr)) == -1) {
		error_handling("connect() error!");
	}
	else
		puts("Socket Connected..........");
	////소켓 생성 및 연결 완료


	char* message[100];
	while (1) {			//고열자가 감지되면, 라즈베리파이 쪽에 문자열 형태로 신호를 보냅니다. 

		scanf("%s", message);	//임의로 키보드입력을 받아서 고열자가 감지되었다고 가정했습니다.
		sendto(sock, message, BUF_SIZE, 0,
			(struct sockaddr*) & serv_adr, sizeof(serv_adr));
	}
	close(sock);
	return 0;

}

void error_handling(char *message) {
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}




