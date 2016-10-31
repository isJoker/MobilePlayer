// IMusicPlayerService.aidl
package com.atguigu.mobileplayer;

// Declare any non-default types here with import statements

interface IMusicPlayerService {

    /**
        * 播放音乐
        */
        void start() ;

       /**
        * 暂停音乐
        */
        void pause() ;


       /**
        * 下一首
        */
        void next() ;


       /**
        * 上一首
        */
        void pre() ;

       /**
        * 设置播放模式
        */

        void setPlaymode(int playmode);

       /**
        * 得到播放模式
        * @return
        */
        int getPlaymode();

       /**
        * 得到艺术家
        * @return
        */
        String getArtist();

       /**
        * 得到歌曲名称
        * @return
        */
        String getName();

       /**
        * 得到当前进度
        * @return
        */
        int getCurrentPosition();


       /**
        * 得到总时长
        * @return
        */
        int getDuration();

       /**
        * 音频的拖动
        * @param position
        */
        void seekTo(int position);

       /**
        * 根据位置播放对应音频文件
        * @param position
        */
        void openAudio(int position);

        /**
        * 判断是否播放中
        */
        boolean isPlaying();

        void notifyChange(String action);

        /**
        得到音乐播放的绝对路径
        */
        String getAudioPaht();

        int getAudioSessionId();

}
