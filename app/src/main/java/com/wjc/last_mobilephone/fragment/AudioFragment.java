package com.wjc.last_mobilephone.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wjc.last_mobilephone.R;
import com.wjc.last_mobilephone.activity.AudioPlayerActivity;
import com.wjc.last_mobilephone.adapter.VideoFragmentAdapter;
import com.wjc.last_mobilephone.domain.MediaItem;

import java.util.ArrayList;

/**
 * 作用：本地音频
 */
public class AudioFragment extends BaseFragment {


    private ListView listview;
    private TextView textView;
    private ArrayList<MediaItem> mediaItems;
    private VideoFragmentAdapter adapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(mediaItems != null &&mediaItems.size() >0 ){
                //有数据
                textView.setVisibility(View.GONE);
                adapter = new VideoFragmentAdapter(mContext,mediaItems,false);
                listview.setAdapter(adapter);
//                adapter.setData(mediaItems);
//                adapter.notifyDataSetChanged();//getCount()-->getView重新执行
            }else{
                //没有数据
                textView.setText("没有找到音频文件...");
                textView.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public View initView() {
        Log.e("TAG","本地视频视图（页面）初始化了...");
        View view = View.inflate(mContext, R.layout.fragment_video,null);
        listview = (ListView) view.findViewById(R.id.listview);
        textView = (TextView) view.findViewById(R.id.tv_nomedia);

        //设置item的点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaItem mediaItem = mediaItems.get(position);

                //1.使用系统安装了的其他播放播放-会把所有的调起来
//                Intent intent = new Intent();
//                intent.setDataAndType(Uri.parse(mediaItem.getData()),"video/*");
//                mContext.startActivity(intent);
                //2.使用自己写的播放器播放
//                Intent intent = new Intent(mContext,SystemPlayerActivity.class);
//                intent.setDataAndType(Uri.parse(mediaItem.getData()),"video/*");
//                mContext.startActivity(intent);

                //3.传递视频列表
                Intent intent = new Intent(mContext,AudioPlayerActivity.class);

//                //传递列表
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("videolist", mediaItems);
//                intent.putExtras(bundle);

                //传递位置
                intent.putExtra("position",position);//
                mContext.startActivity(intent);


            }
        });

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "本地视频数据初始化了...");
//        textView.setText("本地视频的内容");
        //加载本地所有手机默认支持的视频
        getData();//

//        adapter = new VideoFragmentAdapter(mContext,mediaItems);
//        //设置适配器
//        listview.setAdapter(adapter);
    }

    private void getData() {

        //主线程
        new Thread(){
            @Override
            public void run() {
                super.run();

                //子线程

                mediaItems = new ArrayList<MediaItem>();

                ContentResolver resolver = mContext.getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//视频的uri
                String[] objs = new String[]{
                        MediaStore.Audio.Media.DISPLAY_NAME,//视频文件的名称
                        MediaStore.Audio.Media.SIZE,//文件大小
                        MediaStore.Audio.Media.DURATION,//视频文件的时长
                        MediaStore.Audio.Media.DATA,//视频文件绝对地址
                        MediaStore.Audio.Media.ARTIST//艺术家


                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if(cursor != null){

                    while (cursor.moveToNext()){

                        MediaItem mediaItem = new MediaItem();
                        String name = cursor.getString(0);
                        mediaItem.setName(name);
                        long size = cursor.getLong(1);
                        mediaItem.setSize(size);
                        long duration = cursor.getLong(2);
                        mediaItem.setDuration(duration);
                        String data = cursor.getString(3);
                        mediaItem.setData(data);
                        String artist = cursor.getString(4);
                        mediaItem.setArtist(artist);

                        //添加到集合中
                        mediaItems.add(mediaItem);
                    }


                    cursor.close();


                }

                //发消息
                handler.sendEmptyMessage(0);


            }
        }.start();

        //主线程


    }
}
