package com.example.imageandvideo.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imageandvideo.entity.GridViewItem;
import com.example.imageandvideo.R;

import java.util.List;

public class GirdAdapter extends BaseAdapter {
    private Context context;
    private List<GridViewItem> datas;
    private boolean mIsShowDelete;

    public GirdAdapter(Context context, List<GridViewItem> datas)
    {
        this.context = context;
        this.datas = datas;
    }


    // 子项个数
    @Override
    public int getCount() {
        return datas.size();
    }
    // 返回子项对象
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    // 返回子项下标
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 创建 ViewHolder 类
    class ViewHolder {
        ImageView itemImage,deleteImage;
        TextView itemName;

    }
    // 返回子项视图
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GridViewItem gridViewItem = (GridViewItem) getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.itemImage = view.findViewById(R.id.img);
            viewHolder.itemName = view.findViewById(R.id.tv);
            viewHolder.deleteImage = view.findViewById(R.id.delete_markView);

            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (position < datas.size() - 1){
            viewHolder.itemName.setText(gridViewItem.getImgName());
            viewHolder.itemImage.setImageBitmap(gridViewItem.getImgId());
            viewHolder.deleteImage.setVisibility(mIsShowDelete ? View.VISIBLE : View.GONE);
        }else {
            viewHolder.itemName.setText(gridViewItem.getImgName());
            viewHolder.itemImage.setImageBitmap(gridViewItem.getImgId());
            viewHolder.deleteImage.setVisibility(View.GONE);
        }
        if(mIsShowDelete)
        {
            if (position < datas.size() - 1){
                viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datas.remove(position);
                        setmIsShowDelete(false);
                    }
                });
            }
        }
        return view;
    }

    public void setmIsShowDelete(boolean mIsShowDelete){
        this.mIsShowDelete = mIsShowDelete;
        notifyDataSetChanged();
    }
}