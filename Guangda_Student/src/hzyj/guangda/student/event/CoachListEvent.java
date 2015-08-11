package hzyj.guangda.student.event;

public class CoachListEvent {
	private int type;
	private String condition1, condition2, condition3, condition4, condition5, condition6, condition8, condition9, condition10, condition11;

	public CoachListEvent() {
		super();
	}

	public CoachListEvent(int type) {
		super();
		this.type = type;
	}

	public CoachListEvent(String condition1, String condition2, String condition3, String condition4, String condition5, String condition6, String condition8, String condition9, String condition10,
			String condition11) {
		super();
		this.condition1 = condition1;
		this.condition2 = condition2;
		this.condition3 = condition3;
		this.condition4 = condition4;
		this.condition5 = condition5;
		this.condition6 = condition6;
		this.condition8 = condition8;
		this.condition9 = condition9;
		this.condition10 = condition10;
		this.condition11 = condition11;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCondition6() {
		return condition6;
	}

	public void setCondition6(String condition6) {
		this.condition6 = condition6;
	}

	public String getCondition1() {
		return condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	public String getCondition2() {
		return condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}

	public String getCondition3() {
		return condition3;
	}

	public void setCondition3(String condition3) {
		this.condition3 = condition3;
	}

	public String getCondition4() {
		return condition4;
	}

	public void setCondition4(String condition4) {
		this.condition4 = condition4;
	}

	public String getCondition5() {
		return condition5;
	}

	public void setCondition5(String condition5) {
		this.condition5 = condition5;
	}

	public String getCondition8() {
		return condition8;
	}

	public void setCondition8(String condition8) {
		this.condition8 = condition8;
	}

	public String getCondition9() {
		return condition9;
	}

	public void setCondition9(String condition9) {
		this.condition9 = condition9;
	}

	public String getCondition10() {
		return condition10;
	}

	public void setCondition10(String condition10) {
		this.condition10 = condition10;
	}

	public String getCondition11() {
		return condition11;
	}

	public void setCondition11(String condition11) {
		this.condition11 = condition11;
	}

}
