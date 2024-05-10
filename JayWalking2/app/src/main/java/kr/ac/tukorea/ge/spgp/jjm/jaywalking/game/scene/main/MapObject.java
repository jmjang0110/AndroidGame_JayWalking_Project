package kr.ac.tukorea.ge.spgp.jjm.jaywalking.game.scene.main;

import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Random;

import kr.ac.tukorea.ge.spgp.jjm.framework.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp.jjm.framework.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp.jjm.framework.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;

public class MapObject extends Sprite implements IBoxCollidable, IRecyclable {
    protected MainScene.Layer layer;
    protected MapObject(MainScene.Layer layer) {
        super(0);
        this.layer = layer;
    }

    private static final String TAG = MapObject.class.getSimpleName();
    protected static Random random = new Random();
    public static final float SPEED = -2.0f;

    @Override
    public void update(float elapsedSeconds) {
        float dx = SPEED * elapsedSeconds;
        dstRect.offset(dx, 0);
        if (dstRect.right < 0) {
            //Log.d(TAG, "Removing:" + this);
            removeFromScene();
        }
    }

    @NonNull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + System.identityHashCode(this) + "(" + width + "x" + height + ")";
    }

    public void addToScene() {
        Scene scene = Scene.top();
        if (scene == null) {
            Log.e(TAG, "Scene stack is empty in addToScene() " + this.getClass().getSimpleName());
            return;
        }
        scene.add(layer, this);
    }
    public void removeFromScene() {
        Scene scene = Scene.top();
        if (scene == null) {
            Log.e(TAG, "Scene stack is empty in removeFromScene() " + this.getClass().getSimpleName());
            return;
        }
        scene.remove(layer, this);
    }

    @Override
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {

    }
}
