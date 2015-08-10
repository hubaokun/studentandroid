package hzyj.guangda.student.view;

import hzyj.guangda.student.R;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.common.library.llj.base.BaseDialog;

public class BookSuccessDialog extends BaseDialog {
	private ImageView imgClose;
	private Button btnSure;

	public BookSuccessDialog(Context context) {
		super(context,R.style.dim_dialog);
		// TODO Auto-generated constructor stub
	}
	
	public BookSuccessDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public BookSuccessDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.book_success_dialog;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		imgClose = (ImageView)findViewById(R.id.iv_close);
		btnSure = (Button)findViewById(R.id.btn_sure);
		imgClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		
		btnSure.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
