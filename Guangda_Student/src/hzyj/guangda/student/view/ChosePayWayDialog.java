package hzyj.guangda.student.view;

import com.common.library.llj.base.BaseDialog;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hzyj.guangda.student.R;
import hzyj.guangda.student.entity.coinRelus;

public class ChosePayWayDialog extends BaseDialog {
	public ImageView imgChosedXueShiQuan;
	public ImageView imgChosedXiaoBaBi;
	public ImageView imgChosedYuE;
	public RelativeLayout rlXueshiquan;
	public RelativeLayout rlXiaobabi;
	public RelativeLayout rlYue;
	private TextView tvNoMoney;
	private TextView tvRestCouponNum;
	private TextView tvRestCoinNum;
	private TextView tvRestMoney;
	public TextView tvSure;
	public int Type = 0;
	

	public ChosePayWayDialog(Context context) {
		super(context,R.style.dim_dialog);
		// TODO Auto-generated constructor stub
	}
	
	public ChosePayWayDialog(Context context, int theme) {
		super(context, R.style.dim_dialog);
	}

	public ChosePayWayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.chose_pay_way_dialog;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		imgChosedXueShiQuan = (ImageView)findViewById(R.id.img_chosed_xueshiquan);
		imgChosedXiaoBaBi = (ImageView)findViewById(R.id.img_chosed_xiaobabi);
		imgChosedYuE = (ImageView)findViewById(R.id.img_chosed_yue);
		rlXueshiquan = (RelativeLayout)findViewById(R.id.rl_xueshiquan);
		rlXiaobabi = (RelativeLayout)findViewById(R.id.rl_xiaobabi);
		rlYue = (RelativeLayout)findViewById(R.id.rl_yue);
		tvRestCouponNum = (TextView)findViewById(R.id.tv_rest_coupon_num);
		tvRestCoinNum = (TextView)findViewById(R.id.tv_rest_coin_num);
		tvRestMoney = (TextView)findViewById(R.id.tv_reset_money);
		tvSure = (TextView)findViewById(R.id.tv_sure);
		}

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.BOTTOM);
		setCanceledOnTouchOutside(true);
	}
	

	
	public void setChosedItem(int index)
	{
		
		switch (index) {
		case 2: //选择学时券
			imgChosedXueShiQuan.setImageResource(R.drawable.coupon_select);
			imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_normal);
			imgChosedYuE.setImageResource(R.drawable.coupon_normal);
			break;
		case 3: //选择小巴币
			imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_select);
			imgChosedXueShiQuan.setImageResource(R.drawable.coupon_normal);
			imgChosedYuE.setImageResource(R.drawable.coupon_normal);
			break;
		case 1:  //选择余额
			imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_normal);
			imgChosedXueShiQuan.setImageResource(R.drawable.coupon_normal);
			imgChosedYuE.setImageResource(R.drawable.coupon_select);
			break;
			
	     //选择小巴币和余额
		case 4: 
			imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_select);
			imgChosedYuE.setImageResource(R.drawable.coupon_select);
			imgChosedXueShiQuan.setImageResource(R.drawable.coupon_normal);
			break;
		
		default:
			break;
		}
	}
	
	 //混个支付
	
	 public void mixPrice(int Tickets,float Coins,float YuE,float Price,int type,float mixCoins){
		 
	 }
	
	
	//判断每单的状态  修改判断小巴比是否有余额
	
	public void setItemVisible(int Tickets,float Coins,float YuE,float Price,int type,float mixCoins)
	{
		switch (type) {
		case 1:  //选择余额
			YuE = YuE+Price;
//			tvRestMoney.setVisibility(View.GONE);
//			if (YuE<0)
//			{
//				tvRestMoney.setText("余额不足");
//			}else{
//				tvRestMoney.setText(YuE+"元可用");
//			}
			break;
		case 2: //选择学时券
			Tickets = Tickets+1;
//			if (Tickets==0)
//			{
//				rlXueshiquan.setClickable(false);
//				tvRestCouponNum.setVisibility(View.GONE);
//				imgChosedXueShiQuan.setImageResource(R.drawable.coupon_invalid);
//			}else{
//				tvRestCouponNum.setVisibility(View.VISIBLE);
//				rlXueshiquan.setClickable(true);
//				tvRestCouponNum.setText(Tickets+"张学时券可用");
//			}
			break;
		case 3: //选择小巴币
			Coins = Coins+Price; // 小巴币＋订单价格 就是 
//			if (Price>Coins)
//			{
//				rlXiaobabi.setClickable(false);
//				tvRestCoinNum.setVisibility(View.GONE);
//				imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_invalid);
//			}else{
//				tvRestCoinNum.setVisibility(View.VISIBLE);
//				tvRestCoinNum.setText(Coins+"个可用 ");
//				rlXiaobabi.setClickable(true);
//			}
			break;
		case 4://混合支付
			Coins=Coins+mixCoins;
			YuE=YuE+Price-mixCoins;
			break;	
		default:
			break;
		}
		
		if (Coins<=0)
		{

			rlXiaobabi.setClickable(false);
			tvRestCoinNum.setVisibility(View.GONE);
			imgChosedXiaoBaBi.setImageResource(R.drawable.coupon_invalid);
		}else if(0<Coins&&Coins<Price){
			tvRestCoinNum.setVisibility(View.VISIBLE);
			tvRestCoinNum.setText(Coins+"个可用");
			rlXiaobabi.setClickable(true);
			if (YuE<0)
			{
				tvRestMoney.setText("余额不足");
			}else{
				tvRestMoney.setText(YuE+"元可用");
			}
		}else{
			tvRestCoinNum.setVisibility(View.VISIBLE);
			tvRestCoinNum.setText(Coins+"个可用");
			rlXiaobabi.setClickable(true);
		}
		
//		if (Price>YuE)
//		{
//			tvRestMoney.setVisibility(View.VISIBLE);
//		}else{
//			tvRestMoney.setVisibility(View.GONE);
			if (YuE<0)
			{
				tvRestMoney.setText("余额不足");
			}else{
				tvRestMoney.setText(YuE+"元可用");
			}
//		}
		
		if (Tickets==0)
		{
			rlXueshiquan.setClickable(false);
			tvRestCouponNum.setVisibility(View.GONE);
			imgChosedXueShiQuan.setImageResource(R.drawable.coupon_invalid);
		}else{
			tvRestCouponNum.setVisibility(View.VISIBLE);
			rlXueshiquan.setClickable(true);
			tvRestCouponNum.setText(Tickets+"张可用");
		}
	}
}
