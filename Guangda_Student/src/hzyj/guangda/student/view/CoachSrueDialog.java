package hzyj.guangda.student.view;

import org.apache.http.Header;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetUnCompleteOrderResponse;
import hzyj.guangda.student.util.MySubResponseHandler;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.library.llj.base.BaseDialog;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.loopj.android.http.RequestParams;

public class CoachSrueDialog  extends BaseDialog{
	private ImageView mCloseIv;
	private Button btn_sure;
	private Activity mcontext;
	private String mOrderid,studentid;
	private onButtonClickListener mclick;
	private LinearLayout ll_coach_sure;
	private int state;

	public CoachSrueDialog(Activity context) {
		// TODO Auto-generated constructor stub
		super(context, R.style.dim_dialog);
	}
	public CoachSrueDialog(Activity context,String mOrderid,String studentid) {
		// TODO Auto-generated constructor stub
		super(context, R.style.dim_dialog);
		mcontext=context;
		this.studentid=studentid;
		this.mOrderid=mOrderid;
	}

	public CoachSrueDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		
		return R.layout.coach_sure;
	}
	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		btn_sure=(Button)findViewById(R.id.btn_sure);
		mCloseIv=(ImageView)findViewById(R.id.iv_close);
		
		mCloseIv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
				
			}
		});
		
		btn_sure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sendCoachSure();
				
			}
		});
	}
	
	

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.CENTER);
		
	}
	
	 public interface onButtonClickListener{
	        public void onregion(String message);
	    }
	 
	public void sendata(String mOrderid,String studentid,LinearLayout ll_coach_sure,int a){
		this.studentid=studentid;
		this.mOrderid=mOrderid;
		this.ll_coach_sure=ll_coach_sure;
		this.state=a;
	}
	
	public void sendCoachSure(){
	AsyncHttpClientUtil.get().post(mcontext, Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {

		@Override
		public RequestParams setParams(RequestParams requestParams) {
			requestParams.add("action", "CancelOrder");
			requestParams.add("studentid",studentid);
			requestParams.add("orderid",mOrderid);
			return requestParams;
		}
		
		@Override
		public void onFinish() {
			
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
			if(String.valueOf(baseReponse.getCode()).equals("1")){
				if(state==1){
					ll_coach_sure.setVisibility(View.VISIBLE);
				}else{
					mclick=(onButtonClickListener)mcontext;
					mclick.onregion("正在确认");
				}
				dismiss();
			}
			if(String.valueOf(baseReponse.getCode()).equals("2")){
				//取消失败 
				dismiss();
			}
		}
	});
	}


}
