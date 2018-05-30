package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.PollLoadEvent;
import com.google.gwt.event.shared.EventHandler;

public interface PollLoadEventHandler extends EventHandler{
	void onPollsLoadFired(PollLoadEvent event);
}
