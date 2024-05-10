package kr.ac.tukorea.ge.spgp.jjm.framework.framework.objects;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class HorzScrollBackground extends Sprite {
    private final float speed;
    public HorzScrollBackground(int bitmapResId, float speed) {
        super(bitmapResId);
        this.width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();
        setPosition(Metrics.width / 2, Metrics.height / 2, width, Metrics.height);
        this.speed = speed;
    }
    @Override
    public void update(float elapsedSeconds) {
        this.x += speed * elapsedSeconds; // y 값을 스크롤된 양으로 사용한다
    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        float curr = x % width;
        if (curr > 0) curr -= width;
        while (curr < Metrics.width) {
            dstRect.set(curr, 0, curr + width, Metrics.height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += width;
        }
    }
}
