package hzyj.guangda.student.activity;



import java.util.ArrayList;

import org.apache.http.Header;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.views.CommonTitlebar;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import db.DBManager;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.entity.City;
import hzyj.guangda.student.entity.Province;
import hzyj.guangda.student.fragment.ActivityServiceFragment;
import hzyj.guangda.student.response.getHotCityResponse;
import hzyj.guangda.student.response.getHotCityResponse.HotCity;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.CityListDialog;
import hzyj.guangda.student.view.CityListDialog.OnDismissListener;

public class SetLocationActivity extends TitlebarActivity{
	
	private TextView tv_selected_city,tv_gps_location,tv_back;
	private GridView gv_hot_city;
	private ListView  list_province;
	private ArrayList<HotCity> hotCity=new ArrayList<HotCity>();
	private ArrayList<Province> provincelist=new ArrayList<Province>();
	 private ArrayList<City> citylist;
	 private DBManager mgr;
	 private CityListDialog citylistdialog;
	 private DataAdapter hotadapter;
	 private ListDataAdapter listadapter;
	 private RelativeLayout sunshade;

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.select_location_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		tv_selected_city=(TextView) findViewById(R.id.tv_selected_city);
		tv_gps_location=(TextView) findViewById(R.id.tv_gps_location);
		gv_hot_city=(GridView) findViewById(R.id.gv_hot_city);
		list_province=(ListView) findViewById(R.id.list_province);
		sunshade=(RelativeLayout) findViewById(R.id.rl_sunshade);
		//tv_back=(TextView) findViewById(R.id.tv_back);
	}
	
	@Override
	public void addListeners() {
		// TODO Auto-generated method stub
		
		tv_gps_location.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tv_selected_city.setText(tv_gps_location.getText());
			}
		});
		
		
		
		mCommonTitlebar.setRightTextOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
		
	
		citylistdialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(boolean arg0) {
				// TODO Auto-generated method stub
				if(arg0){
			    setCenterText("选择城市");
			    sunshade.setVisibility(View.GONE);
				}
				
			}
		});
		
	

		
	}
	
	

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		
		Bundle bundle=getIntent().getExtras();
		tv_selected_city.setText(bundle.getString("nowSelectCity"));
		
		tv_gps_location.setText(GuangdaApplication.location);
		setCenterText("选择城市");
		hotadapter=new DataAdapter(mBaseFragmentActivity);
		gv_hot_city.setAdapter(hotadapter);	
		
		listadapter=new ListDataAdapter(mBaseFragmentActivity);
		list_province.setAdapter(listadapter);
		getProvice();
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		
		
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.LOCATION_URL, getHotCityResponse.class, new MySubResponseHandler<getHotCityResponse>() {
			@Override
			public void onStart() {
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "getHotCity");
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, getHotCityResponse baseReponse) {
				if (mLoadingDialog.isShowing())
				{
					mLoadingDialog.dismiss();
				}
				if (baseReponse.getCode()==1)
				{
					hotCity.clear();
					hotCity.addAll(baseReponse.getCity());
					hotadapter.setData();
				}else{
					Toast.makeText(mBaseFragmentActivity, baseReponse.getMessage(),Toast.LENGTH_LONG);
				}
			}
		});
	}
	
	
	public void getProvice(){
		mgr = new DBManager(mBaseFragmentActivity);
		provincelist = (ArrayList<Province>) mgr.queryProvince();
		listadapter.setData();
	}
	
	public void getcity(String provinceid){
		citylist = (ArrayList<City>) mgr.queryCity(provinceid);
		
	}
	
	public class DataAdapter extends BaseAdapter {
		Context mContext;
		private LayoutInflater inflater;
    
		public DataAdapter(Context context) {
			mContext = context;
			this.inflater = LayoutInflater.from(context);
		}
		
		public void setData() {
	        notifyDataSetChanged();
	    }
       @Override
		public int getCount() {
			return hotCity.size();
		}
       @Override
		public Object getItem(int position) {
			return position;
		}
       @Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder=null;
			if (convertView == null) {
				
				viewHolder=new ViewHolder();
				convertView = inflater.inflate(R.layout.hot_city_item, null);
				viewHolder.tv_hot_city=(TextView)convertView.findViewById(R.id.tv_hot_city);
				viewHolder.rl_hot_city=(RelativeLayout) convertView.findViewById(R.id.rl_hot_city);
				convertView.setTag(viewHolder);
			} else
	            {
				   viewHolder = (ViewHolder)convertView.getTag();
	            }
			
			viewHolder.tv_hot_city.setText(hotCity.get(position).getCityname());		
			
			viewHolder.rl_hot_city.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					tv_selected_city.setText(hotCity.get(position).getCityname());
					ActivityServiceFragment.nowSelectCity=hotCity.get(position).getCityname();
					ActivityServiceFragment.selectcityid=String.valueOf(hotCity.get(position).getCityid());
					
				}
			});
			return convertView;
		}
	}
	
	

	
	private final class ViewHolder{
		private TextView tv_hot_city;
		private RelativeLayout rl_hot_city;
	}
	
	public class ListDataAdapter extends BaseAdapter {
		 Context mContext;
		 private LayoutInflater inflater;
		
    
		public ListDataAdapter(Context context) {
			mContext = context;
			this.inflater=LayoutInflater.from(context);
		}
       @Override
		public int getCount() {
			return provincelist.size();
		}
       
		public void setData() {
	        notifyDataSetChanged();
	    }
       @Override
		public Object getItem(int position) {
			return position;
		}
       @Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			HoderView holder=null;
			
			if (convertView == null) {
				holder=new HoderView();
				convertView = inflater.inflate( R.layout.select_location_provice_item, null);
				
				holder.tv_province=(TextView) convertView.findViewById(R.id.tv_province);
				holder.citygo=(ImageView) convertView.findViewById(R.id.iv_open_city);
				holder.ll_list_select=(LinearLayout) convertView.findViewById(R.id.ll_list_select);
				convertView.setTag(holder);
			}else{
				holder=(HoderView) convertView.getTag();
			}
			
			holder.tv_province.setText(provincelist.get(position).provinceName);
			
			
			citylist = (ArrayList<City>) mgr.queryCity(provincelist.get(position).provinceId);
			if(citylist.size()==0||citylist.size()==1&&citylist.get(0).cityname.equals(provincelist.get(position).provinceName)){
				holder.citygo.setVisibility(View.GONE);
			}else{
				holder.citygo.setVisibility(View.VISIBLE);
			}
			
            holder.ll_list_select.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
			
					    citylist.clear();
					    citylist = (ArrayList<City>) mgr.queryCity(provincelist.get(position).provinceId); 
						if(citylist.size()==0||citylist.size()==1&&citylist.get(0).cityname.equals(provincelist.get(position).provinceName)){
							 tv_selected_city.setText(provincelist.get(position).provinceName);
							 ActivityServiceFragment.nowSelectCity=provincelist.get(position).provinceName;
							 ActivityServiceFragment.selectcityid=provincelist.get(position).provinceId;
						}else{
							 getcity(provincelist.get(position).provinceId);
							 citylistdialog=new CityListDialog(mBaseFragmentActivity, citylist,tv_selected_city);
							 Window window = citylistdialog.getWindow();
				              window.setWindowAnimations(R.style.anim_in);  //添加动画
				              sunshade.setVisibility(View.VISIBLE);
							 citylistdialog.show();
							 setCenterText(provincelist.get(position).provinceName);
						}
				}
			});
			return convertView;
		}
	}
	private final class HoderView{
		private TextView tv_province;
		private ImageView citygo;
		private LinearLayout ll_list_select;
	} 
	
	

}
