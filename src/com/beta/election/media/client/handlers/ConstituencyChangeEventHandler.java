package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.ConstituencyChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface ConstituencyChangeEventHandler extends EventHandler{
	void onConstituencyChanged(ConstituencyChangeEvent event);
}
