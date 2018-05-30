package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.ConstituencyChangeEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ConstituencyChangeEvent extends GwtEvent<ConstituencyChangeEventHandler>{
	public static Type<ConstituencyChangeEventHandler> TYPE = new Type<ConstituencyChangeEventHandler>();
	private int constituencyId;
	
	public ConstituencyChangeEvent(int constituencyId){
		this.constituencyId = constituencyId;
	}
	
	public int getConstituencyId() {
		return constituencyId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ConstituencyChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ConstituencyChangeEventHandler handler) {
		handler.onConstituencyChanged(this);
	} 
}
