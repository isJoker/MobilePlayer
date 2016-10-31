package com.wjc.last_mobilephone.utils;


import com.wjc.last_mobilephone.domain.Lyric;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 作用：歌词解析工具类
 */
public class LyricUtils {


    /**
     * 是否歌词存在
     * @return
     */
    public boolean isExistsLyric() {
        return isExistsLyric;
    }

    private boolean isExistsLyric = false;

    /**
     * 歌词列表-解析好的歌词和排序好
     */
    private ArrayList<Lyric> lyrics;

    /**
     * 得到歌词列表
     *
     * @return
     */
    public ArrayList<Lyric> getLyrics() {
        return lyrics;
    }

    public void readLyricFile(File file) {
        if (file == null || !file.exists()) {
            //歌词文件不存在
            lyrics = null;
            isExistsLyric = false;
        } else {
            //歌词文件存在
            isExistsLyric = true;

            //创建歌词列表
            lyrics = new ArrayList<>();

            //while循环读取每行歌词
            try {

                String line = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), getCharset(file)));
                //解析歌词每句歌词，并且放入歌词列表中
                while ((line = reader.readLine()) != null) {
                    line = parsedLyric(line);
                }

                reader.close();


            } catch (Exception e) {
                e.printStackTrace();
            }


            //排序-先排序
            Collections.sort(lyrics, new MyComparator());

            //计算每句高亮时间
            for (int i = 0; i < lyrics.size(); i++) {
                Lyric oneLyric = lyrics.get(i);//第一句
                if (i + 1 < lyrics.size()) {
                    Lyric twoLyric = lyrics.get(i+1);//第二句
                    oneLyric.setSleepTime(twoLyric.getTimePoint() - oneLyric.getTimePoint());
                }

            }
        }
    }


    /**
     * 判断文件编码
     * @param file 文件
     * @return 编码：GBK,UTF-8,UTF-16LE
     */
    public String getCharset(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }
    class MyComparator implements Comparator<Lyric> {

        @Override
        public int compare(Lyric lhs, Lyric rhs) {
            if (lhs.getTimePoint() < rhs.getTimePoint()) {
                return -1;
            } else if (lhs.getTimePoint() > rhs.getTimePoint()) {
                return 1;
            } else {
                return 0;
            }

        }
    }

    /**
     * [02:04.12][03:37.32][00:59.73]我在这里欢笑
     *
     * @param line 歌词内容
     * @return
     */
    private String parsedLyric(String line) {
        //第一次出现[的位置
        int pos1 = line.indexOf("[");//0
        int pos2 = line.indexOf("]");//9,当找不到的时候-1
        if (pos1 == 0 && pos2 != -1) {//至少有一句歌词

            //装时间戳
            long[] longTimes = new long[getCountTag(line)];
            String strTime = line.substring(pos1 + 1, pos2);//02:04.12
            longTimes[0] = strTime2LongTime(strTime);//02:04.12-->long
            if (longTimes[0] == -1) {
                return "";
            }

            int i = 1;
            String content = line;//[02:04.12][03:37.32][00:59.73]我在这里欢笑
            while (pos1 == 0 && pos2 != -1) {
                content = content.substring(pos2 + 1);//[03:37.32][00:59.73]我在这里欢笑-->[00:59.73]我在这里欢笑-->我在这里欢笑
                pos1 = content.indexOf("[");//0->0->-1
                pos2 = content.indexOf("]");//9->9,当找不到的时候-1

                if (pos1 == 0 && pos2 != -1) {//还有时间戳
                    strTime = content.substring(pos1 + 1, pos2);//03:37.32-->00:59.73
                    longTimes[i] = strTime2LongTime(strTime);///03:37.32-->long

                    if (longTimes[i] == -1) {
                        return "";
                    }

                    i++;
                }

            }

            Lyric lyric = new Lyric();
            for (int j = 0; j < longTimes.length; j++) {
                if (longTimes[j] != 0) {

                    lyric.setContent(content);
                    lyric.setTimePoint(longTimes[j]);
                    //添加到集合中
                    lyrics.add(lyric);
                    lyric = new Lyric();
                }

            }

            return content;

        }


        return "";
    }

    /**
     * 02:04.12-->long
     * 把str转换成long
     *
     * @param strTime
     * @return
     */
    private long strTime2LongTime(String strTime) {
        long result = -1;
        try {
            //1.把02:04.12按照“：”切成02,和04.12
            String[] s1 = strTime.split(":");
            //2.把04.12安装“.”切成04和12
            String[] s2 = s1[1].split("\\.");
            //转换成毫秒
            long min = Long.parseLong(s1[0]);

            long seconde = Long.parseLong(s2[0]);

            long mil = Long.parseLong(s2[1]);

            result = min * 60 * 1000 + seconde * 1000 + mil * 10;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 判断有多少句歌词
     * [02:04.12][03:37.32][00:59.73]我在这里欢笑
     *
     * @param line
     * @return
     */
    private int getCountTag(String line) {
        int result = 0;
        String[] left = line.split("\\[");
        String[] right = line.split("\\]");
        if (left.length == 0 && right.length == 0) {
            result = 1;
        } else if (left.length > right.length) {
            result = left.length;
        } else {
            result = right.length;
        }
        return result;
    }
}
