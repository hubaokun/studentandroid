package hzyj.guangda.student.response;

import java.util.ArrayList;

import com.common.library.llj.base.BaseReponse;

public class GetCoinsLimitResponse extends BaseReponse{
	
	private ArrayList<coinAffiliation> coinaffiliationlist;
	
    


	public ArrayList<coinAffiliation> getCoinaffiliationlist() {
		return coinaffiliationlist;
	}




	public void setCoinaffiliationlist(ArrayList<coinAffiliation> coinaffiliationlist) {
		this.coinaffiliationlist = coinaffiliationlist;
	}




	public class coinAffiliation{
		private String coin;
		private String msg ;
		public String getCoin() {
			return coin;
		}
		public void setCoin(String coin) {
			this.coin = coin;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
	}

}
