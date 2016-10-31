package com.wjc.last_mobilephone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * 作用：自定义VideoView
 */
public class VitamioVideoView extends io.vov.vitamio.widget.VideoView {
    public VitamioVideoView(Context context) {
        this(context,null);
    }

    public VitamioVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VitamioVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
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
