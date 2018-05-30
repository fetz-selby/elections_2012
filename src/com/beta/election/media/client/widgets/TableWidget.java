package com.beta.election.media.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TableWidget extends Composite {

	private static TableWidgetUiBinder uiBinder = GWT
			.create(TableWidgetUiBinder.class);

	interface TableWidgetUiBinder extends UiBinder<Widget, TableWidget> {
	}
	
	@UiField DivElement headerName, valueName;

	public TableWidget(String header, String value) {
		initWidget(uiBinder.createAndBindUi(this));
		headerName.setInnerText(header);
		valueName.setInnerText(value);
	}

}
