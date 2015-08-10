package hzyj.guangda.student.response;

import java.util.List;

import hzyj.guangda.student.entity.coinRelus;

public class GetCoinRules {
	
	private List<coinRelus> coinrelus;

	public List<coinRelus> getCoinrelus() {
		return coinrelus;
	}

	public void setCoinrelus(List<coinRelus> coinrelus) {
		this.coinrelus = coinrelus;
	}
}
