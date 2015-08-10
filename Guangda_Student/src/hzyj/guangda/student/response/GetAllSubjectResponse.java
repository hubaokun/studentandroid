package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

public class GetAllSubjectResponse extends BaseReponse {
	private List<Subject> subjectlist;

	public List<Subject> getSubjectlist() {
		return subjectlist;
	}

	public void setSubjectlist(List<Subject> subjectlist) {
		this.subjectlist = subjectlist;
	}

	public class Subject {
		private String subjectid;
		private String subjectname;
		private String isdefault;

		public String getSubjectid() {
			return subjectid;
		}

		public void setSubjectid(String subjectid) {
			this.subjectid = subjectid;
		}

		public String getSubjectname() {
			return subjectname;
		}

		public void setSubjectname(String subjectname) {
			this.subjectname = subjectname;
		}

		public String getIsdefault() {
			return isdefault;
		}

		public void setIsdefault(String isdefault) {
			this.isdefault = isdefault;
		}
	}
}
