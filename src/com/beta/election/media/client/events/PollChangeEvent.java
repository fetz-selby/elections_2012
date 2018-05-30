package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.PollChangeEventHandler;
import com.beta.election.media.shared.CandidateModel;
import com.google.gwt.event.shared.GwtEvent;

public class PollChangeEvent extends GwtEvent<PollChangeEventHandler>{
	private CandidateModel model;
	public static Type<PollChangeEventHandler> TYPE = new Type<PollChangeEventHandler>();
	
	public PollChangeEvent(CandidateModel model){
		this.model = model;
	}

	public CandidateModel getModel() {
		return model;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PollChangeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PollChangeEventHandler handler) {
		handler.onPollChanged(this);
	}
}
