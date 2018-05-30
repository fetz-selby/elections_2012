package com.beta.election.media.client.widgets;

import com.beta.election.media.client.widgets.handlers.ConstituencyWidgetListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ConstituencyWidget extends Composite implements ClickHandler{
	private ConstituencyWidgetListener listener;
	private String constituencyName;
	private int id;
	private static RegionWidgetUiBinder uiBinder = GWT
			.create(RegionWidgetUiBinder.class);

	interface RegionWidgetUiBinder extends UiBinder<Widget, ConstituencyWidget> {
	}

	@UiField SimplePanel constituencyField;
	
	private ConstituencyWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public ConstituencyWidget(int constituencyId, String constituencyName, ConstituencyWidgetListener listener){
		this();
		this.id = constituencyId;
		this.constituencyName = constituencyName;
		this.constituencyField.add(new Label(constituencyName));
		this.listener = listener;
		
		initListeners();
	}
	
	private void initListeners(){
		constituencyField.sinkEvents(Event.ONCLICK);
		constituencyField.addHandler(this, ClickEvent.getType());
	}

	@Override
	public void onClick(ClickEvent event) {
		listener.onConstituencyWidgetClicked(id, constituencyName);
	}

}
