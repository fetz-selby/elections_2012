package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.DashBoardEvent;
import com.google.gwt.event.shared.EventHandler;

public interface DashBoardEventHandler extends EventHandler{
	void onDashboardClicked(DashBoardEvent event);
}
