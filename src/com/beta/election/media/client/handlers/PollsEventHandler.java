package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.PollsEvent;
import com.google.gwt.event.shared.EventHandler;

public interface PollsEventHandler extends EventHandler{
	void onPollsClicked(PollsEvent event);
}
