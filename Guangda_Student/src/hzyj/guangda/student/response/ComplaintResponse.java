package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

/**
 * 获取投诉列表
 * 
 * @author liulj
 * 
 */
public class ComplaintResponse extends BaseReponse {
	private List<Complaint> complaintlist;
	private int hasmore;

	public List<Complaint> getComplaintlist() {
		return complaintlist;
	}

	public void setComplaintlist(List<Complaint> complaintlist) {
		this.complaintlist = complaintlist;
	}

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public class Complaint {
		private String coachid;
		private String name;
		private float score;
		private String phone;
		private String studentcardnum;
		private String starttime;
		private String endtime;
		private String coachavatar;
		private String detail;
		private String total;

		public String getCoachavatar() {
			return coachavatar;
		}

		public void setCoachavatar(String coachavatar) {
			this.coachavatar = coachavatar;
		}

		private List<Content> contentlist;

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public String getTotal() {
			return total;
		}

		public void setTotal(String total) {
			this.total = total;
		}

		public String getCoachid() {
			return coachid;
		}

		public void setCoachid(String coachid) {
			this.coachid = coachid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public float getScore() {
			return score;
		}

		public void setScore(float score) {
			this.score = score;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getStudentcardnum() {
			return studentcardnum;
		}

		public void setStudentcardnum(String studentcardnum) {
			this.studentcardnum = studentcardnum;
		}

		public String getStarttime() {
			return starttime;
		}

		public void setStarttime(String starttime) {
			this.starttime = starttime;
		}

		public String getEndtime() {
			return endtime;
		}

		public void setEndtime(String endtime) {
			this.endtime = endtime;
		}

		public List<Content> getContentlist() {
			return contentlist;
		}

		public void setContentlist(List<Content> contentlist) {
			this.contentlist = contentlist;
		}

		public class Content {
			private String complaintid;
			private String order_id;
			private String set;
			private String content;
			private String addtime;
			private String reason;
			private int state;

			public String getReason() {
				return reason;
			}

			public void setReason(String reason) {
				this.reason = reason;
			}

			public String getComplaintid() {
				return complaintid;
			}

			public void setComplaintid(String complaintid) {
				this.complaintid = complaintid;
			}

			public String getOrder_id() {
				return order_id;
			}

			public void setOrder_id(String order_id) {
				this.order_id = order_id;
			}

			public String getSet() {
				return set;
			}

			public void setSet(String set) {
				this.set = set;
			}

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public String getAddtime() {
				return addtime;
			}

			public void setAddtime(String addtime) {
				this.addtime = addtime;
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
