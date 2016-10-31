package com.wjc.last_mobilephone.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjc.last_mobilephone.R;
import com.wjc.last_mobilephone.domain.MediaItem;
import com.wjc.last_mobilephone.utils.Utils;
import com.wjc.last_mobilephone.view.MyTextView;

import java.util.ArrayList;

public class VideoFragmentAdapter extends BaseAdapter {

    private final Context context;
    private  ArrayList<MediaItem> datas;
    private Utils utils;
    private boolean isVideo = false;

    public VideoFragmentAdapter(Context context, ArrayList<MediaItem> mediaItems, boolean isVideo){
        this.context = context;
        this.datas = mediaItems;
        utils = new Utils();
        this.isVideo = isVideo;
    }
    @Override
    public int getCount() {
        if(datas != null && datas.size() >0){
            return datas.size();
        }else {
            return  0;
        }

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_video_fragment,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (MyTextView ) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置得到数据
        MediaItem mediaItem = datas.get(position);
        viewHolder.tv_name.setText(mediaItem.getName());
        viewHolder.tv_time.setText(utils.stringForTime((int) mediaItem.getDuration()));
        viewHolder.tv_size.setText(Formatter.formatFileSize(context,mediaItem.getSize()));

        if(!isVideo){
            //音频
            viewHolder.iv_icon.setImageResource(R.drawable.music_default_bg);
        }

        return convertView;
    }

    public void setData(ArrayList<MediaItem> mediaItems) {
        this.datas = mediaItems;
    }


    static class ViewHolder{
        ImageView iv_icon;
        MyTextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }
}
