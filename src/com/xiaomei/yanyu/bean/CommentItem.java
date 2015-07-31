package com.xiaomei.yanyu.bean;

public class CommentItem {

    private String markService;
    private String userType;
    private String createdate;
    private String userid;
    private String isDelete;
    private String markEnvironment;
    private String type;
    private String avatar;
    private String id;
    private String markEffect;
    private String username;
    private String typeid;
    private String replyRoot;
    private String reply;
    private String content;
    private ShareSubcomment[] subcoments;

    public String getMarkService() {
        return markService;
    }
    public void setMarkService(String markService) {
        this.markService = markService;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
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
    public String getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
    public String getMarkEnvironment() {
        return markEnvironment;
    }
    public void setMarkEnvironment(String markEnvironment) {
        this.markEnvironment = markEnvironment;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMarkEffect() {
        return markEffect;
    }
    public void setMarkEffect(String markEffect) {
        this.markEffect = markEffect;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTypeid() {
        return typeid;
    }
    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }
    public String getReplyRoot() {
        return replyRoot;
    }
    public void setReplyRoot(String replyRoot) {
        this.replyRoot = replyRoot;
    }
    public String getReply() {
        return reply;
    }
    public void setReply(String reply) {
        this.reply = reply;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    public ShareSubcomment[] getSubcomments() {
        return subcoments;
    }

    public void setSubcomments(ShareSubcomment[] subcomments) {
        this.subcoments = subcomments;
    }

    @Override
    public String toString() {
        return "CommentItem [markService=" + markService + ", userType="
                + userType + ", createdate=" + createdate + ", userid="
                + userid + ", isDelete=" + isDelete + ", markEnvironment="
                + markEnvironment + ", type=" + type + ", avatar=" + avatar
                + ", id=" + id + ", markEffect=" + markEffect + ", username="
                + username + ", typeid=" + typeid + ", replyRoot=" + replyRoot
                + ", reply=" + reply + ", content=" + content + "]";
    }
    
    /**
     * 存储列表评论总数，临时这么处理
     */
    private int total;
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    
}
/*
{
    "msg": [
        {
            "mark_service": "5",
            "user_type": null,
            "createdate": "1428474248",
            "userid": "19062244",
            "is_delete": "0",
            "mark_environment": "5",
            "type": "goods",
            "avatar": null,
            "content": "是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，",
            "id": "19000002",
            "mark_effect": "5",
            "username": "托拉夫斯基",
            "typeid": "1010",
            "reply_root": "0",
            "reply": "0"
        },
        {
            "mark_service": "5",
            "user_type": null,
            "createdate": "1428483514",
            "userid": "19062244",
            "is_delete": "0",
            "mark_environment": "5",
            "type": "goods",
            "avatar": "http://bcs.duapp.com/drxiaomei/dev%2Fjingxuan_02.gif",
            "content": "是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，是吧，效果很赞，而且这家机构服务很好，很负责任，问问题都会很耐心回答的，",
            "id": "19000012",
            "mark_effect": "5",
            "username": "托拉夫斯基",
            "typeid": "1010",
            "reply_root": "0",
            "reply": "0"
        },
        {
            "mark_service": null,
            "user_type": "10",
            "createdate": "1429629439",
            "userid": "2",
            "is_delete": null,
            "mark_environment": null,
            "type": "goods",
            "avatar": "http://bcs.duapp.com/drxiaomei/dev%2Fjingxuan_02.gif",
            "content": "hehe",
            "id": "19000013",
            "mark_effect": null,
            "username": "张飞荣",
            "typeid": "1010",
            "reply_root": "19000012",
            "reply": "19000012"
        },
        {
            "mark_service": null,
            "user_type": "10",
            "createdate": "1429629672",
            "userid": "2",
            "is_delete": null,
            "mark_environment": null,
            "type": "goods",
            "avatar": "http://bcs.duapp.com/drxiaomei/dev%2Fjingxuan_02.gif",
            "content": "123",
            "id": "19000014",
            "mark_effect": null,
            "username": "张飞荣",
            "typeid": "1010",
            "reply_root": "19000002",
            "reply": "19000002"
        }
    ],
    "code": 0
}*/