package com.example.imageandvideo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.imageandvideo.util.ActivityCollector;
import com.example.imageandvideo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_CAMERA = 0x001;//拍照
    private static final int REQUEST_CODE_PHOTO = 0x002;//相册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCollector.addActivity(this);

        LinearLayout imageLinearLayout = findViewById(R.id.lin_image);
        LinearLayout videoLinearLayout = findViewById(R.id.lin_video);

        imageLinearLayout.setOnClickListener(this);
        videoLinearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_image:
                Intent imgIntent = new Intent(MainActivity.this, ImagesActivity.class);
                startActivity(imgIntent);
                break;
            case R.id.lin_video:
                Intent videoIntent = new Intent(MainActivity.this,VideoActivity.class);
                startActivity(videoIntent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ActivityCollector.finish();
        return super.onKeyDown(keyCode, event);
    }
}