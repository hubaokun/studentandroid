package hzyj.guangda.student.activity.personal;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.event.Update;
import hzyj.guangda.student.response.ChangeAvatarResponse;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.GetPhotoDialog;
import hzyj.guangda.student.view.GetPhotoDialog.OnGetFileListener;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

/**
 * 
 * @author liulj
 * 
 */
public class UserInfoActivity extends TitlebarActivity {
	private ImageView mHeaderIv;
	private TextView mNameTv;
	private RatingBar mScoreRb;
	private int mWidth = (int) (204 * GuangdaApplication.DISPLAY_WIDTH / 640.0);
	private GetPhotoDialog mGetPhotoDialog;

	@Override
	public int getLayoutId() {
		return R.layout.user_info_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mHeaderIv = (ImageView) findViewById(R.id.iv_header);
		mHeaderIv.getLayoutParams().height = mWidth;
		mHeaderIv.getLayoutParams().width = mWidth;
		mNameTv = (TextView) findViewById(R.id.tv_name);
		mScoreRb = (RatingBar) findViewById(R.id.rb_star);
	}

	@Override
	public void addListeners() {
		mHeaderIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mGetPhotoDialog.show();
			}
		});
	}

	@Override
	public void initViews() {
		mGetPhotoDialog = new GetPhotoDialog(this);
		mCommonTitlebar.getCenterTextView().setText("个人信息");

	}

	@Override
	protected void onResume() {
		super.onResume();
		// 头像
		loadHeadImage(GuangdaApplication.mUserInfo.getAvatarurl(), mWidth, mWidth, mHeaderIv);
		// 名字
		setText(mNameTv, GuangdaApplication.mUserInfo.getRealname());
		// 星级
		mScoreRb.setRating(5f);
	}

	@Override
	public void requestOnCreate() {

	}

	public void identity_info(View view) {
		// 基本信息
		startMyActivity(IdentityInfoActivity.class);
	}

	public void car_info(View view) {
		// 学驾信息
		startMyActivity(CarInfoActivity.class);
	}

	public void personal_info(View view) {
		// 个人信息
		startMyActivity(PersonalInfoActivity.class);
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		mGetPhotoDialog.handleResultBySystemCrop(arg0, arg1, arg2, new OnGetFileListener() {
			@Override
			public void AfterGetFile(final File file) {
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, ChangeAvatarResponse.class, new MySubResponseHandler<ChangeAvatarResponse>() {
					@Override
					public void onStart() {
						mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {
						requestParams.add("action", "ChangeAvatar");
						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
						try {
							requestParams.put("avatar", file);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						return requestParams;
					}

					@Override
					public void onFinish() {
						mLoadingDialog.dismiss();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, ChangeAvatarResponse baseReponse) {
						showToast("修改头像成功！");
						GuangdaApplication.mUserInfo.setAvatarurl(baseReponse.getAvatarurl());
						loadHeadImage(GuangdaApplication.mUserInfo.getAvatarurl(), mWidth, mWidth, mHeaderIv);
						EventBus.getDefault().post(new Update("UserInfo"));
					}

				});
			}
		});
	}
}
