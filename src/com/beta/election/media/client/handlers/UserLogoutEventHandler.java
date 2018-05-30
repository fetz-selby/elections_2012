package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.UserLogoutEvent;
import com.google.gwt.event.shared.EventHandler;

public interface UserLogoutEventHandler extends EventHandler{
	void onUserLogout(UserLogoutEvent event);
}
