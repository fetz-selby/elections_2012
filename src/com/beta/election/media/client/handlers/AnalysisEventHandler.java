package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.AnalysisEvent;
import com.google.gwt.event.shared.EventHandler;

public interface AnalysisEventHandler extends EventHandler{
	void onAnalysisClicked(AnalysisEvent event);
}
