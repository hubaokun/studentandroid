package hzyj.guangda.student.activity;

import org.apache.http.Header;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.ToastUtilLj;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.R;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.CoachListResponse;
import hzyj.guangda.student.response.GetSendInviteCodeResult;
import hzyj.guangda.student.util.MySubResponseHandler;

public class ActivityInputRecord extends TitlebarActivity {
	private EditText etInportRecord;
	private Button btnSure;
	private LinearLayout llGoWay;
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_input_record;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		etInportRecord = (EditText)findViewById(R.id.et_input_record);
		btnSure = (Button)findViewById(R.id.btn_record);
		llGoWay = (LinearLayout)findViewById(R.id.ll_record);
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		llGoWay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(GuangdaApplication.isToBaoMing){
					startMyActivity(BookDriveActivity.class);
					finish();
				}else{
					finish();
				}
			}
		});
		
		btnSure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(!TextUtils.isEmpty(etInportRecord.getText())){
					inputRecord();
				}else{
					showToast("您输入的推荐码不能为空，请重新输入");
				}
				
			}
		});
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		setCenterText("推荐码");
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		
	}
	
	public void inputRecord(){
	   
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.CRECOMM, GetSendInviteCodeResult.class, new MySubResponseHandler<GetSendInviteCodeResult>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.put("action", "CHEAKINVITECODE");
				requestParams.put("InvitedPeopleid",Integer.parseInt(GuangdaApplication.mUserInfo.getStudentid()));// CoachApplication.getInstance().getUserInfo().getCoachid()
				requestParams.put("InviteCode", etInportRecord.getText().toString());
				requestParams.put("type",2);
				return requestParams;
			}
			@Override
			public void onFinish() {
				
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetSendInviteCodeResult baseReponse) {
				if(baseReponse.getInviteCode()==1){
					if (baseReponse.getIsRecommended()==1)
					{
						GuangdaApplication.isInvited=1;
						if(GuangdaApplication.isToBaoMing){
							startMyActivity(BookDriveActivity.class);
							finish();
						}
						else{
							finish();	
						}
						
					}else{
						showToast("您输入的推荐码有误，请重新输入");
					}
				}
				else{
					showToast("您输入的推荐码格式错误，请重新输入");
				}
				
			}
		});
	}
}
