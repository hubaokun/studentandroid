package hzyj.guangda.student.util;

import org.apache.http.Header;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.activity.login.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.common.library.llj.base.BaseApplication;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.MyResponseHandler;
import com.common.library.llj.utils.ToastUtilLj;

/**
 * 
 * @author liulj
 * 
 * @param <T>
 */
public abstract class MySubResponseHandler<T extends BaseReponse> extends MyResponseHandler<T> {
	public MySubResponseHandler() {
		super();
		mRequestParams.add("token", GuangdaApplication.mUserInfo.getToken());
	}

	@Override
	public void onNotSuccess(Context context, int statusCode, Header[] headers, T baseReponse) {
		if (baseReponse.getCode() == 95) {
			//
			ToastUtilLj.show(BaseApplication.mInstance, baseReponse.getMessage());
			//
			GuangdaApplication.mUserInfo.clearUserInfo();
			//
			Intent intent = new Intent(context, LoginActivity.class);
			// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			((Activity) context).startActivity(intent);
			//
		}
	}
}
