package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.ConstituencyLoadEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ConstituencyLoadEvent extends GwtEvent<ConstituencyLoadEventHandler>{

	private int districtId;

	public ConstituencyLoadEvent(int districtId){
		this.districtId = districtId;
	}
	
	public int getDistrictId() {
		return districtId;
	}

	public static Type<ConstituencyLoadEventHandler> TYPE = new Type<ConstituencyLoadEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ConstituencyLoadEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ConstituencyLoadEventHandler handler) {
		handler.onLoadConstituencyFired(this);
	}
	

}
