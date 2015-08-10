package hzyj.guangda.student.activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.view.LiuChengDialog;


public class BookCoackActivity extends TitlebarActivity {
	private Button btnBookCoack;
	private LinearLayout llCaiLiao;
	private LinearLayout llBookKeMuYi,llKeMuYiKaoShi;
	private LiuChengDialog liucheng;
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.book_coach_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		btnBookCoack = (Button)findViewById(R.id.btn_book_coach);
		llCaiLiao = (LinearLayout)findViewById(R.id.ll_suoxucailiao);
		llBookKeMuYi = (LinearLayout)findViewById(R.id.ll_kemuyi);
		llKeMuYiKaoShi = (LinearLayout)findViewById(R.id.ll_lilunpeixun);
	}
	
	

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		llBookKeMuYi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				liucheng.setTitle("通过体检，科目一报名");
				liucheng.setTips("就近选择体检和报名地址");
				String[] message = getResources().getStringArray(R.array.kemuyi);
				String[] messagetitle = getResources().getStringArray(R.array.kemuyi_tips);
				liucheng.setMessage(message, messagetitle);
				liucheng.show();
			}
		});
		
		llCaiLiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				liucheng.setTitle("所需材料");
				liucheng.setTips("");
				String[] message = getResources().getStringArray(R.array.cailiao);
				liucheng.setMessage(message, null);
				liucheng.show();
			}
		});
		
		llKeMuYiKaoShi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				liucheng.setTitle("参加理论培训，通过科目一考试");
				liucheng.setTips("");
				String[] message = getResources().getStringArray(R.array.lilunpeixun);
				liucheng.setMessage(message, null);
				liucheng.show();
			}
		});
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		setCenterText("自助报名");
		liucheng = new LiuChengDialog(mBaseFragmentActivity);
		
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		
	}

}
