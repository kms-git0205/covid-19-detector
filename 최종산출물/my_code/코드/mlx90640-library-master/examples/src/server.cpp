//server.cpp - 서버통신 자체테스트를 위한 파일입니다. / 대현형님의 서버 역할을 합니다.

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

int main(int argc, char* argv[]) {

	////소켓 생성 및 연결
	int serv_sock, clnt_sock, clnt2_sock;
	struct sockaddr_in serv_adr, clnt_adr, clnt2_adr;
	socklen_t clnt_adr_sz, clnt2_adr_sz;

	if (argc != 2) {
		printf("Usage : %s <port>\n", argv[0]);
		exit(1);
	}

	serv_sock = socket(PF_INET, SOCK_STREAM, 0);
	if (serv_sock == -1)
		error_handling("socket() error");

	int option = 1;
	setsockopt(serv_sock, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option));


	memset(&serv_adr, 0, sizeof(serv_adr));
	serv_adr.sin_family = AF_INET;
	serv_adr.sin_addr.s_addr = htonl(INADDR_ANY);
	serv_adr.sin_port = htons(atoi(argv[1]));


	if (bind(serv_sock, (struct sockaddr*) & serv_adr, sizeof(serv_adr)) == -1)
		error_handling("bind() error");
	if (listen(serv_sock, 3) == -1)
		error_handling("listen() error");
	clnt_adr_sz = sizeof(clnt_adr); clnt2_adr_sz = sizeof(clnt2_adr);
	socklen_t serv_adr_sz = sizeof(serv_adr);

	printf("listening\n");
	clnt_sock = accept(serv_sock, (struct sockaddr*) & clnt_adr, &clnt_adr_sz);

	printf("connected...\n");
	////소켓 생성 및 연결 완료

	char message[5000];
	int read_cnt;

	while (1) {		//상대방에게서 받은 메시지를 실시간으로 출력합니다.

		read_cnt = recvfrom(clnt_sock, message, 5000, 0,
			(struct sockaddr*) &clnt_adr, &clnt_adr_sz);
		if (read_cnt > 0) {
			message[read_cnt] = '\0';
			printf("%s\n", message);
			//system("mplayer ~/Desktop/stop-1_8.mp3 > tmp.txt");
		}

		char tmp_char;
		long tmp_long = 0;

		if (ioctl(clnt_sock, FIONREAD, &tmp_long) != -1) {//나머지 쌓인 값을 버림
			for (int i = 0; i < tmp_long; i++)
				recv(clnt_sock, &tmp_char, sizeof(char), 0);
		}
	}

	close(clnt_sock);
	close(clnt2_sock);
	close(serv_sock);
	return 0;

}

void error_handling(char *message) {
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}



