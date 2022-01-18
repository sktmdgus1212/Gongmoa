package com.example.gongmoa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_intro);

        IntroThread introThread = new IntroThread(handler);
        introThread.start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 1){
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
