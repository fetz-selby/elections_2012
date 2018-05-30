package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.DateChangeEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class DateChangeEvent extends GwtEvent<DateChangeEventHandler>{
	private int date;
	public static Type<DateChangeEventHandler> TYPE = new Type<DateChangeEventHandler>();

	public DateChangeEvent(int date){
		this.date = date;
	}
	
	public int getDate() {
		return date;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<DateChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DateChangeEventHandler handler) {
		handler.onDateChanged(this);
	}
}
