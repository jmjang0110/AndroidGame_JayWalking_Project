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
    public  TileStruct[][] CurTileMap;
    private final TileStruct[][] tileMap;
    private final TileStruct[][] tileMap2;
    private final int rows;
    private final int cols;

    private final float tileWidth;
    private final float tileHeight;
    private float scrollX, scrollY;
    private boolean wraps;

    private final Bitmap tileSetBitmap;
    private final Bitmap RoadBitmap;
    private final Paint gridPaint;

    public MainPlayer mainPlayer;
    public Car[][] NPCCar;
    public Car[][] NPCCar2;
    // Button-related fields
    private RectF UpbuttonRect;
    private RectF DownbuttonRect;
    private RectF RightButtonRect;
    private RectF LeftButtonRect;

    private Bitmap buttonBitmap;

    public GridTileMap(String tileSetAsset, int[][] tileMap, float tileWidth, float tileHeight) {
        this.tileSetBitmap = loadBitmapAsset(tileSetAsset);
        this.RoadBitmap = loadBitmapAsset("Road.png");
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.rows = tileMap.length;
        this.cols = tileMap[0].length;

        // Initialize gridPaint
        gridPaint = new Paint();
        gridPaint.setColor(Color.BLUE);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(0.05f);
        this.tileMap = generateTileMap(new Vector2(0.f, 0.f), 1);
        this.tileMap2   = generateTileMap(new Vector2(0.f, -16.f), 2);
        CurTileMap = this.tileMap;

        this.mainPlayer = new MainPlayer("MainPlayer2.png");

        // Initialize buttons
        UpbuttonRect = new RectF(7.5f - 1.5f, 14.5f - 1.5f, 9.f - 1.5f, 16.f - 1.5f);
        DownbuttonRect = new RectF(7.5f - 1.5f, 14.5f, 9.f - 1.5f, 16.f);
        RightButtonRect = new RectF(7.5f, 14.5f, 9.f, 16.f);
        LeftButtonRect = new RectF(7.5f - 3.f, 14.5f, 9.f -3.f, 16.f);

        buttonBitmap = loadBitmapAsset("Button.png");
    }

    private TileStruct[][] generateTileMap(Vector2 Offset, int tileNum) {
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

        if(tileNum == 1){
            NPCCar = new Car[rows][cols];
            for(int y = 0 ; y < rows; y++){
                float drawY = startY + (y - startRow) * tileHeight;
                for(int x = 0; x < cols ; x++){
                    NPCCar[y][x] = new Car(new Vector2(0.f, 77.f), new Vector2(400.f, 220.f), "Truck.png");
                    float drawX = startX + (x - startCol) * tileWidth;
                    Vector2 Position = new Vector2(drawX, drawY);
                    NPCCar[y][x].Pos = Position.add(Offset);
                }
            }

        }
        else if(tileNum == 2){
            NPCCar2 = new Car[rows][cols];
            for(int y = 0 ; y < rows; ++y){
                float drawY = startY + (y - startRow) * tileHeight;
                for(int x = 0; x < cols ; ++x){
                    NPCCar2[y][x] = new Car(new Vector2(0.f, 77.f), new Vector2(400.f, 220.f), "Truck.png");
                    float drawX = startX + (x - startCol) * tileWidth;
                    Vector2 Position = new Vector2(drawX, drawY);
                    NPCCar2[y][x].Pos = Position.add(Offset);
                }
            }
        }


        for (int y = 0; y < rows; y++) {
            float drawY = startY + (y - startRow) * tileHeight;
            int roadCount = 0;
            for (int x = 0; x < cols; x++) {
                float drawX = startX + (x - startCol) * tileWidth;
                Vector2 Position = new Vector2(drawX, drawY);

                if(tileNum == 1){
                    if(y % 2 == 0){
                        map[y][x] = new TileStruct(TileStruct.TileType.ROAD, Position.add(Offset));
                        map[y][x].TileWH = new Vector2(tileWidth, tileHeight);

                        if(x % 3 == 0){

                            if(y == 0){
                                NPCCar[y][x].Pos.x = x * 2.f;
                            }
                            else if(y == 2){
                                NPCCar[y][x + 1].Pos.x = (x + 1) * 2.f;
                                NPCCar[y][x + 1].speed = 1.5f;
                            }
                            else if(y == 4){
                                NPCCar[y][x + 2].Pos.x = (x + 2) * 2.f;
                                NPCCar[y][x + 2].speed = 2.5f;
                            }
                            else{
                                NPCCar[y][x].Pos.x = x * 2.f;
                            }
                        }

                    }
                    else
                    {
                        random = new Random();
                        TileStruct.TileType[] tileTypes = TileStruct.TileType.values();
                        int randomIndex = random.nextInt(tileTypes.length);

                        if(tileTypes[randomIndex] == TileStruct.TileType.ROAD)
                            roadCount += 1;

                        map[y][x] = new TileStruct(tileTypes[randomIndex], Position.add(Offset));
                        map[y][x].TileWH = new Vector2(tileWidth, tileHeight);
                    }

                }
                else if(tileNum == 2){
                    if(y % 2 == 0){
                        map[y][x] = new TileStruct(TileStruct.TileType.ROAD, Position.add(Offset));
                        map[y][x].TileWH = new Vector2(tileWidth, tileHeight);

                        if(x % 3 == 0){
                            NPCCar2[y][x].Pos = Position.add(Offset);
                            if(x % 3 == 0){
                                NPCCar2[y][x].Pos = Position.add(Offset);
                                if(y == 0){
                                    NPCCar2[y][x].Pos.x = x * 2.f;
                                }
                                else if(y == 2){
                                    NPCCar2[y][x + 1].Pos.x = (x + 1) * 2.f;
                                    NPCCar2[y][x + 2].speed = 3.f;
                                }
                                else if(y == 4){
                                    NPCCar2[y][x + 2].Pos.x = (x + 2) * 2.f;
                                    NPCCar2[y][x + 2].speed = 4.f;
                                }
                                else{
                                    NPCCar2[y][x].Pos.x = x * 2.f;
                                }
                            }
                        }

                    }
                    else
                    {
                        random = new Random();
                        TileStruct.TileType[] tileTypes = TileStruct.TileType.values();
                        int randomIndex = random.nextInt(tileTypes.length);
                        if(tileTypes[randomIndex] == TileStruct.TileType.ROAD)
                            roadCount += 1;

                        map[y][x] = new TileStruct(tileTypes[randomIndex], Position.add(Offset));
                        map[y][x].TileWH = new Vector2(tileWidth, tileHeight);
                    }
                }
            }
            if(roadCount == 0){
                map[y][random.nextInt(5)].tp = TileStruct.TileType.ROAD;
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

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                tileMap[y][x].update(elapsedSeconds);
                tileMap2[y][x].update(elapsedSeconds);

                if(tileMap[y][x].Pos.y >= 16.f){
                    Random random = new Random();
                    TileStruct.TileType[] tileTypes = TileStruct.TileType.values();
                    int randomIndex = random.nextInt(tileTypes.length);

                    tileMap[y][x].Pos.y = -16.f;

                    if(y % 2 == 0){
                        tileMap[y][x].UpdateType(TileStruct.TileType.ROAD);
                    }
                    else {
                        tileMap[y][x].UpdateType(tileTypes[randomIndex]);
                    }
                }
                if(tileMap2[y][x].Pos.y >= 16.f){
                    Random random = new Random();
                    TileStruct.TileType[] tileTypes = TileStruct.TileType.values();
                    int randomIndex = random.nextInt(tileTypes.length);

                    tileMap2[y][x].Pos.y = -16.f;

                    if(y % 2 == 0){
                        tileMap2[y][x].UpdateType(TileStruct.TileType.ROAD);
                    }
                    else
                    {
                        tileMap2[y][x].UpdateType(tileTypes[randomIndex]);
                    }
                }
            }
        }

        for (int y = 0; y < rows; y += 2) {
            for (int x = 0; x < cols; x += 3) {
                NPCCar[y][x].update(elapsedSeconds);
                NPCCar[y][x].Pos.y = tileMap[y][x].Pos.y;

                NPCCar2[y][x].update(elapsedSeconds);
                NPCCar2[y][x].Pos.y = tileMap2[y][x].Pos.y;

            }
        }

        mainPlayer.update(elapsedSeconds); // anim uodate
        mainPlayer.Pos = CurTileMap[(int)mainPlayer.IndexFromTileMap.y][(int)mainPlayer.IndexFromTileMap.x].Pos; // pos update


    }

    @Override
    public void draw(Canvas canvas) {
        // dstRect 설정 (화면에 그릴 위치와 크기)
        Rect src = new Rect(0 ,0, 40, 40);
        RectF dst = new RectF(0.f, 0.f, 9.f, 16.f);
        canvas.drawBitmap(tileSetBitmap, src, dst, null);

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

        int endCol = Math.min(startCol + (int) (Metrics.width / tileWidth) + 2, cols); // Adjust +2 for potential negative startX
        int endRow = Math.min(startRow + (int) (Metrics.height / tileHeight) + 2, rows); // Adjust +2 for potential negative startY

        for (int y = 0; y < endRow; y++) {
            for (int x = 0; x < endCol; x++) {
                if(tileMap[y][x].tp == TileStruct.TileType.ROAD){
                    tileMap[y][x].draw(canvas, this.RoadBitmap);
                }
                else {
                    tileMap[y][x].draw(canvas, this.tileSetBitmap);

                }

                if(tileMap2[y][x].tp == TileStruct.TileType.ROAD){
                    tileMap2[y][x].draw(canvas, this.RoadBitmap);
                }
                else {
                    tileMap2[y][x].draw(canvas, this.tileSetBitmap);

                }
            }
        }

        for (int y = 0; y < endRow; y += 2) {
            for (int x = 0; x < endCol; x += 3) {
                NPCCar[y][x].draw(canvas);
                NPCCar2[y][x].draw(canvas);
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
                    TileStruct.TileType type = CurTileMap[(int) mainPlayer.IndexFromTileMap.y - 1][(int) mainPlayer.IndexFromTileMap.x].tp;
                    if (type != TileStruct.TileType.OBSTRUCT && type != TileStruct.TileType.TREE) {
                        mainPlayer.decrementIndexY();
                    }
                }
                else if((int)mainPlayer.IndexFromTileMap.y == 0){
                    if(CurTileMap == this.tileMap){
                        CurTileMap = this.tileMap2 ;
                        mainPlayer.IndexFromTileMap.y = (float)tileMap2.length - 1;
                    }
                    else if(CurTileMap == this.tileMap2){
                        CurTileMap = this.tileMap;
                        mainPlayer.IndexFromTileMap.y = (float)tileMap2.length - 1;
                    }
                }
                return true;
            }
        }
        if (DownbuttonRect.contains(pts[0], pts[1])) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if ((int)mainPlayer.IndexFromTileMap.y + 1 < rows) {
                    TileStruct.TileType type = CurTileMap[(int)mainPlayer.IndexFromTileMap.y + 1][(int)mainPlayer.IndexFromTileMap.x].tp;
                    if (type != TileStruct.TileType.OBSTRUCT && type != TileStruct.TileType.TREE) {
                        mainPlayer.incrementIndexY(rows);
                    }
                }
                else if((int)mainPlayer.IndexFromTileMap.y == tileMap2.length - 1){
                    if(CurTileMap == this.tileMap){
                        CurTileMap = this.tileMap2 ;
                        mainPlayer.IndexFromTileMap.y = 0.f;
                    }
                    else if(CurTileMap == this.tileMap2){
                        CurTileMap = this.tileMap;
                        mainPlayer.IndexFromTileMap.y = 0.f;
                    }
                }
                return true;
            }
        }
        if (RightButtonRect.contains(pts[0], pts[1])) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if ((int)mainPlayer.IndexFromTileMap.x + 1 < cols) {
                    TileStruct.TileType type = CurTileMap[(int)mainPlayer.IndexFromTileMap.y][(int)mainPlayer.IndexFromTileMap.x + 1].tp;
                    if (type != TileStruct.TileType.OBSTRUCT && type != TileStruct.TileType.TREE) {
                        mainPlayer.incrementIndexX(cols);
                    }
                }

                return true;
            }
        }
        if (LeftButtonRect.contains(pts[0], pts[1])) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if ((int)mainPlayer.IndexFromTileMap.x - 1 >= 0) {
                    TileStruct.TileType type = CurTileMap[(int)mainPlayer.IndexFromTileMap.y][(int)mainPlayer.IndexFromTileMap.x - 1].tp;
                    if (type != TileStruct.TileType.OBSTRUCT && type != TileStruct.TileType.TREE) {
                        mainPlayer.decrementIndexX();
                    }
                }

                return true;
            }
        }
        return this.mainPlayer.onTouch(event);
    }

}
