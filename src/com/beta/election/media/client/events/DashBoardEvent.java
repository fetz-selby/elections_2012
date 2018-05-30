package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.DashBoardEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DashBoardEvent extends GwtEvent<DashBoardEventHandler>{

	public static Type<DashBoardEventHandler> TYPE = new Type<DashBoardEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DashBoardEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DashBoardEventHandler handler) {
		handler.onDashboardClicked(this);
	}

}
