package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.RegionLoadEvent;
import com.google.gwt.event.shared.EventHandler;

public interface RegionLoadEventHandler extends EventHandler{
	void onRegionLoadFired(RegionLoadEvent event);
}
