package com.wjc.last_mobilephone.domain;

/**
 * 作用：代表一句歌词
 * [01:06.99]我在这里活着
 */
public class Lyric {

    /**
     * 歌词内容
     */
    private String content;
    /**
     * 时间戳
     */
    private long timePoint;

    /**
     * 高亮变色的事件
     */
    private long sleepTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(long timePoint) {
        this.timePoint = timePoint;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "content='" + content + '\'' +
                ", timePoint=" + timePoint +
                ", sleepTime=" + sleepTime +
                '}';
    }
}
