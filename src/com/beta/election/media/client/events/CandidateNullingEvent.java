package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.CandidateNullingEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class CandidateNullingEvent extends GwtEvent<CandidateNullingEventHandler>{

	public static Type<CandidateNullingEventHandler> TYPE = new Type<CandidateNullingEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CandidateNullingEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CandidateNullingEventHandler handler) {
		handler.onCandidateNullingEventHandlerFired(this);
	}

}
