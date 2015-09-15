package hzyj.guangda.student.view;

import org.apache.http.Header;

import com.common.library.llj.base.BaseDialog;
import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.CoachSrueDialog.onButtonClickListener;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

public class CancelComplaint  extends BaseDialog{

	public CancelComplaint(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	private ImageView mCloseIv;
	private Button btn_sure,btn_cancel;
	private Activity mcontext;
	private String mOrderid,studentid;
	private onButtonClickListener mclick;
	private PtrClassicFrameLayout mPtrClassicFrameLayout;
	private int state;

	public CancelComplaint(Activity context) {
		// TODO Auto-generated constructor stub
		super(context, R.style.dim_dialog);
	}
	public CancelComplaint(Activity context,String mOrderid,String studentid,PtrClassicFrameLayout mPtrClassicFrameLayout) {
		// TODO Auto-generated constructor stub
		super(context, R.style.dim_dialog);
		mcontext=context;
		this.studentid=studentid;
		this.mOrderid=mOrderid;
		this.mPtrClassicFrameLayout=mPtrClassicFrameLayout;
	}
	
	public CancelComplaint(Activity context,String mOrderid,String studentid) {
		// TODO Auto-generated constructor stub
		super(context, R.style.dim_dialog);
		mcontext=context;
		this.studentid=studentid;
		this.mOrderid=mOrderid;
		
	}

	public CancelComplaint(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		
		return R.layout.cancel_complaint_dialog;
	}
	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		btn_sure=(Button)findViewById(R.id.btn_sure);
		mCloseIv=(ImageView)findViewById(R.id.iv_close);
	    btn_cancel=(Button)findViewById(R.id.btn_cancel);
	    
	    btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		
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
				CancelSure();
				
			}
		});
	}
	
	

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.CENTER);
		
	}
	 
	public void sendata(String mOrderid,String studentid,PtrClassicFrameLayout mPtrClassicFrameLayout){
		this.studentid=studentid;
		this.mOrderid=mOrderid;
		this.mPtrClassicFrameLayout=mPtrClassicFrameLayout;
	}
	
	public void CancelSure(){
		
		AsyncHttpClientUtil.get().post(mContext, Setting.SORDER_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {
			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "CancelComplaint");
				requestParams.add("studentid", studentid);
				requestParams.add("orderid",mOrderid);
				return requestParams;
			}

			@Override
			public void onFinish() {
				
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
				if(mPtrClassicFrameLayout!=null){
					mPtrClassicFrameLayout.autoRefresh(true);
					dismiss();
				}else{
					mcontext.finish();
					dismiss();
				}
				
				
			}
		});
		
	}
	

}
