package hzyj.guangda.student.response;

import com.common.library.llj.base.BaseReponse;

public class getStudentAdvertisement extends BaseReponse {
	
	private Config config;
	
	public class Config
	{
		private int student_advertisement_flag;
		private String student_advertisement_url;
		public int getStudent_advertisement_flag() {
			return student_advertisement_flag;
		}
		public void setStudent_advertisement_flag(int student_advertisement_flag) {
			this.student_advertisement_flag = student_advertisement_flag;
		}
		public String getStudent_advertisement_url() {
			return student_advertisement_url;
		}
		public void setStudent_advertisement_url(String student_advertisement_url) {
			this.student_advertisement_url = student_advertisement_url;
		}
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
}
