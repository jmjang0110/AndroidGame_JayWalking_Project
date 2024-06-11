package kr.ac.tukorea.ge.spgp2024.JayWalking.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2024.JayWalking.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.VertScrollBackground;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Fighter fighter;
    private final MainPlayer mainPlayer;

    Score score; // package private
    public GridTileMap gridTileMap;


    public int getScore() {
        return score.getScore();
    }

    public enum Layer {
        bg, enemy, bullet, player, ui, controller, COUNT
    }
    public MainScene() {
        //Metrics.setGameSize(16, 16);
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker(this));

        //add(Layer.bg, new VertScrollBackground(R.mipmap.bg_city, 0.2f));
        //add(Layer.bg, new ForestTiledBg());
       // add(Layer.bg, new VertScrollBackground(R.mipmap.clouds, 0.4f));

        this.fighter = new Fighter();
        add(Layer.player, fighter);

        this.score = new Score(R.mipmap.number_24x32, Metrics.width - 0.5f, 0.5f, 0.6f);
        score.setScore(0);
        add(Layer.ui, score);

        // 화면의 폭과 높이를 측정합니다.
        float screenWidth = Metrics.width;
        float screenHeight = Metrics.height;

        int numRows = 8;
        int numCols = 6;

        float tileWidth = screenWidth / numCols;
        float tileHeight = screenHeight / numRows;

        // 타일 맵을 초기화합니다.
        int[][] tileMap = new int[numRows][numCols];
        // 타일 맵에 필요한 데이터를 채웁니다.

        // GridTileMap을 생성하고 추가합니다.
        this.gridTileMap = new GridTileMap("forest_tiles.png", tileMap, tileWidth, tileHeight);
        add(Layer.bg, gridTileMap);

        this.mainPlayer = new MainPlayer("MainPlayer2.png");
        add(Layer.player, mainPlayer);

    }

    public void addScore(int amount) {
        score.add(amount);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return fighter.onTouch(event);
    }
}
