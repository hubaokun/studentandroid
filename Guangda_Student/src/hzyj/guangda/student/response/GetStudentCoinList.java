package hzyj.guangda.student.response;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

public class GetStudentCoinList extends BaseReponse {
	private int hasmore;
	private int coinnum;
	private String coachname;
	private int fcoinsum;
	private List<RecordList> recordlist;
	
	public class RecordList
	{
		private String addtime;
		private int coinnum;
		private int coinrecordid;
		private int ownerid;
		private int ownertype;
		private int payerid;
		private int payertype;
		private int receiverid;
		private int receivertype;
		private int type;
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public int getReceivertype() {
			return receivertype;
		}
		public void setReceivertype(int receivertype) {
			this.receivertype = receivertype;
		}
		public int getReceiverid() {
			return receiverid;
		}
		public void setReceiverid(int receiverid) {
			this.receiverid = receiverid;
		}
		public int getPayertype() {
			return payertype;
		}
		public void setPayertype(int payertype) {
			this.payertype = payertype;
		}
		public int getPayerid() {
			return payerid;
		}
		public void setPayerid(int payerid) {
			this.payerid = payerid;
		}
		public int getOwnertype() {
			return ownertype;
		}
		public void setOwnertype(int ownertype) {
			this.ownertype = ownertype;
		}
		public int getOwnerid() {
			return ownerid;
		}
		public void setOwnerid(int ownerid) {
			this.ownerid = ownerid;
		}
		public int getCoinrecordid() {
			return coinrecordid;
		}
		public void setCoinrecordid(int coinrecordid) {
			this.coinrecordid = coinrecordid;
		}
		public int getCoinnum() {
			return coinnum;
		}
		public void setCoinnum(int coinnum) {
			this.coinnum = coinnum;
		}
		public String getAddtime() {
			return addtime;
		}
		public void setAddtime(String addtime) {
			this.addtime = addtime;
		} 
		
	}

	public int getHasmore() {
		return hasmore;
	}
	
	

	public int getFcoinsum() {
		return fcoinsum;
	}



	public void setFcoinsum(int fcoinsum) {
		this.fcoinsum = fcoinsum;
	}



	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public int getCoinnum() {
		return coinnum;
	}

	public void setCoinnum(int coinnum) {
		this.coinnum = coinnum;
	}

	public List<RecordList> getRecordlist() {
		return recordlist;
	}

	public void setRecordlist(List<RecordList> recordlist) {
		this.recordlist = recordlist;
	}

	public String getCoachname() {
		return coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
	}

}
