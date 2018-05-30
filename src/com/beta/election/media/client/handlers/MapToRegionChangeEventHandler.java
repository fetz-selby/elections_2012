package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.MapToRegionChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface MapToRegionChangeEventHandler extends EventHandler{
	void onMapToRegionChangeEventHandlerFired(MapToRegionChangeEvent event);
}
