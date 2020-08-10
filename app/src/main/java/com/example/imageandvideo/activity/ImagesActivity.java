package com.example.imageandvideo.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.imageandvideo.entity.GridViewItem;
import com.example.imageandvideo.util.ActivityCollector;
import com.example.imageandvideo.util.GirdAdapter;
import com.example.imageandvideo.util.MyDialog;
import com.example.imageandvideo.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ImagesActivity extends AppCompatActivity implements
        MyDialog.OnButtonClickListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private static final int REQUEST_CODE_CAMERA = 0x001;//拍照
    private static final int REQUEST_CODE_PHOTO = 0x002;//相册

    private GridView gridView;
    private List<GridViewItem> datas = new ArrayList<>();
    private GirdAdapter girdAdapter;
    private  boolean isShowDelete = false;
    MyDialog dialog;
    Bitmap bmpAdd;
    TextView tv_del;
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        ActivityCollector.addActivity(this);

        tv_del = findViewById(R.id.tv_del);
        back = findViewById(R.id.back);

        bmpAdd = BitmapFactory.decodeResource(getResources(),
                R.drawable.add); // 加号
        datas.add(new GridViewItem("",bmpAdd));

        gridView = findViewById(R.id.gridView);
        girdAdapter = new GirdAdapter(this,datas);
        gridView.setAdapter(girdAdapter);

        dialog = new MyDialog(this);
        dialog.setOnButtonClickListener(this);

        gridView.setOnItemClickListener(this);

        tv_del.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_del:
                if (tv_del.getText().equals("編輯")){
                    tv_del.setText("取消");
                }else if (tv_del.getText().equals("取消") ){
                    tv_del.setText("編輯");
                }
                if(isShowDelete)
                {
                    isShowDelete = false;
                    girdAdapter.setmIsShowDelete(isShowDelete);
                    gridView.setAdapter(girdAdapter);
                }else{
                    isShowDelete = true;
                    girdAdapter.setmIsShowDelete(isShowDelete);
                    gridView.setAdapter(girdAdapter);
                }
                break;
            case R.id.back:
                Intent intent = new Intent(ImagesActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == datas.size()-1) { // 点击图片位置为+（最后一张图片）
            // 选择图片
            dialog.show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CAMERA){
            Bundle extras = data.getExtras();
            Bitmap cameraBitmap = (Bitmap) extras.get("data");
            //取得当前日期时间
            String cameraDateStr = getDate();

            datas.remove(datas.size() - 1);
            datas.add(new GridViewItem(cameraDateStr,cameraBitmap));
            datas.add(new GridViewItem("",bmpAdd));
        }else if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_PHOTO){
            ContentResolver resolver = getContentResolver();
            if (data != null) {
                Uri originalUri = data.getData();
                try {

                    // 使用ContentProvider通过URI获取原始图片
                    Bitmap photoBitmap = MediaStore.Images.Media.getBitmap(resolver,originalUri);
                    HashMap<String, Object> photoMap = new HashMap<>();
                    //取得当前日期时间
                    String photoDateStr = getDate();

                    datas.remove(datas.size() - 1);
                    datas.add(new GridViewItem(photoDateStr,photoBitmap));
                    datas.add(new GridViewItem("",bmpAdd));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        girdAdapter = new GirdAdapter(this,datas);
        gridView.setAdapter(girdAdapter);
        girdAdapter.notifyDataSetChanged();
        dialog.dismiss();
    }

    @Override
    public void camera() {
        // 选择照相图片
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
        //这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
        if(takePhotoIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePhotoIntent,REQUEST_CODE_CAMERA);//启动相机
        }
    }

    @Override
    public void photo() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, REQUEST_CODE_PHOTO);
    }

    @Override
    public void cancel() {
        dialog.cancel();
    }

    //获取当前日期(作为图片名称)
    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strDate = simpleDateFormat.format(curDate);
        return  strDate;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent myIntent = new Intent();
            myIntent = new Intent(ImagesActivity.this, MainActivity.class);
            startActivity(myIntent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
