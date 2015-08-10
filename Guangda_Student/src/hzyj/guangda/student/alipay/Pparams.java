package hzyj.guangda.student.alipay;

public class Pparams {
	private String partner;// 合作者身份ID
	private String notify_url;// 服务器异步通知页面路径.需要客户端URL encode。
	private String out_trade_no;// 支付宝合作商户网站唯一订单号。
	private String subject;// 商品名称。该参数最长为128个汉字
	private String seller_id;// 卖家支付宝账号
	private String total_fee;// 总金额
	private String body;// 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
	private String rsakey;// 客户私钥

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getRsakey() {
		return rsakey;
	}

	public void setRsakey(String rsakey) {
		this.rsakey = rsakey;
	}

}
