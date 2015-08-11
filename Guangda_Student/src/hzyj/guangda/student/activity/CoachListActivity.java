package hzyj.guangda.student.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.entity.CoachInfoVo;
import hzyj.guangda.student.event.CoachFilterEvent;
import hzyj.guangda.student.event.CoachListEvent;
import hzyj.guangda.student.response.CoachListResponse;
import hzyj.guangda.student.response.GetCityId;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.MapBottomDialog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.ParseUtilLj;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

/**
 * 教练刷选列表
 * 
 * @author liulj
 * 
 */
public class CoachListActivity extends BaseFragmentActivity {
	private ImageView mAllIv, mMapIv, mFilterIv;
	private PtrClassicFrameLayout mPtrFrameLayout;
	private ListView mListView;
	private int mPage;
	private CoachListAdapter mCoachListAdapter;
	private MapBottomDialog mMapBottomDialog;
	private String condition1;// 教练名字或车牌号
	private String condition3;// 时间刷选
	private String condition6 = "0";// 科目选择(0代表不限)
	private String condition11;// 车型
	private List<CoachInfoVo> coachList = new ArrayList<CoachInfoVo>();
	private String Version,longitude,latitude,cityId;

	@Override
	public void getIntentData() {
		super.getIntentData();
		condition1 = getIntent().getStringExtra("condition1");
		condition3 = getIntent().getStringExtra("condition3");
		condition6 = getIntent().getStringExtra("condition6");
		condition11 = getIntent().getStringExtra("condition11");
		longitude=getIntent().getStringExtra("mLongitude");
		latitude=getIntent().getStringExtra("mLatitude");
		cityId=getIntent().getStringExtra("cityId");
		Version = ((GuangdaApplication) mBaseApplication).getVersion();
	}
	@Override
	public int getLayoutId() {
		return R.layout.coach_list_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame);
		mPtrFrameLayout.setDurationToCloseHeader(800);
		mListView = (ListView) findViewById(R.id.lv_coach);

		mAllIv = (ImageView) findViewById(R.id.iv_all);
		mMapIv = (ImageView) findViewById(R.id.iv_map);
		mFilterIv = (ImageView) findViewById(R.id.iv_filter);
	}

	@Override
	public void addListeners() {
		mPtrFrameLayout.setPtrHandler(new PtrHandler() {
			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				mPage = 0;
				doLoadMoreData();
			}

			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
			}
		});
		mAllIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				condition1 = null;
				condition3 = null;
				condition6 = "0";
				mAllIv.setVisibility(View.INVISIBLE);
				requestOnCreate();
			}
		});
		mMapIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		mFilterIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EventBus.getDefault().postSticky(new CoachFilterEvent(condition1, condition3, condition6));
				ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(mBaseFragmentActivity, R.anim.bottom_to_center, R.anim.no_fade);
				Intent intent = new Intent(mBaseFragmentActivity, CoachFilterActivity2.class);
				ActivityCompat.startActivity(mBaseFragmentActivity, intent, options.toBundle());
			}
		});
	}
	
	

	@Override
	public void initViews() {
		// 是否现实全部按钮
		if (condition1 == null && condition3 == null && condition6.equals("0")) {
			mAllIv.setVisibility(View.INVISIBLE);
		} else {
			mAllIv.setVisibility(View.VISIBLE);
		}
		mMapBottomDialog = new MapBottomDialog(this);

		mCoachListAdapter = new CoachListAdapter(this, R.layout.coach_list_activity_item);
		onLoadMoreData(mListView, mCoachListAdapter);
		mListView.setAdapter(mCoachListAdapter);

		EventBus.getDefault().registerSticky(this);
	}

	/**
	 * 从筛选界面返回的数据
	 * 
	 * @param coachListEvent
	 */
	public void onEventMainThread(CoachListEvent coachListEvent) {
		if (coachListEvent != null) {
			condition1 = coachListEvent.getCondition1();
			condition3 = coachListEvent.getCondition3();
			condition6 = coachListEvent.getCondition6();
			// 是否现实全部按钮
			if (condition1 == null && condition3 == null && condition6.equals("0")) {
				mAllIv.setVisibility(View.INVISIBLE);
			} else {
				mAllIv.setVisibility(View.VISIBLE);
			}
			mPtrFrameLayout.autoRefresh();
		}
	}

	@Override
	public void requestOnCreate() {
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh(true);
			}
		}, 150);
	}

	public void doLoadMoreData() {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SBOOK_URL, CoachListResponse.class, new MySubResponseHandler<CoachListResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetCoachList");
				if (condition1 != null)
					requestParams.add("condition1", condition1);
				if (condition3 != null)
					requestParams.add("condition3", condition3 + " 05:00:00");
				if (condition6 != null)
					requestParams.add("condition6", condition6);
				if (!TextUtils.isEmpty(GuangdaApplication.mUserInfo.getStudentid()))
				{
					requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				}
				requestParams.add("longitude",longitude);
				requestParams.add("latitude",latitude);
				requestParams.add("cityId",cityId);
				requestParams.add("pagenum", mPage + "");
				requestParams.add("version",Version);
				return requestParams;
			}

			@Override
			public void onFinish() {
				mPtrFrameLayout.refreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, CoachListResponse baseReponse) {
				
				initAllData(baseReponse);
			}
		});
	}
	
//	public void getCityId() {
//		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.LOCATION_URL, GetCityId.class, new MySubResponseHandler<GetCityId>() {
//
//			@Override
//			public RequestParams setParams(RequestParams requestParams) {
//				requestParams.add("action", "GETCITYBYCNAME");
//				requestParams.add("cname",city);
//
//				return requestParams;
//			}
//
//			@Override
//			public void onFinish() {
//				
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, GetCityId baseReponse) {
//				cityId=baseReponse.getCitylist().get(0).getCityid();
//			}
//		});
//	}

	private void initAllData(CoachListResponse baseReponse) {
		if (mPage == 0) {
			mCoachListAdapter.clear();
		}
		if (baseReponse.getHasmore() == 0) {
			mCoachListAdapter.showIndeterminateProgress(false);
		} else if (baseReponse.getHasmore() == 1 && baseReponse.getCoachlist() != null) {
			mCoachListAdapter.showIndeterminateProgress(true);
			mPage++;
		}
//		for (int i =0 ;i<baseReponse.getCoachlist().size();i++)
//		{
//			if (!baseReponse.getCoachlist().get(i).getPhone().equals("18888888888"));
//			{
//				coachList.add(baseReponse.getCoachlist().get(i));
//			}
//		}
		
		//测试版 ，测试教练放出，正式版，测试教练移除
//		Iterator<CoachInfoVo> iter = baseReponse.getCoachlist().iterator();  
//		while(iter.hasNext()){  
//		    CoachInfoVo s = iter.next();  
//		    if(s.getPhone().equals("18888888888")){  
//		        iter.remove();  
//		    }
//		}
		
		
		mCoachListAdapter.addAll(baseReponse.getCoachlist());
	}

	private class CoachListAdapter extends QuickAdapter<CoachInfoVo> {

		public CoachListAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, final CoachInfoVo item, int position) {
			if (item != null) {
				
				
				//
				loadHeadImage(item.getAvatarurl(), 120, 120, ((ImageView) helper.getView(R.id.iv_header)));
				//
				helper.setText(R.id.tv_coach, item.getRealname()).setText(R.id.tv_address, item.getDetail()).setRating(R.id.rb_star, ParseUtilLj.parseFloat(item.getScore(), 0f));
				
				
				//驾校暂时先屏蔽掉
				//helper.setText(R.id.tv_school, item.getDrive_school());
				helper.setText(R.id.tv_sumnum,String.valueOf(item.getSumnum()));
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						mMapBottomDialog.updateInfo(item);
						mMapBottomDialog.show();
					}
				});
			}
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		CoachListEvent coachListEven = new CoachListEvent();
		coachListEven.setCondition1(condition1);
		coachListEven.setCondition3(condition3);
		coachListEven.setCondition6(condition6);
		coachListEven.setCondition11(condition11);
		EventBus.getDefault().post(coachListEven);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
