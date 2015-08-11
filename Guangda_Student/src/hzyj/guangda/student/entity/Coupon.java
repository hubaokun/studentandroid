package hzyj.guangda.student.entity;

public class Coupon {
	private long keyLong = 0;
	private String recordid;
	private String couponid;
	private String gettime;
	private int value;
	private int ownertype;
	private int coupontype;
	private String end_time;
	private int state;
	private String title;
	private boolean select;

	public long getKeyLong() {
		return keyLong;
	}

	public void setKeyLong(long keyLong) {
		this.keyLong = keyLong;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public String getRecordid() {
		return recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}

	public String getCouponid() {
		return couponid;
	}

	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}

	public String getGettime() {
		return gettime;
	}

	public void setGettime(String gettime) {
		this.gettime = gettime;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getOwnertype() {
		return ownertype;
	}

	public void setOwnertype(int ownertype) {
		this.ownertype = ownertype;
	}

	public int getCoupontype() {
		return coupontype;
	}

	public void setCoupontype(int coupontype) {
		this.coupontype = coupontype;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
