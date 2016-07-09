package com.example.administrator.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;


public class MainActivity extends Activity {

    private final int THREAD_BEGIN = 0;
    private final int THREAD_END = 1;
    private ProgressDialog mDialog;
    private Button btn;
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case THREAD_BEGIN:
                    mDialog.show();
                    break;

                case THREAD_END:
                    mDialog.dismiss();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDialog = new ProgressDialog(MainActivity.this);
        mDialog.setTitle("Point");
        mDialog.setCancelable(false);
        btn = (Button)findViewById(R.id.my_btn);
        btn.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                mDialog.setMessage("Loading...");
                new Thread(new MyThread()).start();
            }
        });
    }

    private void doSomeThing(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class MyThread implements Runnable{
        @Override
        public void run(){
            Message msg = new Message();
            msg.what = THREAD_BEGIN;
            mHandler.sendMessage(msg);
            doSomeThing();
            msg.what=THREAD_END;
            mHandler.sendEmptyMessage(THREAD_END);
        }
    }
}
