package kr.ac.tukorea.ge.spgp2024.framework.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.map.Converter;
import kr.ac.tukorea.ge.spgp2024.framework.map.Layer;
import kr.ac.tukorea.ge.spgp2024.framework.map.TiledMap;
import kr.ac.tukorea.ge.spgp2024.framework.map.Tileset;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class TiledBackground implements IGameObject {
    private static final String TAG = TiledBackground.class.getSimpleName();
    protected final TiledMap map;
    protected final String assetPath;
    protected Tileset tileset;
    protected Layer layer;
    protected Bitmap bitmap;
    protected final Rect srcRect = new Rect();
    protected final RectF dstRect = new RectF();
    protected float tileWidth, tileHeight;
    protected float scrollX, scrollY;

    public boolean doesWrap() {
        return wraps;
    }

    public void setWraps(boolean wraps) {
        this.wraps = wraps;
    }

    protected boolean wraps;

    public TiledBackground(String assetFilename, float tileWidth, float tileHeight) {
        map = loadMap(assetFilename);
        assetPath = getDirectory(assetFilename);
        setActiveLayer(0);
        setActiveTileset(0);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public void scrollTo(float x, float y) {
        scrollX = x;
        scrollY = y;
    }

    public void setTileSize(float width, float height) {
        this.tileWidth = width;
        this.tileHeight = height;
    }

    public void setActiveTileset(int index) {
        tileset = map.getTilesets()[index];
        String file = assetPath + tileset.getImage();
        bitmap = loadBitmapAsset(file);
    }

    public Tileset getActiveTileset() {
        return tileset;
    }


    public void setActiveLayer(int index) {
        layer = map.getLayers()[index];
    }

    public Layer getActiveLayer() {
        return layer;
    }

    public float getFullWidth() {
        return map.getWidth() * tileWidth;
    }
    public float getFullHeight() {
        return map.getHeight() * tileHeight;
    }
    private static String getDirectory(String assetFilename) {
        int slash = assetFilename.lastIndexOf('/');
        if (slash < 0) {
            return "./";
        }
        return assetFilename.substring(0, slash + 1);
    }

    float speed = 1.0f;
    @Override
    public void update(float elapsedSeconds) {
    }

    @Override
    public void draw(Canvas canvas) {
        float scroll_x = scrollX, scroll_y = scrollY;
        if (wraps) {
            float fullWidth = map.getWidth() * tileWidth;
            scroll_x %= fullWidth;
            if (scroll_x < 0) scroll_x += fullWidth;

            float fullHeight = map.getHeight() * tileHeight;
            scroll_y %= fullHeight;
            if (scroll_y < 0) scroll_y += fullHeight;
        }
        int layer_width = (int) layer.getWidth();
        int layer_height = (int) layer.getHeight();
        float start_dx = -(scroll_x % tileWidth);
        int start_sx = (int) (scroll_x / tileWidth);
        float start_dy = -(scroll_y % tileHeight);
        int sy = (int) (scroll_y / tileHeight);
        float dy = start_dy;
        while (dy < Metrics.height) {
            int sx = start_sx;
            float dx = start_dx;
            while (dx < Metrics.width) {
                int tileNo = layer.tileAt(sx, sy);
                if (tileNo >= 0) {
                    tileset.getRect(srcRect, tileNo);
                    dstRect.set(dx, dy, dx + tileWidth, dy + tileHeight);
                    canvas.drawBitmap(bitmap, srcRect, dstRect, null);
                }

                sx = (sx + 1) % layer_width;
                dx += tileWidth;
            }
            sy = (sy + 1) % layer_height;
            dy += tileHeight;
        }
    }
    private TiledMap loadMap(String fileName) {
        try {
            String json = loadAssetAsString(fileName);
            return Converter.fromJsonString(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String loadAssetAsString(String fileName) throws IOException {
        InputStream inputStream = GameActivity.activity.getAssets().open(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        reader.close();
        inputStream.close();
        return builder.toString();
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
