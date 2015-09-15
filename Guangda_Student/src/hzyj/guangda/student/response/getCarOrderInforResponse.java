package hzyj.guangda.student.response;

import com.common.library.llj.base.BaseReponse;

public class getCarOrderInforResponse extends BaseReponse{
	private String cityname;
	private String enrolltime;
	private String marketprice;
	private String enrollstate;
	private String xiaobaprice;
	private String model;
	private String enrollpay;
	
	
	public String getEnrollpay() {
		return enrollpay;
	}
	public void setEnrollpay(String enrollpay) {
		this.enrollpay = enrollpay;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getEnrolltime() {
		return enrolltime;
	}
	public void setEnrolltime(String enrolltime) {
		this.enrolltime = enrolltime;
	}
	public String getMarketprice() {
		return marketprice;
	}
	public void setMarketprice(String marketprice) {
		this.marketprice = marketprice;
	}
	
	public String getEnrollstate() {
		return enrollstate;
	}
	public void setEnrollstate(String enrollstate) {
		this.enrollstate = enrollstate;
	}
	public String getXiaobaprice() {
		return xiaobaprice;
	}
	public void setXiaobaprice(String xiaobaprice) {
		this.xiaobaprice = xiaobaprice;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
	
	
	
	

}
