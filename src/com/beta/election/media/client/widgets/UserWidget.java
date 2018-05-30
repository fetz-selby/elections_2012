package com.beta.election.media.client.widgets;

import com.beta.election.media.client.Presenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class UserWidget extends Composite implements Presenter{

	private HandlerManager eventBus;
	
	private static UserWidgetUiBinder uiBinder = GWT
			.create(UserWidgetUiBinder.class);

	interface UserWidgetUiBinder extends UiBinder<Widget, UserWidget> {
	}

	@UiField SimplePanel imgBox, popupBox;
	@UiField DivElement profileName;
	
	private UserWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public UserWidget(HandlerManager eventBus){
		this();
		this.eventBus = eventBus;
		initListeners();
		initWidget();
	}

	private void initListeners(){
		popupBox.sinkEvents(Event.ONCLICK);
		popupBox.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				UserOptionPopup popup = new UserOptionPopup(eventBus);
				popup.setAnimationEnabled(true);
				popup.setAutoHideEnabled(true);
				popup.showRelativeTo(popupBox);
			}
		}, ClickEvent.getType());
			
		
	}
	
	private void initWidget(){
		Image image = new Image("images/stock_person.png");
		image.setSize("49px", "42px");
		imgBox.setWidget(image);
		
		Image image2 = new Image("images/arrow_small.png");
		image2.setSize("17px", "11px");
		popupBox.setWidget(image2);
	}
	
	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		screen.add(asWidget());
	}
}
