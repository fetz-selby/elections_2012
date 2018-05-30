package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.RegionLoadEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RegionLoadEvent extends GwtEvent<RegionLoadEventHandler>{

	public static Type<RegionLoadEventHandler> TYPE = new Type<RegionLoadEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RegionLoadEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RegionLoadEventHandler handler) {
		handler.onRegionLoadFired(this);
	}

}
