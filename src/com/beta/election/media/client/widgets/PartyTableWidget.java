package com.beta.election.media.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class PartyTableWidget extends Composite {

	private static PartyTableWidgetUiBinder uiBinder = GWT
			.create(PartyTableWidgetUiBinder.class);

	interface PartyTableWidgetUiBinder extends
			UiBinder<Widget, PartyTableWidget> {
	}

	@UiField Image partyImage;
	@UiField LabelElement labelElement;
	@UiField AnchorElement linkAnchor;
	@UiField DivElement valueLabel;
	
	public PartyTableWidget(String imageUrl, String partyName, String value) {
		initWidget(uiBinder.createAndBindUi(this));
		partyImage.setUrl(imageUrl);
		labelElement.setInnerText(partyName);
		valueLabel.setInnerText(value);
	}

}
