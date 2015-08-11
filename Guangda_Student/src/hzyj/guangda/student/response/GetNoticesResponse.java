package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

/**
 * 获取系统消息
 * 
 * @author liulj
 * 
 */
public class GetNoticesResponse extends BaseReponse {
	private List<Notice> datalist;
	private int hasmore;

	public List<Notice> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<Notice> datalist) {
		this.datalist = datalist;
	}

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public class Notice {

		private String noticeid;
		private String category;
		private String content;
		private String addtime;
		private int readstate;

		public String getNoticeid() {
			return noticeid;
		}

		public void setNoticeid(String noticeid) {
			this.noticeid = noticeid;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
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

		public int getReadstate() {
			return readstate;
		}

		public void setReadstate(int readstate) {
			this.readstate = readstate;
		}

	}
}
