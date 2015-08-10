package hzyj.guangda.student.response;



import java.util.List;

import com.common.library.llj.base.BaseReponse;

public class GetCityId extends BaseReponse{
	private List<CityId> citylist; 
	
	public List<CityId> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<CityId> citylist) {
		this.citylist = citylist;
	}

	public class CityId{
		private int id;
		private int cityid;
		private int provinceid;
		private String province;
		private String hotkey;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getCityid() {
			return cityid;
		}
		public void setCityid(int cityid) {
			this.cityid = cityid;
		}
		public int getProvinceid() {
			return provinceid;
		}
		public void setProvinceid(int provinceid) {
			this.provinceid = provinceid;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getHotkey() {
			return hotkey;
		}
		public void setHotkey(String hotkey) {
			this.hotkey = hotkey;
		}
		
	}
	
}
