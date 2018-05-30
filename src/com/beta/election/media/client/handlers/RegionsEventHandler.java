package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.RegionsEvent;
import com.google.gwt.event.shared.EventHandler;

public interface RegionsEventHandler extends EventHandler{
	void onRegionsClicked(RegionsEvent event);
}
