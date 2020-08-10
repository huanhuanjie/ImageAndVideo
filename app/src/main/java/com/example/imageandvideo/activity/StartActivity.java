package com.example.imageandvideo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.imageandvideo.R;

/**
 * 启动页面
 */
public class StartActivity extends AppCompatActivity {

    private static final int WHAT_DELAY = 0x11;// 启动页的延时跳转
    private static final int DELAY_TIME = 3000;// 延时时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ImageView start = findViewById(R.id.start);
        Glide.with(this).load(R.drawable.start).into(start);

        // 调用handler的sendEmptyMessageDelayed方法
        handler.sendEmptyMessageDelayed(WHAT_DELAY, DELAY_TIME);
    }

    // 创建Handler对象，处理接收的消息
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case WHAT_DELAY:// 延时3秒跳转
                    goHome();
                    break;
            }
        }
    };

    /**
     * 跳转到主页面
     */
    private void goHome() {
        startActivity(new Intent(StartActivity.this, MainActivity.class));
        finish();// 销毁当前活动界面
    }




}
