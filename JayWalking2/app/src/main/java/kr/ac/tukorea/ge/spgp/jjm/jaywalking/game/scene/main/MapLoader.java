package kr.ac.tukorea.ge.spgp.jjm.jaywalking.game.scene.main;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp.jjm.framework.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class MapLoader implements IGameObject {
    private final MainScene scene;
    private final Random random = new Random();
    private float platformX, itemX;
    public MapLoader(MainScene scene) {
        this.scene = scene;
    }

    @Override
    public void update(float elapsedSeconds) {
        platformX += MapObject.SPEED * elapsedSeconds;
        while (platformX < Metrics.width) {
            Platform platform = Platform.get(Platform.Type.RANDOM, platformX, 7); //Metrics.height - 2);
            platform.addToScene();
            platformX += platform.getWidth();
        }
        itemX += MapObject.SPEED * elapsedSeconds;
        while (itemX < Metrics.width) {
            int y = random.nextInt(6) + 1;
            int count = 3;
            if (y < 5) {
                Platform platform = Platform.get(Platform.Type.T_3x1, itemX, y+1);
                platform.addToScene();
            } else {
                count = random.nextInt(5) + 1;
            }
            for (int i = 0; i < count; i++) {
                int y2 = y -= random.nextInt(3);
                JellyItem jellyItem = JellyItem.get(JellyItem.RANDOM, itemX, y2);
                jellyItem.addToScene();
                itemX += jellyItem.getWidth();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
