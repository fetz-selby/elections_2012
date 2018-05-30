package com.beta.election.media.client.inbox.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class InboxView extends Composite {

	private static InboxViewUiBinder uiBinder = GWT
			.create(InboxViewUiBinder.class);

	interface InboxViewUiBinder extends UiBinder<Widget, InboxView> {
	}

	@UiField VerticalPanel container;
	
	public InboxView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
