package hzyj.guangda.student.response;

import com.common.library.llj.base.BaseReponse;

public class GetSendInviteCodeResult extends BaseReponse {
	private int isRecommended;
	private int inviteCode;

	public int getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(int isRecommended) {
		this.isRecommended = isRecommended;
	}

	public int getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(int inviteCode) {
		this.inviteCode = inviteCode;
	}
}
