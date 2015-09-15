package hzyj.guangda.student.activity;

import org.apache.http.Header;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.activity.login.LoginActivity;
import hzyj.guangda.student.activity.setting.MyAccountActivity;
import hzyj.guangda.student.activity.setting.MyCardActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetCarModelResponse;
import hzyj.guangda.student.response.GetWalletInfo;
import hzyj.guangda.student.util.MySubResponseHandler;

public class MyAccount extends TitlebarActivity{
	private LinearLayout llYue;
	private LinearLayout llXiaoBaQuan;
	private TextView tvYue;
	private TextView tvXiaoBaQuan,tv_money;
	private LinearLayout llCoin;
	private TextView tvCoin;
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.my_account;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		llYue = (LinearLayout)findViewById(R.id.ll_yue);
		llXiaoBaQuan = (LinearLayout)findViewById(R.id.ll_xiaobaquan);
		tvYue = (TextView)findViewById(R.id.tv_yue);
		tvXiaoBaQuan = (TextView)findViewById(R.id.tv_xiaobaquan);
		llCoin = (LinearLayout)findViewById(R.id.ll_xiaobabi);
		tvCoin = (TextView)findViewById(R.id.tv_xiaobabi);
		tv_money=(TextView)findViewById(R.id.tv_money);
		
		
		
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		llYue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
				startMyActivity(LoginActivity.class);
			} else {
				startMyActivity(MyAccountActivity.class);
				}
			}
		});
		
		llXiaoBaQuan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid())) {
					startMyActivity(LoginActivity.class);
				} else {
					startMyActivity(MyCardActivity.class);
				}
			}
		});
		
		llCoin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startMyActivity(ActivityMyCoins.class);
			}
		});
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		setCenterText("我的钱包");

	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		getMessage();
	}
	
	
	private void getMessage()
	{
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, GetWalletInfo.class, new MySubResponseHandler<GetWalletInfo>() {
			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETSTUDENTWALLETINFO");
				requestParams.add("studentid",GuangdaApplication.mUserInfo.getStudentid());
				return requestParams;
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					GetWalletInfo baseReponse) {
				// TODO Auto-generated method stub
				tvXiaoBaQuan.setText(""+baseReponse.getCouponsum());
				tvCoin.setText(""+baseReponse.getCoinsum());
				tvYue.setText(""+baseReponse.getMoney());
				GuangdaApplication.mUserInfo.setMoney(""+baseReponse.getMoney());
				tv_money.setText("金额"+baseReponse.getConsumeMoney()+"元"+" "+"小巴币"+baseReponse.getConsumeCoin()+"枚"+" "+"小巴券"+baseReponse.getConsumeCoupon()+"张");
				
			}
			
		});
		
	
		
	}


}
