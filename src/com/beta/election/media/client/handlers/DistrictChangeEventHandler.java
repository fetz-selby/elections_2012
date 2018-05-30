package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.DistrictChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface DistrictChangeEventHandler extends EventHandler{
	void onDistrictChanged(DistrictChangeEvent event);
}
