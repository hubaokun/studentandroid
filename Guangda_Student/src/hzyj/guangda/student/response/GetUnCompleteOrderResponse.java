package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

public class GetUnCompleteOrderResponse extends BaseReponse {
	private List<Order> orderlist;
	private int hasmore;

	public List<Order> getOrderlist() {
		return orderlist;
	}

	public void setOrderlist(List<Order> orderlist) {
		this.orderlist = orderlist;
	}

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public class Order {
		private String creat_time;
		private String orderid;
		private String coachid;
		private String studentid;
		private int studentstate;
		private int coachstate;
		private float total;
		private String coachname;
		private String start_time;
		private String end_time;
		private String detail;
		private int havecomplaint;
		private int hours;
		private String carmodel;
		private String carlicense;
		private List<Hour> orderprice;
		private Cuserinfo cuserinfo;
		private int can_complaint;
		private int need_uncomplaint;
		private int can_cancel;
		private int can_up;
		private int can_down;
		private int can_comment;
		private MyEvaluation myevaluation;
		private Evaluation evaluation;
		
		public int getcoachstate() {
			return coachstate;
		}

		public void setCoachstate(int coachstate) {
			this.coachstate = coachstate;
		}
		
		public int getstudentstate() {
			return studentstate;
		}

		public void setStudentstate(int studentstate) {
			this.studentstate = studentstate;
		}

		public String getCreat_time() {
			return creat_time;
		}

		public void setCreat_time(String creat_time) {
			this.creat_time = creat_time;
		}

		public MyEvaluation getMyevaluation() {
			return myevaluation;
		}

		public void setMyevaluation(MyEvaluation myevaluation) {
			this.myevaluation = myevaluation;
		}

		public Evaluation getEvaluation() {
			return evaluation;
		}

		public void setEvaluation(Evaluation evaluation) {
			this.evaluation = evaluation;
		}

		public Cuserinfo getCuserinfo() {
			return cuserinfo;
		}

		public void setCuserinfo(Cuserinfo cuserinfo) {
			this.cuserinfo = cuserinfo;
		}

		public int getCan_complaint() {
			return can_complaint;
		}

		public void setCan_complaint(int can_complaint) {
			this.can_complaint = can_complaint;
		}

		public int getNeed_uncomplaint() {
			return need_uncomplaint;
		}

		public void setNeed_uncomplaint(int need_uncomplaint) {
			this.need_uncomplaint = need_uncomplaint;
		}

		public int getCan_cancel() {
			return can_cancel;
		}

		public void setCan_cancel(int can_cancel) {
			this.can_cancel = can_cancel;
		}

		public int getCan_up() {
			return can_up;
		}

		public void setCan_up(int can_up) {
			this.can_up = can_up;
		}

		public int getCan_down() {
			return can_down;
		}

		public void setCan_down(int can_down) {
			this.can_down = can_down;
		}

		public int getCan_comment() {
			return can_comment;
		}

		public void setCan_comment(int can_comment) {
			this.can_comment = can_comment;
		}

		public String getOrderid() {
			return orderid;
		}

		public void setOrderid(String orderid) {
			this.orderid = orderid;
		}

		public String getCoachid() {
			return coachid;
		}

		public void setCoachid(String coachid) {
			this.coachid = coachid;
		}

		public String getCoachname() {
			return coachname;
		}

		public void setCoachname(String coachname) {
			this.coachname = coachname;
		}

		public String getStudentid() {
			return studentid;
		}

		public void setStudentid(String studentid) {
			this.studentid = studentid;
		}

		public float getTotal() {
			return total;
		}

		public void setTotal(float total) {
			this.total = total;
		}

		public String getStart_time() {
			return start_time;
		}

		public void setStart_time(String start_time) {
			this.start_time = start_time;
		}

		public String getEnd_time() {
			return end_time;
		}

		public void setEnd_time(String end_time) {
			this.end_time = end_time;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public int getHavecomplaint() {
			return havecomplaint;
		}

		public void setHavecomplaint(int havecomplaint) {
			this.havecomplaint = havecomplaint;
		}

		public int getHours() {
			return hours;
		}

		public void setHours(int hours) {
			this.hours = hours;
		}

		public String getCarmodel() {
			return carmodel;
		}

		public void setCarmodel(String carmodel) {
			this.carmodel = carmodel;
		}

		public String getCarlicense() {
			return carlicense;
		}

		public void setCarlicense(String carlicense) {
			this.carlicense = carlicense;
		}

		public List<Hour> getOrderprice() {
			return orderprice;
		}

		public void setOrderprice(List<Hour> orderprice) {
			this.orderprice = orderprice;
		}

		public class Evaluation {
			private float score;
			private String content;

			public float getScore() {
				return score;
			}

			public void setScore(float score) {
				this.score = score;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

		}

		public class MyEvaluation {
			private float score;
			private String content;

			public float getScore() {
				return score;
			}

			public void setScore(float score) {
				this.score = score;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

		}

		public class Cuserinfo {
			private String realname;
			private String phone;
			private String telphone;
			private float score;
			private int gender;

			public int getGender() {
				return gender;
			}

			public void setGender(int gender) {
				this.gender = gender;
			}

			public String getRealname() {
				return realname;
			}

			public void setRealname(String realname) {
				this.realname = realname;
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

			public float getScore() {
				return score;
			}

			public void setScore(float score) {
				this.score = score;
			}

		}

		public class Hour {
			private String recordid;
			private String orderid;
			private String hour;
			private String longitude;
			private String latitude;
			private String detail;
			private String subject;
			private float price;
			private int state;

			public String getRecordid() {
				return recordid;
			}

			public void setRecordid(String recordid) {
				this.recordid = recordid;
			}

			public String getOrderid() {
				return orderid;
			}

			public void setOrderid(String orderid) {
				this.orderid = orderid;
			}

			public String getHour() {
				return hour;
			}

			public void setHour(String hour) {
				this.hour = hour;
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

			public String getDetail() {
				return detail;
			}

			public void setDetail(String detail) {
				this.detail = detail;
			}

			public String getSubject() {
				return subject;
			}

			public void setSubject(String subject) {
				this.subject = subject;
			}

			public float getPrice() {
				return price;
			}

			public void setPrice(float price) {
				this.price = price;
			}

			public int getState() {
				return state;
			}

			public void setState(int state) {
				this.state = state;
			}

		}

	}
}
