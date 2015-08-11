package hzyj.guangda.student.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.activity.ActivityMyCoins.HolderView;
import hzyj.guangda.student.entity.coinRelus;

public class ActivityMyCoinRules extends TitlebarActivity {
	private ListView lvCoinRules;
	private List<coinRelus> coinRelusArray = new ArrayList<coinRelus>();
	private MyCoinRulesAdapter coinRulesAda;
	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_my_coin_rules;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		lvCoinRules = (ListView)findViewById(R.id.lv_coins_relus);
	}

	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		setCenterText("使用规则");
		
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		InputStream is = this.getResources().openRawResource(R.raw.coinrules);
		try {
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			String json = new String(buffer,"utf-8");
			is.close();
			JSONObject obj;
			try {
				obj = new JSONObject(json);
				JSONArray rules = obj.getJSONArray("coinrelus");
				for (int i= 0 ;i<rules.length();i++)
				{
					coinRelus  coinrelus = new coinRelus();
					coinrelus.setQuestion(rules.getJSONObject(i).getString("question"));
					coinrelus.setAnswer(rules.getJSONObject(i).getString("answer"));
					coinRelusArray.add(coinrelus);
				}
				coinRulesAda = new MyCoinRulesAdapter(mBaseApplication);
				lvCoinRules.setAdapter(coinRulesAda);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private class MyCoinRulesAdapter extends BaseAdapter
	{
		private LayoutInflater inflater;
		 public MyCoinRulesAdapter(Context context)
		 {
			 this.inflater = LayoutInflater.from(context);
		 }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return coinRelusArray.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return coinRelusArray.get(position);
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
                convertView = inflater.inflate(R.layout.coin_rules_item,null);
                holder.tvCoinQuestion = (TextView)convertView.findViewById(R.id.tv_coin_rules_question);
                holder.tvCoinAnswer = (TextView)convertView.findViewById(R.id.tv_coin_rules_answer);
                convertView.setTag(holder);
            }else {
                holder = (HolderView)convertView.getTag();
            }
            
            holder.tvCoinQuestion.setText(coinRelusArray.get(position).getQuestion());
            holder.tvCoinAnswer.setText(coinRelusArray.get(position).getAnswer());
            
			return convertView;
		}
	}
	
	private class HolderView
	{
		private TextView tvCoinQuestion;
		private TextView tvCoinAnswer;
	}

}
