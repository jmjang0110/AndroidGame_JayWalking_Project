package kr.ac.tukorea.ge.spgp2024.JayWalking.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class TileStruct {

    public enum TileType
    {
        WATER, ROAD, OBSTRUCT, PLAYER, CAR
    }

    public TileType tp; // 타일 타입
    public Vector2 Pos; // 타일 위치 ( 화면에서 )
    public Vector2 TileWH; // tile width(x), tile height(y)
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
    public void draw(Canvas canvas, Bitmap bmp) {
        Rect srcRect = new Rect();
        RectF dstRect = new RectF();
        getTileRect(bmp, srcRect);

        dstRect.set(this.Pos.x, this.Pos.y, this.Pos.x + TileWH.x , this.Pos.y + TileWH.y);
        canvas.drawBitmap(bmp, srcRect, dstRect, null);

    }


    private Rect getTileRect(Bitmap bmp, Rect rect) {
        int ImageWidth = bmp.getWidth();
        int ImageHeight = bmp.getHeight();

        // 타일셋에서 한 타일의 가로와 세로 길이를 계산합니다.
        int W = ImageWidth / (int) Metrics.width;
        int H = ImageHeight / (int) Metrics.height;

        // 타일 번호로부터 행과 열을 계산합니다.
        int row = this.tp.ordinal() /  (int)Metrics.width;
        int col = this.tp.ordinal() %  (int)Metrics.height;

        // 타일의 srcRect를 설정합니다.
        rect.set(col * (int) W, row * (int) H, (col + 1) * (int) W, (row + 1) * (int) H);
        return rect;
    }
}
