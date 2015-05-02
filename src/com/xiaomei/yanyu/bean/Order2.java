package com.xiaomei.yanyu.bean;

public class Order2 {

    private String modifydate;
    private String goodsPay;
    private String status;
    private String createdate;
    private String userid;
    private String goodsName;
    private String paydate;
    private String orderId;
    private String goodsImg;
    private String goodsAmount;
    private String id;
    private String orderAmount;
    private String username;
    private String goodsPrice;
    private String goodsStatus;
    private String payType;
    private String orderNum; //订单号
    private String goodsId;
    private String goodsCode;
    private UserInfo userInfo;
    public String getModifydate() {
        return modifydate;
    }
    public void setModifydate(String modifydate) {
        this.modifydate = modifydate;
    }
    public String getGoodsPay() {
        return goodsPay;
    }
    public void setGoodsPay(String goodsPay) {
        this.goodsPay = goodsPay;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCreatedate() {
        return createdate;
    }
    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public String getPaydate() {
        return paydate;
    }
    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getGoodsPrice() {
        return goodsPrice;
    }
    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    public String getGoodsStatus() {
        return goodsStatus;
    }
    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }
    public String getPayType() {
        return payType;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    public String getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
    public String getGoodsCode() {
        return goodsCode;
    }
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getGoodsImg() {
        return goodsImg;
    }
    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
    public String getGoodsAmount() {
        return goodsAmount;
    }
    public void setGoodsAmount(String goodsAmount) {
        this.goodsAmount = goodsAmount;
    }
    public String getOrderAmount() {
        return orderAmount;
    }
    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfo{
        private String mobile;
        private String passport;
        public String getMobile() {
            return mobile;
        }
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        public String getPassport() {
            return passport;
        }
        public void setPassport(String passport) {
            this.passport = passport;
        }
    }
    /*
    {
        "msg": {
            "modifydate": 1428719263,
            "goods_pay": false,
            "status": 1,
            "createdate": 1428719263,
            "userid": "19062271",
            "goods_name": false,
            "paydate": 0,
            "userinfo": "{\"mobile\":\"15010768102\",\"passport\":\"123\"}",
            "order_id": 19062279,
            "goods_img": false,
            "goods_amount": false,
            "id": 19062279,
            "order_amount": false,
            "username": "未定义",
            "goods_price": false,
            "goods_status": 0,
            "pay_type": 0,
            "order_num": "2015041110274333510",
            "goods_id": "1015",
            "goods_code": "1197"
        },
        "code": 0
    }\"15010768102\",\"passport\":\"123\"}",
            "order_id": 19062279,
            "goods_img": false,
            "goods_amount": false,
            "id": 19062279,
            "order_amount": false,
            "username": "未定义",
            "goods_price": false,
            "goods_status": 0,
            "pay_type": 0,
            "order_num": "2015041110274333510",
            "goods_id": "1015",
            "goods_code": "1197"
        },
        "code": 0
    }
    */
}
