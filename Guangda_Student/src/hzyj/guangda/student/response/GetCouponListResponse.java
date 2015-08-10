package hzyj.guangda.student.response;

import hzyj.guangda.student.entity.Coupon;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

/**
 * 
 * @author liulj
 * 
 */
public class GetCouponListResponse extends BaseReponse {
	private List<Coupon> couponlist;

	public List<Coupon> getCouponlist() {
		return couponlist;
	}

	public void setCouponlist(List<Coupon> couponlist) {
		this.couponlist = couponlist;
	}

}
