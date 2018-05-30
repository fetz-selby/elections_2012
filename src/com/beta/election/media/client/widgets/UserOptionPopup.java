package com.beta.election.media.client.widgets;

import com.beta.election.media.client.events.UserLogoutEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserOptionPopup extends PopupPanel {

	private HandlerManager eventBus;
	private static UserOptionPopupUiBinder uiBinder = GWT
			.create(UserOptionPopupUiBinder.class);

	interface UserOptionPopupUiBinder extends UiBinder<Widget, UserOptionPopup> {
	}

	@UiField DivElement accountSetting, logout;
	
	public UserOptionPopup(HandlerManager eventBus) {
		setWidget(uiBinder.createAndBindUi(this));
		this.eventBus = eventBus;
		initListeners();
	}
	
	private void initListeners(){
		com.google.gwt.user.client.Element accountElement = accountSetting.cast();
		DOM.sinkEvents(accountElement, Event.ONCLICK);
		DOM.setEventListener(accountElement, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				
			}
		});
		
		com.google.gwt.user.client.Element logoutElement = logout.cast();
		DOM.sinkEvents(logoutElement, Event.ONCLICK);
		DOM.setEventListener(logoutElement, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				eventBus.fireEvent(new UserLogoutEvent());
			}
		});
	}
}
