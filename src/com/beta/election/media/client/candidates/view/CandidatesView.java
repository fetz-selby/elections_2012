package com.beta.election.media.client.candidates.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.beta.election.media.client.candidates.presenter.*;


public class CandidatesView extends Composite implements CandidatesPresenter.Display{

	private static CandidatesViewUiBinder uiBinder = GWT
			.create(CandidatesViewUiBinder.class);

	interface CandidatesViewUiBinder extends UiBinder<Widget, CandidatesView> {
	}

	@UiField SimplePanel container;
	
	public CandidatesView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setCandidateWidget(Widget screen){
		container.setWidget(screen);
	}

}
