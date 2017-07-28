package com.example.administrator.ggcode.Bean;

import java.util.List;

/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Bean
 * 作者名 ： g小志
 * 日期   ： 2017/7/26
 * 时间   ： 12:29
 */

public class JokeBean {

    /**
     * error_code : 0
     * reason : Success
     * result : {"data":[{"content":"大学有个女同学，女神级别，有一富二代追她，经常假装无意中露出劳力士金表，一身名牌的在她面前晃悠。然而女神依然和一男屌丝深爱，直至结婚。我们一群人都暗中佩服，还是有不拜金的好女人啊。一次同学会，趁她喝高问她当年到底有没有对富二代动心过，她沉默半天才说，当年没见识，那些名牌都不认得，唯独一次觉得他的宾利牛逼，她的屌丝男友还骗她说是比亚迪豪华版\u2026\u2026","hashId":"867AFF72121B555B298D8422263E6DAD","unixtime":1494385240,"updatetime":"2017-05-10 11:00:40"},{"content":"为什么姐夫们都喜欢拿180斤的妹子换俩90的？庸俗！无耻！下流！我就不一样，我喜欢拿210斤的妹子换三个70的~","hashId":"1B5D430B19F288960B3015A215827BC3","unixtime":1494385240,"updatetime":"2017-05-10 11:00:40"},{"content":"我想与老公离婚，就去找律师：\u201c我老公对我不忠。\u201d 律师问：\u201c有根据吗？\u201d 我想了半天，说：\u201c有，他不是我孩子的父亲。\u201d","hashId":"EC51758F883D20F4CE9E59602FAB57EB","unixtime":1494385240,"updatetime":"2017-05-10 11:00:40"},{"content":"大学跟个女生感情很好，每次送她回宿舍，快上楼的时候她总会拥抱我一下，因为毕业肯定会分隔两地，我们都没说破。毕业后第一年，全班聚会，我们又熟悉地拥抱在一起，我说：\u201c工作一年，你一点没变，还是这么漂亮！\u201d她坏笑了一会说：\u201c你倒是变了很多，现在抱抱都不会戳到我了！\u201d","hashId":"B9B168B27663A5FB9847EE5D250D8FFB","unixtime":1494385240,"updatetime":"2017-05-10 11:00:40"},{"content":"震惊！日本究竟发达到什么程度？ 科技发达：已经开发出了时间停止器、记忆抹除器、心动药水、隐形衣等种种高科技产品。 教育发达：平均每个中学生都有三到四个美女老师为自己补课，经常是这位美女老师补完之后下周就变成了另外一位美女老师来补。 医疗发达：无论是骨折男性，还是行动不便的老人，都有专门的美女护士进行全方位护理，和美女老师一样，经常换。 治安良好：当然，这有赖于潜入各个黑恶组织内部的美女搜查官提供的有关黑恶组织的私人监狱地址、老大体貌特征、男性成员数量等情报。","hashId":"18CB68F30E678B24FE94F143F16039F8","unixtime":1494385240,"updatetime":"2017-05-10 11:00:40"},{"content":"第一次相亲的时候，两个人都太小，非常害羞！最后，真的尴尬极了，我就主动挑起话端，并在最后要了男孩子电话号码！回到家以后，还在考虑男孩子太沉默，怕是不合适，媒人就打了电话过来:人家男方没同意，觉得你太主动了，不太好\u2026","hashId":"39F1B309144E7C378118EA1A66C569A2","unixtime":1494385240,"updatetime":"2017-05-10 11:00:40"},{"content":"为什么你们男人的香烟那么贵！？一包中华要六七十块钱。我今天买颗白菜两块五，一把青菜一块二，一块肉九块八，一袋竹笋六块钱，两个菠萝六块钱，一顿丰盛的晚餐才花了二十几，你们发一圈烟冒几下就没了！","hashId":"5E942AEA464DEE737689A2DE830A312C","unixtime":1494385240,"updatetime":"2017-05-10 11:00:40"},{"content":"白天时候上公厕！洗手间只有一格，不分男女。蹲得正爽的时候，突然有人敲门，我就说有人，在大号。这时听见一位年轻的妈妈对宝宝说：＂宝宝要忍一下啰，里面有人！＂接着这位妈妈又对宝宝说：＂宝宝，我们来给叔叔加油！＂小宝宝就用稚嫩的声音不停的喊着：＂加油。。。加油。。。＂卧槽你这让劳资怎么蹲！？","hashId":"441635DB357F48AD0017D748348B45F2","unixtime":1494385240,"updatetime":"2017-05-10 11:00:40"}]}
     */

    private int error_code;
    private String reason;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * content : 大学有个女同学，女神级别，有一富二代追她，经常假装无意中露出劳力士金表，一身名牌的在她面前晃悠。然而女神依然和一男屌丝深爱，直至结婚。我们一群人都暗中佩服，还是有不拜金的好女人啊。一次同学会，趁她喝高问她当年到底有没有对富二代动心过，她沉默半天才说，当年没见识，那些名牌都不认得，唯独一次觉得他的宾利牛逼，她的屌丝男友还骗她说是比亚迪豪华版……
             * hashId : 867AFF72121B555B298D8422263E6DAD
             * unixtime : 1494385240
             * updatetime : 2017-05-10 11:00:40
             */

            private String content;
            private String hashId;
            private int unixtime;
            private String updatetime;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHashId() {
                return hashId;
            }

            public void setHashId(String hashId) {
                this.hashId = hashId;
            }

            public int getUnixtime() {
                return unixtime;
            }

            public void setUnixtime(int unixtime) {
                this.unixtime = unixtime;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }
        }
    }
}
