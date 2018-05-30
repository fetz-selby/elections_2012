package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.RegionChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface RegionChangeEventHandler extends EventHandler{
	void onRegionChanged(RegionChangeEvent event);
}
