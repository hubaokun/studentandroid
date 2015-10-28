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

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.common.library.llj.base.BaseFragmentActivity;
import com.common.library.llj.utils.AsyncHttpClientUtil;
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
public class CoachDetailActivity extends BaseFragmentActivity implements OnClickListener {
	private String mCoachId;
	private ImageView mBackIv;
	private TextView mNameTv, mGenderTv, mAgeTv, mAddressTv, mIdentityTv, mCoachIdTv, mCarTypeTv, mSchoolTv, mCoachLevelTv, mCoachStarTv, mEvaluateTv;
	private LinearLayout mPhoneLi, mMobileLi;
	private TextView mPhoneTv, mMobileTv;
	private RatingBar mRatingBar;
	private LinearListView mLinearListView;
	private List<Comment> mCommentlist = new ArrayList<Comment>();
	private SubCommentAdapter mSubCommentAdapter;
	private TextView mNoDataTv, mCommentNumtv,studentnum;
	private LinearLayout mHasDataLi;
	private PullToRefreshScrollView pullToRefreshSL;
	private int pageNum=0;
	private String telPhone,telMessage;

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
		mBackIv = (ImageView) findViewById(R.id.iv_back);
		mPhoneTv = (TextView) findViewById(R.id.tv_phone);
		mMobileTv = (TextView) findViewById(R.id.tv_mobile);
		mNameTv = (TextView) findViewById(R.id.tv_name);
		mGenderTv = (TextView) findViewById(R.id.tv_gender);
		mAgeTv = (TextView) findViewById(R.id.tv_age);
		mAddressTv = (TextView) findViewById(R.id.tv_address);
		mIdentityTv = (TextView) findViewById(R.id.tv_identity);
		mCoachIdTv = (TextView) findViewById(R.id.tv_coach_id);
		mCarTypeTv = (TextView) findViewById(R.id.tv_car_type);
		mSchoolTv = (TextView) findViewById(R.id.tv_school);
		mCoachLevelTv = (TextView) findViewById(R.id.tv_coach_level);
		mCoachStarTv = (TextView) findViewById(R.id.tv_coach_star);
		mEvaluateTv = (TextView) findViewById(R.id.tv_evaluate);
		mRatingBar = (RatingBar) findViewById(R.id.rb_star);
		mPhoneLi = (LinearLayout) findViewById(R.id.li_phone);
		mMobileLi = (LinearLayout) findViewById(R.id.li_mobile);
		mNoDataTv = (TextView) findViewById(R.id.tv_no_data);
		mHasDataLi = (LinearLayout) findViewById(R.id.li_has_data);
		mCommentNumtv = (TextView) findViewById(R.id.tv_comment_num);
		studentnum=(TextView)findViewById(R.id.tv_studnet_num);
		mLinearListView = (LinearListView) findViewById(R.id.lv_comment);
		pullToRefreshSL=(PullToRefreshScrollView)findViewById(R.id.pull_re_scroll);
		pullToRefreshSL.setMode(Mode.BOTH);
	}

	private void initCoachInfo(CoachInfoVo coachInfoVo) {
		setText(mNameTv, coachInfoVo.getRealname());
		switch (coachInfoVo.getGender()) {
		case 1:
			setText(mGenderTv, "男");
			break;
		case 2:
			setText(mGenderTv, "女");
			break;
		default:
			setText(mGenderTv, "保密");
			break;
		}
		mRatingBar.setRating(ParseUtilLj.parseFloat(coachInfoVo.getScore(), 0F));
		setText(mAgeTv, coachInfoVo.getAge());
		setText(mAddressTv, coachInfoVo.getAddress());
		setText(mIdentityTv, coachInfoVo.getId_cardnum());
		setText(mCoachIdTv, coachInfoVo.getCoach_cardnum());
		mCarTypeTv.setText("");
		if (coachInfoVo.getModellist() != null) {
			int size = coachInfoVo.getModellist().size();
			for (int i = 0; i < size; i++) {
				if (coachInfoVo.getModellist().get(i) != null) {
					String str = coachInfoVo.getModellist().get(i).getModelname();
					if (str != null) {
						if ((i != (size - 1)) && (size != 1)) {
							mCarTypeTv.append(str + ",");
						} else {
							mCarTypeTv.append(str);
						}
					}
				}
			}
		}
		if (TextUtils.isEmpty(coachInfoVo.getDrive_school())) {
			mSchoolTv.setText("暂无");
		} else {
			setText(mSchoolTv, coachInfoVo.getDrive_school());
		}
		setText(mCoachLevelTv, coachInfoVo.getLevel());
		setText(mCoachStarTv, coachInfoVo.getScore());
		setText(mEvaluateTv, coachInfoVo.getSelfeval());

		setText(mPhoneTv, "电话");
		setText(mMobileTv, "短信");
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
		mBackIv.setOnClickListener(this);
		mPhoneLi.setOnClickListener(this);
		mMobileLi.setOnClickListener(this);
		mHasDataLi.setOnClickListener(this);
		
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.li_phone:
			if (!TextUtils.isEmpty(telPhone)) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +telPhone));
				startActivity(intent);
			}
			break;
		case R.id.li_mobile:
			if (!TextUtils.isEmpty(telMessage)) {
				// 系统默认的action，用来打开默认的短信界面
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + telMessage));
				startActivity(intent);
			}
			break;
		case R.id.li_has_data:
			Intent intent = new Intent(mBaseFragmentActivity, MyCommentListActivity.class);
			intent.putExtra("mCoachid", mCoachId);
			intent.putExtra("flag","all_comment");
			startActivity(intent);
			break;
		default:
			break;
		}
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
					mCommentNumtv.setText(+baseReponse.getCount()+"条评论");
					studentnum.setText(baseReponse.getStudentnum()+"个学员/");
					mNoDataTv.setVisibility(View.INVISIBLE);
					mHasDataLi.setVisibility(View.VISIBLE);
					if(pageNum==0){
						mCommentlist.clear();	
					}
					mCommentlist.addAll(baseReponse.getEvalist());
					mSubCommentAdapter.notifyDataSetChanged();
					pullToRefreshSL.onRefreshComplete();
				} else {
					if(mCommentlist.size()==0){
						mNoDataTv.setVisibility(View.VISIBLE);
						mHasDataLi.setVisibility(View.INVISIBLE);
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
			TextView tv_name = ViewHolderUtilLj.get(convertView, R.id.tv_name);
			TextView tv_time = ViewHolderUtilLj.get(convertView, R.id.tv_time);
			TextView tv_content = ViewHolderUtilLj.get(convertView, R.id.tv_comment);
			RelativeLayout student_comment=ViewHolderUtilLj.get(convertView, R.id.comment_list);
			Comment comment = mCommentlist.get(position);
			if (comment != null) {
				// 头像
				//loadHeadImage(comment.getAvatarUrl(), 40, 40, imageView);
				//
				setText(tv_name, comment.getNickname());
				//
				if (!TextUtils.isEmpty(comment.getAddtime())) {
					long time = TimeUitlLj.stringToMilliseconds(2, comment.getAddtime());
					tv_time.setText(TimeUitlLj.getTimeString(time, 2));
				} else {
					tv_time.setText("");
				}
				setText(tv_content, comment.getContent());

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
