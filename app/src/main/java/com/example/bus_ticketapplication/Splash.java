package com.example.bus_ticketapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    private static int SPASH= 3000;
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView name,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom);
        image=(ImageView) findViewById(R.id.twoflyingdoves);
        name=(TextView) findViewById(R.id.text);
        slogan=(TextView) findViewById(R.id.slogan);
        image.setAnimation(topAnim);
        name.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this, LoginUser.class);
                startActivity(intent);
                finish();
            }
        }, SPASH);

    }
}
