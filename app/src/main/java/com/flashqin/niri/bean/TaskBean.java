package com.flashqin.niri.bean;

import java.util.List;

public class TaskBean {
    /**
     * head : {"code":1,"count":7,"message":"Success"}
     * body : {"data":[[{"id":0,"name":"New Members","balanceLowerLimit":0,"balanceUpperLimit":100,"incomeRate":0.003,"tradeTimesLimit":10,"priceLowerLimit":10,"priceUpperLimit":100,"upgradePercent":0.2,"equityMargin":1000,"withdrawTimesLimit":1,"withdrawChargeRate":0.15,"minimalWithdrawAmount":1000,"summary":"Food and necessities","image":"https://img.amazoncash.vip/images/level01.png"},{"id":0,"name":"New Members","balanceLowerLimit":0,"balanceUpperLimit":100,"incomeRate":0.003,"tradeTimesLimit":10,"priceLowerLimit":10,"priceUpperLimit":100,"upgradePercent":0.2,"equityMargin":1000,"withdrawTimesLimit":1,"withdrawChargeRate":0.15,"minimalWithdrawAmount":1000,"summary":"Food and necessities","image":"https://img.amazoncash.vip/images/level02.png"}],[{"id":1,"name":"Junior Members","balanceLowerLimit":100,"balanceUpperLimit":500,"incomeRate":0.003,"tradeTimesLimit":10,"priceLowerLimit":100,"priceUpperLimit":400,"upgradePercent":0.18,"equityMargin":5000,"withdrawTimesLimit":1,"withdrawChargeRate":0.15,"minimalWithdrawAmount":2000,"summary":"Clothing and footwear","image":"https://img.amazoncash.vip/images/level11.png"},{"id":1,"name":"Junior Members","balanceLowerLimit":100,"balanceUpperLimit":500,"incomeRate":0.003,"tradeTimesLimit":10,"priceLowerLimit":100,"priceUpperLimit":400,"upgradePercent":0.18,"equityMargin":5000,"withdrawTimesLimit":1,"withdrawChargeRate":0.15,"minimalWithdrawAmount":2000,"summary":"Clothing and footwear","image":"https://img.amazoncash.vip/images/level12.png"}],[{"id":2,"name":"Intermediate Members","balanceLowerLimit":500,"balanceUpperLimit":2000,"incomeRate":0.0035,"tradeTimesLimit":10,"priceLowerLimit":400,"priceUpperLimit":1500,"upgradePercent":0.15,"equityMargin":20000,"withdrawTimesLimit":2,"withdrawChargeRate":0.12,"minimalWithdrawAmount":2000,"summary":"Office supplies","image":"https://img.amazoncash.vip/images/level21.png"},{"id":2,"name":"Intermediate Members","balanceLowerLimit":500,"balanceUpperLimit":2000,"incomeRate":0.0035,"tradeTimesLimit":10,"priceLowerLimit":400,"priceUpperLimit":1500,"upgradePercent":0.15,"equityMargin":20000,"withdrawTimesLimit":2,"withdrawChargeRate":0.12,"minimalWithdrawAmount":2000,"summary":"Office supplies","image":"https://img.amazoncash.vip/images/level22.png"}],[{"id":3,"name":"Senior Members","balanceLowerLimit":2000,"balanceUpperLimit":8000,"incomeRate":0.004,"tradeTimesLimit":10,"priceLowerLimit":1500,"priceUpperLimit":5000,"upgradePercent":0.12,"equityMargin":100000,"withdrawTimesLimit":2,"withdrawChargeRate":0.12,"minimalWithdrawAmount":2000,"summary":"Cosmetics and luxuries","image":"https://img.amazoncash.vip/images/level31.png"},{"id":3,"name":"Senior Members","balanceLowerLimit":2000,"balanceUpperLimit":8000,"incomeRate":0.004,"tradeTimesLimit":10,"priceLowerLimit":1500,"priceUpperLimit":5000,"upgradePercent":0.12,"equityMargin":100000,"withdrawTimesLimit":2,"withdrawChargeRate":0.12,"minimalWithdrawAmount":2000,"summary":"Cosmetics and luxuries","image":"https://img.amazoncash.vip/images/level32.png"}],[{"id":4,"name":"Sliver Members","balanceLowerLimit":8000,"balanceUpperLimit":20000,"incomeRate":0.004,"tradeTimesLimit":10,"priceLowerLimit":5000,"priceUpperLimit":12000,"upgradePercent":0.08,"equityMargin":300000,"withdrawTimesLimit":3,"withdrawChargeRate":0.1,"minimalWithdrawAmount":2000,"summary":"Phones and mobiles","image":"https://img.amazoncash.vip/images/level41.png"},{"id":4,"name":"Sliver Members","balanceLowerLimit":8000,"balanceUpperLimit":20000,"incomeRate":0.004,"tradeTimesLimit":10,"priceLowerLimit":5000,"priceUpperLimit":12000,"upgradePercent":0.08,"equityMargin":300000,"withdrawTimesLimit":3,"withdrawChargeRate":0.1,"minimalWithdrawAmount":2000,"summary":"Phones and mobiles","image":"https://img.amazoncash.vip/images/level42.png"}],[{"id":5,"name":"Gold Members","balanceLowerLimit":20000,"balanceUpperLimit":100000,"incomeRate":0.005,"tradeTimesLimit":10,"priceLowerLimit":12000,"priceUpperLimit":50000,"upgradePercent":0.05,"equityMargin":500000,"withdrawTimesLimit":4,"withdrawChargeRate":0.1,"minimalWithdrawAmount":2000,"summary":"Computers and electronics","image":"https://img.amazoncash.vip/images/level51.png"},{"id":5,"name":"Gold Members","balanceLowerLimit":20000,"balanceUpperLimit":100000,"incomeRate":0.005,"tradeTimesLimit":10,"priceLowerLimit":12000,"priceUpperLimit":50000,"upgradePercent":0.05,"equityMargin":500000,"withdrawTimesLimit":4,"withdrawChargeRate":0.1,"minimalWithdrawAmount":2000,"summary":"Computers and electronics","image":"https://img.amazoncash.vip/images/level52.png"}],[{"id":6,"name":"Platinum Members","balanceLowerLimit":100000,"balanceUpperLimit":1000000,"incomeRate":0.005,"tradeTimesLimit":10,"priceLowerLimit":50000,"priceUpperLimit":200000,"upgradePercent":0.05,"equityMargin":1000000,"withdrawTimesLimit":5,"withdrawChargeRate":0.06,"minimalWithdrawAmount":2000,"summary":"Cars and vehicles","image":"https://img.amazoncash.vip/images/level61.png"},{"id":6,"name":"Platinum Members","balanceLowerLimit":100000,"balanceUpperLimit":1000000,"incomeRate":0.005,"tradeTimesLimit":10,"priceLowerLimit":50000,"priceUpperLimit":200000,"upgradePercent":0.05,"equityMargin":1000000,"withdrawTimesLimit":5,"withdrawChargeRate":0.06,"minimalWithdrawAmount":2000,"summary":"Cars and vehicles","image":"https://img.amazoncash.vip/images/level62.png"}]]}
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
         * count : 7
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
        private List<List<DataBean>> data;

        public List<List<DataBean>> getData() {
            return data;
        }

        public void setData(List<List<DataBean>> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 0
             * name : New Members
             * balanceLowerLimit : 0.0
             * balanceUpperLimit : 100.0
             * incomeRate : 0.003
             * tradeTimesLimit : 10
             * priceLowerLimit : 10.0
             * priceUpperLimit : 100.0
             * upgradePercent : 0.2
             * equityMargin : 1000.0
             * withdrawTimesLimit : 1
             * withdrawChargeRate : 0.15
             * minimalWithdrawAmount : 1000.0
             * summary : Food and necessities
             * image : https://img.amazoncash.vip/images/level01.png
             */

            private int id;
            private String name;
            private double balanceLowerLimit;
            private double balanceUpperLimit;
            private double incomeRate;
            private int tradeTimesLimit;
            private double priceLowerLimit;
            private double priceUpperLimit;
            private double upgradePercent;
            private double equityMargin;
            private int withdrawTimesLimit;
            private double withdrawChargeRate;
            private double minimalWithdrawAmount;
            private String summary;
            private String image;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            private String desc;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getBalanceLowerLimit() {
                return balanceLowerLimit;
            }

            public void setBalanceLowerLimit(double balanceLowerLimit) {
                this.balanceLowerLimit = balanceLowerLimit;
            }

            public double getBalanceUpperLimit() {
                return balanceUpperLimit;
            }

            public void setBalanceUpperLimit(double balanceUpperLimit) {
                this.balanceUpperLimit = balanceUpperLimit;
            }

            public double getIncomeRate() {
                return incomeRate;
            }

            public void setIncomeRate(double incomeRate) {
                this.incomeRate = incomeRate;
            }

            public int getTradeTimesLimit() {
                return tradeTimesLimit;
            }

            public void setTradeTimesLimit(int tradeTimesLimit) {
                this.tradeTimesLimit = tradeTimesLimit;
            }

            public double getPriceLowerLimit() {
                return priceLowerLimit;
            }

            public void setPriceLowerLimit(double priceLowerLimit) {
                this.priceLowerLimit = priceLowerLimit;
            }

            public double getPriceUpperLimit() {
                return priceUpperLimit;
            }

            public void setPriceUpperLimit(double priceUpperLimit) {
                this.priceUpperLimit = priceUpperLimit;
            }

            public double getUpgradePercent() {
                return upgradePercent;
            }

            public void setUpgradePercent(double upgradePercent) {
                this.upgradePercent = upgradePercent;
            }

            public double getEquityMargin() {
                return equityMargin;
            }

            public void setEquityMargin(double equityMargin) {
                this.equityMargin = equityMargin;
            }

            public int getWithdrawTimesLimit() {
                return withdrawTimesLimit;
            }

            public void setWithdrawTimesLimit(int withdrawTimesLimit) {
                this.withdrawTimesLimit = withdrawTimesLimit;
            }

            public double getWithdrawChargeRate() {
                return withdrawChargeRate;
            }

            public void setWithdrawChargeRate(double withdrawChargeRate) {
                this.withdrawChargeRate = withdrawChargeRate;
            }

            public double getMinimalWithdrawAmount() {
                return minimalWithdrawAmount;
            }

            public void setMinimalWithdrawAmount(double minimalWithdrawAmount) {
                this.minimalWithdrawAmount = minimalWithdrawAmount;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
