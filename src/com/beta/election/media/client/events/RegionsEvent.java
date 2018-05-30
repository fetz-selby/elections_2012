package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.RegionsEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RegionsEvent extends GwtEvent<RegionsEventHandler>{

	public static Type<RegionsEventHandler> TYPE = new Type<RegionsEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RegionsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RegionsEventHandler handler) {
		handler.onRegionsClicked(this);
	}

}
