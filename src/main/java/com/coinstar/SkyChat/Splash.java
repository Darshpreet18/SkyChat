package com.coinstar.fronx;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
ImageView logs;
TextView name,own1,own2;
Animation topAnim,bottomAnim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        logs = findViewById(R.id.logoimg);
        name = findViewById(R.id.logonameimg);
        own1 = findViewById(R.id.ownone);
        own2 = findViewById(R.id.owntwo);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
logs.setAnimation(topAnim);
name.setAnimation(bottomAnim);
own1.setAnimation(bottomAnim);
own2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this,Signup.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

    }
}
