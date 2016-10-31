package com.wjc.last_mobilephone.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.wjc.last_mobilephone.R;
import com.wjc.last_mobilephone.adapter.SearchAdapter;
import com.wjc.last_mobilephone.domain.SearchBean;
import com.wjc.last_mobilephone.speech.JsonParser;
import com.wjc.last_mobilephone.utils.Constants;
import com.wjc.last_mobilephone.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SearchActivity extends Activity implements View.OnClickListener {

    private EditText etSearch;
    private ImageView ivVoice;
    private TextView tvSearch;
    private ListView listview;
    private ProgressBar progressbar;
    private TextView tvResult;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private List<SearchBean.ItemsEntity> items;

    private SearchAdapter adapter;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-09-13 14:48:52 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        setContentView(R.layout.activity_search);
        etSearch = (EditText) findViewById(R.id.et_search);
        ivVoice = (ImageView) findViewById(R.id.iv_voice);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        listview = (ListView) findViewById(R.id.listview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        tvResult = (TextView) findViewById(R.id.tv_result);

        tvSearch.setOnClickListener(this);
        ivVoice.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_voice:
                showVoiceDialog();
//                Toast.makeText(SearchActivity.this, "语音输入", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_search:
                goSearch();
//                Toast.makeText(SearchActivity.this, "搜索", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void goSearch() {
        if(items != null&& items.size() >0){
            items.clear();
            adapter.setData(items);
        }
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }


        String text = etSearch.getText().toString().trim();
        try {
            text = URLEncoder.encode(text,"UTF-8");//%E6%AF%9B%E4%B8%BB%E5%B8%AD
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = Constants.SEARCH_URL+text;
//        String savaJson = CacheUtils.getString(this,Constants.SEARCH_URL);
//        if(!TextUtils.isEmpty(savaJson)){
//            processData(savaJson);
//        }
        getDataFromNet(url);

    }

    private void getDataFromNet(String url) {
        progressbar.setVisibility(View.VISIBLE);
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess==" + result);
//                CacheUtils.putString(SearchActivity.this, Constants.SEARCH_URL, result);
                processData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("onError==" + ex.getMessage());
                progressbar.setVisibility(View.GONE);
                //没有数据
                tvResult.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String result) {
        SearchBean searchBean = parsedJson(result);
        items =  searchBean.getItems();

        if(items != null && items.size() >0){
            //有数据
            tvResult.setVisibility(View.GONE);
            adapter = new SearchAdapter(SearchActivity.this,items);
            //设置适配器
            listview.setAdapter(adapter);

        }else{
            //没有数据
            tvResult.setVisibility(View.VISIBLE);
        }

        progressbar.setVisibility(View.GONE);


    }

    private SearchBean parsedJson(String result) {
        return new Gson().fromJson(result,SearchBean.class);
    }

    private void showVoiceDialog() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());
//2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");//普通话
        mDialog.setParameter(SpeechConstant.DOMAIN, "iat");//日常用语
//若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
//结果
// mDialog.setParameter("asr_sch", "1");
// mDialog.setParameter("nlp_version", "2.0");
//3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener());
//4.显示dialog，接收语音输入
        mDialog.show();
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        /**
         *
         * @param recognizerResult
         * @param b 是否结束
         */
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Log.e("TAG", "onResult==" + recognizerResult.getResultString());
//            Toast.makeText(MainActivity.this, "onResult=="+recognizerResult.getResultString(), Toast.LENGTH_SHORT).show();
            printResult(recognizerResult);
        }

        @Override
        public void onError(SpeechError speechError) {

            Log.e("TAG","speechError=="+speechError.getMessage());
        }
    }


    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        String reuslt = resultBuffer.toString();
        reuslt = reuslt.replace("。","");
        etSearch.setText(reuslt);
        etSearch.setSelection(etSearch.length());
    }

    class MyInitListener implements InitListener {

        /**
         * 监听初始化是否成功
         * @param i
         */
        @Override
        public void onInit(int i) {
            if (i != ErrorCode.SUCCESS) {
                Toast.makeText(SearchActivity.this, "初始化失败了", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
