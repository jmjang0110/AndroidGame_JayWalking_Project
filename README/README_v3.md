# AndroidGame_JayWalking_Project

# 무단횡단 

 '길건너 친구들' 모작 안드로이드 게임 '무단횡단'

## High Concept 
    - Player는 차가 다니는 도로에서 무단횡단을 하며 계속 길을 건넌다. 
    - 길을 건너다가 차에 치이면 게임이 종료된다. 
    - 무단횡단한 차선의 개수가 많을수록 높은 점수를 얻는다.

## 핵심 메카닉 
    - Player는 좌우상하로 이동할 수 있으며 대각선 이동은 불가하다. 
    - Game Camera는 Player기준으로 따라가며 일정하게 Camera위치가 위로 올라간다.
      Camera가 Player를 가리게 되면 게임이 종료된다. ( 시간초과 )
    - 도로에는 차가 주행하고 있다.
    - 무단횡단 도중 차에 치이면 게임이 종료된다.
    - 무단횡단 중간중간 인도나 하천( 돌 다리 )가 있다. ( 차가 다니지 않음 ) 
    - 도로를 건널수록 점수를 획득한다. 

## 개발 범위 
    - 종스크롤 맵 
    - Player                            : 1명 
    - 도로에 지나가는 자동차 종류         : 승용차, 트럭 
    - UI                                : 일시정지, 경과시간, 점수 


## 게임 실행 흐름 ( 예상 )
### Move 
![giphy](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/ef415813-0ce4-4a13-b48d-2b3b02549c0a)![hopping-cat-notes](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/85e3f747-33cc-453b-8328-61c055bc4c4e)
### Game Image
![image](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/d74ab492-d812-4564-83fa-73084c074fba) ![image](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/182788a2-dea8-4a3a-a602-b99c054fbdbb)![Crossy_Road_Gameplay](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/ccddb657-7180-496c-bbf6-ec4e695aec65)




## 개발 일정 
-----------------
    - 1주차 : 클래스 설계 및 구상 
    - 2주차 : 리소스 수집 
    - 3주차 : GameFramework 제작 
    - 4주차 : BackGround ( 종스크롤 ) 구현  
    - 5주차 : Player 구현 
    - 6주차 : 장애물 구현 ( 자동차, 나무 .. )
    - 7주차 : 충돌 처리 
    - 8주차 : UI 배치 
    - 9주차 : 디버깅 및 발표 준비 
-----------------
## 실제 개발 이력 
    - 1주차 : 클래스 설계 및 구상 100 %
    - 2주차 : 리소스 수집 80 % (쓰지 않음)
    - 3주차 : ----------------------------
    - 4주차 : ---------------------------- 
    - 5주차 : Player 구현
    - 6주차 : 장애물 구현 ( 자동차, 나무 .. )
    - 7주차 : ----------------------------
    - 8주차 : ----------------------------
    - 9주차 : 디버깅 및 발표 준비 
-----------------
## 커밋 횟수
    - 1주차 : 0  회
    - 2주차 : 0  회
    - 3주차 : 6  회
    - 4주차 : 0  회
    - 5주차 : 0  회
    - 6주차 : 10 회
    - 7주차 : 0  회
    - 8주차 : 1  회
    - 9주차 : 14 회
  -----------------  
![image](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/3b1072ec-3986-4225-8adc-4d297301a150)


## MainScene에 구현된 class들의 역할
-----------------

MainPlayer : MainScene에 등장하는 플레이어 class 
Car : MainScene에 등장하는 자동차 class 
GridMapTile : 맵 타일을 관리하는 class ( TileStruct 배열로 관리한다. )
TileStruct : 맵의 전체 타일 중 하나를 표현하는 class 
Vector2 : 게임에서 쓰이는 x,y 좌표 class 

-----------------
1. 사용된 기술
2. 참고한 것들 : 수업시간에 진행한 내용들
3. 수업내용에서 차용한 것 : 버튼에 따른 키 입력, 점수판 
4. 직접 개발한 것
   - 타일에 따른 움직임

-----------------
아쉬운 것들
- 충돌처리
- 아이템
- 강을 건너는 다리 구현
- GameOver Scene, Lobby Scene 구현
- 사운드
-----------------
(앱을 스토어에 판다면) 팔기 위해 보충할 것들 
- 점수를 획득하고 해당 점수를 랭킹으로 표시하는 기능
- 다양한 장애물과 효과
- 자연스러운 움직임

-----------------
결국 해결하지 못한 문제/버그


-----------------
기말 프로젝트를 하면서 겪은 어려움
- 안드로이드 프로젝트 자체가 익숙하지 않아 디버깅하는데 오래걸렸다.


-----------------
이번 수업에서 
기대한 것 
스마트폰 게임 앱이 만들어지는 과정이 궁금했다.

얻은 것 
직접 apk파일을 만들면서 게임 앱이 만들어지는 과정을 알게되었다.

얻지 못한 것 


더 좋은 수업이 되기 위해 변화할 점





### 위 이미지 출처
   gif : <https://giphy.com/gifs/crossyroad-hipsterwhale-hipster-whale-why-did-the-chicken-cross-road-W6REsWXWsBZLUkQJAL>
