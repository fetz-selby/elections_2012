package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.AnalysisEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class AnalysisEvent extends GwtEvent<AnalysisEventHandler>{

	public static Type<AnalysisEventHandler> TYPE = new Type<AnalysisEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AnalysisEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AnalysisEventHandler handler) {
		handler.onAnalysisClicked(this);
	}

}
