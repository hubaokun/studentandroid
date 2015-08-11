package hzyj.guangda.student.response;

import com.common.library.llj.base.BaseReponse;

public class GetWalletInfo extends BaseReponse {
	
	private int couponsum;
	private int coinsum;
	private int money;
	
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
