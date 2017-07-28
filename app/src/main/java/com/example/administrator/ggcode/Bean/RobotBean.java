package com.example.administrator.ggcode.Bean;

/**
 * 工程名 ： QNnewsDemo
 * 包名   ： com.example.administrator.ggcode.Bean
 * 作者名 ： g小志
 * 日期   ： 2017/7/27
 * 时间   ： 15:36
 * 功能   ：
 */

public class RobotBean {
    /**
     * reason : 成功的返回
     * result : {"code":100000,"text":"你好呀，～有什么新鲜事儿要对我讲？"}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

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

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * code : 100000
         * text : 你好呀，～有什么新鲜事儿要对我讲？
         */

        private int code;
        private String text;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
