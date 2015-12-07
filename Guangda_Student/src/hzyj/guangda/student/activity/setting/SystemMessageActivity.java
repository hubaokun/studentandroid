package hzyj.guangda.student.activity.setting;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.GetNoticesResponse;
import hzyj.guangda.student.response.GetNoticesResponse.Notice;
import hzyj.guangda.student.util.MySubResponseHandler;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TimeUitlLj;
import com.daimajia.swipe.SwipeLayout;
import com.loopj.android.http.RequestParams;

/**
 * 系统消息列表
 * 
 * @author liulj
 * 
 */
public class SystemMessageActivity extends TitlebarActivity {
	private PtrClassicFrameLayout mPtrFrameLayout;
	private ListView mListView;
	private int mPage;
	private MessageListAdapter mMessageListAdapter;
	private RelativeLayout rl_bg_system_message;

	@Override
	public int getLayoutId() {
		return R.layout.system_message_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame);
		mPtrFrameLayout.setDurationToCloseHeader(800);
		mListView = (ListView) findViewById(R.id.lv_msg);
		rl_bg_system_message=(RelativeLayout) findViewById(R.id.rl_bg_system_message);
		

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

	}

	@Override
	public void initViews() {
		setCenterText("系统消息");

		mMessageListAdapter = new MessageListAdapter(this, R.layout.system_message_activity_item);
		onLoadMoreData(mListView, mMessageListAdapter);
		mListView.setAdapter(mMessageListAdapter);

	}

	@Override
	public void requestOnCreate() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		mPtrFrameLayout.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPtrFrameLayout.autoRefresh(true);
			}
		}, 150);
	}

	public void doLoadMoreData() {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SSET_URL, GetNoticesResponse.class, new MySubResponseHandler<GetNoticesResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "GetNotices");
				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				requestParams.add("pagenum", mPage + "");
				return requestParams;
			}

			@Override
			public void onFinish() {
				mPtrFrameLayout.refreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetNoticesResponse baseReponse) {
				initAllData(baseReponse);
			}
		});
	}

	private void initAllData(GetNoticesResponse baseReponse) {
	
		if (mPage == 0) {
			mMessageListAdapter.clear();
		}
		if (baseReponse.getHasmore() == 0) {
			rl_bg_system_message.setVisibility(View.VISIBLE);
			mMessageListAdapter.showIndeterminateProgress(false);
		} else if (baseReponse.getHasmore() == 1 && baseReponse.getDatalist() != null) {
			mMessageListAdapter.showIndeterminateProgress(true);
			rl_bg_system_message.setVisibility(View.GONE);
			mPage++;
		}
		mMessageListAdapter.addAll(baseReponse.getDatalist());
	}

	/**
	 * 消息列表
	 * 
	 * @author liulj
	 * 
	 */
	private class MessageListAdapter extends QuickAdapter<Notice> {

		public MessageListAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, final Notice item, int position) {
			if (item != null) {
				final SwipeLayout swipe = helper.getView(R.id.swipe);
				swipe.close();
				//
				helper.setText(R.id.tv_msg_type, item.getCategory()).setText(R.id.tv_sum, item.getContent());
				// tag标记
				if (item.getReadstate() == 0) {
					helper.setVisible(R.id.iv_point_tag, true);
				} else {
					helper.setVisible(R.id.iv_point_tag, false);
				}
				// 日期显示
				if (item.getAddtime() != null) {
					String date = TimeUitlLj.millisecondsToString(9, TimeUitlLj.stringToMilliseconds(2, item.getAddtime()));
					helper.setText(R.id.tv_msg_date, date);
				}
				//
				helper.getView(R.id.trash).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						deleteRequest(item.getNoticeid());
					}
				});
				helper.getView(R.id.fl_wrap).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mBaseFragmentActivity, MessageDetailActivity.class);
						intent.putExtra("mDetailStr", item.getContent());
						intent.putExtra("mDateStr", item.getAddtime());
						intent.putExtra("mNoticeid", item.getNoticeid());
						intent.putExtra("mReadState", item.getReadstate());
						startActivity(intent);
					}
				});
			}
		}
	}

	private void deleteRequest(final String noticeid) {
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SSET_URL, GetNoticesResponse.class, new MySubResponseHandler<GetNoticesResponse>() {
			@Override
			public void onStart() {
				super.onStart();
				mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "DelNotice");
				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				requestParams.add("noticeid", noticeid);
				return requestParams;
			}

			@Override
			public void onFinish() {
				mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetNoticesResponse baseReponse) {
				showToast("删除成功！");
				onResume();
				
			}
		});
	}
}
