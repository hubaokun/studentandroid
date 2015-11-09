package hzyj.guangda.student.activity;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.entity.CoachInfoVo;
import hzyj.guangda.student.response.CommentListResponse;
import hzyj.guangda.student.response.CommentListResponse.Comment;
import hzyj.guangda.student.response.GetCoachDetailResponse;
import hzyj.guangda.student.util.MySubResponseHandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.BitmapUtilLj;
import com.common.library.llj.utils.LogUtilLj;
import com.common.library.llj.utils.ParseUtilLj;
import com.common.library.llj.utils.TimeUitlLj;
import com.common.library.llj.utils.ViewHolderUtilLj;
import com.common.library.llj.views.LinearListView;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.listview.PullToRefreshScrollView;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.loopj.android.http.RequestParams;

/**
 * 教练详情
 * 
 * @author liulj
 * 
 */
public class CoachDetailActivity extends BaseFragmentActivity {
	private String mCoachId;
	private ImageView mBackIv;
//	private TextView mNameTv, mGenderTv, mAgeTv, mAddressTv, mIdentityTv, mCoachIdTv, mCarTypeTv, mSchoolTv, mCoachLevelTv, mCoachStarTv, mEvaluateTv;
//	private LinearLayout mPhoneLi, mMobileLi;
//	private TextView mPhoneTv, mMobileTv;
	private TextView tvName;
	private TextView tvAge;
	private TextView tvOrderTime;
	private TextView tvCarType;
	private LinearLayout llFreeCoach;
	private TextView tvAddress;
	private TextView tvAboutSelf;
	private TextView tvBookCoach;
	private TextView tvSumStudent;
	private TextView tvSunPinlun;
	private LinearLayout llCoachMobile;
	private LinearLayout llGray;
	private ImageView imgGray;
	private ImageView imgHeader;
	private ImageView imgStarCoach;
	private RelativeLayout rlPinLun;
	private TextView tvNoPinLun;
	private RatingBar mRatingBar;
	private LinearListView mLinearListView;
	private List<Comment> mCommentlist = new ArrayList<Comment>();
	private SubCommentAdapter mSubCommentAdapter;
//	private TextView mNoDataTv, mCommentNumtv,studentnum;
//	private LinearLayout mHasDataLi;
	private PullToRefreshScrollView pullToRefreshSL;
	private int pageNum=0;
	private String telPhone,telMessage;
	private String mGenger;

	@Override
	public void getIntentData() {
		mCoachId = getIntent().getStringExtra("mCoachId");
		
	}

	@Override
	public int getLayoutId() {
		return R.layout.coach_detail_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		tvName = (TextView)findViewById(R.id.tv_name);
		tvAge = (TextView)findViewById(R.id.tv_age);
		tvOrderTime = (TextView)findViewById(R.id.tv_sumnum);
		tvCarType = (TextView)findViewById(R.id.tv_car_type);
		tvBookCoach = (TextView)findViewById(R.id.tv_subject_order);
		llFreeCoach = (LinearLayout)findViewById(R.id.ll_free_coach);
		tvAddress = (TextView)findViewById(R.id.tv_address);
		llCoachMobile = (LinearLayout)findViewById(R.id.ll_coach_mobile);
		tvAboutSelf = (TextView)findViewById(R.id.tv_about_self);
		tvSunPinlun = (TextView)findViewById(R.id.tv_pinlin_sum);
		tvSumStudent = (TextView)findViewById(R.id.tv_sum_student);
		imgGray = (ImageView)findViewById(R.id.img_gray);
		llGray = (LinearLayout)findViewById(R.id.ll_gray);
		imgHeader = (ImageView)findViewById(R.id.iv_header);
		rlPinLun = (RelativeLayout)findViewById(R.id.rl_pinglun);
		imgStarCoach = (ImageView)findViewById(R.id.img_star_coach);
		mBackIv = (ImageView) findViewById(R.id.iv_back);
		tvNoPinLun = (TextView)findViewById(R.id.tv_no_pinlun);
		mRatingBar = (RatingBar) findViewById(R.id.rb_star);
		mLinearListView = (LinearListView) findViewById(R.id.lv_comment);
		pullToRefreshSL=(PullToRefreshScrollView)findViewById(R.id.pull_re_scroll);
		pullToRefreshSL.setMode(Mode.BOTH);
	}

	private void initCoachInfo(CoachInfoVo coachInfoVo) {
		// 头像图标
		Glide.with(CoachDetailActivity.this).load(coachInfoVo.getAvatarurl()).asBitmap().placeholder(R.drawable.login_head_img).override(150, 150).centerCrop().into(new BitmapImageViewTarget(imgHeader) {
			@Override
			protected void setResource(Bitmap resource) {
				LogUtilLj.LLJi("bitmap:" + resource.getWidth() + "*" + resource.getHeight());
				if (resource != null) {
					view.setImageBitmap(BitmapUtilLj.getRoundBitmap(resource));
				}
			}
		});
		setText(tvName, coachInfoVo.getRealname()+"教练");
		switch (coachInfoVo.getGender()) {
		case 1:
			mGenger = "男";
			llGray.setBackgroundResource(R.drawable.shape_female_round);
			imgGray.setImageResource(R.drawable.ic_female);
			break;
		case 2:
			mGenger = "女";
			llGray.setBackgroundResource(R.drawable.shape_male_round);
			imgGray.setImageResource(R.drawable.ic_male);
			break;
		default:
			mGenger = "保密";
			break;
		}
		mRatingBar.setRating(ParseUtilLj.parseFloat(coachInfoVo.getScore(), 0F));
		setText(tvAge, coachInfoVo.getAge());
		setText(tvAddress, coachInfoVo.getAddress());
		setText(tvOrderTime, coachInfoVo.getSumnum()+"");
//		setText(mIdentityTv, coachInfoVo.getId_cardnum());
//		setText(mCoachIdTv, coachInfoVo.getCoach_cardnum());
		if (coachInfoVo.getModellist() != null) {
			int size = coachInfoVo.getModellist().size();
			for (int i = 0; i < size; i++) {
				if (coachInfoVo.getModellist().get(i) != null) {
					String str = coachInfoVo.getModellist().get(i).getModelname();
					if (str != null) {
						if ((i != (size - 1)) && (size != 1)) {
							tvCarType.append(str + ",");
						} else {
							tvCarType.append(str);
						}
					}
				}
			}
		}
		
		if (coachInfoVo.getFreecoursestate()==1)
		{
			llFreeCoach.setVisibility(View.VISIBLE);
		}else{
			llFreeCoach.setVisibility(View.GONE);
		}
		
		if(coachInfoVo.getSignstate() == 1)
		{
			imgStarCoach.setVisibility(View.VISIBLE);
		}else{
			imgStarCoach.setVisibility(View.GONE);
		}
//		setText(mCoachLevelTv, coachInfoVo.getLevel());
//		setText(mCoachStarTv, coachInfoVo.getScore());
		setText(tvAboutSelf, coachInfoVo.getSelfeval());

//		setText(mPhoneTv, "电话");
//		setText(mMobileTv, "短信");
		if (coachInfoVo.getTelphone() != null) {
			//mPhoneTv.setTag(coachInfoVo.getTelphone());
			telPhone=coachInfoVo.getTelphone();
		}
		if (coachInfoVo.getPhone() != null) {
			//mMobileTv.setTag(coachInfoVo.getPhone());
			telMessage=coachInfoVo.getPhone();
		}
		
	}

	@Override
	public void addListeners() {
		mBackIv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
/*		mPhoneLi.setOnClickListener(this);
		mMobileLi.setOnClickListener(this);*/
		rlPinLun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mBaseFragmentActivity, MyCommentListActivity.class);
				intent.putExtra("mCoachid", mCoachId);
				intent.putExtra("flag","all_comment");
				startActivity(intent);
			}
		});
		
		tvBookCoach.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(CoachDetailActivity.this, R.anim.bottom_to_center, R.anim.no_fade);
				Intent intent = new Intent(CoachDetailActivity.this, SubjectReserveActivity.class);
				intent.putExtra("mCoachId", mCoachId);
				intent.putExtra("mScore", mRatingBar.getRating());
				intent.putExtra("mName", tvName.getText().toString().trim());
				intent.putExtra("mGender", mGenger);
				intent.putExtra("mAddress", tvAddress.getText().toString().trim());
				intent.putExtra("mPhone", telPhone);
				intent.putExtra("scheduleInt", "");
				ActivityCompat.startActivity((Activity) CoachDetailActivity.this, intent, options.toBundle());
			}
		});
		
		llCoachMobile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telPhone));
				startActivity(intent);
			}
		});
		
		pullToRefreshSL.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
				pageNum=0;
				//pullToRefreshSL.setRefreshing();
				getStuComment();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
				pageNum++;
				getStuComment();
			}
		});
		

	
	}

	@Override
	public void initViews() {
		
		mSubCommentAdapter = new SubCommentAdapter();
		mLinearListView.setAdapter(mSubCommentAdapter);
		pullToRefreshSL.setRefreshing();
	}

	@Override
	public void requestOnCreate() {
		AsyncHttpClientUtil.get().post(this, Setting.SBOOK_URL, GetCoachDetailResponse.class, new MySubResponseHandler<GetCoachDetailResponse>() {
			@Override
			public void onStart() {
				mLoadingDialog.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {
					}
				});
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetCoachDetail");
				requestParams.add("coachid", mCoachId);
				
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetCoachDetailResponse baseReponse) {
				if (baseReponse.getCoachinfo() != null) {
					initCoachInfo(baseReponse.getCoachinfo());
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

			}
		});

	}
	
	private void getStuComment(){
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SBOOK_URL, CommentListResponse.class, new MySubResponseHandler<CommentListResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GETCOACHCOMMENTS");
				requestParams.add("coachid", mCoachId);
				//requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				requestParams.add("pagenum", pageNum+"");
				requestParams.add("type","1");
				return requestParams;
			}
			@Override
			public void onFinish() {
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, CommentListResponse baseReponse) {
				if (baseReponse.getEvalist() != null && baseReponse.getEvalist().size() != 0) {
					tvSunPinlun.setText("共"+baseReponse.getCount()+"条");
					tvSumStudent.setText("("+baseReponse.getStudentnum()+"人评论)");
					tvNoPinLun.setVisibility(View.GONE);
					rlPinLun.setVisibility(View.VISIBLE);
					if(pageNum==0){
						mCommentlist.clear();	
					}
					mCommentlist.addAll(baseReponse.getEvalist());
					mSubCommentAdapter.notifyDataSetChanged();
					pullToRefreshSL.onRefreshComplete();
				} else {
					if(mCommentlist.size()==0){
						tvNoPinLun.setVisibility(View.VISIBLE);
						rlPinLun.setVisibility(View.INVISIBLE);
					}
					pullToRefreshSL.onRefreshComplete();
				}
			}
		});
	}

	private class SubCommentAdapter extends BaseAdapter {

		@Override
		public int getCount() {
//			if (mCommentlist.size() > 5) {
//				return 5;
//			} else {
				return mCommentlist.size();
			//}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.comment_list_activity_item, null);
			}
			//ImageView imageView = ViewHolderUtilLj.get(convertView, R.id.iv_head);
			TextView tv_time = ViewHolderUtilLj.get(convertView, R.id.tv_time);
			TextView tv_content = ViewHolderUtilLj.get(convertView, R.id.tv_comment);
			TextView tv_Scroll = ViewHolderUtilLj.get(convertView, R.id.tv_scroll);
			RatingBar rb_starb = ViewHolderUtilLj.get(convertView, R.id.rb_star);
			RelativeLayout student_comment=ViewHolderUtilLj.get(convertView, R.id.comment_list);
			Comment comment = mCommentlist.get(position);
			if (comment != null) {
				// 头像
				//loadHeadImage(comment.getAvatarUrl(), 40, 40, imageView);
				//
				//
				if (!TextUtils.isEmpty(comment.getAddtime())) {
					tv_time.setText(comment.getAddtime().split(" ")[0]);
				} else {
					tv_time.setText("");
				}
				setText(tv_content, comment.getContent());
				
			}
			if (!TextUtils.isEmpty(comment.getScore()))
			{
			rb_starb.setRating(ParseUtilLj.parseFloat(comment.getScore(), 0F));
			tv_Scroll.setText(comment.getScore());
			}else{
				rb_starb.setRating(ParseUtilLj.parseFloat("0", 0F));
				tv_Scroll.setText("0");
			}
			// 单个学员 的所有评论
			student_comment.setTag(position);
			student_comment.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				    int a=(Integer) arg0.getTag();
					Intent intent = new Intent(mBaseFragmentActivity, MyCommentListActivity.class);
					intent.putExtra("mCoachid", mCoachId);
					intent.putExtra("flag","student_comment");
					intent.putExtra("studentId",mCommentlist.get(a).getFrom_user());
					intent.putExtra("student_name",mCommentlist.get(a).getNickname() );
					startActivity(intent);
					
				}
			});
			
			
			return convertView;
		}

	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.no_fade, R.anim.center_to_bottom);
	}
}
