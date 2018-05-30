package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.CandidateLoadEvent;
import com.google.gwt.event.shared.EventHandler;

public interface CandidateLoadEventHandler extends EventHandler{
	void onCandidateLoadFired(CandidateLoadEvent event);
}
