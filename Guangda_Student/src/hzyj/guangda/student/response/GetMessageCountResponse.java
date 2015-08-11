package hzyj.guangda.student.response;

import com.common.library.llj.base.BaseReponse;

public class GetMessageCountResponse extends BaseReponse {
	private int noticecount;

	public int getNoticecount() {
		return noticecount;
	}

	public void setNoticecount(int noticecount) {
		this.noticecount = noticecount;
	}

}
