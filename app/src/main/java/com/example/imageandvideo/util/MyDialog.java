package com.example.imageandvideo.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;

import com.example.imageandvideo.R;

/**
 * 对话框实现类
 * @author admin
 *
 */
public class MyDialog extends Dialog implements OnClickListener {

    private OnButtonClickListener onButtonClickListener;

    public MyDialog(Context context) {
        super(context, R.style.myDialog);

        //初始化布局
        setContentView(R.layout.popup);
        Window dialogWindow = getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.BOTTOM);

        findViewById(R.id.btn_mCamera).setOnClickListener(this);
        findViewById(R.id.btn_mPhoto).setOnClickListener(this);
        findViewById(R.id.btn_mCancle).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_mCamera:
                onButtonClickListener.camera();
                break;
            case R.id.btn_mPhoto:
                onButtonClickListener.photo();
                break;
            case R.id.btn_mCancle:
                onButtonClickListener.cancel();
                break;

            default:
                break;
        }
    }
    /**
     * 按钮的监听器
     * @author Orathee
     * @date 2014年3月20日 下午4:28:39
     */
    public interface OnButtonClickListener{
        void camera();
        void photo();
        void cancel();
    }

    public OnButtonClickListener getOnButtonClickListener() {
        return onButtonClickListener;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

}