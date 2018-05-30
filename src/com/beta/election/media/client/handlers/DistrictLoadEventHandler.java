package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.DistrictLoadEvent;
import com.google.gwt.event.shared.EventHandler;

public interface DistrictLoadEventHandler extends EventHandler{
	void onDistrictLoadFired(DistrictLoadEvent event);
}
