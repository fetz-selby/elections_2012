package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.DistrictChangeEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DistrictChangeEvent extends GwtEvent<DistrictChangeEventHandler>{

	public static Type<DistrictChangeEventHandler> TYPE = new Type<DistrictChangeEventHandler>();
	private int districtId;

	public DistrictChangeEvent(int districtId){
		this.districtId = districtId;
	}
	
	public int getDistrictId() {
		return districtId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DistrictChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DistrictChangeEventHandler handler) {
		handler.onDistrictChanged(this);
	}

}
