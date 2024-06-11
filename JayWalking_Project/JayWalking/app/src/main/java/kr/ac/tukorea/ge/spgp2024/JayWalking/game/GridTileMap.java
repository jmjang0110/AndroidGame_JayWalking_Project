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
import android.util.Log;
import android.view.MotionEvent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class GridTileMap implements IGameObject {
    private static final String TAG = GridTileMap.class.getSimpleName();
    private final TileStruct[][] tileMap;
    private final int rows;
    private final int cols;

    private final float tileWidth;
    private final float tileHeight;
    private float scrollX, scrollY;
    private boolean wraps;

    private final Bitmap tileSetBitmap;
    private final Paint gridPaint;

    public MainPlayer mainPlayer;

    // Button-related fields
    private RectF UpbuttonRect;
    private RectF DownbuttonRect;
    private RectF RightButtonRect;
    private RectF LeftButtonRect;

    private Bitmap buttonBitmap;

    public GridTileMap(String tileSetAsset, int[][] tileMap, float tileWidth, float tileHeight) {
        this.tileSetBitmap = loadBitmapAsset(tileSetAsset);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.rows = tileMap.length;
        this.cols = tileMap[0].length;

        // Initialize gridPaint
        gridPaint = new Paint();
        gridPaint.setColor(Color.BLUE);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(0.05f);
        this.tileMap = generateTileMap();

        this.mainPlayer = new MainPlayer("MainPlayer2.png");

        // Initialize buttons
        UpbuttonRect = new RectF(7.5f, 14.5f, 9.f, 16.f);
        DownbuttonRect = new RectF(0.f, 14.5f, 1.5f, 16.f);
        RightButtonRect = new RectF(7.5f, 13.f, 9.f, 14.5f);
        LeftButtonRect = new RectF(0.f, 13.f, 1.5f, 14.5f);

        buttonBitmap = loadBitmapAsset("Button.png");
    }

    private TileStruct[][] generateTileMap() {
        TileStruct[][] map = new TileStruct[rows][cols];
        Random random = new Random();

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
                map[y][x].TileWH = new Vector2(tileWidth, tileHeight);
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
        mainPlayer.update(elapsedSeconds);
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

                tileMap[y][x].draw(canvas, this.tileSetBitmap);

                canvas.drawLine(drawX, drawY, drawX + tileWidth, drawY, gridPaint);
                canvas.drawLine(drawX, drawY, drawX, drawY + tileHeight, gridPaint);
                canvas.drawLine(drawX + tileWidth, drawY, drawX + tileWidth, drawY + tileHeight, gridPaint);
                canvas.drawLine(drawX, drawY + tileHeight, drawX + tileWidth, drawY + tileHeight, gridPaint);
            }
        }

        this.mainPlayer.draw(canvas);

        // Draw buttons using the bitmap
        Rect srcRect = new Rect(800, 0, 1600, 781);
        canvas.drawBitmap(buttonBitmap, srcRect, UpbuttonRect, null);
        srcRect = new Rect(800, 781, 1600, 1562);
        canvas.drawBitmap(buttonBitmap, srcRect, DownbuttonRect, null);
        srcRect = new Rect(0, 781, 800, 1562);
        canvas.drawBitmap(buttonBitmap, srcRect, LeftButtonRect, null);
        srcRect = new Rect(1600, 781, 2400, 1562);
        canvas.drawBitmap(buttonBitmap, srcRect, RightButtonRect, null);
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

        int W = ImageWidth / (int) Metrics.width;
        int H = ImageHeight / (int) Metrics.height;

        int row = tileNo / (int) Metrics.width;
        int col = tileNo % (int) Metrics.height;

        rect.set(col * W, row * H, (col + 1) * W, (row + 1) * H);
        return rect;
    }

    public boolean onTouch(MotionEvent event) {
        float[] pts = Metrics.fromScreen(event.getX(), event.getY());
        if (UpbuttonRect.contains(pts[0], pts[1])) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if ((int)mainPlayer.IndexFromTileMap.y - 1 >= 0) {
                    TileStruct.TileType type = tileMap[(int) mainPlayer.IndexFromTileMap.y - 1][(int) mainPlayer.IndexFromTileMap.x].tp;
                    if (type != TileStruct.TileType.OBSTRUCT) {
                        mainPlayer.decrementIndexY();
                    }
                }
                return true;
            }
        }
        if (DownbuttonRect.contains(pts[0], pts[1])) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if ((int)mainPlayer.IndexFromTileMap.y + 1 < rows) {
                    TileStruct.TileType type = tileMap[(int)mainPlayer.IndexFromTileMap.y + 1][(int)mainPlayer.IndexFromTileMap.x].tp;
                    if (type != TileStruct.TileType.OBSTRUCT) {
                        mainPlayer.incrementIndexY(rows);
                    }
                }

                return true;
            }
        }
        if (RightButtonRect.contains(pts[0], pts[1])) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if ((int)mainPlayer.IndexFromTileMap.x + 1 < cols) {
                    TileStruct.TileType type = tileMap[(int)mainPlayer.IndexFromTileMap.y][(int)mainPlayer.IndexFromTileMap.x + 1].tp;
                    if (type != TileStruct.TileType.OBSTRUCT) {
                        mainPlayer.incrementIndexX(cols);
                    }
                }

                return true;
            }
        }
        if (LeftButtonRect.contains(pts[0], pts[1])) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if ((int)mainPlayer.IndexFromTileMap.x - 1 >= 0) {
                    TileStruct.TileType type = tileMap[(int)mainPlayer.IndexFromTileMap.y][(int)mainPlayer.IndexFromTileMap.x - 1].tp;
                    if (type != TileStruct.TileType.OBSTRUCT) {
                        mainPlayer.decrementIndexX();
                    }
                }

                return true;
            }
        }
        return this.mainPlayer.onTouch(event);
    }

}
