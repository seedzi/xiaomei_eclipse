package net.sourceforge.simcpux;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xiaomei.yanyu.Payment.WeiXinPayManager;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		android.util.Log.d("111", "id = " + WeiXinPayManager.getInstance().getAppId());
		final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
		api.registerApp(WeiXinPayManager.getInstance().getAppId());
	}
}
