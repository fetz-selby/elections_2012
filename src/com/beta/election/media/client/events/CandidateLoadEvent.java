package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.CandidateLoadEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class CandidateLoadEvent extends GwtEvent<CandidateLoadEventHandler>{

	public static Type<CandidateLoadEventHandler> TYPE = new Type<CandidateLoadEventHandler>();
	private int constituencyId;
	
	public CandidateLoadEvent(int constituencyId){
		this.constituencyId = constituencyId;
	}
	
	public int getConstituencyId() {
		return constituencyId;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CandidateLoadEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CandidateLoadEventHandler handler) {
		handler.onCandidateLoadFired(this);
	}

}
