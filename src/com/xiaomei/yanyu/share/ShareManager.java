package com.xiaomei.yanyu.share;


import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.xiaomei.yanyu.R;

public class ShareManager {
	
	private static ShareManager mInstance;
	private ShareManager(){}
	public static ShareManager getInstance(){
		if(mInstance==null){
			mInstance = new ShareManager();
		}
		return mInstance;
	}
	
	private Activity mAc;
	private String mTargetUrl;
	private String mTitle;
	private String mContent;
    private UMSocialService mController = UMServiceFactory.getUMSocialService(Constants.DESCRIPTOR);
	/**
	 * 初始化
	 * @param ac 当前ac 
	 * @param url 分享target url
	 * @param title 分享tilte
	 * @param content 分享内容
	 */
    public void init(Activity ac,String url,String title,String content){
    	mAc = ac;
    	mTargetUrl = url;
    	mTitle = title;
    	mContent = content;
    	configPlatforms();
    	setShareContent();
    }
    /**
     * 配置分享平台参数</br>
     */
    private void configPlatforms() {
        // 添加新浪SSO授权
//        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        // 添加QQ、QZone平台
        addQQQZonePlatform();
        // 添加微信、微信朋友圈平台
        addWXPlatform();
    }
	
	
    /**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @return
     */
    private void addQQQZonePlatform() {
        String appId = "1104506536";
        String appKey = "mHZPDXRIOLTUjVji";
        // 添加QQ支持
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mAc,
                appId, appKey);
//        qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
        qqSsoHandler.addToSocialSDK();

        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mAc, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }
    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    private void addWXPlatform() {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx67f54f6d2c0d66c8";
        String appSecret = "912a0d27dd139295d96cd4b63977d22c";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(mAc, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(mAc, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }
	
	
    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    private void setShareContent() {

        UMImage localImage = new UMImage(mAc, R.drawable.xiaomei_log);

        //微信分享内容设置
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent
                .setShareContent(mContent);
        weixinContent.setTitle(mTitle);
        weixinContent.setTargetUrl(mTargetUrl);
        weixinContent.setShareMedia(localImage);
        mController.setShareMedia(weixinContent);

        // 朋友圈分享内容设置
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia
                .setShareContent(mContent);
        circleMedia.setTitle(mTitle);
        circleMedia.setShareMedia(localImage);
        circleMedia.setTargetUrl(mTargetUrl);
        mController.setShareMedia(circleMedia);

        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(mContent);
        qzone.setTargetUrl(mTargetUrl);
        qzone.setTitle(mTitle);
        qzone.setShareMedia(localImage);
        mController.setShareMedia(qzone);

        //QQ分享内容设置
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(mContent);
        qqShareContent.setTitle(mTitle);
        qqShareContent.setShareMedia(localImage);
        qqShareContent.setTargetUrl(mTargetUrl);
        mController.setShareMedia(qqShareContent);


        //新浪微博分享设置
        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent
                .setShareContent(mContent);
        sinaContent.setShareImage( localImage);
        mController.setShareMedia(sinaContent);

    }


   
	public void performShare(SHARE_MEDIA platform) {
		mController.postShare(mAc, platform,
				new SnsPostListener() {
					@Override
					public void onStart() {
					}
					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode,
							SocializeEntity entity) {
						String showText = platform.toString();
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							showText += "平台分享成功";
						} else {
							showText += "平台分享失败";
						}
						Toast.makeText(mAc, showText,
								Toast.LENGTH_SHORT).show();
					}
				});
	}
}
