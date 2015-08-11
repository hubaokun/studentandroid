package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

/**
 * 
 * @author liulj
 * 
 */
public class CommentListResponse extends BaseReponse {
	private List<Comment> evalist;
	private int count;
	private int studentnum;
	
	

	public int getStudentnum() {
		return studentnum;
	}

	public void setStudentnum(int studentnum) {
		this.studentnum = studentnum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Comment> getEvalist() {
		return evalist;
	}

	public void setEvalist(List<Comment> evalist) {
		this.evalist = evalist;
	}

	public class Comment {
		private String evaluationid;
		private String order_id;
		private String from_user;
		private String content;
		private String addtime;
		private String score;
		private String nickname;
		private String avatarUrl;

		public String getEvaluationid() {
			return evaluationid;
		}

		public void setEvaluationid(String evaluationid) {
			this.evaluationid = evaluationid;
		}

		public String getOrder_id() {
			return order_id;
		}

		public void setOrder_id(String order_id) {
			this.order_id = order_id;
		}

		public String getFrom_user() {
			return from_user;
		}

		public void setFrom_user(String from_user) {
			this.from_user = from_user;
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

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getAvatarUrl() {
			return avatarUrl;
		}

		public void setAvatarUrl(String avatarUrl) {
			this.avatarUrl = avatarUrl;
		}

	}
}
