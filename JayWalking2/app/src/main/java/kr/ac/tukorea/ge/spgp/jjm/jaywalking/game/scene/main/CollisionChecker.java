package kr.ac.tukorea.ge.spgp.jjm.jaywalking.game.scene.main;

import android.graphics.Canvas;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp.jjm.framework.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2024.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private final MainScene scene;
    private final Player player;

    public CollisionChecker(MainScene scene, Player player) {
        this.scene = scene;
        this.player = player;
    }

    @Override
    public void update(float elapsedSeconds) {
        ArrayList<IGameObject> items = scene.objectsAt(MainScene.Layer.item);
        for (int i = items.size() - 1; i >= 0; i--) {
            IGameObject gobj = items.get(i);
            if (!(gobj instanceof JellyItem)) {
                continue;
            }
            JellyItem item = (JellyItem) gobj;
            if (CollisionHelper.collides(player, item)) {
                scene.remove(MainScene.Layer.item, gobj);
                Sound.playEffect(item.getSoundResId());
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
