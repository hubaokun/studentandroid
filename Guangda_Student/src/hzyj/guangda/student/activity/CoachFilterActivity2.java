package hzyj.guangda.student.activity;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.event.CoachFilterEvent;
import hzyj.guangda.student.event.CoachListEvent;
import hzyj.guangda.student.event.Update;
import hzyj.guangda.student.response.GetAllSubjectResponse;
import hzyj.guangda.student.response.GetAllSubjectResponse.Subject;
import hzyj.guangda.student.response.getCoachHomeResponse;
import hzyj.guangda.student.response.getCoachHomeResponse.getInfor;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.WheelCityPriceDialog;
import hzyj.guangda.student.view.WheelCoachHomeDialog;
import hzyj.guangda.student.view.WheelDateDialog;
import hzyj.guangda.student.view.WheelDateDialog.OnComfirmClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apmem.tools.layouts.FlowLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TimeUitlLj;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

/**
 * 教练筛选
 * 
 * @author liulj
 * 
 */
public class CoachFilterActivity2 extends TitlebarActivity {
	private EditText mCoachNameEt;
	private FlowLayout mSubjectFy;
	private ImageView mFromLeftDateIv, mFromRightDateIv;
	private TextView mFromCenterDateTv;
	private List<Subject> mSubjects = new ArrayList<Subject>();
	private List<TextView> mSubjectTvs = new ArrayList<TextView>();
	private List<ImageView> mSubjectOks = new ArrayList<ImageView>();
	private CoachListEvent mCoachListEvent = new CoachListEvent();
	private TextView mReSetTv, mFilterTv;
	private long mLeftDate, mRightDate;
	private WheelDateDialog mDateFromDialog;
	private TextView tv_coach_home;
	private WheelCoachHomeDialog weelCoachSchooldialog;
	private String schoolId,schoolName,Telphone;
	private ArrayList<getCoachHomeResponse.getInfor> coachHomeList=new ArrayList<getInfor>();


	@Override
	public int getLayoutId() {
		return R.layout.coach_filter_activity2;
	}
	
	@Override
	public void findViews(Bundle savedInstanceState) {
		mCoachNameEt = (EditText) findViewById(R.id.et_coach_name);

		mSubjectFy = (FlowLayout) findViewById(R.id.fy_subject);
		initDefultType();
		mFromCenterDateTv = (TextView) findViewById(R.id.tv_form_date);
		mFromLeftDateIv = (ImageView) findViewById(R.id.iv_from_left_date);
		mFromRightDateIv = (ImageView) findViewById(R.id.iv_from_right_date);

		mReSetTv = (TextView) findViewById(R.id.tv_reset);
		mFilterTv = (TextView) findViewById(R.id.tv_filter);
		
	    tv_coach_home=(TextView)findViewById(R.id.tv_coach_home);
	}

	private void initDefultType() {
		View view = getLayoutInflater().inflate(R.layout.coach_filter_activity_item, null);
		ImageView ok = (ImageView) view.findViewById(R.id.iv_ok);
		TextView content = (TextView) view.findViewById(R.id.tv_content);
		content.setText("不限");
		content.setTag("0");
		ok.setTag("0");
		ok.setVisibility(View.VISIBLE);
		content.setSelected(true);
		mCoachListEvent.setCondition6("0");
		FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(-2, -2);
		lp.rightMargin = 10;
		lp.bottomMargin = 14;
		mSubjectOks.add(ok);
		mSubjectTvs.add(content);
		mSubjectFy.addView(view, lp);
		getCoachHome();
	}
	
	private void getCoachHome(){
		if(GuangdaApplication.location!=null){
			AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SBOOK_URL, getCoachHomeResponse.class, new MySubResponseHandler<getCoachHomeResponse>() {
				@Override
				public void onStart() {
					super.onStart();
					mLoadingDialog.show();
				}

				@Override
				public RequestParams setParams(RequestParams requestParams) {
					requestParams.add("action", "GETDRIVERSCHOOLBYCITYNAME");
					requestParams.add("cityname", GuangdaApplication.location);
					//requestParams.add("cityname","杭州");
					return requestParams;
				}

				@Override
				public void onFinish() {
					mLoadingDialog.dismiss();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, getCoachHomeResponse baseReponse) {
					if(baseReponse.getCode()==1){
						coachHomeList.addAll(baseReponse.getDslist());

						
					}
					
				}
			});
		}else{
			showToast("定位失败");
		}
		
		
	}

	@Override
	public void addListeners() {
		
		tv_coach_home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				weelCoachSchooldialog.WheelCityData(coachHomeList);
				weelCoachSchooldialog.show();
			}
		});
		
		
		mCommonTitlebar.getRightTextView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mFromLeftDateIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mFromCenterDateTv.getText().toString().trim().equals("不限")) {
				} else {
					long current = TimeUitlLj.stringToMilliseconds(9, mFromCenterDateTv.getText().toString().trim());
					if (current == mLeftDate) {
						// 灰色
						v.setSelected(true);
						showToast("您不能超出选择的范围！");

					} else {
						current = current - 24 * 3600 * 1000;
						mFromCenterDateTv.setText(TimeUitlLj.millisecondsToString(9, current));
						if (current == mLeftDate) {
							v.setSelected(true);
						} else {
							mFromRightDateIv.setSelected(false);
						}
					}
				}
			}
		});
		mFromRightDateIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mFromCenterDateTv.getText().toString().trim().equals("不限")) {
					long current = mLeftDate;
					if (current >= mRightDate) {
						showToast("您不能超出选择的范围！");
					} else if (current < mRightDate) {
						mFromCenterDateTv.setText(TimeUitlLj.millisecondsToString(9, current));
					}
				} else {
					long current = TimeUitlLj.stringToMilliseconds(9, mFromCenterDateTv.getText().toString().trim());
					if (current == mRightDate) {
						v.setSelected(true);
						showToast("您不能超出选择的范围！");

					} else if (current < mRightDate) {
						current = current + 24 * 3600 * 1000;
						mFromCenterDateTv.setText(TimeUitlLj.millisecondsToString(9, current));
						if (current == mRightDate) {
							v.setSelected(true);
						} else {
							mFromLeftDateIv.setSelected(false);
						}
					}
				}
			}
		});
		mFromCenterDateTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDateFromDialog.show();
			}
		});
		mReSetTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mSubjectTvs.get(0).performClick();
				mFromCenterDateTv.setText(TimeUitlLj.dateToString(9, new Date()));

				mCoachListEvent.setCondition1(null);
				mCoachListEvent.setCondition3(null);
				mCoachListEvent.setCondition6("0");
			}
		});
		mFilterTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mCoachNameEt.getText().toString().trim())) {
					mCoachListEvent.setCondition1(null);
				} else {
					mCoachListEvent.setCondition1(mCoachNameEt.getText().toString().trim());
				}
				if (mFromCenterDateTv.getText().toString().trim().equals("不限")) {
					mCoachListEvent.setCondition3(null);
				} else {
					mCoachListEvent.setCondition3(mFromCenterDateTv.getText().toString().trim());
				}
				if(schoolId==null){
					mCoachListEvent.setDriverschoolid(null);
				}else{
					mCoachListEvent.setDriverschoolid(schoolId);
				}
				EventBus.getDefault().post(mCoachListEvent);
				finish();
			}
		});
	}

	@Override
	public void initViews() {
		weelCoachSchooldialog=new WheelCoachHomeDialog(this);
		weelCoachSchooldialog.setOnCoachClickListener(new WheelCoachHomeDialog.OnCoachClickListener() {

			@Override
			public void onCoachBtnClick(String schoolid,String name,String telphone) {
				schoolId=schoolid;
				schoolName=name;
				Telphone=telphone;
				
				tv_coach_home.setText(schoolName);
			}
		});
		mDateFromDialog = new WheelDateDialog(this);
		mDateFromDialog.setOnComfirmClickListener(new OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(int year, int month, int day) {
				String monthStr = month < 10 ? ("0" + month) : month + "";
				String dayStr = day < 10 ? ("0" + day) : day + "";
				String str = year + "-" + monthStr + "-" + dayStr;
				if ((TimeUitlLj.stringToMilliseconds(9, str) >= mLeftDate) && (TimeUitlLj.stringToMilliseconds(9, str) <= mRightDate)) {
					mFromLeftDateIv.setSelected(true);
					mFromCenterDateTv.setText(year + "-" + monthStr + "-" + dayStr);
					mDateFromDialog.dismiss();
				} else {
					showToast("您选择的日期已经超出范围！");
				}
			}
		});
		mCommonTitlebar.getLeftTextView().setVisibility(View.INVISIBLE);
		mFromLeftDateIv.setSelected(true);
		mFromCenterDateTv.setText("不限");
		setCenterText("教练筛选");
		setRightDrawable(R.drawable.back_img, 11);

		mLeftDate = TimeUitlLj.stringToMilliseconds(9, TimeUitlLj.dateToString(9, new Date()));

		// Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		// String str = TimeUitlLj.dateToString(9, calendar.getTime());
		String str = TimeUitlLj.millisecondsToString(9, mLeftDate + 29 * 24 * 3600 * 1000l);
		mRightDate = TimeUitlLj.stringToMilliseconds(9, str);
		EventBus.getDefault().registerSticky(this);
	}

	/**
	 * 从筛选界面返回的数据
	 * 
	 * @param coachListEvent
	 */
	public void onEventMainThread(CoachFilterEvent coachFilterEvent) {
		if (coachFilterEvent != null) {
			mCoachListEvent.setCondition1(coachFilterEvent.getCondition1());
			mCoachListEvent.setCondition3(coachFilterEvent.getCondition3());
			mCoachListEvent.setCondition6(coachFilterEvent.getCondition6());

			if (mCoachListEvent.getCondition3() != null) {
				mFromCenterDateTv.setText(mCoachListEvent.getCondition3());
				if (!mCoachListEvent.getCondition3().equals(mLeftDate)) {
					mFromLeftDateIv.setSelected(false);
				}
			}
		}
	}

	@Override
	public void requestOnCreate() {
		doRequest();

	}

	private void doRequest() {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, GetAllSubjectResponse.class, new MySubResponseHandler<GetAllSubjectResponse>() {
			@Override
			public void onStart() {
				super.onStart();
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETQUERYSUBJECT");
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetAllSubjectResponse baseReponse) {
				if (baseReponse.getSubjectlist() != null && baseReponse.getSubjectlist().size() != 0) {
					mSubjects.clear();
					mSubjects.addAll(baseReponse.getSubjectlist());
					for (int i = 0; i < mSubjects.size(); i++) {
						Subject subject = mSubjects.get(i);
						View view = getLayoutInflater().inflate(R.layout.coach_filter_activity_item, null);
						if (subject != null && subject.getSubjectname() != null) {
							ImageView ok = (ImageView) view.findViewById(R.id.iv_ok);
							TextView content = (TextView) view.findViewById(R.id.tv_content);
							content.setText(subject.getSubjectname());
							content.setTag(subject.getSubjectid() + "");
							ok.setTag(subject.getSubjectid() + "");
							FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(-2, -2);
							lp.rightMargin = 10;
							lp.bottomMargin = 14;
							mSubjectOks.add(ok);
							mSubjectTvs.add(content);
							mSubjectFy.addView(view, lp);
						}
					}
					for (TextView score : mSubjectTvs) {
						score.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (!v.isSelected()) {
									for (TextView score2 : mSubjectTvs) {
										if (score2 == v) {
											score2.setSelected(true);
										} else {
											score2.setSelected(false);
										}
									}
									for (ImageView ok : mSubjectOks) {
										if (ok.getTag().toString().equals(v.getTag().toString())) {
											ok.setVisibility(View.VISIBLE);
										} else {
											ok.setVisibility(View.INVISIBLE);
										}
									}
									mCoachListEvent.setCondition6(v.getTag().toString());
									/*Toast.makeText(mBaseFragmentActivity, v.getTag().toString(), 0).show();*/
								}
							}
						});
					}
					for (TextView score2 : mSubjectTvs) {
						if (score2 != null) {
							if (mCoachListEvent.getCondition6() != null && mCoachListEvent.getCondition6().equals(score2.getTag().toString())) {
								score2.performClick();
							}
						}
					}
				}
			}
		});
	}

	@Override
	public void finish() {
		EventBus.getDefault().unregister(this);
		super.finish();
		overridePendingTransition(R.anim.no_fade, R.anim.center_to_bottom);
	}
}
