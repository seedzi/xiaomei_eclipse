package com.yuekuapp;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.widget.Toast;

/**
 * User: sunpengshuai Date: 13-6-27 Time: ����7:55
 */
public class TipTool {

	private static Toast mToast;

	public static void onCreateToastDialog(Context context, String message) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> infos = am.getRunningTasks(1);
		if (infos != null
				&& infos.get(0) != null
				&& context.getPackageName().equals(
						infos.get(0).baseActivity.getPackageName())) {

			if (mToast != null)
				mToast.cancel();
			int duration = Toast.LENGTH_SHORT;
			if (message.length() > 15) {
				duration = Toast.LENGTH_LONG;// ����ַ����Ƚϳ�����ô��ʾ��ʱ��Ҳ��һЩ��
			}
			mToast = Toast.makeText(context, message, duration);
			mToast.show();
		}
	}
}
