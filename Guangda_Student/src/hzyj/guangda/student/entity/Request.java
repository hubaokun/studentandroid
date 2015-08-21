package hzyj.guangda.student.entity;

import java.util.ArrayList;
import java.util.List;

public class Request {
	private List<String> time = new ArrayList<String>();
	private String date;
	private String recordid ;
	private int delmoney;
	private int paytype;


	public List<String> getTime() {
		return time;
	}

	public int getDelmoney() {
		return delmoney;
	}

	public void setDelmoney(int delmoney) {
		this.delmoney = delmoney;
	}

	public void setTime(List<String> time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPaytype() {
		return paytype;
	}

	public void setPaytype(int paytype) {
		this.paytype = paytype;
	}

	public String getRecordid() {
		return recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}
}
