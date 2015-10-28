package hzyj.guangda.student.common;

import hzyj.guangda.student.entity.UserInfoVo;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfo {
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	private Editor mEditor;

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
	private String token;// 用户凭证
	private String cityid;  //城市id
	private String provinceid;
	private String areaid;
	private String longitude;// 经度
	private String latitude;// 纬度
	private String locationname;
	private String baiduid;
	
	private int isfreecourse;  // 1 可以预约免费预约  0 不可以预约

	public UserInfo(Context context) {
		this.mContext = context;
		mSharedPreferences = mContext.getSharedPreferences(Constants.PREFS_USER_INFO, Context.MODE_PRIVATE);
		mEditor = mSharedPreferences.edit();// 将mEditor存在成员变量中
		// 从文件中获取value保存在

		studentid = mSharedPreferences.getString("studentid", null);
		password = mSharedPreferences.getString("password", null);
		phone = mSharedPreferences.getString("phone", null);
		id_cardnum = mSharedPreferences.getString("id_cardnum", null);
		id_cardpicf_url = mSharedPreferences.getString("id_cardpicf_url", null);
		id_cardpicb_url = mSharedPreferences.getString("id_cardpicb_url", null);
		student_cardnum = mSharedPreferences.getString("student_cardnum", null);
		student_cardpicf_url = mSharedPreferences.getString("student_cardpicf_url", null);
		student_cardpicb_url = mSharedPreferences.getString("student_cardpicb_url", null);

		student_card_creat = mSharedPreferences.getString("student_card_creat", null);
		gender = mSharedPreferences.getInt("gender", 0);
		birthday = mSharedPreferences.getString("birthday", null);
		realname = mSharedPreferences.getString("realname", null);
		urgent_person = mSharedPreferences.getString("urgent_person", null);
		urgent_phone = mSharedPreferences.getString("urgent_phone", null);
		money = mSharedPreferences.getString("money", null);
		addtime = mSharedPreferences.getString("addtime", null);
		avatar = mSharedPreferences.getString("avatar", null);
		avatarurl = mSharedPreferences.getString("avatarurl", null);
		address = mSharedPreferences.getString("address", null);
		city = mSharedPreferences.getString("city", null);
		aliaccount = mSharedPreferences.getString("aliaccount", null);
		token = mSharedPreferences.getString("token", null);

		longitude = mSharedPreferences.getString("longitude", null);
		latitude = mSharedPreferences.getString("latitude", null);
		baiduid=mSharedPreferences.getString("locationid", null);
		isfreecourse=mSharedPreferences.getInt("isfreecourse",0);
		
	}

	public void saveUserInfo(UserInfoVo userInfoVo) {
		mEditor.putString("studentid", userInfoVo.getStudentid());
		mEditor.putString("password", userInfoVo.getPassword());
		mEditor.putString("phone", userInfoVo.getPhone());
		mEditor.putString("id_cardnum", userInfoVo.getId_cardnum());
		mEditor.putString("id_cardpicf_url", userInfoVo.getId_cardpicfurl());
		mEditor.putString("id_cardpicb_url", userInfoVo.getId_cardpicburl());
		mEditor.putString("student_cardnum", userInfoVo.getStudent_cardnum());
		mEditor.putString("student_cardpicf_url", userInfoVo.getStudent_cardpicfurl());
		mEditor.putString("student_cardpicb_url", userInfoVo.getStudent_cardpicburl());
		mEditor.putString("student_card_creat", userInfoVo.getStudent_card_creat());
		mEditor.putInt("gender", userInfoVo.getGender());
		mEditor.putString("birthday", userInfoVo.getBirthday());
		mEditor.putString("realname", userInfoVo.getRealname());
		mEditor.putString("urgent_person", userInfoVo.getUrgent_person());
		mEditor.putString("urgent_phone", userInfoVo.getUrgent_phone());
		mEditor.putString("money", userInfoVo.getMoney());
		mEditor.putString("addtime", userInfoVo.getAddtime());
		mEditor.putString("avatar", userInfoVo.getAvatar());
		mEditor.putString("avatarurl", userInfoVo.getAvatarurl());
		mEditor.putString("address", userInfoVo.getAddress());
		mEditor.putString("locationname", userInfoVo.getLocationname());
		mEditor.putString("aliaccount", userInfoVo.getAliaccount());
		mEditor.putString("token", userInfoVo.getToken());
		mEditor.putString("cityid", userInfoVo.getCityid());
		mEditor.putString("provinceid",userInfoVo.getProvinceid());
		mEditor.putString("areaid",userInfoVo.getAreaid());
		mEditor.putString("locationid",userInfoVo.getBaiduid());
		mEditor.putInt("isfreecourse",userInfoVo.getIsfreecourse());
		mEditor.commit();

		studentid = userInfoVo.getStudentid();
		password = userInfoVo.getPassword();
		phone = userInfoVo.getPhone();
		id_cardnum = userInfoVo.getId_cardnum();
		id_cardpicf_url = userInfoVo.getId_cardpicfurl();
		id_cardpicb_url = userInfoVo.getId_cardpicburl();
		student_cardnum = userInfoVo.getStudent_cardnum();
		student_cardpicf_url = userInfoVo.getStudent_cardpicfurl();
		student_cardpicb_url = userInfoVo.getStudent_cardpicburl();

		student_card_creat = userInfoVo.getStudent_card_creat();
		gender = userInfoVo.getGender();
		birthday = userInfoVo.getBirthday();
		realname = userInfoVo.getRealname();
		urgent_person = userInfoVo.getUrgent_person();
		urgent_phone = userInfoVo.getUrgent_phone();
		money = userInfoVo.getMoney();
		addtime = userInfoVo.getAddtime();
		avatar = userInfoVo.getAvatar();
		avatarurl = userInfoVo.getAvatarurl();
		address = userInfoVo.getAddress();
		city = userInfoVo.getCity();
		aliaccount = userInfoVo.getAliaccount();
		token = userInfoVo.getToken();
		locationname = userInfoVo.getLocationname();
		cityid = userInfoVo.getCityid();
		provinceid = userInfoVo.getProvinceid();
		areaid = userInfoVo.getAreaid();
		isfreecourse=userInfoVo.getIsfreecourse();
	}

	public void clearUserInfo() {
		mEditor.clear();
		mEditor.commit();
		studentid = null;
		password = null;
		phone = null;
		id_cardnum = null;
		id_cardpicf_url = null;
		id_cardpicb_url = null;
		student_cardnum = null;
		student_cardpicf_url = null;
		student_cardpicb_url = null;

		student_card_creat = null;
		gender = 0;
		birthday = null;
		realname = null;
		urgent_person = null;
		urgent_phone = null;
		money = null;
		addtime = null;
		avatar = null;
		avatarurl = null;
		address = null;
		city = null;
		aliaccount = null;
		token = null;
		isfreecourse=0;

	}

	
	
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
		mEditor.putString("token", token);
		mEditor.commit();
		this.token = token;
	}

	public String getAliaccount() {
		return aliaccount;
	}

	public void setAliaccount(String aliaccount) {
		mEditor.putString("aliaccount", aliaccount);
		mEditor.commit();
		this.aliaccount = aliaccount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		mEditor.putString("address", address);
		mEditor.commit();
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		mEditor.putString("city", city);
		mEditor.commit();
		this.city = city;
	}

	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		mEditor.putString("avatarurl", avatarurl);
		mEditor.commit();
		this.avatarurl = avatarurl;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		mEditor.putString("longitude", longitude);
		mEditor.commit();
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		mEditor.putString("latitude", latitude);
		mEditor.commit();
		this.latitude = latitude;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		mEditor.putString("studentid", studentid);
		mEditor.commit();
		this.studentid = studentid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		mEditor.putString("password", password);
		mEditor.commit();
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		mEditor.putString("phone", phone);
		mEditor.commit();
		this.phone = phone;
	}

	public String getId_cardnum() {
		return id_cardnum;
	}

	public void setId_cardnum(String id_cardnum) {
		mEditor.putString("id_cardnum", id_cardnum);
		mEditor.commit();
		this.id_cardnum = id_cardnum;
	}

	public String getId_cardpicfurl() {
		return id_cardpicf_url;
	}

	public void setId_cardpicfurl(String id_cardpicf_url) {
		mEditor.putString("id_cardpicf_url", id_cardpicf_url);
		mEditor.commit();
		this.id_cardpicf_url = id_cardpicf_url;
	}

	public String getId_cardpicburl() {
		return id_cardpicb_url;
	}

	public void setId_cardpicburl(String id_cardpicb_url) {
		mEditor.putString("id_cardpicb_url", id_cardpicb_url);
		mEditor.commit();
		this.id_cardpicb_url = id_cardpicb_url;
	}

	public String getStudent_cardnum() {
		return student_cardnum;
	}

	public void setStudent_cardnum(String student_cardnum) {
		mEditor.putString("student_cardnum", student_cardnum);
		mEditor.commit();
		this.student_cardnum = student_cardnum;
	}

	public String getStudent_cardpicfurl() {
		return student_cardpicf_url;
	}

	public void setStudent_cardpicfurl(String student_cardpicf_url) {
		mEditor.putString("student_cardpicf_url", student_cardpicf_url);
		mEditor.commit();
		this.student_cardpicf_url = student_cardpicf_url;
	}

	public String getStudent_cardpicburl() {
		return student_cardpicb_url;
	}

	public void setStudent_cardpicburl(String student_cardpicb_url) {
		mEditor.putString("student_cardpicb_url", student_cardpicb_url);
		mEditor.commit();
		this.student_cardpicb_url = student_cardpicb_url;
	}

	public String getStudent_card_creat() {
		return student_card_creat;
	}

	public void setStudent_card_creat(String student_card_creat) {
		mEditor.putString("student_card_creat", student_card_creat);
		mEditor.commit();
		this.student_card_creat = student_card_creat;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		mEditor.putInt("gender", gender);
		mEditor.commit();
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		mEditor.putString("birthday", birthday);
		mEditor.commit();
		this.birthday = birthday;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		mEditor.putString("realname", realname);
		mEditor.commit();
		this.realname = realname;
	}

	public String getUrgent_person() {
		return urgent_person;
	}

	public void setUrgent_person(String urgent_person) {
		mEditor.putString("urgent_person", urgent_person);
		mEditor.commit();
		this.urgent_person = urgent_person;
	}

	public String getUrgent_phone() {
		return urgent_phone;
	}

	public void setUrgent_phone(String urgent_phone) {
		mEditor.putString("urgent_phone", urgent_phone);
		mEditor.commit();
		this.urgent_phone = urgent_phone;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		mEditor.putString("money", money);
		mEditor.commit();
		this.money = money;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		mEditor.putString("addtime", addtime);
		mEditor.commit();
		this.addtime = addtime;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		mEditor.putString("avatar", avatar);
		mEditor.commit();
		this.avatar = avatar;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}

	public SharedPreferences getmSharedPreferences() {
		return mSharedPreferences;
	}

	public void setmSharedPreferences(SharedPreferences mSharedPreferences) {
		this.mSharedPreferences = mSharedPreferences;
	}

	public Editor getmEditor() {
		return mEditor;
	}

	public void setmEditor(Editor mEditor) {
		this.mEditor = mEditor;
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
	
	public String getBaiduid() {
		return baiduid;
	}

	public void setBaiduid(String baiduid) {
		this.baiduid = baiduid;
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
	
	public void setIsFirst()
	{
		mEditor.putBoolean("isfirst",false);  //首次登录将首次登录致为false
		mEditor.commit();
	}
	
	public boolean getIsFirst()
	{
		return mSharedPreferences.getBoolean("isfirst", true);
	}
}
