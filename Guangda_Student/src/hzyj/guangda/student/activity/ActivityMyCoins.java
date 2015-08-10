package hzyj.guangda.student.activity;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.daoshun.lib.listview.PullToRefreshScrollView;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.OnClick;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetStudentCoinList;
import hzyj.guangda.student.response.GetStudentCoinList.RecordList;
import hzyj.guangda.student.response.GetWalletInfo;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.util.NoScrollListView;

public class ActivityMyCoins extends TitlebarActivity {
	
	private TextView tvCoinsCount;
	private TextView tvCoinSchoolCount;
	private TextView tvCoinCoachCount;
	private TextView tvCoinXiaoBaCount;
	private TextView tvFreeCoins;
	private NoScrollListView lvCoins;
	private myCoinsAdapter myCoinsAda;
	private List<RecordList> studentRecordArray = new ArrayList<RecordList>();
	private TextView tvCoachName;
	private int studentId;
	private PullToRefreshScrollView pullToRefreshSl;
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.my_coins;
	}
	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		tvCoinsCount = (TextView)findViewById(R.id.tv_my_coins_count);
//	tvCoinSchoolCount = (TextView)findViewById(R.id.tv_coins_school_count);
//		tvCoinCoachCount = (TextView)findViewById(R.id.tv_coins_coach_count);
//		tvCoinXiaoBaCount = (TextView)findViewById(R.id.tv_coins_coach_count);
		lvCoins = (NoScrollListView)findViewById(R.id.lv_coins_detail);
		tvCoachName = (TextView)findViewById(R.id.tv_coach_name);
		tvFreeCoins=(TextView)findViewById(R.id.tv_freeze_bi);
		pullToRefreshSl = (PullToRefreshScrollView)findViewById(R.id.pull_re_scroll);
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startMyActivity(ActivityMyCoinRule.class);
			}
		});
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		setCenterText("我的小巴币");
		setRightText("使用规则",10, R.color.text_green);
		tvFreeCoins.setText("");
		tvFreeCoins.append("(小巴币冻结：");
		myCoinsAda = new myCoinsAdapter(mBaseApplication);
		lvCoins.setAdapter(myCoinsAda);
	}

	@Override
	public void requestOnCreate() {
		//lvCoins.setFocusable(false);
		studentId = Integer.valueOf(GuangdaApplication.mUserInfo.getStudentid());
		// TODO Auto-generated method stub
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, GetStudentCoinList.class, new MySubResponseHandler<GetStudentCoinList>() {
			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETSTUDENTCOINRECORDLIST");
				requestParams.add("studentid",studentId+"");
				return requestParams;
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					GetStudentCoinList baseReponse) {
				if (baseReponse.getCode() == 1)
				{
					tvCoinsCount.setText(baseReponse.getCoinnum()+"");
					tvFreeCoins.append(baseReponse.getFcoinsum()+"");
					tvFreeCoins.append("枚)");
					if (!TextUtils.isEmpty(baseReponse.getCoachname()))
					{
						tvCoachName.setText(baseReponse.getCoachname());
					}else{
						tvCoachName.setText("所属驾校");
					}
					if (baseReponse.getRecordlist()!=null&&baseReponse.getRecordlist().size()!=0)
					{
						studentRecordArray.addAll(baseReponse.getRecordlist());
						myCoinsAda.notifyDataSetChanged();
					}
				}else{
					if (TextUtils.isEmpty(baseReponse.getMessage()))
					{
						showToast(baseReponse.getMessage());
					}
				}
				// TODO Auto-generated method stub
			}
		});
	}
	
	public class myCoinsAdapter extends BaseAdapter
	{
		
		private LayoutInflater inflater;
		 public myCoinsAdapter(Context context)
		 {
			 this.inflater = LayoutInflater.from(context);
		 }

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return studentRecordArray.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return studentRecordArray.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HolderView holder = null;
            if (convertView == null){
                holder = new HolderView();
                convertView = inflater.inflate(R.layout.my_coin_item,null);
                holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_order_title);
//                holder.tvOrderId = (TextView)convertView.findViewById(R.id.tv_order_id);
                holder.tvMoney = (TextView)convertView.findViewById(R.id.tv_pay_money);
                holder.tvOrderTime = (TextView)convertView.findViewById(R.id.tv_order_time);
                convertView.setTag(holder);
            }else {
                holder = (HolderView)convertView.getTag();
            }
            holder.tvOrderTime.setText(studentRecordArray.get(position).getAddtime());
            
            if (studentRecordArray.get(position).getPayerid() == studentId && studentRecordArray.get(position).getPayertype() == 3)
            {
            	holder.tvTitle.setText("支出");
            	holder.tvMoney.setText("-"+studentRecordArray.get(position).getCoinnum());
            	holder.tvMoney.setTextColor(getResources().getColor(R.color.text_red));
            }
            if (studentRecordArray.get(position).getReceiverid() == studentId && studentRecordArray.get(position).getReceivertype() == 3)
            {
            	holder.tvTitle.setText("收入");
            	holder.tvMoney.setText("+"+studentRecordArray.get(position).getCoinnum());
            	holder.tvMoney.setTextColor(getResources().getColor(R.color.text_green));
            }
            
            
			return convertView;
		}
		
	}
	
	public class HolderView
	{
//		private TextView tvOrderId;
		private TextView tvOrderTime;
		private TextView tvTitle;
		private TextView tvMoney;
	}
	
	

}
