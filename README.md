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
### Move 
![giphy](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/ef415813-0ce4-4a13-b48d-2b3b02549c0a)![hopping-cat-notes]

## 클래스 구조 설계 구상 
![image](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/bb3642e6-9341-4359-8237-989b4d6b21bb)

## 2D 맵 구성 
![image](https://github.com/jmjang0110/AndroidGame_JayWalking_Project/assets/90159618/ea1a959c-d986-4334-bbcf-76be9ace0e68)

## 아이디어
2D 맵 타일에 Object 배치.

자동차(움직임) 및 Obstacle Object 가 움직일 때는 해당 2D 맵 타일을 Walk ImPossible 로 바꾼다.
Player 는 Walk Possible 구간만 움직일 수 있다. 
필드는 한 줄 씩 생성된다.


## 개발 일정 
-----------------
    - 1주차 : [80%] 클래스 설계 및 구상 
    - 2주차 : [80%] 리소스 수집                 --> PokeMon 이미지로 쓰겠습니다 ( 사유 : 길건너 친구들 리소스를 못 찾겠습니다. ) 
    - 3주차 : [50%] GameFramework 제작          --> CookieRun, DragonFlight 참고중..
    - 4주차 : BackGround ( 종스크롤 ) 구현  
    - 5주차 : Player 구현 
    - 6주차 : 장애물 구현 ( 자동차, 나무 .. )
    - 7주차 : 충돌 처리 
    - 8주차 : UI 배치 
    - 9주차 : 디버깅 및 발표 준비 
-----------------




### 위 이미지 출처
   gif : <https://giphy.com/gifs/crossyroad-hipsterwhale-hipster-whale-why-did-the-chicken-cross-road-W6REsWXWsBZLUkQJAL>
