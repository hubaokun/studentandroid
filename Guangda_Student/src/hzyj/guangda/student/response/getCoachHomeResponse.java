package hzyj.guangda.student.response;

import java.util.ArrayList;

import com.common.library.llj.base.BaseReponse;

public class getCoachHomeResponse extends BaseReponse{
	private ArrayList<getInfor> dslist;
	
	
	
	public ArrayList<getInfor> getDslist() {
		return dslist;
	}



	public void setDslist(ArrayList<getInfor> dslist) {
		this.dslist = dslist;
	}



	public class getInfor{
		private String schoolid;
		private String name;
		private String telphone;
		public String getSchoolid() {
			return schoolid;
		}
		public void setSchoolid(String schoolid) {
			this.schoolid = schoolid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getTelphone() {
			return telphone;
		}
		public void setTelphone(String telphone) {
			this.telphone = telphone;
		}
		
		
		
	}

}
