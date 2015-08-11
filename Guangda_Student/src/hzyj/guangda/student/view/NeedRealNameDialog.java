package hzyj.guangda.student.view;

import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.personal.IdentityInfoActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.common.library.llj.base.BaseDialog;

public class NeedRealNameDialog extends BaseDialog {
	private Button btnSetRealName;
	private Context mcontext;
	public NeedRealNameDialog(Context context) {
		super(context, R.style.dim_dialog);
		mcontext = context;
	}

	public NeedRealNameDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
		mcontext = context;
	}

	public NeedRealNameDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mcontext = context;
	}
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.dialog_need_real_name;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		btnSetRealName = (Button)findViewById(R.id.btn_set_real_name);
		btnSetRealName.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (mcontext,IdentityInfoActivity.class);
				mcontext.startActivity(intent);
				dismiss();
			}
		});
	}

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.CENTER);
	}

}
