package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.CandidatesEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class CandidatesEvent extends GwtEvent<CandidatesEventHandler>{

	public static Type<CandidatesEventHandler> TYPE = new Type<CandidatesEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CandidatesEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CandidatesEventHandler handler) {
		handler.onCandidatesClicked(this);
	}

}
