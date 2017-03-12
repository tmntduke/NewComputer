package com.example.tmnt.newcomputer.Model;

import java.util.List;

/**
 * Created by tmnt on 2017/3/7.
 */
public class NewsInfo {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2017-03-07 20:34","title":"FB聊天机器人错误回应高达70%，人工智能不过如","description":"网易IT","picUrl":"http://cms-bucket.nosdn.127.net/a6d08941291a4af8a506f94986bdb1bc20170307203417.png","url":"http://tech.163.com/17/0307/20/CEV0JB6R00097U7T.html"},{"ctime":"2017-03-07 20:53","title":"谷歌神经机器翻译系统 新增3种语言支持","description":"网易IT","picUrl":"http://cms-bucket.nosdn.127.net/4d4e08b9fa0f41afba84a946260cfff620170307205342.png","url":"http://tech.163.com/17/0307/20/CEV1MRPM00097U7T.html"},{"ctime":"2017-03-07 19:42","title":"Facebook人工智能大揭秘:为何AI如此不可或缺？","description":"网易IT","picUrl":"http://cms-bucket.nosdn.127.net/6b23bf3695d2477fae6d25bd743933c720170307194159.png","url":"http://tech.163.com/17/0307/19/CEUTJB4J00097U7T.html"},{"ctime":"2017-03-07 19:52","title":"停车场内的汽车将成为智能公路的中继器？","description":"网易IT","picUrl":"http://cms-bucket.nosdn.127.net/0514a27cae3c40f5bfa6cb1a72aae8f220170307195222.png","url":"http://tech.163.com/17/0307/19/CEUU6MBA00097U7T.html"},{"ctime":"2017-03-07 17:09","title":"自动驾驶报告详解：25家汽车厂商到底谁棋高一招","description":"网易IT","picUrl":"http://cms-bucket.nosdn.127.net/d27825ed9a8145f887c2ae559233ca3620170307170907.png","url":"http://tech.163.com/17/0307/17/CEUKRHR900097U7T.html"}]
     */

    private int code;
    private String msg;
    /**
     * ctime : 2017-03-07 20:34
     * title : FB聊天机器人错误回应高达70%，人工智能不过如
     * description : 网易IT
     * picUrl : http://cms-bucket.nosdn.127.net/a6d08941291a4af8a506f94986bdb1bc20170307203417.png
     * url : http://tech.163.com/17/0307/20/CEV0JB6R00097U7T.html
     */

    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
