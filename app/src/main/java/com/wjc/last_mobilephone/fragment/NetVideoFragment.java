package com.wjc.last_mobilephone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.wjc.last_mobilephone.R;
import com.wjc.last_mobilephone.activity.SystemPlayerActivity;
import com.wjc.last_mobilephone.domain.MediaItem;
import com.wjc.last_mobilephone.utils.CacheUtils;
import com.wjc.last_mobilephone.utils.Constants;
import com.wjc.last_mobilephone.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

/**
 * 作用：网络视频
 */
public class NetVideoFragment extends BaseFragment {

    private ListView listview;
    private ProgressBar progressbar;
    private TextView tv_nomedia;
    private MaterialRefreshLayout refresh;

    private ArrayList<MediaItem> mediaItems;

    private MyAdapter myAdapter;

    @Override
    public View initView() {
        Log.e("TAG", "网络视频视图（页面）初始化了...");
        View view = View.inflate(mContext, R.layout.fragment_new_video, null);
        listview = (ListView) view.findViewById(R.id.listview);
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        tv_nomedia = (TextView) view.findViewById(R.id.tv_nomedia);
        refresh = (MaterialRefreshLayout) view.findViewById(R.id.refresh);

        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //3.传递视频列表
                Intent intent = new Intent(mContext,SystemPlayerActivity.class);

                //传递列表
                Bundle bundle = new Bundle();
                bundle.putSerializable("videolist", mediaItems);
                intent.putExtras(bundle);

                //传递位置
                intent.putExtra("position",position);
                mContext.startActivity(intent);
            }
        });
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            /**
             * 下拉刷新
             * @param materialRefreshLayout
             */
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {

                getDataFromNet();

            }


            /**
             * 加载更多
             * @param materialRefreshLayout
             */
            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                getMoreDataFromNet();
            }
        });


        return view;
    }

    private void getMoreDataFromNet() {
        RequestParams reques = new RequestParams("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
        x.http().get(reques, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                LogUtil.e("onSuccess==" + result);
                processMoreData(result);
                refresh.finishRefreshLoadMore();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onError==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished==");
            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "网络视频数据初始化了...");

        String saveJson = CacheUtils.getString(mContext,Constants.NET_VIDEO_URL);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams reques = new RequestParams(Constants.NET_VIDEO_URL);
        x.http().get(reques, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                CacheUtils.putString(mContext,Constants.NET_VIDEO_URL,result);
                LogUtil.e("onSuccess==" + result);
                processData(result);
                refresh.finishRefresh();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onError==" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("onCancelled==" + cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("onFinished==");
            }
        });

    }

    /**
     * 解析和绑定数据
     *
     * @param json
     */
    private void processData(String json) {

        mediaItems = parsedJson(json);

        if(mediaItems != null && mediaItems.size() >0){
            //有视频
            tv_nomedia.setVisibility(View.GONE);
            //设置适配器
            myAdapter = new MyAdapter();
            listview.setAdapter(myAdapter);
        }else{
            //没有视频
            tv_nomedia.setVisibility(View.VISIBLE);
        }

        progressbar.setVisibility(View.GONE);

    }

    /**
     * 解析和绑定数据
     *
     * @param json
     */
    private void processMoreData(String json) {

        mediaItems.addAll(parsedJson(json));
        myAdapter.notifyDataSetChanged();

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mediaItems.size();
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
                convertView = View.inflate(mContext,R.layout.item_net_video,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.tv_desc);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置获得对应的数据
            MediaItem mediaItem = mediaItems.get(position);
            x.image().bind(viewHolder.iv_icon,mediaItem.getImageUrl());
            viewHolder.tv_name.setText(mediaItem.getName());
            viewHolder.tv_desc.setText(mediaItem.getDesc());


            return convertView;
        }
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;

    }

    /**
     * 手动解析json数据
     * @param json
     * @return
     */
    private ArrayList<MediaItem> parsedJson(String json) {
        ArrayList<MediaItem> mediaItems = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray trailers = jsonObject.optJSONArray("trailers");
            for (int i = 0; i < trailers.length(); i++) {
                JSONObject item = (JSONObject) trailers.get(i);
                if (item != null) {
                    MediaItem mediaItem = new MediaItem();

                    String name = item.optString("movieName");
                    mediaItem.setName(name);

                    String desc = item.optString("videoTitle");
                    mediaItem.setDesc(desc);

                    String imageUrl = item.optString("coverImg");
                    mediaItem.setImageUrl(imageUrl);

                    String data = item.optString("url");
                    mediaItem.setData(data);

                    mediaItems.add(mediaItem);

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mediaItems;
    }
}
