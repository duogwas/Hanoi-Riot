package fithou.duogwas.hanoi_riot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import fithou.duogwas.hanoi_riot.R;

public class SplashScreen extends AppCompatActivity {
    private static int Splash_screen = 5000;
    Animation top_ani, bottom_ani;
    ImageView img_logo;
    TextView tv_hnriot, tv_quanly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        AnhXa();
        ConnectAni();
        NextScreen();
    }

    private void AnhXa() {
        img_logo = findViewById(R.id.img_logo);
        tv_hnriot = findViewById(R.id.tv_hnriot);
        tv_quanly = findViewById(R.id.tv_quanly);
        top_ani = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottom_ani = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
    }

    private void ConnectAni() {
        img_logo.setAnimation(top_ani);
        tv_hnriot.setAnimation(bottom_ani);
        tv_quanly.setAnimation(bottom_ani);
    }

    private void NextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,DangNhap.class);
                startActivity(intent);
                finish();
            }
        },Splash_screen);
    }
}