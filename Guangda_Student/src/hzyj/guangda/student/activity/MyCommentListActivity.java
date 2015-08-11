package hzyj.guangda.student.activity;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.CommentListResponse;
import hzyj.guangda.student.response.CommentListResponse.Comment;
import hzyj.guangda.student.util.MySubResponseHandler;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import org.apache.http.Header;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TimeUitlLj;
import com.loopj.android.http.RequestParams;

/**
 * 我的评论列表
 * 
 * @author liulj
 * 
 */
public class MyCommentListActivity extends TitlebarActivity {
	private PtrClassicFrameLayout mPtrFrameLayout;
	private ListView mListView;
	private CommentListAdapter mCommentListAdapter;
	private int mPage;
	private String mCoachid;

	@Override
	public void getIntentData() {
		super.getIntentData();
		mCoachid = getIntent().getStringExtra("mCoachid");
	}

	@Override
	public int getLayoutId() {
		return R.layout.my_comment_list_activity;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		mPtrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_frame);
		mPtrFrameLayout.setDurationToCloseHeader(800);
		mListView = (ListView) findViewById(R.id.lv_coach);
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

		mCommentListAdapter = new CommentListAdapter(this, R.layout.comment_list_activity_item);
		onLoadMoreData(mListView, mCommentListAdapter);
		mListView.setAdapter(mCommentListAdapter);
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
		AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SBOOK_URL, CommentListResponse.class, new MySubResponseHandler<CommentListResponse>() {

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "getCoachComments");
				requestParams.add("coachid", mCoachid);
				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				requestParams.add("pagenum", mPage + "");
				return requestParams;
			}

			@Override
			public void onFinish() {
				mPtrFrameLayout.refreshComplete();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, CommentListResponse baseReponse) {
				initAllData(baseReponse);
			}
		});
	}

	private void initAllData(CommentListResponse baseReponse) {
		setCenterText("评论(" + baseReponse.getCount() + ")");
		if (mPage == 0) {
			mCommentListAdapter.clear();
		}
		mCommentListAdapter.addAllNoNotify(baseReponse.getEvalist());
		if (baseReponse.getCount() != 0 && (baseReponse.getCount() == mCommentListAdapter.getList().size())) {
			mCommentListAdapter.showIndeterminateProgress(false);
		} else if (baseReponse.getCount() != 0 && baseReponse.getEvalist() != null) {
			mCommentListAdapter.showIndeterminateProgress(true);
			mPage++;
		}
		mCommentListAdapter.notifyDataSetChanged();
	}

	private class CommentListAdapter extends QuickAdapter<Comment> {

		public CommentListAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, final Comment item, int position) {
			if (item != null) {
				// 头像
				loadHeadImage(item.getAvatarUrl(), 120, 120, ((ImageView) helper.getView(R.id.iv_head)));
				// 昵称和评论
				helper.setText(R.id.tv_name, item.getNickname()).setText(R.id.tv_comment, item.getContent());
				// 时间
				TextView textView = helper.getView(R.id.tv_time);

				if (!TextUtils.isEmpty(item.getAddtime())) {
					long time = TimeUitlLj.stringToMilliseconds(2, item.getAddtime());
					textView.setText(TimeUitlLj.getTimeString(time, 2));
				} else {
					textView.setText("");
				}
			}
		}

	}
}
