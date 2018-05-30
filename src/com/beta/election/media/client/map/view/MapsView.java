package com.beta.election.media.client.map.view;

import com.beta.election.media.client.map.presenter.MapsPresenter;
import com.beta.election.media.client.widgets.MapsInputWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class MapsView extends Composite implements MapsPresenter.Display{

	private static MapsViewUiBinder uiBinder = GWT
			.create(MapsViewUiBinder.class);

	interface MapsViewUiBinder extends UiBinder<Widget, MapsView> {
	}

	@UiField SimplePanel mainPanel;
	
	public MapsView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setMap(MapsInputWidget widget) {
		mainPanel.setWidget(widget);
	}

}
