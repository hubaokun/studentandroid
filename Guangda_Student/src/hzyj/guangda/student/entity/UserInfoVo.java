package hzyj.guangda.student.entity;

public class UserInfoVo {
	private String studentid;// 学员ID
	private String password;// 密码(md5加密,加密之后的小写字符)
	private String phone;// 电话号码
	private String id_cardnum;// 身份证号码
	private String id_cardpicf_url;// 身份证正面照片地址
	private String id_cardpicb_url;// 身份证反面照片地址
	private String student_cardnum;// 学员证号码
	private String student_cardpicf_url;// 学员证正面照片地址
	private String student_cardpicb_url;// 学员证反面照片地址
	private String student_card_creat;// 学员证发证时间
	private int gender;// 性别1.男2.女
	private String birthday;// 生日
	private String realname;// 真实姓名
	private String urgent_person;// 紧急联系人姓名
	private String urgent_phone;// 紧急联系人电话
	private String money;// 用户账户余额
	private String addtime;// 注册时间
	private String avatar;// 学员头像,大小为中
	private String avatarurl;// 学员头像,大小为中
	private String address;
	private String city;
	private String aliaccount;
	private String token;
	private String cityid;
	private String provinceid;
	private String areaid;
	private String locationname;
	private String baiduid;
	private int isfreecourse; // 1 可以参加活动 0 你参加活动
	private String alipay_account;
	
	
	
	
	public int getIsfreecourse() {
		return isfreecourse;
	}

	public void setIsfreecourse(int isfreecourse) {
		this.isfreecourse = isfreecourse;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAliaccount() {
		return aliaccount;
	}

	public void setAliaccount(String aliaccount) {
		this.aliaccount = aliaccount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId_cardnum() {
		return id_cardnum;
	}

	public void setId_cardnum(String id_cardnum) {
		this.id_cardnum = id_cardnum;
	}

	public String getId_cardpicfurl() {
		return id_cardpicf_url;
	}

	public void setId_cardpicfurl(String id_cardpicf_url) {
		this.id_cardpicf_url = id_cardpicf_url;
	}

	public String getId_cardpicburl() {
		return id_cardpicb_url;
	}

	public void setId_cardpicburl(String id_cardpicb_url) {
		this.id_cardpicb_url = id_cardpicb_url;
	}

	public String getStudent_cardnum() {
		return student_cardnum;
	}

	public void setStudent_cardnum(String student_cardnum) {
		this.student_cardnum = student_cardnum;
	}

	public String getStudent_cardpicfurl() {
		return student_cardpicf_url;
	}

	public void setStudent_cardpicfurl(String student_cardpicf_url) {
		this.student_cardpicf_url = student_cardpicf_url;
	}

	public String getStudent_cardpicburl() {
		return student_cardpicb_url;
	}

	public void setStudent_cardpicburl(String student_cardpicb_url) {
		this.student_cardpicb_url = student_cardpicb_url;
	}

	public String getStudent_card_creat() {
		return student_card_creat;
	}

	public void setStudent_card_creat(String student_card_creat) {
		this.student_card_creat = student_card_creat;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUrgent_person() {
		return urgent_person;
	}

	public void setUrgent_person(String urgent_person) {
		this.urgent_person = urgent_person;
	}

	public String getUrgent_phone() {
		return urgent_phone;
	}

	public void setUrgent_phone(String urgent_phone) {
		this.urgent_phone = urgent_phone;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getBaiduid() {
		return baiduid;
	}

	public void setBaiduid(String baiduid) {
		this.baiduid = baiduid;
	}

	public String getAlipay_account() {
		return alipay_account;
	}

	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}
	
	

}
