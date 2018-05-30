package com.beta.election.media.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MediaElection2 implements EntryPoint {

	public void onModuleLoad() {
		RetrieveServiceAsync rpc = GWT.create(RetrieveService.class);

		HandlerManager eventBus = new HandlerManager(null);
		AppController app = new AppController(rpc, eventBus,
				RootPanel.get("date_panel"), RootPanel.get("user_panel"),
				RootPanel.get("left_filter"), RootPanel.get("right_filter"),
				RootPanel.get("dashboard"), RootPanel.get("polls"),
				RootPanel.get("analysis"), RootPanel.get("region"),
				RootPanel.get("candidate"), RootPanel.get("map"), RootPanel.get("history"), RootPanel.get("presRadio"), RootPanel.get("paliaRadio"), RootPanel.get("graph_analyses"), RootPanel.get("seat_container"), RootPanel.get("region_analysis"));
		app.go(RootPanel.get("content_panel"));
	}
}
