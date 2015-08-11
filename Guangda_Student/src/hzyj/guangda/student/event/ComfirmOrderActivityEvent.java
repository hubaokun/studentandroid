package hzyj.guangda.student.event;

import hzyj.guangda.student.response.GetCanUseCouponResponse;

public class ComfirmOrderActivityEvent {
	private GetCanUseCouponResponse mGetCanUseCouponResponse;

	public ComfirmOrderActivityEvent(GetCanUseCouponResponse mGetCanUseCouponResponse) {
		super();
		this.mGetCanUseCouponResponse = mGetCanUseCouponResponse;
	}

	public GetCanUseCouponResponse getmGetCanUseCouponResponse() {
		return mGetCanUseCouponResponse;
	}

	public void setmGetCanUseCouponResponse(GetCanUseCouponResponse mGetCanUseCouponResponse) {
		this.mGetCanUseCouponResponse = mGetCanUseCouponResponse;
	}

}
