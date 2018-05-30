package com.beta.election.media.client.analysis.split.view;

import com.beta.election.media.client.analysis.split.presenter.AnalysisSplitPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class AnalysisSplitView extends Composite implements AnalysisSplitPresenter.Display{

	private static AnalysisSplitViewUiBinder uiBinder = GWT
			.create(AnalysisSplitViewUiBinder.class);

	interface AnalysisSplitViewUiBinder extends
			UiBinder<Widget, AnalysisSplitView> {
	}

	@UiField SimplePanel sideA, sideB;
	
	
	public AnalysisSplitView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setContainerA(IsWidget widget) {
		sideA.setWidget(widget.asWidget());
	}

	@Override
	public void setContainerB(IsWidget widget) {
		sideB.setWidget(widget.asWidget());
	}

}
