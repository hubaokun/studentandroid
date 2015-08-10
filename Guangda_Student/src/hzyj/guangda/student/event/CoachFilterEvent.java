package hzyj.guangda.student.event;

public class CoachFilterEvent {
	private String condition1, condition3, condition6;

	public CoachFilterEvent() {
		super();
	}

	public CoachFilterEvent(String condition1, String condition3, String condition6) {
		super();
		this.condition1 = condition1;
		this.condition3 = condition3;
		this.condition6 = condition6;
	}

	public String getCondition1() {
		return condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	public String getCondition3() {
		return condition3;
	}

	public void setCondition3(String condition3) {
		this.condition3 = condition3;
	}

	public String getCondition6() {
		return condition6;
	}

	public void setCondition6(String condition6) {
		this.condition6 = condition6;
	}

}
