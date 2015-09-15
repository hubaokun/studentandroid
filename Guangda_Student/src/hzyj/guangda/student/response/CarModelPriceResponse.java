package hzyj.guangda.student.response;

import java.util.ArrayList;

import com.common.library.llj.base.BaseReponse;

public class CarModelPriceResponse extends BaseReponse{
	private ArrayList<Modelprice>  modelprice;
	

	public ArrayList<Modelprice> getModelprice() {
		return modelprice;
	}

	public void setModelprice(ArrayList<Modelprice> modelprice) {
		this.modelprice = modelprice;
	}

	

}
