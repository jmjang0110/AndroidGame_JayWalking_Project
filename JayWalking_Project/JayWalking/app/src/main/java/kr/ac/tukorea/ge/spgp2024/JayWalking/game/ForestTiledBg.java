package kr.ac.tukorea.ge.spgp2024.JayWalking.game;

import kr.ac.tukorea.ge.spgp2024.framework.objects.TiledBackground;

public class ForestTiledBg extends TiledBackground {
    private static final float TILE_SIZE = 9.0f / 16;
    private static final float SPEED = 0.2f;
    public ForestTiledBg() {
        super("bg_map/earth.tmj", TILE_SIZE, TILE_SIZE);
        setWraps(true);
    }

    @Override
    public void update(float elapsedSeconds) {
        scrollY += -SPEED * elapsedSeconds;
    }
}
