package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.HistoryEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class HistoryEvent extends GwtEvent<HistoryEventHandler>{
	private int constituencyId;
	private String constituencyName;
	public static Type<HistoryEventHandler> TYPE = new Type<HistoryEventHandler>();

	public HistoryEvent(int constituencyId, String constituencyName){
		this.constituencyId = constituencyId;
		this.constituencyName = constituencyName;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<HistoryEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(HistoryEventHandler handler) {
		handler.onHistoryClicked(this);
	}

	public int getConstituencyId() {
		return constituencyId;
	}

	public String getConstituencyName() {
		return constituencyName;
	}
	
}
