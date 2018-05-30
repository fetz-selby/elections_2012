package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.CandidatesEvent;
import com.google.gwt.event.shared.EventHandler;

public interface CandidatesEventHandler extends EventHandler{
	void onCandidatesClicked(CandidatesEvent event);
}
