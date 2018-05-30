package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.DistrictLoadEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DistrictLoadEvent extends GwtEvent<DistrictLoadEventHandler>{

	public static Type<DistrictLoadEventHandler> TYPE = new Type<DistrictLoadEventHandler>();
	private int regionId;
	
	public DistrictLoadEvent(int regionId){
		this.regionId = regionId;
	}
	
	public int getRegionId() {
		return regionId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DistrictLoadEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DistrictLoadEventHandler handler) {
		handler.onDistrictLoadFired(this);
	}
	

}
