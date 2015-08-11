package hzyj.guangda.student.response;

import hzyj.guangda.student.entity.Coupon;

import java.util.List;

import com.alipay.sdk.data.Response;
import com.common.library.llj.base.BaseReponse;

/**
 * 获得可以用的小巴券
 * 
 * @author liulj
 * 
 */
public class GetCanUseCouponResponse extends BaseReponse {
	private List<Coupon> couponlist;
	private int canUseDiff;
	private int canUseMaxCount;
	private int coinnum;
	private int money;

	public List<Coupon> getCouponlist() {
		return couponlist;
	}

	public void setCouponlist(List<Coupon> couponlist) {
		this.couponlist = couponlist;
	}

	public int getCanUseDiff() {
		return canUseDiff;
	}

	public void setCanUseDiff(int canUseDiff) {
		this.canUseDiff = canUseDiff;
	}

	public int getCanUseMaxCount() {
		return canUseMaxCount;
	}

	public void setCanUseMaxCount(int canUseMaxCount) {
		this.canUseMaxCount = canUseMaxCount;
	}

	public int getCoinnum() {
		return coinnum;
	}

	public void setCoinnum(int coinnum) {
		this.coinnum = coinnum;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
