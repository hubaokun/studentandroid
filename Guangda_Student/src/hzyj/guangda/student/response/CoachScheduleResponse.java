package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

/**
 * 
 * @author liulj
 * 
 */
public class CoachScheduleResponse extends BaseReponse {
	private List<Data> datelist;
	private int coachstate; //1 开课， 0 休息
	private int remindstate;//1 已提醒过  0 未提醒
	
	

	public int getCoachstate() {
		return coachstate;
	}

	public void setCoachstate(int coachstate) {
		this.coachstate = coachstate;
	}

	public int getRemindstate() {
		return remindstate;
	}

	public void setRemindstate(int remindstate) {
		this.remindstate = remindstate;
	}

	public List<Data> getDatelist() {
		return datelist;
	}

	public void setDatelist(List<Data> datelist) {
		this.datelist = datelist;
	}

	public class Data implements Comparable<Data> {
		private long dateLong;
		private String date;
		private int hour;
		private int state;
		private int cancelstate;
		private float price;
		private int isrest;
		private String addressid;
		private String addressdetail;
		private String subjectid;
		private String subject;
		private int pasttime;
		private int isbooked;

		private int status;// 0不能选，1可以选，2选中

		public long getDateLong() {
			return dateLong;
		}

		public void setDateLong(long dateLong) {
			this.dateLong = dateLong;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
			this.hour = hour;
		}

		public int getState() {
			return state;
		}

		public void setState(int state) {
			this.state = state;
		}

		public int getCancelstate() {
			return cancelstate;
		}

		public void setCancelstate(int cancelstate) {
			this.cancelstate = cancelstate;
		}

		public float getPrice() {
			return price;
		}

		public void setPrice(float price) {
			this.price = price;
		}

		public int getIsrest() {
			return isrest;
		}

		public void setIsrest(int isrest) {
			this.isrest = isrest;
		}

		public String getAddressid() {
			return addressid;
		}

		public void setAddressid(String addressid) {
			this.addressid = addressid;
		}

		public String getAddressdetail() {
			return addressdetail;
		}

		public void setAddressdetail(String addressdetail) {
			this.addressdetail = addressdetail;
		}

		public String getSubjectid() {
			return subjectid;
		}

		public void setSubjectid(String subjectid) {
			this.subjectid = subjectid;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public int getPasttime() {
			return pasttime;
		}

		public void setPasttime(int pasttime) {
			this.pasttime = pasttime;
		}

		public int getIsbooked() {
			return isbooked;
		}

		public void setIsbooked(int isbooked) {
			this.isbooked = isbooked;
		}

		@Override
		public int compareTo(Data another) {
			int a = hour;
			long b = another.getHour();
			if (b > a) {
				return -1;
			} else if (b < a) {
				return 1;
			} else {
				return 0;
			}
		}

	}
}
