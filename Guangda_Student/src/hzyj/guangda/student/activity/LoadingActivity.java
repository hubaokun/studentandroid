package hzyj.guangda.student.activity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.common.UserInfo;
import hzyj.guangda.student.event.Update;
import hzyj.guangda.student.response.LoginResponse;
import hzyj.guangda.student.response.getStudentAdvertisement;
import hzyj.guangda.student.util.DataBaseUtil;
import hzyj.guangda.student.util.MySubResponseHandler;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import db.DBManager;

import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.PackageMangerUtilLj;
import com.igexin.sdk.PushManager;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;
import hzyj.guangda.student.util.ImageLoadSaveTask;;
/**
 * 加载界面
 * 
 * @author liulj
 * 
 */
public class LoadingActivity extends BaseFragmentActivity {
	private ImageView mLoadingIv;
	private LinearLayout mContentLi;
//	private TextView mVersionNameTv;
	public static UserInfo mUserInfo;
	private Context mContext;

	@Override
	public int getLayoutId() {
		return R.layout.loading_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		//初始化个推
		PushManager.getInstance().initialize(this.getApplicationContext());
		mLoadingIv = (ImageView) findViewById(R.id.iv_loading);
		mContentLi = (LinearLayout) findViewById(R.id.li_content);
//		mVersionNameTv = (TextView) findViewById(R.id.tv_version_name);
	}

	@Override
	public void addListeners() {

	}

	/**
	 * 初始化动画并执行动画
	 */
	private void initAnimation() {
		mLoadingIv.setVisibility(View.VISIBLE);
		AlphaAnimation mHideAnimation = new AlphaAnimation(0f, 1f);
		mHideAnimation.setDuration(2000);
		mHideAnimation.setFillAfter(true);
		mHideAnimation.setInterpolator(new DecelerateInterpolator());
		mHideAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mLoadingIv.postDelayed(new Runnable() {

					@Override
					public void run() {
						getAdvertisement();
					}
				}, 2000);
			}
		});
		mLoadingIv.startAnimation(mHideAnimation);
	}
	
	
	private void getAdvertisement(){
		AsyncHttpClientUtil.get().post(this, Setting.SYSTEM_URL, getStudentAdvertisement.class, new MySubResponseHandler<getStudentAdvertisement>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "CHECKCONFIG");
				return requestParams;
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, getStudentAdvertisement baseReponse) {
				if (baseReponse.getCode() == 1)
				{
					if (baseReponse.getConfig().getStudent_advertisement_flag() == 1)
					{
						try {
				               new ImageLoadSaveTask(LoadingActivity.this, mLoadingIv).execute(baseReponse.getConfig().getStudent_advertisement_url());
				           } catch (Exception e) {
				               e.printStackTrace();
				           }
						Timer timer = new Timer(); 
						TimerTask task = new TimerTask() {  
						    @Override  
						    public void run() {   
								startMyActivity(MapHomeActivity.class);
								finish();
						     }
						 };
						timer.schedule(task, 1000 * 3); //5秒后
					}else{
						startMyActivity(MapHomeActivity.class);
						finish();
					}
				}else{
					if (baseReponse.getMessage()!=null)
					{
						showToast(baseReponse.getMessage());
					}
				}
				//uploadPushInfo();
			}
		});
	}

	@Override
	public void initViews() {
		mContext=this;
		initAnimation();
//		mVersionNameTv.setText("版本：" + PackageMangerUtilLj.getAppVersionName(this));
		
		 boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	        if(hasSDCard){
	            copyDataBaseToPhone();
	        }else{
	            Toast.makeText(mContext, "未检测到SDCard", Toast.LENGTH_LONG).show();
	        }
	}
	
	private void copyDataBaseToPhone() {
		
		
		
        DataBaseUtil util = new DataBaseUtil(LoadingActivity.this);
        
        if (GuangdaApplication.mUserInfo.getIsFirst())  //判断是否是第一次启动应用，如果是则将数据库清除
        {
        	try {
				util.deleteDataBase(mContext);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        // 判断数据库是否存在
        boolean dbExist = util.checkDataBase();
        if (dbExist) {
        } else {// 不存在就把raw里的数据库写入手机
            try {
                util.copyDataBase();
                GuangdaApplication.mUserInfo.setIsFirst();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

	
	

	@Override
	public void requestOnCreate() {

		

	}

	@Override
	protected void onPause() {
		super.onPause();
	}
}
