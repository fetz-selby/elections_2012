package com.beta.election.media.client.widgets;

import com.beta.election.media.client.widgets.handlers.HasHistoryWidgetHandler;
import com.beta.election.media.client.widgets.handlers.HistoryWidgetHandler;
import com.beta.election.media.shared.CandidateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class HistoryWidget extends Composite implements HasHistoryWidgetHandler{

	private CandidateModel model;
	private HistoryWidgetHandler handler;
	private static HistoryWidgetUiBinder uiBinder = GWT
			.create(HistoryWidgetUiBinder.class);

	interface HistoryWidgetUiBinder extends UiBinder<Widget, HistoryWidget> {
	}

	@UiField HTMLPanel container;
	@UiField DivElement nameField, partyField, votesField;
	@UiField Image logo;
	
	public HistoryWidget(CandidateModel model) {
		initWidget(uiBinder.createAndBindUi(this));
		this.model = model;
		initListeners();
		initComponents();
	}
	
	private void initListeners(){
		container.sinkEvents(Event.ONCLICK);
		container.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onHistoryWidgetClicked(model);
				}
			}
		}, ClickEvent.getType());
	}
	
	private void initComponents(){
		nameField.setInnerText(model.getCandidateName());
		partyField.setInnerText(model.getPartyName());
		votesField.setInnerText(model.getFormattedVotes());
		logo.setUrl(model.getLogo());
	}

	@Override
	public void setHistoryWidgetHandler(HistoryWidgetHandler handler) {
		this.handler = handler;
	}

}
