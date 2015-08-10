package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

public class GetCity extends BaseReponse {
 	
	private List<Province> china;
 	
	public class Province
	{
		private int id;
		private int provinceid;
		private String province;
		private List<City> cities;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
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
		public List<City> getCities() {
			return cities;
		}
		public void setCities(List<City> cities) {
			this.cities = cities;
		}
	}
	
	public class City
	{
		private int id;
		private int cityid;
		private String city;
		private int provinceid;
		private String hotkey;
		private List<Zone> areas;
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
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public int getProvinceid() {
			return provinceid;
		}
		public void setProvinceid(int provinceid) {
			this.provinceid = provinceid;
		}
		public String getHotkey() {
			return hotkey;
		}
		public void setHotkey(String hotkey) {
			this.hotkey = hotkey;
		}
		public List<Zone> getAreas() {
			return areas;
		}
		public void setAreas(List<Zone> areas) {
			this.areas = areas;
		}
	}
	
	public class Zone
	{
		private int id;
		private int areaid;
		private String area;
		private int cityid;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getAreaid() {
			return areaid;
		}
		public void setAreaid(int areaid) {
			this.areaid = areaid;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public int getCityid() {
			return cityid;
		}
		public void setCityid(int cityid) {
			this.cityid = cityid;
		}
	}

	public List<Province> getChina() {
		return china;
	}

	public void setChina(List<Province> china) {
		this.china = china;
	}

}
