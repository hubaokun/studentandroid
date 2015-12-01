package hzyj.guangda.student.activity;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.Header;

import com.common.library.llj.utils.AsyncHttpClientUtil;
import com.loopj.android.http.RequestParams;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import hzyj.guangda.student.GuangdaApplication;
import hzyj.guangda.student.R;
import hzyj.guangda.student.TitlebarActivity;
import hzyj.guangda.student.alipay.AliPayTask;
import hzyj.guangda.student.alipay.Pparams;
import hzyj.guangda.student.common.Setting;
import hzyj.guangda.student.response.RechargeResponse;
import hzyj.guangda.student.response.WxRechargeResponse;
import hzyj.guangda.student.util.MySubResponseHandler;
import hzyj.guangda.studnet.wxapi.WParams;
import hzyj.guangda.studnet.wxapi.WxPayTask;
import hzyj.guangda.studnet.wxapi.key;

public class ActivityRechargePay extends TitlebarActivity{
	
	
	private TextView tv_pay,tv_pay_much,tvTitleRight;
	private LinearLayout ll_weixin_pay,ll_alipay;
	private ImageView iv_alipay_select,iv_weixin_select;
	
	
	private String much_money;
	private String wPage;  // 1一键报名 2 充值
	private String cityid,model;
	
	
	private boolean isSelectWX=false;

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.select_recharge_way;
	}

	@Override
	public void findViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		tv_pay_much=(TextView) findViewById(R.id.tv_pay_much);
		ll_weixin_pay=(LinearLayout) findViewById(R.id.ll_weixin_pay);
		ll_alipay=(LinearLayout) findViewById(R.id.ll_alipay);
		iv_weixin_select=(ImageView) findViewById(R.id.iv_weixin_select);
		iv_alipay_select=(ImageView) findViewById(R.id.iv_alipay_select);
		tv_pay=(TextView) findViewById(R.id.tv_pay);
		
		tvTitleRight = (TextView)findViewById(R.id.tv_right_text);
		
		
	}
	
	//获取客户端ip
	// 得到本机ip地址
//    public String getLocalHostIp()
//    {
//        String ipaddress = "";
//        try
//        {
//            Enumeration<NetworkInterface> en = NetworkInterface
//                    .getNetworkInterfaces();
//            // 遍历所用的网络接口
//            while (en.hasMoreElements())
//            {
//                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
//                Enumeration<InetAddress> inet = nif.getInetAddresses();
//                // 遍历每一个接口绑定的所有ip
//                while (inet.hasMoreElements())
//                {
//                    InetAddress ip = inet.nextElement();
//                    if (!ip.isLoopbackAddress()
//                            && InetAddressUtils.isIPv4Address(ip
//                                    .getHostAddress()))
//                    {
//                        return ipaddress = "本机的ip是" + "：" + ip.getHostAddress();
//                    }
//                }
//
//            }
//        }
//        catch (SocketException e)
//        {
//            Log.e("feige", "获取本地ip地址失败");
//            e.printStackTrace();
//        }
//        return ipaddress;
//
//    }
	
	public static String GetHostIp() {    
	    try {  
	        for (Enumeration<NetworkInterface> en = NetworkInterface  
	                .getNetworkInterfaces(); en.hasMoreElements();) {   // 遍历所用的网络接口
	            NetworkInterface intf = en.nextElement();  
	            for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr  
	                    .hasMoreElements();) {                       	// 得到每一个网络接口绑定的所有ip
	                InetAddress inetAddress = ipAddr.nextElement();  
	                if (!inetAddress.isLoopbackAddress()&& inetAddress instanceof Inet4Address) {   // 判断是否是ipv4                      
	                    return inetAddress.getHostAddress();  
	                }  
	            }  
	        }  
	    } catch (SocketException ex) {  
	    } catch (Exception e) {  
	    }  
	    return null;  
	} 

	@Override
	public void addListeners() {
		ll_weixin_pay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isSelectWX){
					return;
				}else{
					iv_weixin_select.setBackgroundResource(R.drawable.ic_selected);
					iv_alipay_select.setBackgroundResource(R.drawable.ic_gray_selected);
					isSelectWX=true;
				}
			}
		});
		
		ll_alipay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isSelectWX){
					iv_alipay_select.setBackgroundResource(R.drawable.ic_selected);
					iv_weixin_select.setBackgroundResource(R.drawable.ic_gray_selected);
					isSelectWX=false;
				}
				
			}
		});
		
		tv_pay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(wPage.equals("1")){    //一件报名  调用的支付
					if(isSelectWX){       //一键报名的微信支付  
						AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, WxRechargeResponse.class, new MySubResponseHandler<WxRechargeResponse>() {
						@Override
						public void onStart() {
							super.onStart();
							mLoadingDialog.show();
						}
			
						@Override
						public RequestParams setParams(RequestParams requestParams) {
							requestParams.add("action", "PROMOENROLL");
							requestParams.add("studentid",GuangdaApplication.mUserInfo.getStudentid());
							requestParams.add("cityid",cityid);
							requestParams.add("model",model);
							requestParams.add("amount",much_money);
							requestParams.add("resource","1");
							requestParams.add("trade_type","APP");
							requestParams.add("spbill_create_ip",GetHostIp());
							requestParams.add("appid",key.appid);
							return requestParams;
						}
			
						@Override
						public void onFinish() {
							mLoadingDialog.dismiss();
						}
			
						@Override
						public void onSuccess(int statusCode, Header[] headers, WxRechargeResponse baseReponse) {
							if(baseReponse.getWeixinpay().equals("0")){  //0 启用 1 不启用
								WParams Wparams = new WParams();
								Wparams.setBody(baseReponse.getBody());
								Wparams.setNotify_url(baseReponse.getNotify_url());
								Wparams.setOut_trade_no(baseReponse.getOut_trade_no());
								Wparams.setPartner(baseReponse.getPartner());
								Wparams.setSign(baseReponse.getSign());
								Wparams.setSeller_id(baseReponse.getSeller_id());
								Wparams.setSubject(baseReponse.getSubject());
								Wparams.setTotal_fee(baseReponse.getTotal_fee());
								Wparams.setPrivate_key(baseReponse.getPrivate_key());
								Wparams.setAppId(baseReponse.getAppId());
								Wparams.setPrepay_id(baseReponse.getPrepay_id());
								Wparams.setTimeStamp(baseReponse.getTimeStamp());
								Wparams.setNonceStr(baseReponse.getNonceStr());
								Wparams.setSignType(baseReponse.getSignType());
								Wparams.setPaySign(baseReponse.getPaySign());
								Wparams.setMch_id(baseReponse.getMch_id());
								// mPparams.setTotal_fee(0.01 + "");
								WxPayTask mWxPayTask = new WxPayTask(mBaseFragmentActivity,Wparams);
								mWxPayTask.Execute();
							}else{
								showToast("微信支付暂时停止使用，请使用支付宝");
							}
							
						}
					});
					}else{
						AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, RechargeResponse.class, new MySubResponseHandler<RechargeResponse>() {
						@Override
						public void onStart() {
							super.onStart();
							mLoadingDialog.show();
						}
			
						@Override
						public RequestParams setParams(RequestParams requestParams) {
							requestParams.add("action", "PROMOENROLL");
							requestParams.add("studentid",GuangdaApplication.mUserInfo.getStudentid());
							requestParams.add("cityid",cityid);
							requestParams.add("model",model);
							requestParams.add("amount",much_money);
							requestParams.add("resource","0");
							return requestParams;
						}
			
						@Override
						public void onFinish() {
							mLoadingDialog.dismiss();
						}
			
						@Override
						public void onSuccess(int statusCode, Header[] headers, RechargeResponse baseReponse) {
							Pparams mPparams = new Pparams();
							mPparams.setBody(baseReponse.getBody());
							mPparams.setNotify_url(baseReponse.getNotify_url());
							mPparams.setOut_trade_no(baseReponse.getOut_trade_no());
							mPparams.setPartner(baseReponse.getPartner());
							mPparams.setRsakey(baseReponse.getPrivate_key());
							mPparams.setSeller_id(baseReponse.getSeller_id());
							mPparams.setSubject(baseReponse.getSubject());
							mPparams.setTotal_fee(baseReponse.getTotal_fee());
							// mPparams.setTotal_fee(0.01 + "");
							AliPayTask mAliPayTask = new AliPayTask(mBaseFragmentActivity, mPparams, baseReponse.getOut_trade_no());
							mAliPayTask.Execute();
						}
					});
					}
				}else{
					
					AsyncHttpClientUtil.get().post(mBaseFragmentActivity, Setting.SUSER_URL, WxRechargeResponse.class, new MySubResponseHandler<WxRechargeResponse>() {
						@Override
						public void onStart() {
							super.onStart();
							mLoadingDialog.show();
							tvTitleRight.setEnabled(false);
						}

						@Override
						public RequestParams setParams(RequestParams requestParams) {
							requestParams.add("action", "Recharge");
							requestParams.add("studentid", GuangdaApplication.mUserInfo.getStudentid());
							requestParams.add("amount", much_money);
							if(isSelectWX){
								requestParams.add("resource","1");
							}else{
								requestParams.add("resource","0");  // 1 微信 0 支付宝
							}
							if(isSelectWX){
								requestParams.add("trade_type","APP");
								requestParams.add("spbill_create_ip",GetHostIp());
								requestParams.add("appid",key.appid);
							}
							return requestParams;
						}

						@Override
						public void onFinish() {
							mLoadingDialog.dismiss();
							tvTitleRight.setEnabled(true);
						}

						@Override
						public void onSuccess(int statusCode, Header[] headers, WxRechargeResponse baseReponse) {
							if(isSelectWX){
								if(baseReponse.getWeixinpay().equals("0")){
									WParams Wparams = new WParams();
									Wparams.setBody(baseReponse.getBody());
									Wparams.setNotify_url(baseReponse.getNotify_url());
									Wparams.setOut_trade_no(baseReponse.getOut_trade_no());
									Wparams.setPartner(baseReponse.getPartner());
									Wparams.setSign(baseReponse.getSign());
									Wparams.setSeller_id(baseReponse.getSeller_id());
									Wparams.setSubject(baseReponse.getSubject());
									Wparams.setTotal_fee(baseReponse.getTotal_fee());
									Wparams.setPrivate_key(baseReponse.getPrivate_key());
									Wparams.setAppId(baseReponse.getAppId());
									Wparams.setPrepay_id(baseReponse.getPrepay_id());
									Wparams.setTimeStamp(baseReponse.getTimeStamp());
									Wparams.setNonceStr(baseReponse.getNonceStr());
									Wparams.setSignType(baseReponse.getSignType());
									Wparams.setPaySign(baseReponse.getPaySign());
									Wparams.setMch_id(baseReponse.getMch_id());
									// mPparams.setTotal_fee(0.01 + "");
									WxPayTask mWxPayTask = new WxPayTask(mBaseFragmentActivity,Wparams);
									mWxPayTask.Execute();
								}else{
									showToast("微信支付暂时停止使用，请使用支付宝");
								}
								
							}else{
								Pparams mPparams = new Pparams();
								mPparams.setBody(baseReponse.getBody());
								mPparams.setNotify_url(baseReponse.getNotify_url());
								mPparams.setOut_trade_no(baseReponse.getOut_trade_no());
								mPparams.setPartner(baseReponse.getPartner());
								mPparams.setRsakey(baseReponse.getPrivate_key());
								mPparams.setSeller_id(baseReponse.getSeller_id());
								mPparams.setSubject(baseReponse.getSubject());
								mPparams.setTotal_fee(baseReponse.getTotal_fee());
								// mPparams.setTotal_fee(0.01 + "");
								AliPayTask mAliPayTask = new AliPayTask(mBaseFragmentActivity, mPparams, baseReponse.getOut_trade_no());
								mAliPayTask.Execute();
							}
							
						}

					});
					
					

				}

				
			}
		});
		
		
	}

	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		Bundle bundle=getIntent().getExtras();
		much_money=bundle.getString("money");
		wPage=bundle.getString("wPage");
		if(wPage.equals("1")){
			cityid=bundle.getString("cityid");
			model=bundle.getString("model");
		}
		tv_pay_much.setText(much_money+"元");
		setCenterText("选择支付方式");
		
	}

	@Override
	public void requestOnCreate() {
		// TODO Auto-generated method stub
		
	}

}
