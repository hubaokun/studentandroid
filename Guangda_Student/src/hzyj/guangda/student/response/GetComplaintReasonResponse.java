package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

/**
 * 获得投诉的原因
 * 
 * @author liulj
 * 
 */
public class GetComplaintReasonResponse extends BaseReponse {
	private List<Reason> reasonlist;

	public List<Reason> getReasonlist() {
		return reasonlist;
	}

	public void setReasonlist(List<Reason> reasonlist) {
		this.reasonlist = reasonlist;
	}

	public class Reason {
		private int setid;
		private int type;
		private String content;
		private String addtime;

		public int getSetid() {
			return setid;
		}

		public void setSetid(int setid) {
			this.setid = setid;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
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

	}
}
