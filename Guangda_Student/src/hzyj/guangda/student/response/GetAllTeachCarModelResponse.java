package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

public class GetAllTeachCarModelResponse extends BaseReponse {
	private List<Teachcar> teachcarlist;

	public List<Teachcar> getTeachcarlist() {
		return teachcarlist;
	}

	public void setTeachcarlist(List<Teachcar> teachcarlist) {
		this.teachcarlist = teachcarlist;
	}

	public class Teachcar {
		private int modelid;
		private String modelname;

		public int getModelid() {
			return modelid;
		}

		public void setModelid(int modelid) {
			this.modelid = modelid;
		}

		public String getModelname() {
			return modelname;
		}

		public void setModelname(String modelname) {
			this.modelname = modelname;
		}

	}
}
