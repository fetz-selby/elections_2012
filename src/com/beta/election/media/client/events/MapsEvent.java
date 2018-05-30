package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.MapsEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class MapsEvent extends GwtEvent<MapsEventHandler>{

	public static Type<MapsEventHandler> TYPE = new Type<MapsEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MapsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MapsEventHandler handler) {
		handler.onMapsClicked(this);
	}

}
