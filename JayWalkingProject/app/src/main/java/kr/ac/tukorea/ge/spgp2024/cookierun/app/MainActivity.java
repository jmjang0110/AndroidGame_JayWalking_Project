package kr.ac.tukorea.ge.spgp2024.cookierun.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp2024.cookierun.BuildConfig;
import kr.ac.tukorea.ge.spgp2024.cookierun.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (BuildConfig.DEBUG) {
            startActivity(new Intent(this, CookieRunActivity.class));
        }
    }
    public void onBtnStartGame(View view) {
        startActivity(new Intent(this, CookieRunActivity.class));
    }
}