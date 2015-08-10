package hzyj.guangda.student.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.R;

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
				finish();
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
}
