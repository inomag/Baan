package com.example.userapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    private TextView intro;
    private CharSequence charSequence;
    private int index;
    long delay = 50;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        intro = findViewById(R.id.intro);

        animateText("RESCUE WITH LOVE AND PEACE SHALL FOLLOW ");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SplashScreen.this, "Splash Done", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SplashScreen.this,UserRegister.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        },3000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            intro.setText(charSequence.subSequence(0,index++));
            if(index<charSequence.length()){
                handler.postDelayed(runnable,delay);
            }
        }
    };

    public void animateText(CharSequence cs){
        charSequence = cs;
        index = 0;
        intro.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }
}