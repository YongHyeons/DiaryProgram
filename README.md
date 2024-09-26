# Calender - Diary Program


## 캘린더와 다이어리 프로그램

PC사용자가 자신의 일정을 효율적으로 관리하거나 계획하기 위해 사용하는 프로그램입니다.

Java의 Swing Components을 활용한 GUI Program

<br><br>
### 📖 DISCRIPTION

이 프로그램은 캘린더 다이어리 프로그램으로, PC 사용자가 자신의 일정, 일기, 메모 등을 캘린더에 입력하고 저장할 수 있도록 설계되었습니다. 주요 기능은 다음과 같습니다:

1. 이벤트 등록: 사용자는 날짜를 클릭하여 이벤트 제목, 위치, 시작 날짜, 종료 날짜, 반복 여부, 세부 내용을 입력해 일정을 등록할 수 있습니다.

2. 이벤트 제목은 캘린더에 표시되며, 클릭 시 수정 및 삭제가 가능합니다.
 - 제목은 검색 시 식별자로 사용됩니다.
 - 반복 설정: 시작 날짜와 종료 날짜를 다르게 설정하여 여러 날에 걸친 일정을 등록할 수 있으며, 반복 여부를 설정하여 매주, 매월, 매년 반복되는 이벤트를 생성할 수 있습니다.

3. 캘린더 탐색: 월별, 연도별로 일정을 확인할 수 있으며, 버튼을 눌러 이전/다음 달 또는 연도로 쉽게 이동할 수 있습니다.

4. 로그인 기능: 로그인한 사용자만 자신의 일정을 등록 및 확인할 수 있으며, 다른 사용자에게는 보이지 않습니다.

이 프로그램은 사용자 맞춤형 일정을 효과적으로 관리할 수 있는 기능을 제공합니다.
<br><br>
#### ⭐ ERD

![bbs_erd](https://github.com/user-attachments/assets/4e81dffc-1cea-4091-9c0c-14bc275eb9ab)

<br><br>

#### ⭐ Project Tree

![image](https://github.com/user-attachments/assets/b42902cb-0384-48f7-9b8e-7f05a47d3b7c)

<br><br>

#### ⭐ Technologies Used
> Language
- JAVA
> Tool
- MySQL (현재는 Oracle로 변경)
  
<br><br>

---
### 📖 View

<br>

#### ⭐ 로그인

![login](https://github.com/user-attachments/assets/d76ff07b-fc68-4e8e-82b0-827c17466340)

- 사용자의 아이디, 비밀번호를 입력받아 로그인 검사
- 회원가입 없이 로그인이 가능하게 구현
- 회원가입, 비밀번호 찾기로 이동할 수 있게 구성

<br><br>

#### ⭐ 회원가입

![sign](https://github.com/user-attachments/assets/a9eb0e8c-533d-4a94-ac26-9fd618fdc84a)

- 아이디 입력, 비밀번호 입력, 비밀번호 찾기 힌트를 위한 질문 제공
- 비밀번호 확인 기능 제공
  
<br><br>

#### ⭐ 비밀번호 찾기

![password](https://github.com/user-attachments/assets/ab2b0784-cd4a-4d31-b2bf-90debd12bc1c)

- 아이디와 비밀번호 찾기 힌트를 보여주고 알맞는 답변을 할 시 비밀번호를 제공하여 비밀번호 찾기 가능

<br><br>

#### ⭐ 캘린더 - 월별

![main](https://github.com/user-attachments/assets/7950e3b1-7b4d-4f01-a4dc-a41288715081)

- 로그인 이후 바로 보여지는 화면으로 현재 날짜를 기준으로 월별 캘린더 화면을 보여준다.
- 상단 메뉴를 통해 연별로 캘린더를 볼 수 있게 기능 구현
- 일정 검색을 위한 검색창 구현
- 캘린더에서 특정 날짜 선택 시 일정 등록 페이지 표시
  
<br><br>

#### ⭐ 캘린더 - 연별

![year](https://github.com/user-attachments/assets/38a3688c-ce0f-4191-a06d-1d2db6034b24)

- 다른 월별 날짜를 보기 위한 연별 캘린더를 표시
- 특정 월을 클릭 시 해당 월을 기준으로 월별 캘린더를 표시해준다
  
<br><br>

#### ⭐ 일정 등록

![event](https://github.com/user-attachments/assets/bca3f21a-30ed-4afe-bb6b-1a906dbfbc73)

- 일정 이름, 위치, 일정 시간 및 옵션을 설정할 수 있다
- 일정에 대한 상세 내용을 적을 수 있게 상세정보 입력 기능 구현

<br><br>

