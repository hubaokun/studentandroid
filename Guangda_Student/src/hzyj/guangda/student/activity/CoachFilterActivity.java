package hzyj.guangda.student.activity;

import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.event.CoachListEvent;
import hzyj.guangda.student.response.GetAllTeachCarModelResponse;
import hzyj.guangda.student.response.GetAllTeachCarModelResponse.Teachcar;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.WheelDateDialog;
import hzyj.guangda.student.view.WheelDateDialog.OnComfirmClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;
import org.apmem.tools.layouts.FlowLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.listener.OnMyTextWatcher;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.ParseUtilLj;
import com.common.library.llj.utils.TimeUitlLj;
import com.common.library.llj.views.UnscrollableGridView;
import com.loopj.android.http.RequestParams;

import de.greenrobot.event.EventBus;

/**
 * 
 * @author liulj
 * 
 */
@SuppressLint("InflateParams")
public class CoachFilterActivity extends TitlebarActivity {
	private EditText mCoachNameEt;
	private TextView mFromCenterDateTv, mFromCenterTimeTv;
	private ImageView mFromLeftDateIv, mFromRightDateIv, mFromLeftTimeIv, mFromRightTimeIv;
	private TextView mToCenterDateTv, mToCenterTimeTv;
	private ImageView mToLeftDateIv, mToRightDateIv, mToLeftTimeIv, mToRightTimeIv;
	private FlowLayout mScoresFy, mGenderFy, mCarTypeFy;

	// private String mScore;// 0,1,2,3,4
	// private String mGender;// 性别 1.男 2.女 0.不限
	// private String mCarType;// 车型ID 0.不限 -1.表示其它
	private int mFromTime = 8;
	private int mToTime = 23;

	private String[] mScoresValue = new String[] { "不限", "4星以上", "3星以上", "2星以上", "1星以上" };
	private List<ImageView> mScoreOks = new ArrayList<ImageView>();
	private List<TextView> mScores = new ArrayList<TextView>();

	private String[] mGenderValue = new String[] { "不限", "男", "女" };
	private List<ImageView> mGenderOks = new ArrayList<ImageView>();
	private List<TextView> mGenders = new ArrayList<TextView>();

	private List<ImageView> mCarTypeOks = new ArrayList<ImageView>();
	private List<TextView> mCarTypes = new ArrayList<TextView>();

	private UnscrollableGridView mGridView;
	private EditText mFromEt, mToEt;
	private MoneyAdapter mMoneyAdapter;
	private int mSelectPosition = -1;

	private CoachListEvent mCoachListEvent = new CoachListEvent();

	private TextView mReSetTv, mFilterTv;

	private WheelDateDialog mDateFromDialog, mDateToDialog;

	private long mLeftDate, mRightDate;

	@Override
	public int getLayoutId() {
		return R.layout.coach_filter_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mCoachNameEt = (EditText) findViewById(R.id.et_coach_name);

		mFromCenterDateTv = (TextView) findViewById(R.id.tv_form_date);
		mFromCenterTimeTv = (TextView) findViewById(R.id.tv_form_time);

		mFromLeftDateIv = (ImageView) findViewById(R.id.iv_from_left_date);
		mFromRightDateIv = (ImageView) findViewById(R.id.iv_from_right_date);
		mFromLeftTimeIv = (ImageView) findViewById(R.id.iv_from_left_time);
		mFromRightTimeIv = (ImageView) findViewById(R.id.iv_from_right_time);

		mToCenterDateTv = (TextView) findViewById(R.id.tv_to_date);
		mToCenterTimeTv = (TextView) findViewById(R.id.tv_to_time);

		mToLeftDateIv = (ImageView) findViewById(R.id.iv_to_left_date);
		mToRightDateIv = (ImageView) findViewById(R.id.iv_to_right_date);
		mToLeftTimeIv = (ImageView) findViewById(R.id.iv_to_left_time);
		mToRightTimeIv = (ImageView) findViewById(R.id.iv_to_right_time);

		mScoresFy = (FlowLayout) findViewById(R.id.fy_scores);
		initScores();
		mGenderFy = (FlowLayout) findViewById(R.id.fy_gender);
		initGender();
		mCarTypeFy = (FlowLayout) findViewById(R.id.flowLayout);
		initDefultType();
		mGridView = (UnscrollableGridView) findViewById(R.id.gv_money);
		mFromEt = (EditText) findViewById(R.id.et_money_from);
		mToEt = (EditText) findViewById(R.id.et_money_to);

		mReSetTv = (TextView) findViewById(R.id.tv_reset);
		mFilterTv = (TextView) findViewById(R.id.tv_filter);

	}

	private void initScores() {
		for (int i = 0; i < 5; i++) {
			View view = getLayoutInflater().inflate(R.layout.coach_filter_activity_item, null);
			ImageView ok = (ImageView) view.findViewById(R.id.iv_ok);
			TextView content = (TextView) view.findViewById(R.id.tv_content);
			content.setText(mScoresValue[i]);
			switch (i) {
			case 0:
				ok.setVisibility(View.VISIBLE);
				content.setSelected(true);
				mCoachListEvent.setCondition2("0");
				content.setTag("0");
				ok.setTag("0");
				break;
			case 1:
				content.setTag("4");
				ok.setTag("4");
				break;
			case 2:
				content.setTag("3");
				ok.setTag("3");
				break;
			case 3:
				content.setTag("2");
				ok.setTag("2");
				break;
			case 4:
				content.setTag("1");
				ok.setTag("1");
				break;
			}
			FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(-2, -2);
			lp.rightMargin = 10;
			lp.bottomMargin = 14;
			mScoreOks.add(ok);
			mScores.add(content);
			mScoresFy.addView(view, lp);
		}
		for (TextView score : mScores) {
			score.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!v.isSelected()) {
						for (TextView score2 : mScores) {
							if (score2 == v) {
								score2.setSelected(true);
							} else {
								score2.setSelected(false);
							}
						}
						for (ImageView ok : mScoreOks) {
							if (ok.getTag().toString().equals(v.getTag().toString())) {
								ok.setVisibility(View.VISIBLE);
							} else {
								ok.setVisibility(View.INVISIBLE);
							}
						}
						mCoachListEvent.setCondition2(v.getTag().toString());
					}
				}
			});
		}
	}

	private void initGender() {
		for (int i = 0; i < 3; i++) {
			View view = getLayoutInflater().inflate(R.layout.coach_filter_activity_item, null);
			ImageView ok = (ImageView) view.findViewById(R.id.iv_ok);
			TextView content = (TextView) view.findViewById(R.id.tv_content);
			content.setText(mGenderValue[i]);
			content.setTag(i + "");
			ok.setTag(i + "");
			if (i == 0) {
				ok.setVisibility(View.VISIBLE);
				content.setSelected(true);
				mCoachListEvent.setCondition5("0");
			}
			FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(-2, -2);
			lp.rightMargin = 10;
			lp.bottomMargin = 14;
			mGenderOks.add(ok);
			mGenders.add(content);
			mGenderFy.addView(view, lp);
		}
		for (TextView score : mGenders) {
			score.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (!v.isSelected()) {
						for (TextView score2 : mGenders) {
							if (score2 == v) {
								score2.setSelected(true);
							} else {
								score2.setSelected(false);
							}
						}
						for (ImageView ok : mGenderOks) {
							if (ok.getTag().toString().equals(v.getTag().toString())) {
								ok.setVisibility(View.VISIBLE);
							} else {
								ok.setVisibility(View.INVISIBLE);
							}
						}
						mCoachListEvent.setCondition5(v.getTag().toString());
					}

				}
			});
		}
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
		mCoachListEvent.setCondition10("0");
		FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(-2, -2);
		lp.rightMargin = 10;
		lp.bottomMargin = 14;
		mCarTypeOks.add(ok);
		mCarTypes.add(content);
		mCarTypeFy.addView(view, lp);
	}

	@Override
	public void addListeners() {
		mCoachNameEt.addTextChangedListener(new OnMyTextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s != null) {
					mCoachListEvent.setCondition1(s.toString());
				}
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
				long current = TimeUitlLj.stringToMilliseconds(9, mFromCenterDateTv.getText().toString().trim());
				if (current == mLeftDate) {
					showToast("您不能超出选择的范围！");

				} else if (current > mLeftDate) {
					current = current - 24 * 3600 * 1000;
					mFromCenterDateTv.setText(TimeUitlLj.millisecondsToString(9, current));
					if (current == mLeftDate) {
						v.setSelected(true);
					} else {
						mFromRightDateIv.setSelected(false);
					}
				}
			}
		});
		mFromRightDateIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				long current = TimeUitlLj.stringToMilliseconds(9, mFromCenterDateTv.getText().toString().trim());
				if (current == mRightDate) {
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
		});
		mFromCenterDateTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDateFromDialog.show();
			}
		});
		mFromLeftTimeIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mFromTime == 5) {
					showToast("您不能超出选择的范围！");
				} else if (mFromTime > 5) {
					mFromTime--;
					mFromCenterTimeTv.setText(mFromTime + "");
					if (mFromTime == 5) {
						v.setSelected(true);
					} else {
						mFromRightTimeIv.setSelected(false);
					}
				}
			}
		});
		mFromRightTimeIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mFromTime == 23) {
					showToast("您不能超出选择的范围！");
				} else if (mFromTime < 23) {
					mFromTime++;
					mFromCenterTimeTv.setText(mFromTime + "");
					if (mFromTime == 23) {
						v.setSelected(true);
					} else {
						mFromLeftTimeIv.setSelected(false);
					}
				}

			}
		});
		mToLeftDateIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				long current = TimeUitlLj.stringToMilliseconds(9, mToCenterDateTv.getText().toString().trim());
				if (current == mLeftDate) {
					showToast("您不能超出选择的范围！");

				} else if (current > mLeftDate) {
					current = current - 24 * 3600 * 1000;
					mToCenterDateTv.setText(TimeUitlLj.millisecondsToString(9, current));
					if (current == mLeftDate) {
						v.setSelected(true);
					} else {
						mToRightDateIv.setSelected(false);
					}
				}
			}
		});
		mToRightDateIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				long current = TimeUitlLj.stringToMilliseconds(9, mToCenterDateTv.getText().toString().trim());
				if (current == mRightDate) {
					showToast("您不能超出选择的范围！");

				} else if (current < mRightDate) {
					current = current + 24 * 3600 * 1000;
					mToCenterDateTv.setText(TimeUitlLj.millisecondsToString(9, current));
					if (current == mRightDate) {
						v.setSelected(true);
					} else {
						mToLeftDateIv.setSelected(false);
					}
				}
			}
		});
		mToCenterDateTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDateToDialog.show();
			}
		});
		mToLeftTimeIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mToTime == 5) {
					showToast("您不能超出选择的范围！");
				} else if (mToTime > 5) {
					mToTime--;
					mToCenterTimeTv.setText(mToTime + "");
					if (mToTime == 5) {
						v.setSelected(true);
					} else {
						mToRightTimeIv.setSelected(false);
					}
				}
			}
		});
		mToRightTimeIv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mToTime == 23) {
					showToast("您不能超出选择的范围！");
				} else if (mToTime < 23) {
					mToTime++;
					mToCenterTimeTv.setText(mToTime + "");
					if (mToTime == 23) {
						v.setSelected(true);
					} else {
						mToLeftTimeIv.setSelected(false);
					}
				}
			}
		});
		mReSetTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCoachNameEt.setText("");
				reSetPart();
				// mScoreOks.get(0).setVisibility(View.VISIBLE);
				// mScores.get(0).setSelected(true);
				mScores.get(0).performClick();

				// mGenderOks.get(0).setVisibility(View.VISIBLE);
				// mGenders.get(0).setSelected(true);
				mGenders.get(0).performClick();
				mFromEt.setText("");
				mToEt.setText("");

				mSelectPosition = -1;
				mMoneyAdapter.notifyDataSetChanged();

				// mCarTypeOks.get(0).setVisibility(View.VISIBLE);
				// mCarTypes.get(0).setSelected(true);
				mCarTypes.get(0).performClick();

				mCoachListEvent.setCondition1(null);
				mCoachListEvent.setCondition2("0");
				mCoachListEvent.setCondition3(null);
				mCoachListEvent.setCondition4(null);
				mCoachListEvent.setCondition5("0");
				mCoachListEvent.setCondition8(null);
				mCoachListEvent.setCondition9(null);
				mCoachListEvent.setCondition10("0");
			}
		});
		mFilterTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!TextUtils.isEmpty(mCoachNameEt.getText().toString().trim()))
					mCoachListEvent.setCondition1(mCoachNameEt.getText().toString().trim());
				if (!TextUtils.isEmpty(mFromEt.getText().toString().trim()))
					mCoachListEvent.setCondition8(mFromEt.getText().toString().trim());
				if (!TextUtils.isEmpty(mToEt.getText().toString().trim()))
					mCoachListEvent.setCondition9(mToEt.getText().toString().trim());

				String formTime = ParseUtilLj.parseInt(mFromCenterTimeTv.getText().toString()) < 10 ? "0" + mFromCenterTimeTv.getText().toString() : "" + mFromCenterTimeTv.getText().toString();
				String toTime = ParseUtilLj.parseInt(mToCenterTimeTv.getText().toString()) < 10 ? "0" + mToCenterTimeTv.getText().toString() : "" + mToCenterTimeTv.getText().toString();
				mCoachListEvent.setCondition3(mFromCenterDateTv.getText().toString().trim() + " " + formTime + ":00:00");
				mCoachListEvent.setCondition4(mToCenterDateTv.getText().toString().trim() + " " + toTime + ":00:00");
				EventBus.getDefault().post(mCoachListEvent);
				finish();
			}
		});
	}

	@Override
	public void initViews() {
		mDateFromDialog = new WheelDateDialog(this);
		mDateFromDialog.setOnComfirmClickListener(new OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(int year, int month, int day) {
				String monthStr = month < 10 ? ("0" + month) : month + "";
				String dayStr = day < 10 ? ("0" + day) : day + "";
				String str = year + "-" + monthStr + "-" + dayStr;
				if ((TimeUitlLj.stringToMilliseconds(9, str) > mLeftDate) && (TimeUitlLj.stringToMilliseconds(9, str) < mRightDate)) {
					mFromCenterDateTv.setText(year + "-" + monthStr + "-" + dayStr);
					mDateFromDialog.dismiss();
				} else {
					showToast("您选择的日期已经超出范围！");
				}
			}
		});
		mDateToDialog = new WheelDateDialog(this);
		mDateToDialog.setOnComfirmClickListener(new OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(int year, int month, int day) {
				String monthStr = month < 10 ? ("0" + month) : month + "";
				String dayStr = day < 10 ? ("0" + day) : day + "";
				String str = year + "-" + monthStr + "-" + dayStr;
				if ((TimeUitlLj.stringToMilliseconds(9, str) > mLeftDate) && (TimeUitlLj.stringToMilliseconds(9, str) < mRightDate)) {
					mToCenterDateTv.setText(year + "-" + monthStr + "-" + dayStr);
					mDateToDialog.dismiss();
				} else {
					showToast("您选择的日期已经超出范围！");
				}
			}
		});
		mCommonTitlebar.getLeftTextView().setVisibility(View.INVISIBLE);
		setCenterText("教练筛选");
		setRightDrawable(R.drawable.back_img, 11);

		reSetPart();

		mMoneyAdapter = new MoneyAdapter(this, R.layout.coach_filter_activity_money_item);
		mGridView.setAdapter(mMoneyAdapter);

		List<MoneyItem> mMoneyItems = new ArrayList<MoneyItem>();
		mMoneyItems.add(new MoneyItem("实惠型", "1-50"));
		mMoneyItems.add(new MoneyItem("中档型", "50-100"));
		mMoneyItems.add(new MoneyItem("优秀型", "100-150"));
		mMoneyAdapter.addAll(mMoneyItems);

	}

	private void reSetPart() {
		mFromLeftDateIv.setSelected(true);
		mToRightDateIv.setSelected(true);
		mToRightTimeIv.setSelected(true);
		mFromTime = 8;
		mToTime = 23;
		mFromCenterDateTv.setText(TimeUitlLj.dateToString(9, new Date()));
		mFromCenterTimeTv.setText(mFromTime + "");
		mLeftDate = TimeUitlLj.stringToMilliseconds(9, mFromCenterDateTv.getText().toString().trim());

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		mToCenterDateTv.setText(TimeUitlLj.dateToString(9, calendar.getTime()));
		mToCenterTimeTv.setText(mToTime + "");
		mRightDate = TimeUitlLj.stringToMilliseconds(9, mToCenterDateTv.getText().toString().trim());

		String formTime = ParseUtilLj.parseInt(mFromCenterTimeTv.getText().toString()) < 10 ? "0" + mFromCenterTimeTv.getText().toString() : "" + mFromCenterTimeTv.getText().toString();
		String toTime = ParseUtilLj.parseInt(mToCenterTimeTv.getText().toString()) < 10 ? "0" + mToCenterTimeTv.getText().toString() : "" + mToCenterTimeTv.getText().toString();
		mCoachListEvent.setCondition3(mFromCenterDateTv.getText().toString().trim() + " " + formTime + ":00:00");
		mCoachListEvent.setCondition4(mToCenterDateTv.getText().toString().trim() + " " + toTime + ":00:00");
	}

	@Override
	public void requestOnCreate() {
		doRequest();

	}

	private void doRequest() {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SMY_URL, GetAllTeachCarModelResponse.class, new MySubResponseHandler<GetAllTeachCarModelResponse>() {
			@Override
			public void onStart() {
				super.onStart();
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetAllSubject");
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetAllTeachCarModelResponse baseReponse) {
				if (baseReponse.getTeachcarlist() != null) {
					int count = baseReponse.getTeachcarlist().size();
					for (int i = 0; i < count; i++) {
						Teachcar teachcar = baseReponse.getTeachcarlist().get(i);
						if (teachcar != null) {
							View view = getLayoutInflater().inflate(R.layout.coach_filter_activity_item, null);
							ImageView ok = (ImageView) view.findViewById(R.id.iv_ok);
							TextView content = (TextView) view.findViewById(R.id.tv_content);
							content.setText(teachcar.getModelname());
							content.setTag(teachcar.getModelid() + "");
							ok.setTag(teachcar.getModelid() + "");
							FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(-2, -2);
							lp.rightMargin = 10;
							lp.bottomMargin = 14;
							mCarTypeOks.add(ok);
							mCarTypes.add(content);
							mCarTypeFy.addView(view, lp);
						}
					}
					for (TextView score : mCarTypes) {
						score.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (!v.isSelected()) {
									for (TextView score2 : mCarTypes) {
										if (v == score2) {
											score2.setSelected(true);
										} else {
											score2.setSelected(false);
										}
									}
									for (ImageView ok : mCarTypeOks) {
										if (ok.getTag().toString().equals(v.getTag().toString())) {
											ok.setVisibility(View.VISIBLE);
										} else {
											ok.setVisibility(View.INVISIBLE);
										}
									}
									mCoachListEvent.setCondition10(v.getTag().toString());
								}

							}
						});
					}
				}

			}
		});
	}

	private class MoneyAdapter extends QuickAdapter<MoneyItem> {

		public MoneyAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, final MoneyItem item, final int position) {
			if (item != null) {
				LinearLayout wrap = helper.getView(R.id.li_wrap);
				ImageView ok = helper.getView(R.id.iv_ok);
				TextView title = helper.getView(R.id.tv_title);
				TextView money = helper.getView(R.id.tv_money);
				title.setText(item.getTitle());
				money.setText(item.getMoney());
				if (mSelectPosition != -1 && (mSelectPosition == position)) {
					ok.setVisibility(View.VISIBLE);
					wrap.setSelected(true);
					title.setSelected(true);
				} else {
					ok.setVisibility(View.INVISIBLE);
					wrap.setSelected(false);
					title.setSelected(false);
				}
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						mSelectPosition = position;
						mMoneyAdapter.notifyDataSetChanged();
						String[] str = item.getMoney().split("-");
						mFromEt.setText(str[0]);
						mToEt.setText(str[1]);
					}
				});
			}
		}
	}

	private class MoneyItem {
		private String title;
		private String money;

		public MoneyItem(String title, String money) {
			super();
			this.title = title;
			this.money = money;
		}

		public String getTitle() {
			return title;
		}

		public String getMoney() {
			return money;
		}

	}
}
