package com.wjc.last_mobilephone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 作用：自定义VideoView
 */
public class VideoView extends android.widget.VideoView {
    public VideoView(Context context) {
        this(context,null);
    }

    public VideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置视频的高和宽
     * @param videoWidht
     * @param videoHeight
     */
    public void setVideoSize(int videoWidht,int videoHeight){
        ViewGroup.LayoutParams l = getLayoutParams();
        l.width  = videoWidht;
        l.height = videoHeight;
        setLayoutParams(l);

    }
}
