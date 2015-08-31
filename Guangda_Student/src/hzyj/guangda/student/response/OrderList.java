package hzyj.guangda.student.response;

import hzyj.guangda.student.entity.Coupon;

public class OrderList {
	private float price;
	private int type;
	private Coupon coupon;
	private int demoney;
	
	
	public int getDemoney() {
		return demoney;
	}
	public void setDemoney(int demoney) {
		this.demoney = demoney;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Coupon getCoupon() {
		return coupon;
	}
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
}
