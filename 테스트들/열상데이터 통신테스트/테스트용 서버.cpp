#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<sys/socket.h>

#include<string>
#include<cstring>
#include<fstream>

#define BUF_SIZE 5000


using namespace std;


void error_handling(char *message);


int main(int argc, char *argv[]) {

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


	if (bind(serv_sock, (struct sockaddr*)&serv_adr, sizeof(serv_adr)) == -1)
		error_handling("bind() error");
	if (listen(serv_sock, 1) == -1)
		error_handling("listen() error");
	clnt_adr_sz = sizeof(clnt_adr);

	clnt_sock = accept(serv_sock, (struct sockaddr*)&clnt_adr, &clnt_adr_sz);
	////소켓 생성 및 연결






	///통신부분 

	//serv_sock, clnt_sock로 통신

	char message[5000];
	int read_cnt;

	int i = 0;
	while (1) {
		
		read_cnt = recvfrom(serv_sock, message, BUF_SIZE, 0,
			(struct sockaddr*)&clnt_adr, &clnt_adr_sz);


		if (read_cnt != -1 && read_cnt != 0)
			printf("%s\n", message);

		else if (read_cnt == -1)
			printf("읽기 에러\n");
		else if (read_cnt == 0)
			printf("읽기가 0을 리턴\n");

	}



	///통신부분

	close(clnt_sock);
	close(serv_sock);
	return 0;

}


void error_handling(char *message) {
	fputs(message, stderr);
	fputc('\n', stderr);
	exit(1);
}
