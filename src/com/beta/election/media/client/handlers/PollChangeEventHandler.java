package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.PollChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface PollChangeEventHandler extends EventHandler{
	void onPollChanged(PollChangeEvent event);
}
