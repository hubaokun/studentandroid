package hzyj.guangda.student.entity;

import java.util.List;

public class CoachInfoVo {
	private String longitude;
	private String latitude;
	private String coachid;
	private String realname;
	private String avatar;
	private String avatarurl;
	private String detail;
	private String score;// 教练综合评分
	private int gender;// 1男 2 女 0保密
	private String phone;
	private String telphone;
	private String id_cardnum;
	private String coach_cardnum;
	private String drive_school;
	private String address;// 教练住址
	private String age;
	private String years;// 教龄
	private String price;
	private String level;// 教练等级
	private String selfeval;// 教练自我评价
	private int sumnum;
	private List<Model> modellist;
	private int orderbyaccompany;
	private int accompanynum;
	
	private int freecoursestate; //教练体验课是否开课  1：有免费体验    0：没有
	private int signstate;		//0=未签约、1=已签约、2=签约过期
	
	
	
    
	public int getFreecoursestate() {
		return freecoursestate;
	}

	public void setFreecoursestate(int freecoursestate) {
		this.freecoursestate = freecoursestate;
	}

	public int getAccompanynum() {
		return accompanynum;
	}

	public void setAccompanynum(int accompanynum) {
		this.accompanynum = accompanynum;
	}

	public int getOrderbyaccompany() {
		return orderbyaccompany;
	}

	public void setOrderbyaccompany(int orderbyaccompany) {
		this.orderbyaccompany = orderbyaccompany;
	}

	public int getSumnum() {
		return sumnum;
	}

	public void setSumnum(int sumnum) {
		this.sumnum = sumnum;
	}

	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}

	public class Model {
		private String modelid;
		private String modelname;
		private String addtime;

		public String getModelid() {
			return modelid;
		}

		public void setModelid(String modelid) {
			this.modelid = modelid;
		}

		public String getModelname() {
			return modelname;
		}

		public void setModelname(String modelname) {
			this.modelname = modelname;
		}

		public String getAddtime() {
			return addtime;
		}

		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}

	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getId_cardnum() {
		return id_cardnum;
	}

	public void setId_cardnum(String id_cardnum) {
		this.id_cardnum = id_cardnum;
	}

	public String getCoach_cardnum() {
		return coach_cardnum;
	}

	public void setCoach_cardnum(String coach_cardnum) {
		this.coach_cardnum = coach_cardnum;
	}

	public String getDrive_school() {
		return drive_school;
	}

	public void setDrive_school(String drive_school) {
		this.drive_school = drive_school;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSelfeval() {
		return selfeval;
	}

	public void setSelfeval(String selfeval) {
		this.selfeval = selfeval;
	}

	public List<Model> getModellist() {
		return modellist;
	}

	public void setModellist(List<Model> modellist) {
		this.modellist = modellist;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCoachid() {
		return coachid;
	}

	public void setCoachid(String coachid) {
		this.coachid = coachid;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getSignstate() {
		return signstate;
	}

	public void setSignstate(int signstate) {
		this.signstate = signstate;
	}

}
