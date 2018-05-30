package com.beta.election.media.client.handlers;

import com.beta.election.media.client.events.DateChangeEvent;
import com.google.gwt.event.shared.EventHandler;

public interface DateChangeEventHandler extends EventHandler{
	void onDateChanged(DateChangeEvent event);
}
