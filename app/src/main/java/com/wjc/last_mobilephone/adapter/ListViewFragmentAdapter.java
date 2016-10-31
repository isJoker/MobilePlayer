package com.wjc.last_mobilephone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wjc.last_mobilephone.R;
import com.wjc.last_mobilephone.domain.ListViewBean;
import com.wjc.last_mobilephone.utils.Utils;

import org.xutils.x;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;



public class ListViewFragmentAdapter extends BaseAdapter {
    private static final String TAG = ListViewFragmentAdapter.class.getSimpleName();
    private Context context;
    private List<ListViewBean.ListBean> list;
    private Utils utils;
    /**
     * 视频
     */
    private static final int TYPE_VIDEO = 0;

    /**
     * 图片
     */
    private static final int TYPE_IMAGE = 1;

    /**
     * 文字
     */
    private static final int TYPE_TEXT = 2;

    /**
     * GIF图片
     */
    private static final int TYPE_GIF = 3;


    /**
     * 软件推广
     */
    private static final int TYPE_AD = 4;

    public ListViewFragmentAdapter(Context context, List<ListViewBean.ListBean> list) {
        this.context = context;
        this.list = list;
        utils = new Utils();
    }


    //返回总类型数据
    @Override
    public int getViewTypeCount() {
        return 5;
    }

    /**
     * 当前item是什么类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int itemViewType = -1;
        //根据位置，从列表中得到一个数据对象
        ListViewBean.ListBean listBean = list.get(position);
        String type = listBean.getType();//得到类型
        Log.e(TAG, "type===" + type);
        if ("video".equals(type)) {
            itemViewType = TYPE_VIDEO;
        } else if ("image".equals(type)) {
            itemViewType = TYPE_IMAGE;
        } else if ("text".equals(type)) {
            itemViewType = TYPE_TEXT;
        } else if ("gif".equals(type)) {
            itemViewType = TYPE_GIF;
        } else {
            itemViewType = TYPE_AD;//广播
        }
        return itemViewType;
    }


    @Override
    public int getCount() {
        return list.size();
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
        //初始化item布局
        int itemViewType = getItemViewType(position);//更加位置得到对应的类型
        ViewHolder viewHolder;
        if (convertView == null) {
            //创建ViewHolder
            viewHolder = new ViewHolder();
            //根据不同的类型加载不同的布局
            convertView = initView(convertView, itemViewType, viewHolder);
            //初始化公共部分
            initCommonView(convertView, itemViewType, viewHolder);
            //设置tag
            convertView.setTag(viewHolder);
        } else {
            //getTag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindData(position, itemViewType, viewHolder);

        return convertView;
    }

    private View initView(View convertView, int itemViewType, ViewHolder viewHolder) {
        switch (itemViewType) {
            case TYPE_VIDEO://视频
                convertView = View.inflate(context, R.layout.all_video_item, null);
                //在这里实例化特有的
                viewHolder.tv_play_nums = (TextView) convertView.findViewById(R.id.tv_play_nums);
                viewHolder.tv_video_duration = (TextView) convertView.findViewById(R.id.tv_video_duration);
                viewHolder.iv_commant = (ImageView) convertView.findViewById(R.id.iv_commant);
                viewHolder.tv_commant_context = (TextView) convertView.findViewById(R.id.tv_commant_context);
                viewHolder.jcv_videoplayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.jcv_videoplayer);
                break;
            case TYPE_IMAGE://图片
                convertView = View.inflate(context, R.layout.all_image_item, null);
                viewHolder.iv_image_icon = (ImageView) convertView.findViewById(R.id.iv_image_icon);
                break;
            case TYPE_TEXT://文字
                convertView = View.inflate(context, R.layout.all_text_item, null);
                break;
            case TYPE_GIF://gif
                convertView = View.inflate(context, R.layout.all_gif_item, null);
                viewHolder.iv_image_gif = (ImageView) convertView.findViewById(R.id.iv_image_gif);
                break;
            case TYPE_AD://软件广告
                convertView = View.inflate(context, R.layout.all_ad_item, null);
                viewHolder.btn_install = (Button) convertView.findViewById(R.id.btn_install);
                viewHolder.iv_image_icon = (ImageView) convertView.findViewById(R.id.iv_image_icon);
                break;
        }
        return convertView;
    }


    private void bindData(int position, int itemViewType, ViewHolder viewHolder) {
        //根据位置得到数据,绑定数据
        ListViewBean.ListBean listBean = list.get(position);

        switch (itemViewType) {
            case TYPE_VIDEO://视频
                bindData(viewHolder, listBean);
                //第一个参数是视频播放地址，第二个参数是显示封面的地址，第三参数是标题
                boolean setUp = viewHolder.jcv_videoplayer.setUp(
                        listBean.getVideo().getVideo().get(0), JCVideoPlayer.SCREEN_LAYOUT_LIST,
                        "");
                //加载图片
                if (setUp) {
                    ImageLoader.getInstance().displayImage(listBean.getVideo().getThumbnail().get(0),
                            viewHolder.jcv_videoplayer.thumbImageView);
                }
                viewHolder.tv_play_nums.setText(listBean.getVideo().getPlaycount() + "次播放");
                viewHolder.tv_video_duration.setText(utils.stringForTime(listBean.getVideo().getDuration() * 1000) + "");

                break;
            case TYPE_IMAGE://图片
                bindData(viewHolder, listBean);
                viewHolder.iv_image_icon.setImageResource(R.drawable.bg_item);
//                int  height = listBean.getImage().getImage().getHeight()<= DensityUtil.getScreenHeight()*0.75?listBean.getImage().getImage().getHeight(): (int) (DensityUtil.getScreenHeight() * 0.75);
//
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.getScreenWidth(),height);
//                viewHolder.iv_image_icon.setLayoutParams(params);
//                LogUtil.e("listBean.getImage().getImage().getBig().get(0)=="+listBean.getImage().getImage().getSmall().get(0));
                if (listBean.getImage() != null && listBean.getImage() != null && listBean.getImage().getSmall() != null) {
//                    x.image().bind(viewHolder.iv_image_icon, listBean.getImage().getBig().get(0));
                    Glide.with(context).load(listBean.getImage().getDownload_url().get(0)).placeholder(R.drawable.bg_item).error(R.drawable.bg_item).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.iv_image_icon);
                }
                break;
            case TYPE_TEXT://文字
                bindData(viewHolder, listBean);
                break;
            case TYPE_GIF://gif
                bindData(viewHolder, listBean);
                if (listBean.getGif() != null && listBean.getGif() != null && listBean.getGif().getImages() != null) {
                    Glide.with(context).load(listBean.getGif().getImages().get(0)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(viewHolder.iv_image_gif);
                }
                break;
            case TYPE_AD://软件广告
                break;
        }

        //设置文本
        viewHolder.tv_context.setText(listBean.getText()+"_"+listBean.getType());

    }


    /**
     * 绑定公共部分的数据
     * @param viewHolder
     * @param mediaItem
     */

    private void bindData(ViewHolder viewHolder, ListViewBean.ListBean mediaItem) {
        if (mediaItem.getU() != null && mediaItem.getU().getHeader() != null && mediaItem.getU().getHeader().get(0) != null) {
            x.image().bind(viewHolder.iv_headpic, mediaItem.getU().getHeader().get(0));
        }
        if (mediaItem.getU() != null && mediaItem.getU().getName() != null) {
            viewHolder.tv_name.setText(mediaItem.getU().getName() + "");
        }

        viewHolder.tv_time_refresh.setText(mediaItem.getPasstime());

        //设置标签
        List<ListViewBean.ListBean.TagsBean> tagsBeen = mediaItem.getTags();
        if (tagsBeen != null && tagsBeen.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < tagsBeen.size(); i++) {
                buffer.append(tagsBeen.get(i).getName() + " ");
            }
            viewHolder.tv_video_kind_text.setText(buffer.toString());
        }

        //设置点赞，踩,转发
        viewHolder.tv_shenhe_ding_number.setText(mediaItem.getUp());
        viewHolder.tv_shenhe_cai_number.setText(mediaItem.getDown() + "");
        viewHolder.tv_posts_number.setText(mediaItem.getForward() + "");

    }


    /**
     * 初始化公共的控件
     *
     * @param convertView
     * @param itemViewType
     * @param viewHolder
     */
    private void initCommonView(View convertView, int itemViewType, ViewHolder viewHolder) {
        switch (itemViewType) {
            case TYPE_VIDEO://视频
            case TYPE_IMAGE://图片
            case TYPE_TEXT://文字
            case TYPE_GIF://gif
                //加载除开广告部分的公共部分视图
                //user info
                viewHolder.iv_headpic = (ImageView) convertView.findViewById(R.id.iv_headpic);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                viewHolder.tv_time_refresh = (TextView) convertView.findViewById(R.id.tv_time_refresh);
                viewHolder.iv_right_more = (ImageView) convertView.findViewById(R.id.iv_right_more);
                //bottom
                viewHolder.iv_video_kind = (ImageView) convertView.findViewById(R.id.iv_video_kind);
                viewHolder.tv_video_kind_text = (TextView) convertView.findViewById(R.id.tv_video_kind_text);
                viewHolder.tv_shenhe_ding_number = (TextView) convertView.findViewById(R.id.tv_shenhe_ding_number);
                viewHolder.tv_shenhe_cai_number = (TextView) convertView.findViewById(R.id.tv_shenhe_cai_number);
                viewHolder.tv_posts_number = (TextView) convertView.findViewById(R.id.tv_posts_number);
                viewHolder.ll_download = (LinearLayout) convertView.findViewById(R.id.ll_download);

                break;
        }


        //中间公共部分 -所有的都有
        viewHolder.tv_context = (TextView) convertView.findViewById(R.id.tv_context);
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setData(List<ListViewBean.ListBean> list) {
        this.list = list;
    }


    static class ViewHolder {
        //user_info
        ImageView iv_headpic;
        TextView tv_name;
        TextView tv_time_refresh;
        ImageView iv_right_more;
        //bottom
        ImageView iv_video_kind;
        TextView tv_video_kind_text;
        TextView tv_shenhe_ding_number;
        TextView tv_shenhe_cai_number;
        TextView tv_posts_number;
        LinearLayout ll_download;

        //中间公共部分 -所有的都有
        TextView tv_context;


        //Video
        TextView tv_play_nums;
        TextView tv_video_duration;
        ImageView iv_commant;
        TextView tv_commant_context;
        JCVideoPlayerStandard jcv_videoplayer;

        //Image
        ImageView iv_image_icon;

        //Gif
        ImageView iv_image_gif;

        //软件推广
        Button btn_install;
    }

}
