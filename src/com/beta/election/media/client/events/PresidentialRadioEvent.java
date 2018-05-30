package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.PresidentialRadioHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PresidentialRadioEvent extends GwtEvent<PresidentialRadioHandler>{

	public static Type<PresidentialRadioHandler> TYPE = new Type<PresidentialRadioHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PresidentialRadioHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PresidentialRadioHandler handler) {
		handler.onPresidentialRadioClicked(this);
	}

}
