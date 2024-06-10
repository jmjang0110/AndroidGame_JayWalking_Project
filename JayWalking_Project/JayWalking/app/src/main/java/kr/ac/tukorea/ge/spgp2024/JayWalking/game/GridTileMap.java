package kr.ac.tukorea.ge.spgp2024.JayWalking.game;

import kr.ac.tukorea.ge.spgp2024.framework.map.TiledMap;
import kr.ac.tukorea.ge.spgp2024.framework.objects.TiledBackground;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint;
import android.media.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class GridTileMap implements IGameObject {
    private static final String TAG = GridTileMap.class.getSimpleName();
    private final TileStruct[][] tileMap;
    private final int rows;
    private final int cols;

    private final Rect srcRect = new Rect();
    private final RectF dstRect = new RectF();
    private final float tileWidth;
    private final float tileHeight;
    private float scrollX, scrollY;
    private boolean wraps;

    private final Bitmap tileSetBitmap;
    private final Paint gridPaint;


    public GridTileMap(String tileSetAsset, int[][] tileMap, float tileWidth, float tileHeight) {
        this.tileSetBitmap = loadBitmapAsset(tileSetAsset);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.rows = tileMap.length;
        this.cols = tileMap[0].length;

        // Initialize gridPaint
        gridPaint = new Paint();
        gridPaint.setColor(Color.BLUE); // Grid line color (white in this example)
        gridPaint.setStyle(Paint.Style.STROKE); // Drawing style: only stroke without filling
        gridPaint.setStrokeWidth(0.05f); // Grid line thickness (2 pixels in this example)
        this.tileMap = generateTileMap(); // Generate a new 10x20 tile map
    }

    // generateTileMap 메서드는 10x20 크기의 랜덤 타일 맵을 생성하는 보조 메서드입니다.
    private TileStruct[][] generateTileMap() {
        TileStruct[][] map = new TileStruct[rows][cols];
        Random random = new Random();
        int numTiles = (tileSetBitmap.getWidth() / (int) tileWidth) * (tileSetBitmap.getHeight() / (int) tileHeight);

        float scroll_x = scrollX, scroll_y = scrollY;
        if (wraps) {
            scroll_x %= cols * tileWidth;
            if (scroll_x < 0) scroll_x += cols * tileWidth;
            scroll_y %= rows * tileHeight;
            if (scroll_y < 0) scroll_y += rows * tileHeight;
        }

        int startCol = (int) (scroll_x / tileWidth);
        float startX = -(scroll_x % tileWidth);
        int startRow = (int) (scroll_y / tileHeight);
        float startY = -(scroll_y % tileHeight);

        for (int y = 0; y < rows; y++) {
            float drawY = startY + (y - startRow) * tileHeight;
            for (int x = 0; x < cols; x++) {
                random = new Random();
                TileStruct.TileType[] tileTypes = TileStruct.TileType.values();
                int randomIndex = random.nextInt(tileTypes.length);


                float drawX = startX + (x - startCol) * tileWidth;
                Vector2 Position = new Vector2(drawX, drawY);
                map[y][x] = new TileStruct(tileTypes[randomIndex], Position);

            }
        }

        return map;
    }
    public void scrollTo(float x, float y) {
        scrollX = x;
        scrollY = y;
    }

    public void setWraps(boolean wraps) {
        this.wraps = wraps;
    }

    @Override
    public void update(float elapsedSeconds) {
        // Update logic if needed
    }


    @Override
    public void draw(Canvas canvas) {
        float scroll_x = scrollX, scroll_y = scrollY;
        if (wraps) {
            scroll_x %= cols * tileWidth;
            if (scroll_x < 0) scroll_x += cols * tileWidth;
            scroll_y %= rows * tileHeight;
            if (scroll_y < 0) scroll_y += rows * tileHeight;
        }

        int startCol = (int) (scroll_x / tileWidth);
        float startX = -(scroll_x % tileWidth);
        int startRow = (int) (scroll_y / tileHeight);
        float startY = -(scroll_y % tileHeight);


        for (int y = startRow; y < rows && startY < Metrics.height; y++) {
            float drawY = startY + (y - startRow) * tileHeight;
            for (int x = startCol; x < cols && startX < Metrics.width; x++) {
                float drawX = startX + (x - startCol) * tileWidth;

                // 타일 비트맵을 그립니다.
                int tileNo = tileMap[y % rows][x % cols].tp.ordinal();
                getTileRect(tileNo, srcRect);

                dstRect.set(drawX, drawY, drawX + tileWidth , drawY + tileHeight);
                canvas.drawBitmap(tileSetBitmap, srcRect, dstRect, null);

                // 그리드 선을 그립니다.
                canvas.drawLine(drawX, drawY, drawX + tileWidth, drawY, gridPaint); // 타일 상단 가로선
                canvas.drawLine(drawX, drawY, drawX, drawY + tileHeight, gridPaint); // 타일 왼쪽 세로선
                canvas.drawLine(drawX + tileWidth, drawY, drawX + tileWidth, drawY + tileHeight, gridPaint); // 타일 오른쪽 세로선
                canvas.drawLine(drawX, drawY + tileHeight, drawX + tileWidth, drawY + tileHeight, gridPaint); // 타일 하단 가로선
            }
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

    private Rect getTileRect(int tileNo, Rect rect) {
        int ImageWidth = tileSetBitmap.getWidth();
        int ImageHeight = tileSetBitmap.getHeight();

        // 타일셋에서 한 타일의 가로와 세로 길이를 계산합니다.
        int W = ImageWidth / (int) Metrics.width;
        int H = ImageHeight / (int) Metrics.height;

        // 타일 번호로부터 행과 열을 계산합니다.
        int row = tileNo /  (int)Metrics.width;
        int col = tileNo %  (int)Metrics.height;

        // 타일의 srcRect를 설정합니다.
        rect.set(col * (int) W, row * (int) H, (col + 1) * (int) W, (row + 1) * (int) H);
        return rect;
    }

}
