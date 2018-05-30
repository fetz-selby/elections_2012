package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.PollLoadEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PollLoadEvent extends GwtEvent<PollLoadEventHandler>{

	public static Type<PollLoadEventHandler> TYPE = new Type<PollLoadEventHandler>();
	private int year;
	
	public PollLoadEvent(int year){
		this.year = year;
	}
	
	public int getYear() {
		return year;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PollLoadEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PollLoadEventHandler handler) {
		handler.onPollsLoadFired(this);
	}

}
