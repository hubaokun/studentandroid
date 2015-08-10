package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

public class GetMyBalanceResponse extends BaseReponse {
	private float balance;
	private float fmoney;
	private List<Record> recordlist;

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public float getFmoney() {
		return fmoney;
	}

	public void setFmoney(float fmoney) {
		this.fmoney = fmoney;
	}

	public List<Record> getRecordlist() {
		return recordlist;
	}

	public void setRecordlist(List<Record> recordlist) {
		this.recordlist = recordlist;
	}

	public class Record {
		private String recordid;
		private String userid;
		private int type;
		private String addtime;
		private float amount;

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public String getRecordid() {
			return recordid;
		}

		public void setRecordid(String recordid) {
			this.recordid = recordid;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}

		public String getAddtime() {
			return addtime;
		}

		public void setAddtime(String addtime) {
			this.addtime = addtime;
		}

		public float getAmount() {
			return amount;
		}

		public void setAmount(float amount) {
			this.amount = amount;
		}

	}
}
