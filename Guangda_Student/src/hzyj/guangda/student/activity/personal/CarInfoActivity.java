package hzyj.guangda.student.activity.personal;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.PerfectStudentResponse;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.student.view.BirthdayDialog;
import hzyj.guangda.student.view.BirthdayDialog.OnComfirmClickListener;
import hzyj.guangda.student.view.GetPhotoDialog;
import hzyj.guangda.student.view.GetPhotoDialog.OnGetFileListener;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.BitmapUtilLj;
import com.common.library.llj.utils.DensityUtils;
import com.loopj.android.http.RequestParams;

/**
 * 学驾信息
 * 
 * @author liulj
 * 
 */
public class CarInfoActivity extends TitlebarActivity {
	private EditText mStdIdEt, mIdentityEt;
	private TextView mMakeCardTv;
	private RelativeLayout mIdentityFrontRl, mIdentityBackRl, mStdFrontRl, mStdBackRl;
	private TextView mIdentityFrontTv, mIdentityBackTv, mStdFrontTv, mStdBackTv;
	private ImageView mIdentityFrontIv, mIdentityBackIv, mStdFrontIv, mStdBackIv;
	private GetPhotoDialog mGetPhotoDialog;
	private int mPhotoType = 1;
	private File mIdentityFrontFile, mIdentityBackFile, mStdFrontFile, mStdBackFile;
	private int mWidth;
	private int mHeight;
	private BirthdayDialog mBirthdayDialog;
	private boolean mCanClick;

	@Override
	public int getLayoutId() {
		return R.layout.carinfo_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mStdIdEt = (EditText) findViewById(R.id.et_student_id);
		mMakeCardTv = (TextView) findViewById(R.id.tv_make_card);
		mIdentityEt = (EditText) findViewById(R.id.et_identity_num);

		mIdentityFrontRl = (RelativeLayout) findViewById(R.id.rl_identity_front);
		mIdentityBackRl = (RelativeLayout) findViewById(R.id.rl_identity_back);
		mStdFrontRl = (RelativeLayout) findViewById(R.id.rl_std_front);
		mStdBackRl = (RelativeLayout) findViewById(R.id.rl_std_back);

		mIdentityFrontTv = (TextView) findViewById(R.id.tv_identity_front);
		mIdentityBackTv = (TextView) findViewById(R.id.tv_identity_back);
		mStdFrontTv = (TextView) findViewById(R.id.tv_std_front);
		mStdBackTv = (TextView) findViewById(R.id.tv_std_back);

		mIdentityFrontIv = (ImageView) findViewById(R.id.iv_identity_front);
		mIdentityBackIv = (ImageView) findViewById(R.id.iv_identity_back);
		mStdFrontIv = (ImageView) findViewById(R.id.iv_std_front);
		mStdBackIv = (ImageView) findViewById(R.id.iv_std_back);
		mWidth = (int) (((GuangdaApplication.DISPLAY_WIDTH - DensityUtils.dp2px(this, 45))) / 2.0);
		mHeight = (int) (mWidth * 210 / 258.0);

		mIdentityFrontRl.getLayoutParams().height = mHeight;
		mIdentityBackRl.getLayoutParams().height = mHeight;
		mStdFrontRl.getLayoutParams().height = mHeight;
		mStdBackRl.getLayoutParams().height = mHeight;
	}

	@Override
	public void addListeners() {
		mStdIdEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mStdIdEt.setSelected(true);
				} else {
					mStdIdEt.setSelected(false);
				}
			}
		});
		mStdIdEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (mStdIdEt.length()==0)
				{
					mCanClick = false;
				}else{
					mCanClick = true;
				}
			}
		});
		mMakeCardTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mBirthdayDialog.show();
				mCanClick = true;
			}
		});
		mIdentityEt.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					mIdentityEt.setSelected(true);
				} else {
					mIdentityEt.setSelected(false);
				}
			}
		});
		mIdentityEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (mIdentityEt.length() == 0)
				{
					mCanClick = false;
				}else{
					mCanClick = true;
				}
			}
		});
		mIdentityFrontRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPhotoType = 1;
				mGetPhotoDialog.show();
				
			}
		});
		mIdentityBackRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPhotoType = 2;
				mGetPhotoDialog.show();
				mCanClick = true;
			}
		});
		mStdFrontRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPhotoType = 3;
				mGetPhotoDialog.show();
				mCanClick = true;
			}
		});
		mStdBackRl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPhotoType = 4;
				mGetPhotoDialog.show();
				mCanClick = true;
			}
		});
		mCommonTitlebar.setRightTextOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mCanClick) {
					showToast("请你填写信息后，再提交");
					return;
				}
				AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, PerfectStudentResponse.class, new MySubResponseHandler<PerfectStudentResponse>() {
					@Override
					public void onStart() {
						mLoadingDialog.show();
					}

					@Override
					public RequestParams setParams(RequestParams requestParams) {
						requestParams.add("action", "PerfectStudentInfo");
						requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
						requestParams.add("idnum", mIdentityEt.getText().toString().trim());
						requestParams.add("studentcardnum", mStdIdEt.getText().toString().trim());
						requestParams.add("scardyear", mMakeCardTv.getText().toString().trim());
						try {
							if (mIdentityFrontFile != null)
								requestParams.put("cardpic1", mIdentityFrontFile);
							if (mIdentityBackFile != null)
								requestParams.put("cardpic2", mIdentityBackFile);
							if (mStdFrontFile != null)
								requestParams.put("cardpic3", mStdFrontFile);
							if (mStdBackFile != null)
								requestParams.put("cardpic4", mStdBackFile);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						return requestParams;
					}

					@Override
					public void onFinish() {
						mLoadingDialog.dismiss();
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers, PerfectStudentResponse baseReponse) {
						showToast("修改成功！");
						mIdentityFrontFile = null;
						mIdentityBackFile = null;
						mStdFrontFile = null;
						mStdBackFile = null;

						GuangdaApplication.mUserInfo.setStudent_cardnum(mStdIdEt.getText().toString().trim());
						GuangdaApplication.mUserInfo.setStudent_card_creat(mMakeCardTv.getText().toString().trim());
						GuangdaApplication.mUserInfo.setId_cardnum(mIdentityEt.getText().toString().trim());
						if (baseReponse.getCardpic1url() != null)
							GuangdaApplication.mUserInfo.setId_cardpicfurl(baseReponse.getCardpic1url());
						if (baseReponse.getCardpic2url() != null)
							GuangdaApplication.mUserInfo.setId_cardpicburl(baseReponse.getCardpic2url());
						if (baseReponse.getCardpic3url() != null)
							GuangdaApplication.mUserInfo.setStudent_cardpicfurl(baseReponse.getCardpic3url());
						if (baseReponse.getCardpic4url() != null)
							GuangdaApplication.mUserInfo.setStudent_cardpicburl(baseReponse.getCardpic4url());
						finish();
					}

					@Override
					public void onNotSuccess(Context context, int statusCode, Header[] headers, PerfectStudentResponse baseReponse) {
						super.onNotSuccess(context, statusCode, headers, baseReponse);
						if (baseReponse.getCode() == 2) {
							showToast("用户不存在");
						}
					}
				});
			}
		});
	}

	private boolean isAllEmpter() {
		boolean a = TextUtils.isEmpty(mStdIdEt.getText().toString().trim());
		boolean b = TextUtils.isEmpty(mMakeCardTv.getText().toString().trim());
		boolean c = TextUtils.isEmpty(mIdentityEt.getText().toString().trim());
		boolean d = mIdentityFrontFile == null;
		boolean e = mIdentityBackFile == null;
		boolean f = mStdFrontFile == null;
		boolean g = mStdBackFile == null;
		return a && b && c && d && e && f && g;

	}

	@Override
	public void initViews() {
		mGetPhotoDialog = new GetPhotoDialog(this);

		mBirthdayDialog = new BirthdayDialog(this);
		mBirthdayDialog.setOnComfirmClickListener(new OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(int year, int month, int day) {
				String monthStr = month < 10 ? "0" + month : "" + month;
				String dayStr = day < 10 ? "0" + day : "" + day;
				mMakeCardTv.setText(year + "-" + monthStr + "-" + dayStr);
			}
		});

		mCommonTitlebar.getCenterTextView().setText("学驾信息");
		// 学员证号
		setText(mStdIdEt, GuangdaApplication.mUserInfo.getStudent_cardnum());
		// 制证时间
		setText(mMakeCardTv, GuangdaApplication.mUserInfo.getStudent_card_creat());
		// 身份证号码
		setText(mIdentityEt, GuangdaApplication.mUserInfo.getId_cardnum());
		//
		loadImageNoHolder(GuangdaApplication.mUserInfo.getId_cardpicfurl(), mWidth, mHeight, mIdentityFrontIv);
		loadImageNoHolder(GuangdaApplication.mUserInfo.getId_cardpicburl(), mWidth, mHeight, mIdentityBackIv);
		loadImageNoHolder(GuangdaApplication.mUserInfo.getStudent_cardpicfurl(), mWidth, mHeight, mStdFrontIv);
		loadImageNoHolder(GuangdaApplication.mUserInfo.getStudent_cardpicburl(), mWidth, mHeight, mStdBackIv);

		if (isAllEmpter()) {
			setRightText("提交", 10, R.color.text_deep_gray);
			mCanClick = false;
		} else {
			setRightText("提交", 10, R.color.text_light_green);
			mCanClick = true;
		}
	}

	@Override
	public void requestOnCreate() {

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		mGetPhotoDialog.handleResult(arg0, arg1, arg2, new OnGetFileListener() {
			@Override
			public void AfterGetFile(File file) {
				switch (mPhotoType) {
				case 1:
					mIdentityFrontFile = BitmapUtilLj.compressImageFromFile(file);
					loadFileToImage(mIdentityFrontFile, mWidth, mHeight, mIdentityFrontIv);
					break;
				case 2:
					mIdentityBackFile = BitmapUtilLj.compressImageFromFile(file);
					loadFileToImage(mIdentityBackFile, mWidth, mHeight, mIdentityBackIv);
					break;
				case 3:
					mStdFrontFile = BitmapUtilLj.compressImageFromFile(file);
					loadFileToImage(mStdFrontFile, mWidth, mHeight, mStdFrontIv);
					break;
				case 4:
					mStdBackFile = BitmapUtilLj.compressImageFromFile(file);
					loadFileToImage(mStdBackFile, mWidth, mHeight, mStdBackIv);
					break;

				default:
					break;
				}

			}
		});
	}
}
