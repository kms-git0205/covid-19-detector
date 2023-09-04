# covid-19-detector
무인 고열자 감지 시스템(컴퓨터공학 종합설계)

열화상카메라(라즈베리파이 + 모듈), 실상카메라를 통해 고열자를 탐지하고 JAVA로 UI 및 데이터베이스를 관리하며, 스피커로 경고방송을 내보내는 시스템입니다.

사용기술 : 
  열화상카메라 : C언어 - mlx90640-library-master(flir 사의 열화상카메라 모듈 라이브러리) / 소켓을 통한 스피커 연동
  실상 카메라 : Python - yolo AI object detection 
  UI : JAVA

세부사항은 보고서 참조(무인 고열자 탐지 시스템.hwp)

# 실행구조 : 

![image](https://github.com/kms-git0205/covid-19-detector/assets/63552400/ede90b43-8dd0-484d-91fd-5623efc80500)
