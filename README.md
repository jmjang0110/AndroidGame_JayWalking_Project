# AndroidGame_JayWalking_Project

# 무단횡단 

 '길건너 친구들' 모작 안드로이드 게임 '무단횡단'

 # GitHub Commit 
![image](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/8e31bbab-7a9b-48a2-af83-5d459f589954)

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
### Game
![download](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/490b2837-c841-48b4-887a-58dfc907f4a2)
### Die
![maxresdefault](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/cbe4a0b5-fcf2-4bf5-bcd8-5ead534c665c)
### Move 
![giphy](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/ef415813-0ce4-4a13-b48d-2b3b02549c0a)![hopping-cat-notes](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/85e3f747-33cc-453b-8328-61c055bc4c4e)
### Game Image
![image](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/d74ab492-d812-4564-83fa-73084c074fba) ![image](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/182788a2-dea8-4a3a-a602-b99c054fbdbb)![Crossy_Road_Gameplay](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/ccddb657-7180-496c-bbf6-ec4e695aec65)


## 클래스 구조 설계 구상 
![image](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/c8b4ec7f-2742-43e8-8568-7f2d5a9dc0ce)


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




### 위 이미지 출처
   gif : <https://giphy.com/gifs/crossyroad-hipsterwhale-hipster-whale-why-did-the-chicken-cross-road-W6REsWXWsBZLUkQJAL>
