package com.wjc.last_mobilephone.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.wjc.last_mobilephone.R;
import com.wjc.last_mobilephone.activity.ShowImageAndGifActivity;
import com.wjc.last_mobilephone.adapter.ListViewFragmentAdapter;
import com.wjc.last_mobilephone.domain.ListViewBean;
import com.wjc.last_mobilephone.utils.CacheUtils;
import com.wjc.last_mobilephone.utils.Constants;
import com.wjc.last_mobilephone.utils.LogUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 作用：网络音频
 */
public class ListViewFragment extends BaseFragment {


    private ListView listview;
    private ProgressBar progressbar;
    private TextView tv_nomedia;
    private MaterialRefreshLayout refresh;

    private List<ListViewBean.ListBean> list;

    private ListViewFragmentAdapter myAdapter;

    @Override
    public View initView() {
        Log.e("TAG", "网络视频视图（页面）初始化了...");
        View view = View.inflate(mContext, R.layout.fragment_net_audio, null);
        listview = (ListView) view.findViewById(R.id.listview);
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        tv_nomedia = (TextView) view.findViewById(R.id.tv_nomedia);
        refresh = (MaterialRefreshLayout) view.findViewById(R.id.refresh);

        //设置点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                ListViewBean.ListBean listBean = list.get(position);
                if(listBean !=null ){
                    //3.传递视频列表
                    Intent intent = new Intent(mContext,ShowImageAndGifActivity.class);
                    if(listBean.getType().equals("gif")){
                        String url = listBean.getGif().getImages().get(0);
                        intent.putExtra("url",url);
                        mContext.startActivity(intent);
                    }else if(listBean.getType().equals("image")){
                        String url = listBean.getImage().getBig().get(0);
                        intent.putExtra("url",url);
                        mContext.startActivity(intent);
                    }
                }


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

    @Override
    public void initData() {
        super.initData();
        Log.e("TAG", "网络视频数据初始化了...");

        String saveJson = CacheUtils.getString(mContext, Constants.NET_AUDIO_URL);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        getDataFromNet();
    }

    /**
     * 用xutils联网获取数据
     */
    private void getDataFromNet() {
        RequestParams reques = new RequestParams(Constants.NET_AUDIO_URL);
        x.http().get(reques, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                //把网络音频的URI保存到本地
                CacheUtils.putString(mContext,Constants.NET_AUDIO_URL,result);
                LogUtil.e("onSuccess==" + result);
                processData(result);
                //结束刷新
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

        list = parsedJson(json);

        if(list != null && list.size() >0){
            //有视频
            tv_nomedia.setVisibility(View.GONE);
            //设置适配器
            myAdapter = new ListViewFragmentAdapter(mContext, list);
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

        list.addAll(parsedJson(json));//添加到原来的集合
        myAdapter.setData(list);//重新把数据设置到适配器
        myAdapter.notifyDataSetChanged();//刷新

    }


    /**
     * 解析json数据
     * @param json
     * @return
     */
    private List<ListViewBean.ListBean>  parsedJson(String json) {
        ListViewBean listViewBean = new Gson().fromJson(json,ListViewBean.class);
        return listViewBean.getList();
    }
}
