package com.wjc.last_mobilephone.domain;

import java.util.List;

/**
 * 作用：网络音频的对象
 */
public class ListViewBean {

    /**
     * count : 4273
     * np : 1473792962
     */

    private InfoBean info;
    /**
     * status : 4
     * comment : 93
     * top_comments : [{"voicetime":0,"precid":0,"content":"非要把自己搞得跟特么傻逼一样？就特么不给你红","like_count":64,"u":{"header":["http://qzapp.qlogo.cn/qzapp/100336987/CA03375FD39968A18FC1492FB37B91D4/100","http://qzapp.qlogo.cn/qzapp/100336987/CA03375FD39968A18FC1492FB37B91D4/100"],"sex":"m","uid":"14804457","name":"夢畔藍橋"},"preuid":0,"passtime":"2016-09-12 11:49:22","voiceuri":"","id":63508324},{"voicetime":0,"precid":0,"content":"我相信你能红的，继续努力，下次记得往屎里钻啊，这样红的快点。","like_count":15,"u":{"header":["http://wimg.spriteapp.cn/profile/large/2016/02/28/56d2e00e5a3d2_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/02/28/56d2e00e5a3d2_mini.jpg"],"sex":"m","uid":"11052159","name":"?な聖爺"},"preuid":0,"passtime":"2016-09-12 23:32:52","voiceuri":"","id":63557797},{"voicetime":0,"precid":0,"content":"不管儿孙们怎么看、反正你爸爸我笑了！赞+32😂","like_count":5,"u":{"header":["http://wimg.spriteapp.cn/profile/large/2016/08/25/57beddeb2a8fe_mini.jpg","http://dimg.spriteapp.cn/profile/large/2016/08/25/57beddeb2a8fe_mini.jpg"],"sex":"m","uid":"18406222","name":"魔域-夜欲"},"preuid":0,"passtime":"2016-09-12 13:29:21","voiceuri":"","id":63515553}]
     * tags : [{"id":1,"name":"搞笑"},{"id":55,"name":"微视频"},{"id":248,"name":"囧事"}]
     * bookmark : 251
     * text : 大哥，听我解释，我真不是故意的~~
     * up : 1400
     * share_url : http://a.f.budejie.com/share/20595803.html?wx.qq.com
     * down : 134
     * forward : 478
     * u : {"header":["http://wimg.spriteapp.cn/profile/picture1/M00/11/66/wKiFQ1S_zQ2AVGsdAAB3pvdiX8k.cn_qza","http://dimg.spriteapp.cn/profile/picture1/M00/11/66/wKiFQ1S_zQ2AVGsdAAB3pvdiX8k.cn_qza"],"is_v":false,"uid":"13241908","is_vip":false,"name":"七级床震i"}
     * passtime : 2016-09-14 09:57:02
     * video : {"playfcount":17482,"height":640,"width":480,"video":["http://wvideo.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4","http://bvideo.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4","http://dv.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4"],"download":["http://wvideo.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4","http://bvideo.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4","http://dv.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4"],"duration":22,"playcount":37887,"thumbnail":["http://wimg.spriteapp.cn/picture/2016/0912/57d606a883065__b.jpg","http://dimg.spriteapp.cn/picture/2016/0912/57d606a883065__b.jpg"],"thumbnail_small":["http://wimg.spriteapp.cn/crop/150x150/picture/2016/0912/57d606a883065__b.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2016/0912/57d606a883065__b.jpg"]}
     * type : video
     * id : 20595803
     */

    private List<ListBean> list;

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public InfoBean getInfo() {
        return info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public static class InfoBean {
        private int count;
        private int np;

        public void setCount(int count) {
            this.count = count;
        }

        public void setNp(int np) {
            this.np = np;
        }

        public int getCount() {
            return count;
        }

        public int getNp() {
            return np;
        }
    }

    public static class ListBean {
        private int status;
        private String comment;
        private String bookmark;
        private String text;
        private String up;
        private String share_url;
        private int down;
        private int forward;
        /**
         * header : ["http://wimg.spriteapp.cn/profile/picture1/M00/11/66/wKiFQ1S_zQ2AVGsdAAB3pvdiX8k.cn_qza","http://dimg.spriteapp.cn/profile/picture1/M00/11/66/wKiFQ1S_zQ2AVGsdAAB3pvdiX8k.cn_qza"]
         * is_v : false
         * uid : 13241908
         * is_vip : false
         * name : 七级床震i
         */

        private UBean u;
        private String passtime;
        /**
         * playfcount : 17482
         * height : 640
         * width : 480
         * video : ["http://wvideo.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4","http://bvideo.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4","http://dv.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4"]
         * download : ["http://wvideo.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4","http://bvideo.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4","http://dv.spriteapp.cn/video/2016/0912/57d606a8a533a_wpd.mp4"]
         * duration : 22
         * playcount : 37887
         * thumbnail : ["http://wimg.spriteapp.cn/picture/2016/0912/57d606a883065__b.jpg","http://dimg.spriteapp.cn/picture/2016/0912/57d606a883065__b.jpg"]
         * thumbnail_small : ["http://wimg.spriteapp.cn/crop/150x150/picture/2016/0912/57d606a883065__b.jpg","http://dimg.spriteapp.cn/crop/150x150/picture/2016/0912/57d606a883065__b.jpg"]
         */

        private VideoBean video;

        private ImageBean image;

        private GifBean gif;


        private String type;
        private String id;
        /**
         * voicetime : 0
         * precid : 0
         * content : 非要把自己搞得跟特么傻逼一样？就特么不给你红
         * like_count : 64
         * u : {"header":["http://qzapp.qlogo.cn/qzapp/100336987/CA03375FD39968A18FC1492FB37B91D4/100","http://qzapp.qlogo.cn/qzapp/100336987/CA03375FD39968A18FC1492FB37B91D4/100"],"sex":"m","uid":"14804457","name":"夢畔藍橋"}
         * preuid : 0
         * passtime : 2016-09-12 11:49:22
         * voiceuri :
         * id : 63508324
         */

        private List<TopCommentsBean> top_comments;
        /**
         * id : 1
         * name : 搞笑
         */

        private List<TagsBean> tags;

        public VideoBean getVideo() {
            return video;
        }

        public void setVideo(VideoBean video) {
            this.video = video;
        }

        public ImageBean getImage() {
            return image;
        }

        public void setImage(ImageBean image) {
            this.image = image;
        }

        public GifBean getGif() {
            return gif;
        }

        public void setGif(GifBean gif) {
            this.gif = gif;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setBookmark(String bookmark) {
            this.bookmark = bookmark;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setUp(String up) {
            this.up = up;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public void setDown(int down) {
            this.down = down;
        }

        public void setForward(int forward) {
            this.forward = forward;
        }

        public void setU(UBean u) {
            this.u = u;
        }

        public void setPasstime(String passtime) {
            this.passtime = passtime;
        }


        public void setType(String type) {
            this.type = type;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTop_comments(List<TopCommentsBean> top_comments) {
            this.top_comments = top_comments;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public int getStatus() {
            return status;
        }

        public String getComment() {
            return comment;
        }

        public String getBookmark() {
            return bookmark;
        }

        public String getText() {
            return text;
        }

        public String getUp() {
            return up;
        }

        public String getShare_url() {
            return share_url;
        }

        public int getDown() {
            return down;
        }

        public int getForward() {
            return forward;
        }

        public UBean getU() {
            return u;
        }

        public String getPasstime() {
            return passtime;
        }




        public String getType() {
            return type;
        }

        public String getId() {
            return id;
        }

        public List<TopCommentsBean> getTop_comments() {
            return top_comments;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public static class UBean {
            private boolean is_v;
            private String uid;
            private boolean is_vip;
            private String name;
            private List<String> header;

            public void setIs_v(boolean is_v) {
                this.is_v = is_v;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public void setIs_vip(boolean is_vip) {
                this.is_vip = is_vip;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setHeader(List<String> header) {
                this.header = header;
            }

            public boolean isIs_v() {
                return is_v;
            }

            public String getUid() {
                return uid;
            }

            public boolean isIs_vip() {
                return is_vip;
            }

            public String getName() {
                return name;
            }

            public List<String> getHeader() {
                return header;
            }
        }

        public static class GifBean {
            private List<String> download_url;
            private List<String> gif_thumbnail;
            private int height;
            private int width;
            private List<String> images;

            public List<String> getDownload_url() {
                return download_url;
            }

            public void setDownload_url(List<String> download_url) {
                this.download_url = download_url;
            }

            public List<String> getGif_thumbnail() {
                return gif_thumbnail;
            }

            public void setGif_thumbnail(List<String> gif_thumbnail) {
                this.gif_thumbnail = gif_thumbnail;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }
        }

        public static class VideoBean {
            private List<String> download;
            private int duration;
            private int height;
            private int width;
            private int playcount;
            private int playfcount;
            private List<String> thumbnail;
            private List<String> video;

            public List<String> getDownload() {
                return download;
            }

            public void setDownload(List<String> download) {
                this.download = download;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getPlaycount() {
                return playcount;
            }

            public void setPlaycount(int playcount) {
                this.playcount = playcount;
            }

            public int getPlayfcount() {
                return playfcount;
            }

            public void setPlayfcount(int playfcount) {
                this.playfcount = playfcount;
            }

            public List<String> getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(List<String> thumbnail) {
                this.thumbnail = thumbnail;
            }

            public List<String> getVideo() {
                return video;
            }

            public void setVideo(List<String> video) {
                this.video = video;
            }
        }

        public static class ImageBean {
            private List<String> big;
            private List<String> download_url;
            private List<String> medium;
            private List<String> small;
            private int width;
            private int height;

            public List<String> getBig() {
                return big;
            }

            public void setBig(List<String> big) {
                this.big = big;
            }

            public List<String> getDownload_url() {
                return download_url;
            }

            public void setDownload_url(List<String> download_url) {
                this.download_url = download_url;
            }

            public List<String> getMedium() {
                return medium;
            }

            public void setMedium(List<String> medium) {
                this.medium = medium;
            }

            public List<String> getSmall() {
                return small;
            }

            public void setSmall(List<String> small) {
                this.small = small;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }


        public static class TopCommentsBean {
            private int voicetime;
            private int precid;
            private String content;
            private int like_count;
            /**
             * header : ["http://qzapp.qlogo.cn/qzapp/100336987/CA03375FD39968A18FC1492FB37B91D4/100","http://qzapp.qlogo.cn/qzapp/100336987/CA03375FD39968A18FC1492FB37B91D4/100"]
             * sex : m
             * uid : 14804457
             * name : 夢畔藍橋
             */

            private UBean u;
            private int preuid;
            private String passtime;
            private String voiceuri;
            private int id;

            public void setVoicetime(int voicetime) {
                this.voicetime = voicetime;
            }

            public void setPrecid(int precid) {
                this.precid = precid;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setLike_count(int like_count) {
                this.like_count = like_count;
            }

            public void setU(UBean u) {
                this.u = u;
            }

            public void setPreuid(int preuid) {
                this.preuid = preuid;
            }

            public void setPasstime(String passtime) {
                this.passtime = passtime;
            }

            public void setVoiceuri(String voiceuri) {
                this.voiceuri = voiceuri;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getVoicetime() {
                return voicetime;
            }

            public int getPrecid() {
                return precid;
            }

            public String getContent() {
                return content;
            }

            public int getLike_count() {
                return like_count;
            }

            public UBean getU() {
                return u;
            }

            public int getPreuid() {
                return preuid;
            }

            public String getPasstime() {
                return passtime;
            }

            public String getVoiceuri() {
                return voiceuri;
            }

            public int getId() {
                return id;
            }

            public static class UBean {
                private String sex;
                private String uid;
                private String name;
                private List<String> header;

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public void setUid(String uid) {
                    this.uid = uid;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setHeader(List<String> header) {
                    this.header = header;
                }

                public String getSex() {
                    return sex;
                }

                public String getUid() {
                    return uid;
                }

                public String getName() {
                    return name;
                }

                public List<String> getHeader() {
                    return header;
                }
            }
        }

        public static class TagsBean {
            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
