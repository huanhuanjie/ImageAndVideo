package com.example.imageandvideo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.PrecomputedTextCompat;

import com.example.imageandvideo.util.ActivityCollector;
import com.example.imageandvideo.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.util.Util;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = "VideoActivity";
    public static final int FULL_PLAYER_Resource = 1;  //大窗口
    public static final int SMALL_PLAYER_Resource = 2;  //小窗口

    LinearLayout linearLayout_head;
    LinearLayout linearLayout_end;

    private PlayerView smallView;
    private PlayerView fullView;

    private ExoPlayer fullExoPlayer;
    private ExoPlayer smallExoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ActivityCollector.addActivity(this);

        linearLayout_head = findViewById(R.id.linearLayout_head);
        linearLayout_end = findViewById(R.id.linearLayout_end);
        ImageView hangup = findViewById(R.id.hangup);
        ImageView answer = findViewById(R.id.answer);

        //创建player
        fullExoPlayer = new SimpleExoPlayer.Builder(this).build();
        smallExoPlayer = new SimpleExoPlayer.Builder(this).build();
        //创建view
        fullView = findViewById(R.id.exoplayerview_activity_video);
        smallView = findViewById(R.id.exoplayerview_activity_video2);

        smallView.setVisibility(View.INVISIBLE);
        linearLayout_end.setVisibility(View.INVISIBLE);

        fullView.setPlayer(fullExoPlayer);

        //加载视频文件
        Uri uriFull = RawResourceDataSource.buildRawResourceUri(R.raw.video1);
        prepareResource(uriFull,FULL_PLAYER_Resource);

        //循环播放
        fullExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
        //启动
        fullExoPlayer.setPlayWhenReady(true);
        answer.setOnClickListener(this);
        hangup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.answer:
                linearLayout_head.setVisibility(View.GONE);
                linearLayout_end.setVisibility(View.VISIBLE);
                ImageView hangup_button = findViewById(R.id.hangup_button);

                smallView.setVisibility(View.VISIBLE);
                smallView.setPlayer(smallExoPlayer);
                smallView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switchView();
                    }
                });

                Uri uriSmall = RawResourceDataSource.buildRawResourceUri(R.raw.video2);
                prepareResource(uriSmall,SMALL_PLAYER_Resource);

                //循环播放
                smallExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
                //启动
                smallExoPlayer.setPlayWhenReady(true);

                hangup_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(VideoActivity.this,MainActivity.class);
                        startActivity(intent);
                        releasePlayer(fullExoPlayer);
                        releasePlayer(smallExoPlayer);
                    }
                });
                break;
            case R.id.hangup:
                Intent intent = new Intent(VideoActivity.this,MainActivity.class);
                startActivity(intent);
                releasePlayer(fullExoPlayer);
                break;
        }
    }

    //准备播放资源
    private void prepareResource(Uri uri,int type){
        DefaultDataSourceFactory factory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,"imageandvideo"));
        if (uri != null){
            ProgressiveMediaSource source = new ProgressiveMediaSource.Factory(factory).createMediaSource(uri);
            if (type == FULL_PLAYER_Resource){
                fullExoPlayer.prepare(source);
            } else {
                smallExoPlayer.prepare(source);
            }
        }
    }

    //切换视频
    private void switchView(){
        //交换两个view的尺寸参数
        ViewGroup.LayoutParams layoutParams = smallView.getLayoutParams();
        smallView.setLayoutParams(fullView.getLayoutParams());
        fullView.setLayoutParams(layoutParams);
        //交换变量
        PlayerView temp = smallView;
        smallView = fullView;
        fullView = temp;
        //清除全屏的点击事件
        fullView.setOnClickListener(null);
        smallView.setOnClickListener(view -> switchView());
        //将smallView置于顶层，bringToFront()方法不起作用，，，
        //parent实际上为relativeLayout
        ViewGroup parent = ((ViewGroup)smallView.getParent());
        //移除之后重新添加可以使smallView在数组最后端，即在组件的最上面
        parent.removeView(smallView);
        parent.addView(smallView);
    }

    //释放ExoPlayer
    public void releasePlayer(ExoPlayer player){
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        releasePlayer(fullExoPlayer);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            linearLayout_head.setVisibility(View.GONE);
            linearLayout_end.setVisibility(View.GONE);
            Intent intent = new Intent(VideoActivity.this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
