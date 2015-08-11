package hzyj.guangda.student.response;

import hzyj.guangda.student.entity.CoachInfoVo;

import com.common.library.llj.base.BaseReponse;

/**
 * 
 * @author liulj
 * 
 */
public class GetCoachDetailResponse extends BaseReponse {
	private CoachInfoVo coachinfo;

	public CoachInfoVo getCoachinfo() {
		return coachinfo;
	}

	public void setCoachinfo(CoachInfoVo coachinfo) {
		this.coachinfo = coachinfo;
	}

}
