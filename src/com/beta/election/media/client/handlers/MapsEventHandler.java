package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.MapsEvent;
import com.google.gwt.event.shared.EventHandler;

public interface MapsEventHandler extends EventHandler{
	void onMapsClicked(MapsEvent event);
}
