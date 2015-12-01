package hzyj.guangda.student.response;

import com.common.library.llj.base.BaseReponse;

public class GetXiaoBaService extends BaseReponse {
	
	private String simulateUrl;   //模拟培训
	private String bookreceptionUrl;  //在线约考
	private String cityid;
	private String cityname;
	
	public String getSimulateUrl() {
		return simulateUrl;
	}
	public void setSimulateUrl(String simulateUrl) {
		this.simulateUrl = simulateUrl;
	}
	public String getBookreceptionUrl() {
		return bookreceptionUrl;
	}
	public void setBookreceptionUrl(String bookreceptionUrl) {
		this.bookreceptionUrl = bookreceptionUrl;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	
	
}
