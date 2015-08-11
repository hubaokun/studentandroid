package hzyj.guangda.student.activity.order;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.common.library.llj.base.BaseReponse;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TextUtilLj;
import com.loopj.android.http.RequestParams;

/**
 * 评论界面
 * 
 * @author liulj
 * 
 */
public class CommentActivity extends TitlebarActivity {
	private RatingBar mAttitudeRb, mQualityRb, mLooksRb;
	private TextView mAttitudeTv, mQualityTv, mLooksTv;

	private TextView mPlaceOrderTimeTv, mOrderCoachTv, mOrderTimeTv, mOrderAddressTv, mAllMoneyTv, mAllMoneyTagTv;
	private String mOrderid;
	private String mCreatTime;
	private String mOrderCoach;
	private String mOrderTime;
	private String mOrderAddress;
	private String mAllMoney;
	private EditText etComment;

	@Override
	public void getIntentData() {
		super.getIntentData();
		Intent intent = getIntent();
		mOrderid = intent.getStringExtra("mOrderid");
		mCreatTime = intent.getStringExtra("mCreatTime");
		mOrderCoach = intent.getStringExtra("mOrderCoach");
		mOrderTime = intent.getStringExtra("mOrderTime");
		mOrderAddress = intent.getStringExtra("mOrderAddress");
		mAllMoney = intent.getStringExtra("mAllMoney");
	}

	@Override
	public int getLayoutId() {
		return R.layout.comment_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mAttitudeRb = (RatingBar) findViewById(R.id.rb_attitude);
		mQualityRb = (RatingBar) findViewById(R.id.rb_quality);
		mLooksRb = (RatingBar) findViewById(R.id.rb_looks);

		mAttitudeTv = (TextView) findViewById(R.id.tv_attitude);
		mQualityTv = (TextView) findViewById(R.id.tv_quality);
		mLooksTv = (TextView) findViewById(R.id.tv_looks);

		mPlaceOrderTimeTv = (TextView) findViewById(R.id.tv_place_order_time);
		mOrderCoachTv = (TextView) findViewById(R.id.tv_order_coach);
		mOrderTimeTv = (TextView) findViewById(R.id.tv_order_time);
		mOrderAddressTv = (TextView) findViewById(R.id.tv_order_address);
		mAllMoneyTv = (TextView) findViewById(R.id.tv_all_money);
		mAllMoneyTagTv = (TextView) findViewById(R.id.tv_all_money_tag);
		mAllMoneyTagTv.getLayoutParams().width = (int) TextUtilLj.getTextViewLength(mPlaceOrderTimeTv, "下单时间：");
		etComment = (EditText)findViewById(R.id.tv_content);
	}

	@Override
	public void addListeners() {
		mAttitudeRb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				mAttitudeTv.setText(rating + "分");

			}
		});
		mQualityRb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				mQualityTv.setText(rating + "分");

			}
		});
		mLooksRb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
				mLooksTv.setText(rating + "分");

			}
		});
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doResquest();
			}
		});
	}

	@Override
	public void initViews() {
		mCommonTitlebar.getCenterTextView().setText("评价订单");
		setRightText("提交", 10);

		setText(mPlaceOrderTimeTv, mCreatTime);
		setText(mOrderCoachTv, mOrderCoach);
		setText(mOrderTimeTv, mOrderTime);
		setText(mOrderAddressTv, mOrderAddress);
		setText(mAllMoneyTv, mAllMoney);
	}

	@Override
	public void requestOnCreate() {

	}

	private void doResquest() {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.CTASK_URL, BaseReponse.class, new MySubResponseHandler<BaseReponse>() {

			@Override
			public void onStart() {
				super.onStart();
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "EvaluationTask");
				requestParams.add("userid", GuangdaApplication.mUserInfo.getStudentid());
				requestParams.add("type", 2 + "");
				requestParams.add("orderid", mOrderid);
				requestParams.add("score1", mAttitudeRb.getRating() + "");
				requestParams.add("score2", mQualityRb.getRating() + "");
				requestParams.add("score3", mLooksRb.getRating() + "");
				requestParams.add("content", etComment.getText().toString().trim());
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, BaseReponse baseReponse) {
				showToast("评论成功");
				finish();
			}
		});
	}
}
