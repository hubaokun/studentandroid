package hzyj.guangda.student.response;

import com.common.library.llj.base.BaseReponse;

public class GetWalletInfo extends BaseReponse {
	
	private int couponsum;
	private int coinsum;
	private int money;
	private int consumeMoney;
	private int consumeCoin;
	private int consumeCoupon;
	private int  fcoinsum;
	
	
	
	
	public int getFcoinsum() {
		return fcoinsum;
	}
	public void setFcoinsum(int fcoinsum) {
		this.fcoinsum = fcoinsum;
	}
	public int getConsumeMoney() {
		return consumeMoney;
	}
	public void setConsumeMoney(int consumeMoney) {
		this.consumeMoney = consumeMoney;
	}
	public int getConsumeCoin() {
		return consumeCoin;
	}
	public void setConsumeCoin(int consumeCoin) {
		this.consumeCoin = consumeCoin;
	}
	public int getConsumeCoupon() {
		return consumeCoupon;
	}
	public void setConsumeCoupon(int consumeCoupon) {
		this.consumeCoupon = consumeCoupon;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getCouponsum() {
		return couponsum;
	}
	public void setCouponsum(int couponsum) {
		this.couponsum = couponsum;
	}
	public int getCoinsum() {
		return coinsum;
	}
	public void setCoinsum(int coinsum) {
		this.coinsum = coinsum;
	}

}
