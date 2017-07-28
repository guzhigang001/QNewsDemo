package com.example.administrator.ggcode.Bean;

import java.util.List;

/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Bean
 * 作者名 ： g小志
 * 日期   ： 2017/7/26
 * 时间   ： 19:55
 * 功能   ：
 */

public class TodayOfHistoryDetailBean {

    /**
     * reason : success
     * result : [{"e_id":"8552","title":"艾德礼组英国工党内阁，出任首相","content":"    在71年前的今天，1945年7月26日 (农历六月十八)，艾德礼组英国工党内阁，出任首相。\r\n    法西斯德国投降后，英国于1945年7月进行大选，工党获胜。7月26日，艾德礼组英工党内阁，出任首相，这是第一个在下院取得多数的英工党政府。\r\n    克莱蒙恃-艾德礼(1883-1967)，毕业于牛津大学，1906年加入独立工党。1924年、1929年两次入工党内阁，1935年被选为工党领袖。第二次世界大战时参加丘吉尔的战时联合内阁，任掌玺大臣、副首相等职。\r\n    工党内阁实行了一系列国有化和社会改革的政策，影响深远。1946年政府把英格兰银行，煤炭工业、公路运输、民间航空及通讯设施收归国有。1948年建立英国电业委员会，1949年建立煤气委员会。对电业和煤气实行国有化。1949年不顾国内外的激烈反对通过法案使铁矿和钢铁工业国有化。1949年通过原子能法案，使原子能的发展控制在军需部手中。这些措施大大改变了英国工业的面貌。工党政府还推行\u201c福利国家\u201d政策。1946年通过了国民健康服务法和国民保险法，建立了国民保险制度。1949年通过新建城镇法案。同时艾德礼政府还改革了选举制度。\r\n    在对外政策方面，1947年给予印度独立。6月17日通过印巴分治法案，印度被划分为印度和巴基斯坦两国。锡兰和缅甸也在1947年获得独立。英军于1948年从巴勒斯坦退出。犹太人建立了以色列国。工党积极主张英美合作，接受了美国的马歇尔复兴欧洲计划。1948年3月参加建立西欧联盟。1949年4月加入北大西洋公约组织。\r\n    1951年10月25日（距今65年）大选，工党遭到失败，艾德礼辞职，丘吉尔重新组成保守党政府。\r\n    \r\n\r\n","picNo":"1","picUrl":[{"pic_title":"理查德·艾德礼","id":1,"url":"http://images.juheapi.com/history/8552_1.jpg"}]}]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * e_id : 8552
         * title : 艾德礼组英国工党内阁，出任首相
         * content :     在71年前的今天，1945年7月26日 (农历六月十八)，艾德礼组英国工党内阁，出任首相。
         法西斯德国投降后，英国于1945年7月进行大选，工党获胜。7月26日，艾德礼组英工党内阁，出任首相，这是第一个在下院取得多数的英工党政府。
         克莱蒙恃-艾德礼(1883-1967)，毕业于牛津大学，1906年加入独立工党。1924年、1929年两次入工党内阁，1935年被选为工党领袖。第二次世界大战时参加丘吉尔的战时联合内阁，任掌玺大臣、副首相等职。
         工党内阁实行了一系列国有化和社会改革的政策，影响深远。1946年政府把英格兰银行，煤炭工业、公路运输、民间航空及通讯设施收归国有。1948年建立英国电业委员会，1949年建立煤气委员会。对电业和煤气实行国有化。1949年不顾国内外的激烈反对通过法案使铁矿和钢铁工业国有化。1949年通过原子能法案，使原子能的发展控制在军需部手中。这些措施大大改变了英国工业的面貌。工党政府还推行“福利国家”政策。1946年通过了国民健康服务法和国民保险法，建立了国民保险制度。1949年通过新建城镇法案。同时艾德礼政府还改革了选举制度。
         在对外政策方面，1947年给予印度独立。6月17日通过印巴分治法案，印度被划分为印度和巴基斯坦两国。锡兰和缅甸也在1947年获得独立。英军于1948年从巴勒斯坦退出。犹太人建立了以色列国。工党积极主张英美合作，接受了美国的马歇尔复兴欧洲计划。1948年3月参加建立西欧联盟。1949年4月加入北大西洋公约组织。
         1951年10月25日（距今65年）大选，工党遭到失败，艾德礼辞职，丘吉尔重新组成保守党政府。



         * picNo : 1
         * picUrl : [{"pic_title":"理查德·艾德礼","id":1,"url":"http://images.juheapi.com/history/8552_1.jpg"}]
         */

        private String e_id;
        private String title;
        private String content;
        private String picNo;
        private List<PicUrlBean> picUrl;

        public String getE_id() {
            return e_id;
        }

        public void setE_id(String e_id) {
            this.e_id = e_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicNo() {
            return picNo;
        }

        public void setPicNo(String picNo) {
            this.picNo = picNo;
        }

        public List<PicUrlBean> getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(List<PicUrlBean> picUrl) {
            this.picUrl = picUrl;
        }

        public static class PicUrlBean {
            /**
             * pic_title : 理查德·艾德礼
             * id : 1
             * url : http://images.juheapi.com/history/8552_1.jpg
             */

            private String pic_title;
            private int id;
            private String url;

            public String getPic_title() {
                return pic_title;
            }

            public void setPic_title(String pic_title) {
                this.pic_title = pic_title;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
