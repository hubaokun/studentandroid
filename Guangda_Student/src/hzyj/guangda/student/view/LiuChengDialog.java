package hzyj.guangda.student.view;

import java.util.List;

import hzyj.guangda.student.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.library.llj.base.BaseDialog;

public class LiuChengDialog extends BaseDialog {
	private ImageView imgClose;
	private TextView tvTitle;
	private TextView tvTips;
	private LinearLayout llMessage;

	public LiuChengDialog(Context context) {
		super(context,R.style.dim_dialog);
		// TODO Auto-generated constructor stub
	}
	public LiuChengDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public LiuChengDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.show_liucheng_detail_dialog;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		imgClose = (ImageView)findViewById(R.id.iv_close);
		tvTips = (TextView)findViewById(R.id.tv_tips);
		tvTitle = (TextView)findViewById(R.id.show_title);
		llMessage = (LinearLayout)findViewById(R.id.ll_message);
		imgClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}
	
	public void setTitle(String title)
	{
		tvTitle.setText(title);
	}
	
	public void setTips(String tips)
	{
		if (!"".equals(tips))
		{
			tvTips.setVisibility(View.VISIBLE);
			tvTips.setText(tips);
		}else{
			tvTips.setVisibility(View.GONE);
		}
	}
	
	public void setMessage(String[] message,String[] messageTitle)
	{
		for (int i=0;i<message.length;i++)
		{
			if (messageTitle!=null)
			{
				TextView tvMessageTitle = new TextView(mContext);
				tvMessageTitle.setText(messageTitle[i]);
				TextPaint tp = tvMessageTitle.getPaint(); 
				tp.setFakeBoldText(true); 
				llMessage.addView(tvMessageTitle);
			}
			TextView tvMessage = new TextView(mContext);
			tvMessage.setText(message[i]);
			llMessage.addView(tvMessage);
		}
	}
	

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.CENTER);
		setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				llMessage.removeAllViews();
			}
		});
		setCanceledOnTouchOutside(true);
	}

}
