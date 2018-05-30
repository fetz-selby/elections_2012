package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.PointEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class PointEvent extends GwtEvent<PointEventHandler>{

	public static Type<PointEventHandler> TYPE = new Type<PointEventHandler>();
	private String point;
	
	public PointEvent(String point){
		this.point = point;
	}
	
	public String getPoint() {
		return point;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PointEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PointEventHandler handler) {
		handler.onPointClicked(this);
	}

}
