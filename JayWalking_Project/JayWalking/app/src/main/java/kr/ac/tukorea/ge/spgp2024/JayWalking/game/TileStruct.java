package kr.ac.tukorea.ge.spgp2024.JayWalking.game;


public class TileStruct {

    public enum TileType
    {
        WATER, ROAD, OBSTRUCT, PLAYER, CAR
    }

    public TileType tp; // 타일 타입
    public Vector2 Pos; // 타일 위치 ( 화면에서 )
    //public Vector2 TileWH; // tile width(x), tile height(y)
    public Vector2 PixelLT; // 타일이 비트맵에서 그려질 위치 ( type 에 따라 달라짐 )
    public Vector2 PixelSize; // LT 에서 쓰일 사이즈

    public TileStruct(TileType type, Vector2 position) {
        this.tp = type;
        this.Pos = position;
        switch (tp) {
            case WATER:
                this.PixelSize = new Vector2(50, 50); // 예시 크기
                this.PixelLT = new Vector2(100, 100); // 예시 위치
                break;
            case ROAD:
                this.PixelSize = new Vector2(60, 60); // 예시 크기
                this.PixelLT = new Vector2(200, 200); // 예시 위치
                break;
            case OBSTRUCT:
                this.PixelSize = new Vector2(70, 70); // 예시 크기
                this.PixelLT = new Vector2(300, 300); // 예시 위치
                break;
            case PLAYER:
                this.PixelSize = new Vector2(80, 80); // 예시 크기
                this.PixelLT = new Vector2(400, 400); // 예시 위치
                break;
            case CAR:
                this.PixelSize = new Vector2(90, 90); // 예시 크기
                this.PixelLT = new Vector2(500, 500); // 예시 위치
                break;
            default:
                // 기본적으로 (0, 0) 크기와 위치로 설정
                this.PixelSize = new Vector2(0, 0);
                this.PixelLT = new Vector2(0, 0);
                break;
        }
    }


}
