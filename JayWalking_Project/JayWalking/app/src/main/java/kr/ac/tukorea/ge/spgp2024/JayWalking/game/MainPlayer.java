package kr.ac.tukorea.ge.spgp2024.JayWalking.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.IOException;
import java.io.InputStream;

import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;

public class MainPlayer implements IGameObject {
    public  Vector2 Pos;
    public Vector2 IndexFromTileMap;

    public Vector2 PixelLT; // 101.5, 105
    public Vector2 PixelSize;
    public int AnimIndex = 0;

    public Rect srcRect;
    public RectF dstRect;
    public Bitmap Playerbmp;
    private float animTimer = 0;
    private boolean animDirectionUp = true;
    private final float ANIM_INTERVAL = 0.05f; // 0.5 seconds

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
    }

    @Override
    public void draw(Canvas canvas) {
        if (Playerbmp != null) {
            // srcRect 설정 (비트맵의 전체 영역을 그릴 경우)
            srcRect = new Rect((int)PixelLT.x + ((int)PixelSize.x * this.AnimIndex), (int)PixelLT.y
                    , (int)PixelLT.x + ((int)PixelSize.x * this.AnimIndex) + (int)PixelSize.x
                    , (int)PixelLT.y + (int)PixelSize.y);

            // dstRect 설정 (화면에 그릴 위치와 크기)
            dstRect = new RectF(Pos.x, Pos.y, Pos.x + 3.f, Pos.y + 3.f);

            // 비트맵을 그리기
            canvas.drawBitmap(Playerbmp, srcRect, dstRect, null);
        }
    }
    public MainPlayer(String bmpFileName){
        Playerbmp = loadBitmapAsset((bmpFileName));
        Pos = new Vector2(3.f, 3.f);
        PixelLT = new Vector2(0, 105.f);
        PixelSize = new Vector2(102f, 105.f);

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
