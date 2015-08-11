package hzyj.guangda.student.receive;

import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.LoadingActivity;
import hzyj.guangda.student.view.MessageDialog;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.common.library.llj.base.BaseApplication;
import com.common.library.llj.utils.ActivityManagerUtilLj;
import com.igexin.sdk.PushConsts;

/**
 * 
 * @author liulj
 * 
 */
public class PushDemoReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			byte[] payload = bundle.getByteArray("payload");
			if (payload != null) {
				String data = new String(payload);
				JSONObject dataJson = null;
				try {
					dataJson = new JSONObject(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				if (dataJson != null) {
					int type = 0;
					String message = null;
					try {
						type = dataJson.getInt("type");
						message = dataJson.getString("message");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (ActivityManagerUtilLj.isApplicationInForeground(context)) {
						// 最前 HomeActivity
						if (BaseApplication.mActivityList.size() != 0) {
							MessageDialog messageDialog = new MessageDialog(BaseApplication.getCurrentActivity());
							messageDialog.setMessage(message);
							messageDialog.show();
						}
					} else {
						// 弹出通知，点击启动app
						NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
						NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
						mBuilder.setContentTitle("小巴学车学员端")// 设置通知栏标题
								.setContentText(message == null ? "您有新的消息" : message)// 设置通知栏显示内容
								.setAutoCancel(true)// 设置这个标志当用户单击面板就可以让通知将自动取消
								.setSmallIcon(R.drawable.ic_launcher);// 设置通知小ICON
						Intent intent1 = new Intent(context, LoadingActivity.class);
						intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
						PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
						mBuilder.setContentIntent(pendingIntent);
						Notification notification = mBuilder.build();
						notification.defaults = Notification.DEFAULT_SOUND;
						mNotificationManager.notify(56789, notification);// 定义新闻的通知ID为56789
					}
				}
			}
			break;
		}
	}
}
