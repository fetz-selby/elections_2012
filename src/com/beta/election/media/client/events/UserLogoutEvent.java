package com.beta.election.media.client.events;

import com.beta.election.media.client.handlers.UserLogoutEventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class UserLogoutEvent extends GwtEvent<UserLogoutEventHandler>{

	public static Type<UserLogoutEventHandler> TYPE = new Type<UserLogoutEventHandler>();
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UserLogoutEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UserLogoutEventHandler handler) {
		handler.onUserLogout(this);
	}

}
