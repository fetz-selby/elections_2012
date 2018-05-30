package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.RegionAnalysisEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class RegionAnalysisEvent extends GwtEvent<RegionAnalysisEventHandler>{

	public static Type<RegionAnalysisEventHandler> TYPE = new Type<RegionAnalysisEventHandler>();

	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RegionAnalysisEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RegionAnalysisEventHandler handler) {
		handler.onRegionAnalysisClicked(this);
	}

}
