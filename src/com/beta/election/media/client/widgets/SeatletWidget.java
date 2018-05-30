package com.beta.election.media.client.widgets;

import com.beta.election.media.shared.SeatUpdateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class SeatletWidget extends Composite {

	private SeatUpdateModel model;
	private static SeatletWidgetUiBinder uiBinder = GWT
			.create(SeatletWidgetUiBinder.class);

	interface SeatletWidgetUiBinder extends UiBinder<Widget, SeatletWidget> {
	}

	@UiField Image logo;
	@UiField LabelElement partyName, voteCount;
	
	public SeatletWidget(SeatUpdateModel model) {
		initWidget(uiBinder.createAndBindUi(this));
		this.model = model;
		initComponents();
	}
	
	private void initComponents(){
		partyName.setInnerText(model.getName());
		voteCount.setInnerText(""+model.getCounts());
		logo.setUrl(model.getLogo());
	}

}
