package com.beta.election.media.client.widgets;

import com.beta.election.media.shared.PartyModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class SummaryPartyWidget extends Composite {

	private int id;
	private String name;
	private SummaryPartyWidgetEventHandler handler;
	private static SummaryPartyWidgetUiBinder uiBinder = GWT
			.create(SummaryPartyWidgetUiBinder.class);

	interface SummaryPartyWidgetUiBinder extends
			UiBinder<Widget, SummaryPartyWidget> {
	}

	public interface SummaryPartyWidgetEventHandler{
		void onIconClicked(int id, String name);
	}
	
	@UiField Image partyImage;
	@UiField LabelElement labelElement;
	@UiField AnchorElement linkAnchor;
	
	public SummaryPartyWidget(PartyModel model) {
		initWidget(uiBinder.createAndBindUi(this));
		this.id = model.getId();
		this.name = model.getName();
		partyImage.setUrl(model.getLogoUrl());
		labelElement.setInnerText(model.getName());
		initEvent();
	}
	
	private void initEvent(){
		Element link = (Element)linkAnchor.cast();
		DOM.sinkEvents(link, Event.ONCLICK);
		DOM.setEventListener(link, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				if(handler != null){
					handler.onIconClicked(id, name);
				}
			}
		});
	}

	public void setSummaryPartyWidgetEventHandler(SummaryPartyWidgetEventHandler handler){
		this.handler = handler;
	}
	
}
