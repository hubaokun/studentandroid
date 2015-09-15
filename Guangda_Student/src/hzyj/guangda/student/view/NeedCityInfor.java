package hzyj.guangda.student.view;

import com.common.library.llj.base.BaseDialog;

import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.personal.IdentityInfoActivity;

public class NeedCityInfor extends BaseDialog {
	private Button btnSetCity;
	public NeedCityInfor(Context context) {
		super(context,R.style.dim_dialog);
		// TODO Auto-generated constructor stub
	}
	
	public NeedCityInfor(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public NeedCityInfor(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.dialog_need_city_and_name;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		btnSetCity = (Button)findViewById(R.id.btn_set_city);
		btnSetCity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (mContext,IdentityInfoActivity.class);
				mContext.startActivity(intent);
				dismiss();
			}
		});
	}

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.CENTER);
		setCanceledOnTouchOutside(false);
		setCancelable(false);
	}

}

