package ask.piyush.findnearest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import ask.piyush.findnearest.R;

/**
 * Created by piyush on 17/8/15.
 */
public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIMER = 2500;
    private ImageView splashImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        splashImage = (ImageView) findViewById(R.id.splash_image);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMER);
    }
}
