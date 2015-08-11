package hzyj.guangda.student.event;

public class OrderDetailEvent {
	private String orderId;

	public OrderDetailEvent(String orderId) {
		super();
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
