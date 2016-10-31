package com.atguigu.player.ui.fragment.second.child.childpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.player.R;
import com.atguigu.player.adapter.RecyclerViewFragmentAdapter;
import com.atguigu.player.base.BaseFragment;
import com.atguigu.player.entity.NetAudioBean;
import com.atguigu.player.utils.Constants;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class RecyclerViewPagerFragment extends BaseFragment {
    private static final String ARG_TYPE = "arg_pos";
    public static int TYPE_HOT = 1;
    public static int TYPE_FAV = 2;

    private int mType = TYPE_HOT;

    private RecyclerView recyclerview;
    private ProgressBar progressbar;
    private TextView tv_nomedia;
    private MaterialRefreshLayout refresh;

    private List<NetAudioBean.ListEntity> datas;

    private RecyclerViewFragmentAdapter myAdapter;

    public static RecyclerViewPagerFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        RecyclerViewPagerFragment fragment = new RecyclerViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(ARG_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhihu_fragment_second_pager_recyclerview, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDataFromNet();
    }

    private void initView(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        tv_nomedia = (TextView) view.findViewById(R.id.tv_nomedia);
        refresh = (MaterialRefreshLayout) view.findViewById(R.id.refresh);

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

    }

    private void getMoreDataFromNet() {
        RequestParams reques = new RequestParams(Constants.NET_AUDIO_URL);
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

    private void getDataFromNet() {
        RequestParams reques = new RequestParams(Constants.NET_AUDIO_URL);
        x.http().get(reques, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

//                CacheUtils.putString(mContext,Constants.NET_AUDIO_URL,result);
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

        datas = parsedJson(json);

        if(datas != null && datas.size() >0){
            //有视频
            tv_nomedia.setVisibility(View.GONE);
            //设置适配器
            myAdapter = new RecyclerViewFragmentAdapter(getActivity(),datas);
            recyclerview.setAdapter(myAdapter);
            recyclerview.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(View view) {

                }

                @Override
                public void onChildViewDetachedFromWindow(View view) {
                    if (JCVideoPlayerManager.listener() != null) {
                        JCVideoPlayer videoPlayer = (JCVideoPlayer) JCVideoPlayerManager.listener();
                        if (((ViewGroup) view).indexOfChild(videoPlayer) != -1 && videoPlayer.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                            JCVideoPlayer.releaseAllVideos();
                        }
                    }
                }
            });

            recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
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

        datas.addAll(parsedJson(json));
        myAdapter.setData(datas);
        myAdapter.notifyDataSetChanged();

    }


    /**
     * 手动解析json数据
     * @param json
     * @return
     */
    private List<NetAudioBean.ListEntity>  parsedJson(String json) {

        NetAudioBean netAudioBean = new Gson().fromJson(json,NetAudioBean.class);
        return netAudioBean.getList();
    }
}
