package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.PresidentialRadioEvent;
import com.google.gwt.event.shared.EventHandler;

public interface PresidentialRadioHandler extends EventHandler{
	void onPresidentialRadioClicked(PresidentialRadioEvent event);
}
