package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.PaliamentaryEvent;
import com.google.gwt.event.shared.EventHandler;

public interface PaliamentaryEventHandler extends EventHandler{
	void onPaliamentaryRadioClicked(PaliamentaryEvent event);
}
