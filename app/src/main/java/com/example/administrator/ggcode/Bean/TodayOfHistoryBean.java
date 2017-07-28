package com.example.administrator.ggcode.Bean;

import java.util.List;

/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Bean
 * 作者名 ： g小志
 * 日期   ： 2017/7/26
 * 时间   ： 15:56
 * 功能   ：
 */

public class TodayOfHistoryBean {

    /**
     * reason : success
     * result : [{"day":"7/26","date":"146年07月26日","title":"梁冀鸩杀汉质帝","e_id":"8535"},{"day":"7/26","date":"291年07月26日","title":"西晋贾后开始专权","e_id":"8536"},{"day":"7/26","date":"291年07月26日","title":"八王之乱","e_id":"8537"},{"day":"7/26","date":"1139年07月26日","title":"阿方索一世战胜摩尔人，成为第一位葡萄牙国王","e_id":"8538"},{"day":"7/26","date":"1645年07月26日","title":"李自成九宫山遇难","e_id":"8539"},{"day":"7/26","date":"1724年07月26日","title":"清代大学士纪晓岚出生","e_id":"8540"},{"day":"7/26","date":"1825年07月26日","title":"英国博物学家赫胥黎诞辰","e_id":"8541"},{"day":"7/26","date":"1856年07月26日","title":"肖伯纳诞辰","e_id":"8542"},{"day":"7/26","date":"1866年07月26日","title":"普鲁士和奥地利缔结停战协定，普奥战争结束","e_id":"8543"},{"day":"7/26","date":"1887年07月26日","title":"世界语创立","e_id":"8544"},{"day":"7/26","date":"1898年07月26日","title":"清政府改《时务报》为官办","e_id":"8545"},{"day":"7/26","date":"1912年07月26日","title":"参议院同意周学熙为财政总长","e_id":"8546"},{"day":"7/26","date":"1914年07月26日","title":"爱尔兰争端 都柏林发生起义","e_id":"8547"},{"day":"7/26","date":"1920年07月26日","title":"庞氏骗局","e_id":"8548"},{"day":"7/26","date":"1940年07月26日","title":"日本提出\u201c大东亚共荣圈\u201d构想","e_id":"8549"},{"day":"7/26","date":"1943年07月26日","title":"洛杉矶光化学烟雾事件","e_id":"8550"},{"day":"7/26","date":"1945年07月26日","title":"波茨坦公告发表","e_id":"8551"},{"day":"7/26","date":"1945年07月26日","title":"艾德礼组英国工党内阁，出任首相","e_id":"8552"},{"day":"7/26","date":"1947年07月26日","title":"日本降舰驶抵上海","e_id":"8553"},{"day":"7/26","date":"1947年07月26日","title":"美国军事力量终于得以统一","e_id":"8554"},{"day":"7/26","date":"1952年07月26日","title":"伊娃·庇隆去世，阿根廷为她哀悼","e_id":"8555"},{"day":"7/26","date":"1953年07月26日","title":"古巴爆发\u201c七·二六运动\u201d","e_id":"8556"},{"day":"7/26","date":"1954年07月26日","title":"中国第一次自己制造初级教练机成功","e_id":"8557"},{"day":"7/26","date":"1954年07月26日","title":"我国首批飞机制造成功，并举行试飞典礼","e_id":"8558"},{"day":"7/26","date":"1956年07月26日","title":"国家开始对私营工商业进行社会主义改造","e_id":"8559"},{"day":"7/26","date":"1956年07月26日","title":"苏伊士运河收归国有","e_id":"8560"},{"day":"7/26","date":"1959年07月26日","title":"台湾著名歌手童安格出生","e_id":"8561"},{"day":"7/26","date":"1963年07月26日","title":"南斯拉夫发生地震，1000人死亡","e_id":"8562"},{"day":"7/26","date":"1965年07月26日","title":"毛泽东提出把医疗卫生工作重点放到农村去","e_id":"8563"},{"day":"7/26","date":"1978年07月26日","title":"第一个试管婴儿在伦敦诞生","e_id":"8564"},{"day":"7/26","date":"1982年07月26日","title":"西德营救船拯救越南难民","e_id":"8565"},{"day":"7/26","date":"1993年07月26日","title":"美国军事家马修\u2022邦克\u2022李奇微去世","e_id":"8566"},{"day":"7/26","date":"1995年07月26日","title":"加利授予驻前南盔总司令空袭决策权","e_id":"8567"},{"day":"7/26","date":"1998年07月26日","title":"柬埔寨大选顺利举行　洪森获胜","e_id":"8568"},{"day":"7/26","date":"1998年07月26日","title":"CIH病毒开始袭击美国","e_id":"8569"},{"day":"7/26","date":"2005年07月26日","title":"朝核问题六方会谈取得进展","e_id":"8570"},{"day":"7/26","date":"2005年07月26日","title":"英国首都伦敦地铁和公交汽车相继发生多起爆炸","e_id":"8571"},{"day":"7/26","date":"2009年07月26日","title":"马英九当选中国国民党主席","e_id":"8572"},{"day":"7/26","date":"2010年07月26日","title":"维基解密网上公开了大量的秘密文件，引起轩然大波","e_id":"8573"}]
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
         * day : 7/26
         * date : 146年07月26日
         * title : 梁冀鸩杀汉质帝
         * e_id : 8535
         */

        private String day;
        private String date;
        private String title;
        private String e_id;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getE_id() {
            return e_id;
        }

        public void setE_id(String e_id) {
            this.e_id = e_id;
        }
    }
}
