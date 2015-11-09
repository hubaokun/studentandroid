package hzyj.guangda.student.view;

import java.security.spec.MGF1ParameterSpec;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.activity.CoachDetailActivity;
import hzyj.guangda.student.activity.CoachFilterActivity2;
import hzyj.guangda.student.activity.SubjectReserveActivity;
import hzyj.guangda.student.activity.login.LoginActivity;
import hzyj.guangda.student.entity.CoachInfoVo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.sax.StartElementListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.common.library.llj.base.BaseDialog;
import com.common.library.llj.utils.BitmapUtilLj;
import com.common.library.llj.utils.LogUtilLj;
import com.common.library.llj.utils.ParseUtilLj;

public class MapBottomDialog extends BaseDialog {
	private TextView mCoachNameTv, mAddressTv, mCoachDetailTv, mSubjectOrderTv,tv_sumnum;
	private RatingBar mStarRb;
	private String mCoachId;
	private ImageView mHeaderIv,coach_sex;
	private NeedRealNameDialog realNameDialog;
	private Context mcontext;
	private String phone;
	private String mCoachGender;
	private ImageView imgStarCoach;
	private LinearLayout llFreeClass;

	public MapBottomDialog(Context context) {
		super(context, R.style.dim_dialog);
		mcontext = context;
		ShowRealNameDialog();
	}

	public MapBottomDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
		mcontext = context;
		ShowRealNameDialog();
	}

	public MapBottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mcontext = context;
		ShowRealNameDialog();
	}
	
	private void ShowRealNameDialog()
	{
		realNameDialog = new NeedRealNameDialog(mcontext);
		realNameDialog.setCanceledOnTouchOutside(true); // dialog 点击外部消失
		
	}

	@Override
	protected int getLayoutId() {
		return R.layout.map_bottom_dialog;
	}

	@Override
	protected void findViews() {
		mHeaderIv = (ImageView) findViewById(R.id.iv_header);
		//mCoachGender = (TextView)findViewById(R.id.tv_coach);
		mCoachNameTv = (TextView) findViewById(R.id.tv_name);
		mAddressTv = (TextView) findViewById(R.id.tv_address);
		mCoachDetailTv = (TextView) findViewById(R.id.tv_coach_detail);
		mSubjectOrderTv = (TextView) findViewById(R.id.tv_subject_order);
		mStarRb = (RatingBar) findViewById(R.id.rb_star);
		tv_sumnum = (TextView) findViewById(R.id.tv_sumnum);
		coach_sex=(ImageView)findViewById(R.id.coach_sex);
		imgStarCoach = (ImageView)findViewById(R.id.img_star_coach);
		llFreeClass = (LinearLayout)findViewById(R.id.ll_free_coach);
		//
		mCoachDetailTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.bottom_to_center, R.anim.no_fade);
				Intent intent = new Intent(mContext, CoachDetailActivity.class);
				intent.putExtra("mCoachId", mCoachId);
				ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
				// Intent intent = new Intent(mContext, CoachDetailActivity.class);
				// intent.putExtra("mCoachId", mCoachId);
				// mContext.startActivity(intent);
				dismiss();
			}
		});
		//
		mSubjectOrderTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.bottom_to_center, R.anim.no_fade);
				Intent intent = new Intent(mContext, SubjectReserveActivity.class);
				intent.putExtra("mCoachId", mCoachId);
				intent.putExtra("mScore", mStarRb.getRating());
				intent.putExtra("mName", mCoachNameTv.getText().toString().trim());
				intent.putExtra("mGender", mCoachGender);
				intent.putExtra("mAddress", mAddressTv.getText().toString().trim());
				intent.putExtra("mPhone", phone);
				intent.putExtra("scheduleInt", "");
				ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
//				if (GuangdaApplication.mUserInfo.getRealname()!=null&&GuangdaApplication.mUserInfo.getPhone()!=null)
//				{
//				ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(mContext, R.anim.bottom_to_center, R.anim.no_fade);
//				Intent intent = new Intent(mContext, SubjectReserveActivity.class);
//				intent.putExtra("mCoachId", mCoachId);
//				intent.putExtra("mScore", mStarRb.getRating());
//				intent.putExtra("mName", mCoachNameTv.getText().toString().trim());
//				intent.putExtra("mGender", mCoachGender.getText().toString().trim());
//				intent.putExtra("mAddress", mAddressTv.getText().toString().trim());
//				ActivityCompat.startActivity((Activity) mContext, intent, options.toBundle());
//				// Intent intent = new Intent(mContext, SubjectReserveActivity.class);
//				// intent.putExtra("mCoachId", mCoachId);
//				// intent.putExtra("mScore", mStarRb.getRating());
//				// intent.putExtra("mName", mCoachNameTv.getText().toString().trim());
//				// intent.putExtra("mAddress", mAddressTv.getText().toString().trim());
//				// mContext.startActivity(intent);
//				dismiss();
//				return;
//				}
//				if (GuangdaApplication.mUserInfo.getRealname()==null&&GuangdaApplication.mUserInfo.getPhone()!=null)
//				{
//					realNameDialog.show();
//					return;
//				}
//				
//				if (GuangdaApplication.mUserInfo.getRealname() == null&&GuangdaApplication.mUserInfo.getPhone()==null)
//				{
//					Intent intent = new Intent(mcontext,LoginActivity.class);
//					mcontext.startActivity(intent);
//					return;
//				}
			}
		});
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.BOTTOM);
		setCanceledOnTouchOutside(true);
	}

	public void updateInfo(CoachInfoVo coachInfoVo) {
		mCoachId = coachInfoVo.getCoachid();
		// 头像图标
		Glide.with(mContext).load(coachInfoVo.getAvatarurl()).asBitmap().placeholder(R.drawable.login_head_img).override(150, 150).centerCrop().into(new BitmapImageViewTarget(mHeaderIv) {
			@Override
			protected void setResource(Bitmap resource) {
				LogUtilLj.LLJi("bitmap:" + resource.getWidth() + "*" + resource.getHeight());
				if (resource != null) {
					view.setImageBitmap(BitmapUtilLj.getRoundBitmap(resource));
				}
			}
		});
		//mCoachGender.setText("");
		mCoachNameTv.setText(coachInfoVo.getRealname() == null ? "" : coachInfoVo.getRealname()+"教练");
		if (coachInfoVo.getGender() == 1) {
			//mCoachGender.append("(" + "男" + ")");
			mCoachGender="(男)";
			coach_sex.setImageResource(R.drawable.ic_female); 
			coach_sex.setBackgroundResource(R.drawable.shape_female_round);
		} else if (coachInfoVo.getGender() == 2) {
			//mCoachGender.append("(" + "女" + ")");
			mCoachGender="(女)";
			coach_sex.setImageResource(R.drawable.ic_male);
			coach_sex.setBackgroundResource(R.drawable.shape_male_round);
		}
		tv_sumnum.setText(""+coachInfoVo.getSumnum());
		mCoachNameTv.setText(coachInfoVo.getRealname());
		mStarRb.setRating(ParseUtilLj.parseFloat(coachInfoVo.getScore(), 0f));
		mAddressTv.setText(coachInfoVo.getDetail() == null ? "" : "练车地址："+coachInfoVo.getDetail());
		if (coachInfoVo.getSignstate()==1)
		{
			imgStarCoach.setVisibility(View.VISIBLE);
		}else{
			imgStarCoach.setVisibility(View.GONE);
		}
		
		if (coachInfoVo.getFreecoursestate()==1)
		{
			llFreeClass.setVisibility(View.VISIBLE);
		}else{
			llFreeClass.setVisibility(View.GONE);
		}
		phone = coachInfoVo.getPhone();
	}

}
