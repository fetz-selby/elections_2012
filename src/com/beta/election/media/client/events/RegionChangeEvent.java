package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.RegionChangeEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RegionChangeEvent extends GwtEvent<RegionChangeEventHandler>{

	public static Type<RegionChangeEventHandler> TYPE = new Type<RegionChangeEventHandler>();
	private int regionId;
	
	public RegionChangeEvent(int regionId){
		this.regionId = regionId;
	}
	
	public int getRegionId() {
		return regionId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RegionChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RegionChangeEventHandler handler) {
		handler.onRegionChanged(this);
	}

}
