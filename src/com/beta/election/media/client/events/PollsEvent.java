package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.PollsEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PollsEvent extends GwtEvent<PollsEventHandler>{

	public static Type<PollsEventHandler> TYPE = new Type<PollsEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PollsEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PollsEventHandler handler) {
		handler.onPollsClicked(this);
	}

}
