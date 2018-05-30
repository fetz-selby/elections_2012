package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.HistoryEvent;
import com.google.gwt.event.shared.EventHandler;

public interface HistoryEventHandler extends EventHandler{
	void onHistoryClicked(HistoryEvent event);
}
