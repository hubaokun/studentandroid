package net.sourceforge.simcpux;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import hzyj.guangda.studnet.wxapi.key;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		final IWXAPI api = WXAPIFactory.createWXAPI(context, null);

		api.registerApp(key.appid);
	}
}
