 package hzyj.guangda.studnet.wxapi;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;


public class WxPayTask {
	private WParams Wparams;
	private Activity mContext;
	 IWXAPI msgApi;
	 List<NameValuePair> signParams;
	 private String Sign;
	
	  PayReq req;
	
	
	public WxPayTask(Activity mcontext,WParams Wparams){
		this.Wparams=Wparams;
		this.mContext=mcontext;	
	}
	
	public void Execute(){
		req=new PayReq();
		msgApi = WXAPIFactory.createWXAPI(mContext,null);
		genPayReq();
		sendPayReq();
	}
	
	

	// 生成签名
	private void genPayReq() {
		req.appId =Wparams.getAppId();
		req.partnerId = Wparams.getMch_id();
		req.prepayId= Wparams.getPrepay_id();
		req.packageValue="Sign=WXPay";
		req.nonceStr= Wparams.getNonceStr();
		req.timeStamp= Wparams.getTimeStamp();


		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		//sb.append("sign\n"+req.sign+"\n\n");


	}
	
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(key.mch_key);

        //appid=wxa508cf6ae267e0a8&noncestr=pz5um4ffP5BOpIVE8PMicxB4L&package=Sign=WXpay&
		//partnerid=1274984501&prepayid=wx20151012165828a9355df1660527388519&timestamp=1444640308&key=WWuMV5X2hjW4ETsxt6UbehSodSwQCrIq
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		
		Log.e("orion",appSign);
		return appSign;
	}
	
	private void sendPayReq() {
	  //注册appid
		msgApi.registerApp(key.appid);
		if( msgApi.openWXApp()){
			Toast.makeText(mContext,"1",Toast.LENGTH_SHORT);
		}
		msgApi.sendReq(req);
		
	}
	

	


}
