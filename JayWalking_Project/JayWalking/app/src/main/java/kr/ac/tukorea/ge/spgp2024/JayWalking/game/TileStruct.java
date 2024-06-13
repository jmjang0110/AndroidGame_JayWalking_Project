package kr.ac.tukorea.ge.spgp2024.JayWalking.game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class TileStruct {

    public enum TileType
    {
        WATER, ROAD, OBSTRUCT, TREE
    }

    public float MaxSpeed = 2.f;
    public float CurSpeed = 1.f;
    public TileType tp; // 타일 타입
    public Vector2 Pos; // 타일 위치 ( 화면에서 )
    public Vector2 TileWH; // tile width(x), tile height(y)
    public Vector2 PixelLT; // 타일이 비트맵에서 그려질 위치 ( type 에 따라 달라짐 )
    public Vector2 PixelSize; // LT 에서 쓰일 사이즈
    // 테두리를 그릴 때 사용할 Paint 객체
    private Paint borderPaint;

    public TileStruct(TileType type, Vector2 position) {
        this.tp = type;
        this.Pos = position;
        UpdateType(tp);
        // 테두리 Paint 객체 초기화
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStrokeWidth(0.05f); // 테두리 선의 두께 조절
    }


    public void update(float elapsedSeconds) {
        this.Pos.y += elapsedSeconds * CurSpeed;

        // 1초에 0.05f씩 증가하도록 설정 (10초에 0.5f)
        float speedIncrement = 0.005f * elapsedSeconds;

        // CurSpeed가 MaxSpeed를 초과하지 않도록 조절
        if (CurSpeed + speedIncrement > MaxSpeed) {
            CurSpeed = MaxSpeed;
        } else {
            CurSpeed += speedIncrement;
        }
    }

    public void UpdatePos(Vector2 pos){
        this.Pos = pos;
    }

    public void UpdateType(TileType type){
        tp = type;
        switch (type) {
            case WATER:
                this.PixelSize = new Vector2(64, 64); // 예시 크기
                this.PixelLT = new Vector2(128, 320); // 예시 위치
                break;
            case ROAD:
                this.PixelSize = new Vector2(600, 320); // 예시 크기
                this.PixelLT = new Vector2(850, 0); // 예시 위치
                break;
            case OBSTRUCT:
                this.PixelSize = new Vector2(64, 64); // 예시 크기
                this.PixelLT = new Vector2(0, 252); // 예시 위치
                break;
            case TREE:
                this.PixelSize = new Vector2(64, 64); // 예시 크기
                this.PixelLT = new Vector2(0, 193); // 예시 위치
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

        srcRect.set((int)this.PixelLT.x, (int)this.PixelLT.y,
                (int)this.PixelLT.x + (int)PixelSize.x , (int)this.PixelLT.y + (int)PixelSize.y);
        dstRect.set(this.Pos.x, this.Pos.y, this.Pos.x + TileWH.x , this.Pos.y + TileWH.y);
        canvas.drawBitmap(bmp, srcRect, dstRect, null);

        // Draw border around the tile

        //canvas.drawRect(dstRect.left, dstRect.top, dstRect.right, dstRect.bottom, borderPaint);
    }
}
