package com.example.gongmoa;

import android.os.Message;

import android.os.Handler;

public class IntroThread extends Thread{
    private Handler handler;

    public IntroThread(Handler handler){
        this.handler = handler;
    }
    @Override
    public void run(){

        Message msg = new Message();

        try{
            Thread.sleep(3000);
            msg.what = 1;
            handler.sendEmptyMessage(msg.what);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
