//real_speaker.cpp - 경보를 울리기 위해 스피커 신호를 받는 코드입니다.

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

//mp3파일 재생 명령어 -> tmp.txt에 출력을 저장합니다.
#define MP3_PLAY_CMD1 "mplayer ./stop-1_4.mp3 > tmp.txt;"	//잠시 멈춰주세요.
#define MP3_PLAY_CMD2 "mplayer ./wait-1_4.mp3 > tmp.txt;"	//담당자가 올 때까지 대기해주세요. 
#define MP3_PLAY_CMD3 "mplayer ./step_back-1_4.mp3 > tmp.txt;"	//뒤로 물러나주세요.
#define MP3_PLAY_CMD4 "mplayer ./keep_distance-1_4.mp3 > tmp.txt;"	//간격을 두고 입장해주세요.
#define MP3_PLAY_CMD5 "mplayer ./oneline-1_4.mp3 > tmp.txt;"	//일렬로 입장해주세요.

void error_handling(char *message);

int main(int argc, char* argv[]) {
	////소켓 생성 및 연결
	int serv_sock, clnt_sock;
	struct sockaddr_in serv_adr, clnt_adr;
	socklen_t clnt_adr_sz;

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
	clnt_adr_sz = sizeof(clnt_adr);
	socklen_t serv_adr_sz = sizeof(serv_adr);

	printf("listening\n");
	clnt_sock = accept(serv_sock, (struct sockaddr*) & clnt_adr, &clnt_adr_sz);

	printf("connected...\n");
	////소켓 생성 및 연결

	char message[500];
	int read_cnt;

	while (1) {		//신호를 받았을 시, 경보를 재생하고, 화면에 신호를 받았다고 출력합니다.
					//또한, 받은 신호에 따라 여러가지 경보음을 울립니다.
		read_cnt = recvfrom(clnt_sock, message, 500, 0,
			(struct sockaddr*) &clnt_adr, &clnt_adr_sz);
		if (read_cnt > 0) {
			printf("\n\n\n\n\nSignal received!\n");
			printf("\n\n\n\n\n\n\n\n\n\n\n\n\n");	
			if (strcmp(message, "#1") == 0) system(MP3_PLAY_CMD1);
			if (strcmp(message, "#2") == 0) system(MP3_PLAY_CMD2);
			if (strcmp(message, "#3") == 0) system(MP3_PLAY_CMD3);
			if (strcmp(message, "#4") == 0) system(MP3_PLAY_CMD4);
			if (strcmp(message, "#5") == 0) system(MP3_PLAY_CMD5);	
			printf("received message : %s\n", message);
		}

		char tmp_char;
		long tmp_long = 0;

		if (ioctl(clnt_sock, FIONREAD, &tmp_long) != -1) { //소켓에 쌓인 나머지 값을 버림
			for (int i = 0; i < tmp_long; i++)
				recv(clnt_sock, &tmp_char, sizeof(char), 0);
		}
	}

	close(clnt_sock);
	close(serv_sock);
	return 0;

}

void error_handling(char *message) {
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}





