package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.RegionAnalysisEvent;
import com.google.gwt.event.shared.EventHandler;

public interface RegionAnalysisEventHandler extends EventHandler{
	void onRegionAnalysisClicked(RegionAnalysisEvent event);
}
