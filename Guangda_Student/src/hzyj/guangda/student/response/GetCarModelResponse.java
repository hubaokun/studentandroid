package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

public class GetCarModelResponse extends BaseReponse {
	private List<CarModel> modellist;

	public List<CarModel> getModellist() {
		return modellist;
	}

	public void setModellist(List<CarModel> modellist) {
		this.modellist = modellist;
	}

	public class CarModel {
		private String modelid;
		private String modelname;
		private String searchname;
		private boolean select;

		public boolean isSelect() {
			return select;
		}

		public void setSelect(boolean select) {
			this.select = select;
		}

		public String getModelid() {
			return modelid;
		}

		public void setModelid(String modelid) {
			this.modelid = modelid;
		}

		public String getModelname() {
			return modelname;
		}

		public void setModelname(String modelname) {
			this.modelname = modelname;
		}

		public String getSearchname() {
			return searchname;
		}

		public void setSearchname(String searchname) {
			this.searchname = searchname;
		}

	}
}
