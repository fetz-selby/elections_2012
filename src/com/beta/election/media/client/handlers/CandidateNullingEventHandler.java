package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.CandidateNullingEvent;
import com.google.gwt.event.shared.EventHandler;

public interface CandidateNullingEventHandler extends EventHandler{
	void onCandidateNullingEventHandlerFired(CandidateNullingEvent event);
}
