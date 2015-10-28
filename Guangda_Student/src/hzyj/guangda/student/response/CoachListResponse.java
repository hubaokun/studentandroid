package hzyj.guangda.student.response;

import hzyj.guangda.student.entity.CoachInfoVo;

import java.util.List;

import com.common.library.llj.base.BaseReponse;

/**
 * 
 * @author liulj
 * 
 */
public class CoachListResponse extends BaseReponse {
	private List<CoachInfoVo> coachlist;
	private int hasmore;// 是否还有更多数据  

	public int getHasmore() {
		return hasmore;
	}

	public void setHasmore(int hasmore) {
		this.hasmore = hasmore;
	}

	public List<CoachInfoVo> getCoachlist() {
		return coachlist;
	}

	public void setCoachlist(List<CoachInfoVo> coachlist) {
		this.coachlist = coachlist;
	}

}
