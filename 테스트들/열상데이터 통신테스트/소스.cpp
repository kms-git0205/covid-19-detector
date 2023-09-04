#include<iostream>
#include<string>

using namespace std;

int main() {

	
	
	string a = "asdfadsf";
	
	char* message = "hisadfhi";

	message = (char*)a.c_str();
	printf("%d", a.length());
}