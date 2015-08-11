package hzyj.guangda.student.activity;

import org.apache.http.Header;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.order.MyOrderListActivity;
import hzyj.guangda.student.activity.personal.IdentityInfoActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.BookCoachReponse;
import hzyj.guangda.student.response.GetVerCodeResponse;
import hzyj.guangda.student.util.DialogConfirmListener;
import hzyj.guangda.student.util.DialogUtil;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.BookSuccessDialog;
import hzyj.guangda.student.view.ReserveNotSuccessDialog;
import hzyj.guangda.student.view.ReserveSuccessDialog;

import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TextUtilLj;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BookDriveActivity extends BaseFragmentActivity {
	private ImageView btnBack;
	private TextView etName;
	private TextView tvPhone;
	private Button btnBookCoach;
	private TextView tvAuto;
	private TextView tvNeedOne;
	private TextView tvNeedTwo;
	private TextView tvNeedThreed;
	private Dialog mDialog=null;
	private BookDriveActivity mActivity;
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.book_app_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		btnBack = (ImageView)findViewById(R.id.iv_map);
		//tvAuto = (TextView)findViewById(R.id.iv_filter);
		etName = (TextView)findViewById(R.id.et_book_name);
		tvPhone = (TextView)findViewById(R.id.et_book_phone);
		btnBookCoach = (Button)findViewById(R.id.btn_book_drive);
		tvNeedOne = (TextView)findViewById(R.id.tv_need_one);
		//tvNeedTwo = (TextView)findViewById(R.id.tv_need_two);
		tvNeedThreed = (TextView)findViewById(R.id.tv_need_threed);
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getRealname()))
		{
				etName.setText(GuangdaApplication.mUserInfo.getRealname());	
		}
		else{
			DialogUtil dUtil = new DialogUtil(new DialogConfirmListener(){

				@Override
				public void doConfirm(String str) {
					// TODO Auto-generated method stub
					if(mDialog!=null){
						Intent intent=new Intent(BookDriveActivity.this,IdentityInfoActivity.class);
						startActivity(intent);
						mDialog.dismiss();
					}
				}

				@Override
				public void doCancel() {
					// TODO Auto-generated method stub
					if(mDialog!=null){
						mDialog.dismiss();
					}
				}	
			});
			mDialog=dUtil.CallConfirmDialog("填写学员基本信息","","",mActivity, mDialog);
		}
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
//		tvAuto.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(BookDriveActivity.this,BookCoackActivity.class);
//				startActivity(intent);
//			}
//		});
		
		btnBookCoach.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
					@Override
					public void onStart() {
						mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {
						requestParams.add("action", "ENROLL");
						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
						//showToast(GuangdaApplication.mUserInfo.getStudentid().toString());
						return requestParams;
					}

					@Override
					public void onFinish() {
						mLoadingDialog.dismiss();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
						if (mLoadingDialog.isShowing())
						{
							mLoadingDialog.dismiss();
						}
						if (baseReponse.getCode()==1)
						{
							BookSuccessDialog bookSuccess = new BookSuccessDialog(mBaseFragmentActivity);
							bookSuccess.setCanceledOnTouchOutside(true); // dialog 点击外部消失
							bookSuccess.show();
						}else{
							showToast(baseReponse.getMessage());
						}
					}

					@Override
					public void onNotSuccess(Context context, int statusCode, Header[] headers, BaseReponse baseReponse) {
						if (mLoadingDialog.isShowing())
						{
						mLoadingDialog.dismiss();
						}
						showToast(baseReponse.getMessage());
						//new ReserveNotSuccessDialog(mBaseFragmentActivity).show();
					}
				});
			}
		});
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		mActivity = this;
		tvNeedOne.setText(getResources().getString(R.string.need_one));
		//tvNeedTwo.setText(getResources().getString(R.string.need_two));
		tvNeedThreed.setText(getResources().getString(R.string.need_threed));
		tvPhone.setText(GuangdaApplication.mUserInfo.getPhone());
		
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		
	}
}
