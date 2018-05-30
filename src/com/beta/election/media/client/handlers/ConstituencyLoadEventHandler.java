package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.ConstituencyLoadEvent;
import com.google.gwt.event.shared.EventHandler;

public interface ConstituencyLoadEventHandler extends EventHandler{
	void onLoadConstituencyFired(ConstituencyLoadEvent event);
}
