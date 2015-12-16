package hzyj.guangda.student.activity.setting;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.activity.ActivityRechargePay;
import hzyj.guangda.student.alipay.AliPayTask;
import hzyj.guangda.student.alipay.Pparams;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.RechargeResponse;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.ParseUtilLj;
import com.loopj.android.http.RequestParams;

/**
 * 请输入金额(充值界面)
 * 
 * @author liulj
 * 
 */
public class RechargeActivity extends TitlebarActivity {
	private EditText mMoneyEt;
	private TextView tvTitleRight;
	private TextView tv_to_pay;

	@Override
	public int getLayoutId() {
		return R.layout.recharge_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mMoneyEt = (EditText) findViewById(R.id.et_money);
		tvTitleRight = (TextView)findViewById(R.id.tv_right_text);
		
		tv_to_pay=(TextView) findViewById(R.id.tv_to_pay);
	}

	@Override
	public void addListeners() {
//		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (TextUtils.isEmpty(mMoneyEt.getText().toString().trim())) {
//					showToast("请输入金额");
//					return;
//				}
//				if (ParseUtilLj.parseInt(mMoneyEt.getText().toString().trim()) <= 0) {
//					showToast("请输入正确的金额");
//					return;
//				}
//				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, RechargeResponse.class, new MySubResponseHandler<RechargeResponse>() {
//					@Override
//					public void onStart() {
//						super.onStart();
//						mLoadingDialog.show();
//						tvTitleRight.setEnabled(false);
//					}
//
//					@Override
//					public RequestParams setParams(RequestParams requestParams) {
//						requestParams.add("action", "Recharge");
//						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
//						requestParams.add("amount", mMoneyEt.getText().toString().trim());
//						requestParams.add("resource","0");
//						return requestParams;
//					}
//
//					@Override
//					public void onFinish() {
//						mLoadingDialog.dismiss();
//						tvTitleRight.setEnabled(true);
//					}
//
//					@Override
//					public void onSuccess(int statusCode, Header[] headers, RechargeResponse baseReponse) {
//						Pparams mPparams = new Pparams();
//						mPparams.setBody(baseReponse.getBody());
//						mPparams.setNotify_url(baseReponse.getNotify_url());
//						mPparams.setOut_trade_no(baseReponse.getOut_trade_no());
//						mPparams.setPartner(baseReponse.getPartner());
//						mPparams.setRsakey(baseReponse.getPrivate_key());
//						mPparams.setSeller_id(baseReponse.getSeller_id());
//						mPparams.setSubject(baseReponse.getSubject());
//						mPparams.setTotal_fee(baseReponse.getTotal_fee());
//						// mPparams.setTotal_fee(0.01 + "");
//						AliPayTask mAliPayTask = new AliPayTask(mBaseFragmentActivity, mPparams, baseReponse.getOut_trade_no());
//						mAliPayTask.Execute();
//					}
//
//				});
//
//			}
//		});
//		
		//跳转选择支付方式
		
		tv_to_pay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (TextUtils.isEmpty(mMoneyEt.getText().toString().trim())) {
					showToast("请输入金额");
					return;
				}
				if (ParseUtilLj.parseInt(mMoneyEt.getText().toString().trim()) <= 0) {
					showToast("请输入正确的金额");
					return;
				}
				Intent intent=new Intent(RechargeActivity.this,ActivityRechargePay.class);
				Bundle bundle=new Bundle();
				bundle.putString("money",Integer.valueOf(mMoneyEt.getText().toString().trim())+"");
				intent.putExtras(bundle);
				intent.putExtra("wPage","2");
				startActivity(intent);
				
				
			}
		});
		
		
	}

	@Override
	public void initViews() {
		setCenterText("请输入金额");
		//setRightText("提交", 10);

	}

	@Override
	public void requestOnCreate() {

	}
}
