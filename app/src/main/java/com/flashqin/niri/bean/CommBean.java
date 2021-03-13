package com.flashqin.niri.bean;

import java.util.List;

public class CommBean {
    /**
     * head : {"code":1,"count":33,"message":"Success"}
     * body : {"data":[{"id":73,"type":3,"walletId":10001,"relatedId":4247756618801,"side":"+","amount":4.55,"summary":"2021-03-01, Commission of Crawl Order"},{"id":72,"type":3,"walletId":10001,"relatedId":4247748618801,"side":"+","amount":3.01,"summary":"2021-03-01, Commission of Crawl Order"},{"id":71,"type":3,"walletId":10001,"relatedId":4247735618801,"side":"+","amount":3.5,"summary":"2021-03-01, Commission of Crawl Order"},{"id":70,"type":3,"walletId":10001,"relatedId":4247610618901,"side":"+","amount":3.39,"summary":"2021-03-01, Commission of Crawl Order"},{"id":69,"type":3,"walletId":10001,"relatedId":4247604618901,"side":"+","amount":1.52,"summary":"2021-03-01, Commission of Crawl Order"},{"id":68,"type":3,"walletId":10001,"relatedId":4247489618801,"side":"+","amount":4.22,"summary":"2021-03-01, Commission of Crawl Order"},{"id":67,"type":3,"walletId":10001,"relatedId":4247480618801,"side":"+","amount":5.02,"summary":"2021-03-01, Commission of Crawl Order"},{"id":66,"type":3,"walletId":10001,"relatedId":4247400618901,"side":"+","amount":4.49,"summary":"2021-03-01, Commission of Crawl Order"},{"id":65,"type":3,"walletId":10001,"relatedId":4247301618801,"side":"+","amount":3.68,"summary":"2021-03-01, Commission of Crawl Order"},{"id":64,"type":3,"walletId":10001,"relatedId":4246865618901,"side":"+","amount":1.8,"summary":"2021-03-01, Commission of Crawl Order"},{"id":63,"type":3,"walletId":10001,"relatedId":4246829618901,"side":"+","amount":2.51,"summary":"2021-03-01, Commission of Crawl Order"},{"id":22,"type":3,"walletId":10001,"relatedId":4177507618801,"side":"+","amount":3.3,"summary":"2021-02-28, Commission of Crawl Order"},{"id":21,"type":3,"walletId":10001,"relatedId":4177489618801,"side":"+","amount":4.39,"summary":"2021-02-28, Commission of Crawl Order"},{"id":20,"type":3,"walletId":10001,"relatedId":4175745618801,"side":"+","amount":2.13,"summary":"2021-02-28, Commission of Crawl Order"},{"id":19,"type":3,"walletId":10001,"relatedId":4164540618901,"side":"+","amount":3.09,"summary":"2021-02-28, Commission of Crawl Order"},{"id":18,"type":3,"walletId":10001,"relatedId":4164482618901,"side":"+","amount":3.4,"summary":"2021-02-28, Commission of Crawl Order"},{"id":17,"type":3,"walletId":10001,"relatedId":4164051618901,"side":"+","amount":1.58,"summary":"2021-02-28, Commission of Crawl Order"},{"id":16,"type":3,"walletId":10001,"relatedId":4163996618901,"side":"+","amount":3.74,"summary":"2021-02-28, Commission of Crawl Order"},{"id":15,"type":3,"walletId":10001,"relatedId":4163978618901,"side":"+","amount":4.08,"summary":"2021-02-28, Commission of Crawl Order"},{"id":14,"type":3,"walletId":10001,"relatedId":4163329618901,"side":"+","amount":4.72,"summary":"2021-02-28, Commission of Crawl Order"},{"id":13,"type":3,"walletId":10001,"relatedId":4163241618901,"side":"+","amount":2.86,"summary":"2021-02-28, Commission of Crawl Order"},{"id":12,"type":3,"walletId":10001,"relatedId":4163022618901,"side":"+","amount":1.46,"summary":"2021-02-28, Commission of Crawl Order"},{"id":11,"type":3,"walletId":10001,"relatedId":4159080618901,"side":"+","amount":3.93,"summary":"2021-02-27, Commission of Crawl Order"},{"id":10,"type":3,"walletId":10001,"relatedId":4158871618801,"side":"+","amount":2.05,"summary":"2021-02-27, Commission of Crawl Order"},{"id":9,"type":3,"walletId":10001,"relatedId":4157476618801,"side":"+","amount":1.46,"summary":"2021-02-27, Commission of Crawl Order"},{"id":8,"type":3,"walletId":10001,"relatedId":4157462618801,"side":"+","amount":1.72,"summary":"2021-02-27, Commission of Crawl Order"},{"id":7,"type":3,"walletId":10001,"relatedId":4156965618801,"side":"+","amount":3.54,"summary":"2021-02-27, Commission of Crawl Order"},{"id":6,"type":3,"walletId":10001,"relatedId":4156933618801,"side":"+","amount":1.62,"summary":"2021-02-27, Commission of Crawl Order"},{"id":5,"type":3,"walletId":10001,"relatedId":4156864618801,"side":"+","amount":3.65,"summary":"2021-02-27, Commission of Crawl Order"},{"id":4,"type":3,"walletId":10001,"relatedId":4156816618801,"side":"+","amount":3.41,"summary":"2021-02-27, Commission of Crawl Order"},{"id":3,"type":3,"walletId":10001,"relatedId":4156799618801,"side":"+","amount":4.75,"summary":"2021-02-27, Commission of Crawl Order"},{"id":2,"type":3,"walletId":10001,"relatedId":4156687618801,"side":"+","amount":2.58,"summary":"2021-02-27, Commission of Crawl Order"},{"id":1,"type":3,"walletId":10001,"relatedId":4156155618901,"side":"+","amount":3.27,"summary":"2021-02-27, Commission of Crawl Order"}]}
     */

    private HeadBean head;
    private BodyBean body;

    public HeadBean getHead() {
        return head;
    }

    public void setHead(HeadBean head) {
        this.head = head;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class HeadBean {
        /**
         * code : 1
         * count : 33
         * message : Success
         */

        private int code;
        private int count;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class BodyBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 73
             * type : 3
             * walletId : 10001
             * relatedId : 4247756618801
             * side : +
             * amount : 4.55
             * summary : 2021-03-01, Commission of Crawl Order
             */

            private int id;
            private int type;
            private int walletId;
            private long relatedId;
            private String side;
            private double amount;
            private String summary;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getWalletId() {
                return walletId;
            }

            public void setWalletId(int walletId) {
                this.walletId = walletId;
            }

            public long getRelatedId() {
                return relatedId;
            }

            public void setRelatedId(long relatedId) {
                this.relatedId = relatedId;
            }

            public String getSide() {
                return side;
            }

            public void setSide(String side) {
                this.side = side;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }
        }
    }
}
