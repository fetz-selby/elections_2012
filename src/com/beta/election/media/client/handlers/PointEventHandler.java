package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.PointEvent;
import com.google.gwt.event.shared.EventHandler;

public interface PointEventHandler extends EventHandler{
	void onPointClicked(PointEvent event);
}
