package com.beta.election.media.client.polls.view;

import com.beta.election.media.client.polls.presenter.PollsPresenter;
import com.beta.election.media.client.widgets.PollWidget;
import com.beta.election.media.client.widgets.TinyScrollPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


public class PollsView extends Composite implements PollsPresenter.Display{

	private static PollsViewUiBinder uiBinder = GWT
			.create(PollsViewUiBinder.class);

	interface PollsViewUiBinder extends UiBinder<Widget, PollsView> {
	}

	@UiField TinyScrollPanel container;
	
	public PollsView() {
		initWidget(uiBinder.createAndBindUi(this));
		initComponents();
	}
	
	private void initComponents(){
		container.setTinySize("780px", "450px");
	}

	@Override
	public void addPollsWidget(PollWidget widget) {
		container.add(widget);
	}

	@Override
	public void doLoad() {
		container.load();
	}
	

}
