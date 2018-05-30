package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.MapToRegionChangeEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class MapToRegionChangeEvent extends GwtEvent<MapToRegionChangeEventHandler>{

	public static Type<MapToRegionChangeEventHandler> TYPE = new Type<MapToRegionChangeEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<MapToRegionChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(MapToRegionChangeEventHandler handler) {
		handler.onMapToRegionChangeEventHandlerFired(this);
	}

}
