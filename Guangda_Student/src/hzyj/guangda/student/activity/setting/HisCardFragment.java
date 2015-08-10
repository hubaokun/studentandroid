package hzyj.guangda.student.activity.setting;

import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.entity.Coupon;
import hzyj.guangda.student.response.GetCouponListResponse;
import hzyj.guangda.student.util.MySubResponseHandler;

import org.apache.http.Header;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.common.library.llj.adapterhelp.BaseAdapterHelper;
import com.common.library.llj.adapterhelp.QuickAdapter;
import com.common.library.llj.base.BaseFragment;
import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.common.library.llj.utils.TimeUitlLj;
import com.loopj.android.http.RequestParams;

/**
 * 历史小巴券列表
 * 
 * @author liulj
 * 
 */
public class HisCardFragment extends BaseFragment {
	private ListView mListView;
	private CradAdapter mCradAdapter;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.his_card_fragment, container, false);
		mListView = (ListView) view.findViewById(R.id.lv_card);

		return view;
	}

	@Override
	protected void addListeners(View view, Bundle savedInstanceState) {

	}

	@Override
	protected void initViews(View view, Bundle savedInstanceState) {
		mCradAdapter = new CradAdapter(mBaseFragmentActivity, R.layout.current_card_item);
		mListView.setAdapter(mCradAdapter);
	}

	@Override
	protected void requestOnCreate() {
		AsyncHttpClientUtil.get().post(getActivity(), Setting.SBOOK_URL, GetCouponListResponse.class, new MySubResponseHandler<GetCouponListResponse>() {
			@Override
			public void onStart() {
				super.onStart();
				mBaseFragmentActivity.mLoadingDialog.show();
			}

			@Override
			public RequestParams setParams(RequestParams requestParams) {
				requestParams.add("action", "getHisCouponList");
				requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
				return requestParams;
			}

			@Override
			public void onFinish() {
				mBaseFragmentActivity.mLoadingDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, GetCouponListResponse baseReponse) {
				initAllData(baseReponse);
			}
		});

	}

	private void initAllData(GetCouponListResponse baseReponse) {
		if (baseReponse.getCouponlist() != null) {
			mCradAdapter.addAll(baseReponse.getCouponlist());
		}
	}

	private class CradAdapter extends QuickAdapter<Coupon> {

		public CradAdapter(Context context, int layoutResId) {
			super(context, layoutResId);
		}

		@Override
		protected void convert(BaseAdapterHelper helper, View convertView, Coupon item, int position) {
			if (item != null) {
				TextView money = helper.getView(R.id.tv_title);
				TextView status = helper.getView(R.id.tv_status);
				TextView sub_title = helper.getView(R.id.tv_sub_title);
				TextView from = helper.getView(R.id.tv_from);
				TextView end_time = helper.getView(R.id.tv_limit);
				// 标题
				if (item.getCoupontype() == 1) {
					money.setText("小巴券-学时券");
					sub_title.setText("本券可以抵用1学时学费");
				} else if (item.getCoupontype() == 2) {
					money.setText("小巴券-抵价券");
					sub_title.setText("本券可以抵用学费" + item.getValue() + "元");
				}
				// 来源
				from.setText(item.getTitle() == null ? "" : item.getTitle());
				// 状态
				switch (item.getState()) {
				case 1:
					status.setVisibility(View.VISIBLE);
					status.setText("已使用");
					break;
				default:
					status.setVisibility(View.VISIBLE);
					status.setText("已过期");
					break;
				}
				// 期限
				if (!TextUtils.isEmpty(item.getEnd_time())) {
					end_time.setText("有效期：" + TimeUitlLj.millisecondsToString(8, TimeUitlLj.stringToMilliseconds(2, item.getEnd_time())));
				}
			}
		}
	}
}
