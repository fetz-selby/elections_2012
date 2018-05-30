package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.PaliamentaryEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PaliamentaryEvent extends GwtEvent<PaliamentaryEventHandler>{

	public static Type<PaliamentaryEventHandler> TYPE = new Type<PaliamentaryEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PaliamentaryEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PaliamentaryEventHandler handler) {
		handler.onPaliamentaryRadioClicked(this);
	}

}
