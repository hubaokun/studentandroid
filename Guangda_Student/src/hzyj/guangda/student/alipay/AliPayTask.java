/**
 * 
 */
package hzyj.guangda.student.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * @author qiuch
 * 
 */
public class AliPayTask {

	public static final String TAG = "alipay-sdk";
	private Activity mContext;
	private Pparams mPparams;
	private String info;
	private static final int RQF_PAY = 1;
	private static final int RQF_LOGIN = 2;
	private String orderid;
	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);

			switch (msg.what) {
			case RQF_PAY:
				Log.e("pay---result", "ok");
				// Toast.makeText(mContext, "支付成功，我们将尽快为您安排发货", Toast.LENGTH_SHORT).show();
				String resultStr = (String) msg.obj;
				if (resultStr != null) {
					if (resultStr.contains("9000")) {// ==============
						Toast.makeText(mContext, "支付成功!", Toast.LENGTH_SHORT).show();
						((Activity) mContext).finish();
					} else if (resultStr.contains("4000")) {
						Toast.makeText(mContext, "系统异常", Toast.LENGTH_SHORT).show();
					} else if (resultStr.contains("4001")) {
						Toast.makeText(mContext, "数据格式不正确", Toast.LENGTH_SHORT).show();
					} else if (resultStr.contains("4003")) {
						Toast.makeText(mContext, "该用户绑定的支付宝账户被冻结或不允许支付", Toast.LENGTH_SHORT).show();
					} else if (resultStr.contains("4004")) {
						Toast.makeText(mContext, "该用户已解除绑定", Toast.LENGTH_SHORT).show();
					} else if (resultStr.contains("4005")) {
						Toast.makeText(mContext, "绑定失败或没有绑定", Toast.LENGTH_SHORT).show();
					} else if (resultStr.contains("4006")) {
						Toast.makeText(mContext, "订单支付失败", Toast.LENGTH_SHORT).show();
					} else if (resultStr.contains("4010")) {
						Toast.makeText(mContext, "重新绑定账户", Toast.LENGTH_SHORT).show();
					} else if (resultStr.contains("6000")) {
						Toast.makeText(mContext, "支付服务正在进行升级操作", Toast.LENGTH_SHORT).show();
					} else if (resultStr.contains("6001")) {
						Toast.makeText(mContext, "用户中途取消支付操作", Toast.LENGTH_SHORT).show();
					} else if (resultStr.contains("7001")) {
						Toast.makeText(mContext, "网页支付失败", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
					}
				}

				// mContext.finish();
				break;
			case RQF_LOGIN: {
				Toast.makeText(mContext, result.getResult(), Toast.LENGTH_SHORT).show();
			}
				break;
			default:
				break;
			}
		};
	};

	public AliPayTask(Activity mContext, Pparams mPparams, String orderid) {
		super();
		this.mContext = mContext;
		this.mPparams = mPparams;
		this.orderid = orderid;
	}

	public void Execute() {

		String sign = "";
		try {
			info = getNewOrderInfo();
			sign = Rsa.sign(info, mPparams.getRsakey());
			sign = URLEncoder.encode(sign, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		info += "&sign=\"" + sign + "\"&" + getSignType();
		Log.i("ExternalPartner", "start pay");
		// start the pay.
		Log.i(TAG, "info = " + info);

		try {
			new Thread() {

				public void run() {
					// AliPay alipay = new AliPay(mContext, mHandler);
					PayTask alipay = new PayTask(mContext);

					// 设置为沙箱模式，不设置默认为线上环境
					// alipay.setSandBox(true);

					String result = alipay.pay(info);

					Log.i(TAG, "result = " + result);
					Message msg = new Message();
					msg.what = RQF_PAY;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			}.start();

		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e("exception", "alipay");
			Toast.makeText(mContext, "Failure calling remote service", Toast.LENGTH_SHORT).show();
		}
	}

	private String getNewOrderInfo() throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		sb.append("partner=\"");
		sb.append(mPparams.getPartner());
		sb.append("\"&out_trade_no=\"");
		sb.append(mPparams.getOut_trade_no());
		sb.append("\"&subject=\"");
		sb.append(mPparams.getSubject());
		sb.append("\"&body=\"");
		sb.append(mPparams.getBody());
		sb.append("\"&total_fee=\"");
		sb.append(mPparams.getTotal_fee());
		sb.append("\"&notify_url=\"");

		// 网址需要做URL编码
		sb.append(URLEncoder.encode(mPparams.getNotify_url(), "utf-8"));
		sb.append("\"&service=\"mobile.securitypay.pay");
		sb.append("\"&_input_charset=\"utf-8");
		sb.append("\"&return_url=\"");
		sb.append(URLEncoder.encode("m.alipay.com", "utf-8"));
		sb.append("\"&payment_type=\"1");
		sb.append("\"&seller_id=\"");
		sb.append(mPparams.getSeller_id());
		sb.append("\"&it_b_pay=\"120m");

		// 如果show_url值为空，可不传
		// sb.append("\"&show_url=\"");
		// sb.append("\"&it_b_pay=\"1m");
		sb.append("\"");

		return new String(sb);
	}

	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
