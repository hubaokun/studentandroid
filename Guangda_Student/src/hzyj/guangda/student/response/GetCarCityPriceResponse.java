package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

public class GetCarCityPriceResponse extends BaseReponse{
	
	private List<OpenCity> opencity;
	
	
	public List<OpenCity> getOpencity() {
		return opencity;
	}


	public void setOpencity(List<OpenCity> opencity) {
		this.opencity = opencity;
	}


	
	
}
