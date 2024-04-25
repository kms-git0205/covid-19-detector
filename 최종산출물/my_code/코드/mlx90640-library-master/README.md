
12171588 김민석

1. 열화상카메라 모듈에 대한 코드들 입니다. (개발 운영체제 : 리눅스)
   functions, headers 폴더안에는 열화상 카메라에서 지원하는 API 및 함수들이 정의되어 있습니다.
   이 부분은 제공받은 소스들 입니다.


2. examples/src에 제가 구현한 코드들이 들어 있습니다. 필요없는 파일은 전부 삭제했습니다.
   실행되는 모든 코드들은 examples/src에 있습니다.


3. 실제 기능 구현에 사용된 코드는 Total_test.cpp 와 real_speaker.cpp입니다.
   server.cpp는 대현선배님 서버쪽과의 통신을 자체테스트 하기 위해 만들었습니다.
   UIclient.cpp는 UI쪽과의 통신을 자체테스트 하기 위해 만들었습니다.


4. Total_test.cpp를 실행하는 방법은 다음과 같습니다.

   1- examples/src로 이동합니다.
   2- Total_test.cpp파일을 같은위치의 test.cpp로 복사합니다.
   3- README.txt가 있는 위치에서 make를 수행합니다.
   4- 실행파일은 examples/ 에 생성됩니다.


5. 이외의 코드들은 gcc에 -o -pthread -lwiringPI 옵션을 주어서 컴파일을 진행하면 됩니다.
