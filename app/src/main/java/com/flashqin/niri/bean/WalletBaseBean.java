package com.flashqin.niri.bean;

public class WalletBaseBean {
    /**
     * head : {"code":1,"count":1,"message":"Success"}
     * body : {"data":{"memberId":10001,"totalRecharge":0,"totalWithdraw":0,"totalRevenue":56.91,"totalCommission":0,"totalSalary":0,"summary":""}}
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
         * data : {"memberId":10001,"totalRecharge":0,"totalWithdraw":0,"totalRevenue":56.91,"totalCommission":0,"totalSalary":0,"summary":""}
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
             * memberId : 10001
             * totalRecharge : 0.0
             * totalWithdraw : 0.0
             * totalRevenue : 56.91
             * totalCommission : 0.0
             * totalSalary : 0.0
             * summary :
             */

            private int memberId;
            private double totalRecharge;
            private double totalWithdraw;
            private double totalRevenue;
            private double totalCommission;
            private double totalSalary;
            private String summary;

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public double getTotalRecharge() {
                return totalRecharge;
            }

            public void setTotalRecharge(double totalRecharge) {
                this.totalRecharge = totalRecharge;
            }

            public double getTotalWithdraw() {
                return totalWithdraw;
            }

            public void setTotalWithdraw(double totalWithdraw) {
                this.totalWithdraw = totalWithdraw;
            }

            public double getTotalRevenue() {
                return totalRevenue;
            }

            public void setTotalRevenue(double totalRevenue) {
                this.totalRevenue = totalRevenue;
            }

            public double getTotalCommission() {
                return totalCommission;
            }

            public void setTotalCommission(double totalCommission) {
                this.totalCommission = totalCommission;
            }

            public double getTotalSalary() {
                return totalSalary;
            }

            public void setTotalSalary(double totalSalary) {
                this.totalSalary = totalSalary;
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
