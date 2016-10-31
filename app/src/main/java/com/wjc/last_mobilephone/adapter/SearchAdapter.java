package com.wjc.last_mobilephone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjc.last_mobilephone.R;
import com.wjc.last_mobilephone.domain.SearchBean;
import com.wjc.last_mobilephone.utils.Utils;

import org.xutils.x;

import java.util.List;


public class SearchAdapter extends BaseAdapter {

    private final Context context;
    private List<SearchBean.ItemsEntity> datas;
    private Utils utils;

    public SearchAdapter(Context context, List<SearchBean.ItemsEntity> mediaItems){
        this.context = context;
        this.datas = mediaItems;
        utils = new Utils();
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
            convertView = View.inflate(context, R.layout.item_net_video,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据位置获得对应的数据
        SearchBean.ItemsEntity mediaItem = datas.get(position);
        x.image().bind(viewHolder.iv_icon, mediaItem.getItemImage().getImgUrl1());
        viewHolder.tv_name.setText(mediaItem.getItemTitle());
        viewHolder.tv_desc.setText(mediaItem.getKeywords());


        return convertView;
    }
    public void setData(List<SearchBean.ItemsEntity> mediaItems) {
        this.datas = mediaItems;
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;

    }
}
