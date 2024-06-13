package kr.ac.tukorea.ge.spgp2024.JayWalking.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.IOException;
import java.io.InputStream;

import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;

public class Car implements IGameObject {

    public boolean collide;
    public Vector2 PixelLT;
    public Vector2 PixelSize;
    public Rect srcRect;
    public RectF dstRect;

    public Bitmap bmp;

    public float speed = 1.5f;
    public  Vector2 Pos;
    public Vector2 IndexFromTileMap = new Vector2(0.f, 0.f);

    public void SetPos(Vector2 p){ Pos = p; }
    public void SetSpeed(float f){ speed = f; }
    public void SetPosX(float x) { Pos.x = x; }
    public Car(Vector2 pxLT, Vector2 pxSize, String bmpFileName){
        PixelLT = pxLT;
        PixelSize   = pxSize;
        bmp = loadBitmapAsset(bmpFileName);
    }
    @Override
    public void update(float elapsedSeconds) {
        Pos.x += elapsedSeconds * speed;
        if(Pos.x >= 9.f){
            Pos.x = -1.5f;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (bmp != null) {
            // srcRect 설정 (비트맵의 전체 영역을 그릴 경우)
            srcRect = new Rect((int)PixelLT.x, (int)PixelLT.y
                    , (int)PixelLT.x + (int) PixelSize.x
                    , (int) PixelLT.y + (int) PixelSize.y);

            // dstRect 설정 (화면에 그릴 위치와 크기)
            dstRect = new RectF(Pos.x, Pos.y, Pos.x + 1.5f, Pos.y + 2.f);
            // 비트맵을 그리기
            canvas.drawBitmap(bmp, srcRect, dstRect, null);
            // Paint 객체를 생성하여 테두리 색상과 두께 설정
            Paint borderPaint = new Paint();
            if(collide == false)
                borderPaint.setColor(Color.GREEN); // 원하는 테두리 색상 설정
            else
                borderPaint.setColor(Color.RED); // 원하는 테두리 색상 설정

            borderPaint.setStyle(Paint.Style.STROKE); // 테두리만 그리도록 설정
            borderPaint.setStrokeWidth(0.05f); // 원하는 테두리 두께 설정

            // dstRect에 네모 테두리를 그리기
            canvas.drawRect(dstRect, borderPaint);
        }
    }

    private Bitmap loadBitmapAsset(String fileName) {
        try {
            InputStream inputStream = GameActivity.activity.getAssets().open(fileName);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
