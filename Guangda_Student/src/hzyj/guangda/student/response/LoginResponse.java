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
	private UserInfoVo UserInfo;

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
