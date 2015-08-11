package hzyj.guangda.student.response;

import hzyj.guangda.student.entity.UserInfoVo;

import com.common.library.llj.base.BaseReponse;

/**
 * 
 * @author liulj
 * 
 */
public class LoginResponse extends BaseReponse {
	private int isbind;
	private int isInvited;
	private UserInfoVo UserInfo;
	
	

	public int getIsInvited() {
		return isInvited;
	}

	public void setIsInvited(int isInvited) {
		this.isInvited = isInvited;
	}

	public int getIsbind() {
		return isbind;
	}

	public void setIsbind(int isbind) {
		this.isbind = isbind;
	}

	public UserInfoVo getUserInfo() {
		return UserInfo;
	}

	public void setUserInfo(UserInfoVo userInfo) {
		UserInfo = userInfo;
	}

}
