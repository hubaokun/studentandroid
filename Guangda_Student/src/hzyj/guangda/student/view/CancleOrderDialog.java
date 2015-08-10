package hzyj.guangda.student.view;



import hzyj.guangda.student.R;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.common.library.llj.base.BaseDialog;

public class CancleOrderDialog extends BaseDialog{
	private Activity mcontext;
	private String tag,mOrderid,studentid;
	private Button btn_cancle,btn_cancle_order;
	private CoachSrueDialog coachsure;
	

	public CancleOrderDialog(Context context) {
		// TODO Auto-generated constructor stub
		super(context, R.style.dim_dialog);
	}
	
	public CancleOrderDialog(Activity context) {
		super(context, R.style.dim_dialog);
		mcontext=context;
	}
	public CancleOrderDialog(Activity context,String mOrderid,String studentid) {
		super(context, R.style.dim_dialog);
		mcontext=context;
		this.mOrderid=mOrderid;
		this.studentid=studentid;
	}
	
   

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.cancle_order;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		btn_cancle_order=(Button)findViewById(R.id.btn_cancle_order);
		btn_cancle=(Button)findViewById(R.id.btn_cancle);
		
		btn_cancle_order.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//弹出 取消框
				coachsure=new CoachSrueDialog(mcontext,mOrderid,studentid);
				coachsure.show();
				dismiss();
				
			}
			
		});
		
		btn_cancle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.BOTTOM);
	}

}
