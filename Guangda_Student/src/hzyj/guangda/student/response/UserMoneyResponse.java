package hzyj.guangda.student.response;

import com.common.library.llj.base.BaseReponse;

public class UserMoneyResponse extends BaseReponse {
	private float money;
	private float fmoney;
	private float gmoney;

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public float getFmoney() {
		return fmoney;
	}

	public void setFmoney(float fmoney) {
		this.fmoney = fmoney;
	}

	public float getGmoney() {
		return gmoney;
	}

	public void setGmoney(float gmoney) {
		this.gmoney = gmoney;
	}

}
