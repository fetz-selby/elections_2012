package com.beta.election.media.client.region.view;

import com.beta.election.media.client.region.presenter.RegionPresenter;
import com.beta.election.media.client.widgets.AccordionPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class RegionView extends Composite implements RegionPresenter.Display{

	private static RegionViewUiBinder uiBinder = GWT
			.create(RegionViewUiBinder.class);

	interface RegionViewUiBinder extends UiBinder<Widget, RegionView> {
	}

	@UiField SimplePanel mainContainer;
	@UiField AccordionPanel accordion;
	
	public RegionView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void addSection(int id, String header, IsWidget widget) {
		accordion.addSection(id, header, widget);
	}

	@Override
	public void load() {
		Scheduler.get().scheduleDeferred(new Command() {
		    public void execute () {
				accordion.load();
		    }
		});
	}

	@Override
	public void setActiveTab(final int regionId) {
		Scheduler.get().scheduleDeferred(new Command() {
		    public void execute () {
				accordion.showActive(regionId);
		    }
		});
	}
	
}
