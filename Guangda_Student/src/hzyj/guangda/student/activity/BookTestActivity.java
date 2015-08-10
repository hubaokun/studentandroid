package hzyj.guangda.student.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;

public class BookTestActivity extends TitlebarActivity {
	private LinearLayout llBookTest;
	private LinearLayout llBoolMoni;
	
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.book_test_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		llBookTest = (LinearLayout)findViewById(R.id.ll_book_test);
		llBoolMoni = (LinearLayout)findViewById(R.id.ll_book_moni);
		
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		llBookTest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String url = "http://www.hzti.com:9004/drv_web/index.do";
				 Uri u = Uri.parse(url);  
				 Intent it = new Intent(Intent.ACTION_VIEW, u);
				 BookTestActivity.this.startActivity(it); 
			}
		});
		
		llBoolMoni.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String url = "http://xqc.qc5qc.com/reservation";
				 Uri u = Uri.parse(url);  
				 Intent it = new Intent(Intent.ACTION_VIEW, u);
				 BookTestActivity.this.startActivity(it); 
			}
		});
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		setCenterText("在线预约");
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		
	}

}
