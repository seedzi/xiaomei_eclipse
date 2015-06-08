package com.weixin.paydemo.wxapi;




import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiaomei.yanyu.R;
import com.xiaomei.yanyu.Payment.WeiXinPayManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
    
    private TextView infoTv;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, WeiXinPayManager.getInstance().getAppId());

        api.handleIntent(getIntent(), this);
        
        infoTv = (TextView) findViewById(R.id.info);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
//		switch (resp.getType() ) {
//		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//		case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
//		case ConstantsAPI.COMMAND_SENDAUTH:
//		case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
//		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//		case ConstantsAPI.COMMAND_UNKNOWN:
//			break;
//		case ConstantsAPI.COMMAND_PAY_BY_WX:
//			break;
//		default:
//			break;
//		}
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errStr +";code=" + String.valueOf(resp.errCode)));
			builder.show();
		}
	}
}