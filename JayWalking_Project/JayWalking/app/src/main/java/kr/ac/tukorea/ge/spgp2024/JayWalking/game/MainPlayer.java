package kr.ac.tukorea.ge.spgp2024.JayWalking.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;

import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class MainPlayer implements IGameObject {
    public  Vector2 Pos;
    public Vector2 IndexFromTileMap = new Vector2(0.f, 0.f);

    public Vector2 PixelLT; // 101.5, 105
    public Vector2 PixelSize;
    public int AnimIndex = 0;

    public Rect srcRect;
    public RectF dstRect;
    public Bitmap Playerbmp;
    private float animTimer = 0;
    private boolean animDirectionUp = true;
    private final float ANIM_INTERVAL = 0.05f; // 0.5 seconds
    private float targetX;
    private RectF targetRect = new RectF();
    @Override
    public void update(float elapsedSeconds) {
        animTimer += elapsedSeconds;
        if (animTimer >= ANIM_INTERVAL) {
            animTimer -= ANIM_INTERVAL;
            if (animDirectionUp) {
                AnimIndex++;
                if (AnimIndex >= 3) {
                    animDirectionUp = false;
                }
            } else {
                AnimIndex--;
                if (AnimIndex <= 0) {
                    animDirectionUp = true;
                }
            }
        }

        // 인덱스에 따른 Pos 값 계산
        Pos.x = IndexFromTileMap.x * (9.f / 6.f);
        Pos.y = IndexFromTileMap.y * (16.f / 8.f);
    }

    @Override
    public void draw(Canvas canvas) {
        if (Playerbmp != null) {
            // srcRect 설정 (비트맵의 전체 영역을 그릴 경우)
            srcRect = new Rect((int)PixelLT.x + ((int)PixelSize.x * this.AnimIndex), (int)PixelLT.y
                    , (int)PixelLT.x + ((int)PixelSize.x * this.AnimIndex) + (int)PixelSize.x
                    , (int)PixelLT.y + (int)PixelSize.y);

            // dstRect 설정 (화면에 그릴 위치와 크기)
            dstRect = new RectF(Pos.x, Pos.y, Pos.x + 1.5f, Pos.y + 2.f);

            // 비트맵을 그리기
            canvas.drawBitmap(Playerbmp, srcRect, dstRect, null);
            // Paint 객체를 생성하여 테두리 색상과 두께 설정
            Paint borderPaint = new Paint();
            borderPaint.setColor(Color.BLACK); // 원하는 테두리 색상 설정
            borderPaint.setStyle(Paint.Style.STROKE); // 테두리만 그리도록 설정
            borderPaint.setStrokeWidth(0.05f); // 원하는 테두리 두께 설정

            // dstRect에 네모 테두리를 그리기
            canvas.drawRect(dstRect, borderPaint);
        }
    }
    public MainPlayer(String bmpFileName){
        Playerbmp = loadBitmapAsset((bmpFileName));
        Pos = new Vector2(0.f, 0.f);
        PixelLT = new Vector2(10.f, 115.f);
        PixelSize = new Vector2(101f, 108.f);

    }
    private Bitmap loadBitmapAsset(String fileName) {
        try {
            InputStream inputStream = GameActivity.activity.getAssets().open(fileName);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float[] pts = Metrics.fromScreen(event.getX(), event.getY());
                Pos.x = pts[0] - 1.5f;
                Pos.y = pts[1] - 1.5f;
                return true;
        }
        return false;

    }

    // Increase indexX by 1
    public void incrementIndexX(int cols) {
        if (IndexFromTileMap.x < cols - 1) {
            IndexFromTileMap.x++;
        }
    }

    // Decrease indexX by 1
    public void decrementIndexX() {
        if (IndexFromTileMap.x > 0) {
            IndexFromTileMap.x--;
        }
    }

    // Increase indexY by 1
    public void incrementIndexY(int rows) {
        if (IndexFromTileMap.y < rows - 1) {
            IndexFromTileMap.y++;
        }
    }

    // Decrease indexY by 1
    public void decrementIndexY() {
        if (IndexFromTileMap.y > 0) {
            IndexFromTileMap.y--;
        }
    }
}
