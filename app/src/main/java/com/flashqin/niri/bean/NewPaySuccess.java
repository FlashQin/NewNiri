package com.flashqin.niri.bean;

public class NewPaySuccess {
    /**
     * head : {"code":1,"count":1,"message":"Success"}
     * body : {"data":{"order_no":"2103150000000379136200709361","mer_no":"gm761100000010781","pname":"ailwyn","sign":"b730e0d3b452b7a256be8763c2d87d03","goods":"iphone12","err_code":"SUCCESS","order_time":"20210315200709","pemail":"ailwyn@163.com","phone":"98123456","order_data":"https://api.ngn999.live/order/FLUTTERWAVE_213041121926881280","countryCode":"NGA","err_msg":"Success","order_amount":1000,"notifyUrl":"https://pay.kaymu.vip/v1/orfeyt/rechargeCallback","ccy_no":"NGN","busi_code":"100501","mer_order_no":"5507229924701","status":"SUCCESS"}}
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
         * count : 1
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
        /**
         * data : {"order_no":"2103150000000379136200709361","mer_no":"gm761100000010781","pname":"ailwyn","sign":"b730e0d3b452b7a256be8763c2d87d03","goods":"iphone12","err_code":"SUCCESS","order_time":"20210315200709","pemail":"ailwyn@163.com","phone":"98123456","order_data":"https://api.ngn999.live/order/FLUTTERWAVE_213041121926881280","countryCode":"NGA","err_msg":"Success","order_amount":1000,"notifyUrl":"https://pay.kaymu.vip/v1/orfeyt/rechargeCallback","ccy_no":"NGN","busi_code":"100501","mer_order_no":"5507229924701","status":"SUCCESS"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * order_no : 2103150000000379136200709361
             * mer_no : gm761100000010781
             * pname : ailwyn
             * sign : b730e0d3b452b7a256be8763c2d87d03
             * goods : iphone12
             * err_code : SUCCESS
             * order_time : 20210315200709
             * pemail : ailwyn@163.com
             * phone : 98123456
             * order_data : https://api.ngn999.live/order/FLUTTERWAVE_213041121926881280
             * countryCode : NGA
             * err_msg : Success
             * order_amount : 1000
             * notifyUrl : https://pay.kaymu.vip/v1/orfeyt/rechargeCallback
             * ccy_no : NGN
             * busi_code : 100501
             * mer_order_no : 5507229924701
             * status : SUCCESS
             */

            private String order_no;
            private String mer_no;
            private String pname;
            private String sign;
            private String goods;
            private String err_code;
            private String order_time;
            private String pemail;
            private String phone;
            private String order_data;
            private String countryCode;
            private String err_msg;
            private int order_amount;
            private String notifyUrl;
            private String ccy_no;
            private String busi_code;
            private String mer_order_no;
            private String status;

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public String getMer_no() {
                return mer_no;
            }

            public void setMer_no(String mer_no) {
                this.mer_no = mer_no;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getGoods() {
                return goods;
            }

            public void setGoods(String goods) {
                this.goods = goods;
            }

            public String getErr_code() {
                return err_code;
            }

            public void setErr_code(String err_code) {
                this.err_code = err_code;
            }

            public String getOrder_time() {
                return order_time;
            }

            public void setOrder_time(String order_time) {
                this.order_time = order_time;
            }

            public String getPemail() {
                return pemail;
            }

            public void setPemail(String pemail) {
                this.pemail = pemail;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getOrder_data() {
                return order_data;
            }

            public void setOrder_data(String order_data) {
                this.order_data = order_data;
            }

            public String getCountryCode() {
                return countryCode;
            }

            public void setCountryCode(String countryCode) {
                this.countryCode = countryCode;
            }

            public String getErr_msg() {
                return err_msg;
            }

            public void setErr_msg(String err_msg) {
                this.err_msg = err_msg;
            }

            public int getOrder_amount() {
                return order_amount;
            }

            public void setOrder_amount(int order_amount) {
                this.order_amount = order_amount;
            }

            public String getNotifyUrl() {
                return notifyUrl;
            }

            public void setNotifyUrl(String notifyUrl) {
                this.notifyUrl = notifyUrl;
            }

            public String getCcy_no() {
                return ccy_no;
            }

            public void setCcy_no(String ccy_no) {
                this.ccy_no = ccy_no;
            }

            public String getBusi_code() {
                return busi_code;
            }

            public void setBusi_code(String busi_code) {
                this.busi_code = busi_code;
            }

            public String getMer_order_no() {
                return mer_order_no;
            }

            public void setMer_order_no(String mer_order_no) {
                this.mer_order_no = mer_order_no;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
