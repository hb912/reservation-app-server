<div align="center">
  <br />
  <img src="https://i.imgur.com/iLXcSkF.png?1" title="source: imgur.com" alt="logo" width="550px" height="300px"/>
  <br />
  <h1>:tent: 딩굴딩굴 - 캠핑장</h1>
  <div style="font-size: 16px">
  엘리스 소프트웨어 트랙 2기 부산 3팀의 두번째 웹 프로젝트 입니다.<br />
  캠핑장에 대한 정보를 확인하고 예약할 수 있는 홈페이지 입니다.<br />
  </div>
  <br />
  
</div>
<br />
<br />
<br />

## :memo: 기획 의도 & 목적

기존 사이트는 객실 위치를 이미지로 확인할 수밖에 없었고, 예약을 하려면 예약 페이지로 이동한 후에 글로만 예약하기 때문에 사용자가 예약하고도 객실위치를 직관적으로 확인을 하기 어려웠습니다.<br />
그래서 저희는 이러한 불편함을 개선하는 것을 목표로 하였습니다.<br />
저희가 기획한 서비스는 사용자가 지도를 통해 직접 예약위치를 확인하고, 선택하여 바로 예약할 수 있어, 일관된 흐름으로 사용자의 불편함을 개선했습니다.<br /><br /><br />


## :mag: 웹 서비스의 주제와 기능 소개
- <font size="4">주제 : 캠핑 예약 사이트</font>
- <font size="6">서비스 구조도</font>
  ![기능구조도](https://github.com/hb912/reservation-app-server/assets/43055448/93fe3370-6407-4d59-9eff-235dab8ae743)


<br />

- <font size="3">서브 기능</font>
  1.  카카오 로그인 기능
  2.  구글 맵 API
  3.  캘린더
  4.  페이지네이션
  5.  리뷰
  6.  이미지 캐러셀
  7.  모달
  8.  객실 UX
  9.  refresh token을 통한 자동 로그인
  10. redis저장소를 이용한 패스워드 찾기


<br /><br />

## 🛠 주요 기술 스택  🛠
<br />

### **Back-end**
<img alt="springboot" src ="https://img.shields.io/badge/Spring Boot-6DB33F.svg?&style=for-the-badge&logo=Spring Boot&logoColor=FFFFFF"/> &nbsp;
<img alt="spring security" src ="https://img.shields.io/badge/Spring Security-6DB33F.svg?&style=for-the-badge&logo=Spring Security&logoColor=ffffff"/>&nbsp;
<img alt="JWT" src ="https://img.shields.io/badge/JWT-000000.svg?&style=for-the-badge&logo=JSON Web Tokens&logoColor=ffffff"/>&nbsp;

<br />

### **DataBase**
<img alt="redis" src ="https://img.shields.io/badge/redis-DC382D.svg?&style=for-the-badge&logo=Redis&logoColor=ffffff"/>&nbsp;
<img alt="mysql" src ="https://img.shields.io/badge/mysql-4479A1.svg?&style=for-the-badge&logo=MySQL&logoColor=ffffff"/>&nbsp;

<br />

### **Front-end**
<img alt="React" src ="https://img.shields.io/badge/React-61DAFB.svg?&style=for-the-badge&logo=React&logoColor=FFFFFF"/> &nbsp;
<img alt="styled--components" src ="https://img.shields.io/badge/styled -- components-DB7093.svg?&style=for-the-badge&logo=styled-components&logoColor=333333"/>&nbsp;
<img alt="bootstrap" src ="https://img.shields.io/badge/react--bootstrap-7952B3.svg?&style=for-the-badge&logo=bootstrap&logoColor=ffffff"/>&nbsp;
<img alt="MUI" src ="https://img.shields.io/badge/material--UI-007FFF.svg?&style=for-the-badge&logo=MUI&logoColor=ffffff"/>&nbsp;
<img alt="antdesign" src ="https://img.shields.io/badge/Ant Design-0170FE.svg?&style=for-the-badge&logo=Ant Design&logoColor=ffffff"/>&nbsp;

<br/>

### **Dev-ops**
<img alt="gitlab" src ="https://img.shields.io/badge/gitHub-181717.svg?&style=for-the-badge&logo=GitHub&logoColor=FFFFFF"/> &nbsp;
<img alt="git" src ="https://img.shields.io/badge/git-f85832.svg?&style=for-the-badge&logo=Git&logoColor=ffffff"/>&nbsp;
<img alt="nginx" src ="https://img.shields.io/badge/nginx-009639.svg?&style=for-the-badge&logo=nginx&logoColor=ffffff"/>&nbsp;
<img alt="aws" src ="https://img.shields.io/badge/aws-232F3E.svg?&style=for-the-badge&logo=Amazon AWS&logoColor=ffffff"/>&nbsp;
<img alt="aws ec2" src ="https://img.shields.io/badge/ec2-FF9900.svg?&style=for-the-badge&logo=Amazon EC2&logoColor=ffffff"/>&nbsp;
<img alt="aws rds" src ="https://img.shields.io/badge/rds-527FFF.svg?&style=for-the-badge&logo=Amazon RDS&logoColor=ffffff"/>&nbsp;
<img alt="aws route53" src ="https://img.shields.io/badge/route-8C4FFF.svg?&style=for-the-badge&logo=Amazon Route 53&logoColor=ffffff"/>&nbsp;
<img alt="docker" src ="https://img.shields.io/badge/Docker-2496ED.svg?&style=for-the-badge&logo=Docker&logoColor=ffffff"/>&nbsp;
<img alt="jenkins" src ="https://img.shields.io/badge/jenkins-D24939.svg?&style=for-the-badge&logo=jenkins&logoColor=ffffff"/>&nbsp;
<img alt="eslint" src ="https://img.shields.io/badge/eslint-4B32C3.svg?&style=for-the-badge&logo=ESLint&logoColor=ffffff"/>&nbsp;
<img alt="prettier" src ="https://img.shields.io/badge/prettier-F7B93E.svg?&style=for-the-badge&logo=Prettier&logoColor=333333"/>&nbsp;
<br /><br />

## :clipboard: 프로젝트 구성도

<br/>

| [🔗와이어프레임(Wireframe)](https://kdt-gitlab.elice.io/sw_track/class_02_busan/web_project_2/team3/project-template/-/wikis/Wireframe)

| [🔗프로토타입(Prototype)](https://www.figma.com/file/NstxG3reXALAHqt0wEtrIf/Untitled?node-id=0%3A1)

| [🔗유저 시나리오 및 API 명세서](https://www.figma.com/file/NstxG3reXALAHqt0wEtrIf/Untitled?node-id=0%3A1)

| [🔗API 문서](https://documenter.getpostman.com/view/21028820/UzR1K2iz)

### 서버 아키텍처

![image](https://github.com/hb912/reservation-app-server/assets/43055448/9b977bc6-6bac-4d4c-8bca-ebf005068141)

### Entity class diagram

![image](https://github.com/hb912/reservation-app-server/assets/43055448/db241757-a347-495c-9033-7305965fe5df)


### ERD

![image](https://github.com/hb912/reservation-app-server/assets/43055448/284d9564-fa38-4518-a409-6939e0e7196e)


<br />


## 🎥 데모 영상
<br />

### 회원가입, 로그인, 로그아웃

&nbsp;&nbsp;&nbsp;&nbsp;![Imgur](https://imgur.com/cP7cGzm.gif)
### 유저 이메일 찾기, 패스워드 찾기

&nbsp;&nbsp;&nbsp;&nbsp;![Imgur](https://i.imgur.com/IQeNnFI.gif)
### 객실 상세 보기

&nbsp;&nbsp;&nbsp;&nbsp;![Imgur](https://i.imgur.com/KllmzQF.gif)
### 객실 예약 하기

&nbsp;&nbsp;&nbsp;&nbsp;![Imgur](https://i.imgur.com/KimXChJ.gif)
### 유저 예약 조회

&nbsp;&nbsp;&nbsp;&nbsp;![Imgur](https://i.imgur.com/qIcfAYr.gif)
### 유저 정보 수정

&nbsp;&nbsp;&nbsp;&nbsp;![Imgur](https://i.imgur.com/BTpzQmR.gif)
### 관리자 페이지

&nbsp;&nbsp;&nbsp;&nbsp;![Imgur](https://i.imgur.com/GxqRAHs.gif)



## 👪 구성원 역할
<br />

| 이름 | 역할 | 구현 기능 | 
| ------ | ------ | ------ |
|  김재영   |  프론트엔드(팀장)   | MyPage   |
|  정승우   |  프론트엔드,백엔드   | AdminPage  |
|  최정훈   |  프론트엔드   | Reservation,payment  |
|  박우람   |  프론트엔드   | Main, Login  |
|  김채홍   |  백엔드   |  Backend API  |

