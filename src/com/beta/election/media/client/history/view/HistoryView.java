package com.beta.election.media.client.history.view;

import com.beta.election.media.client.history.presenter.HistoryPresenter;
import com.beta.election.media.client.widgets.TabsPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class HistoryView extends Composite implements HistoryPresenter.Display{

	private TabsPanel tabs;
	private static HistoryViewUiBinder uiBinder = GWT
			.create(HistoryViewUiBinder.class);

	interface HistoryViewUiBinder extends UiBinder<Widget, HistoryView> {
	}

	//@UiField SimplePanel container;
	@UiField Anchor backToRegionLink;
	@UiField SimplePanel chartContainer, detailsContainer;
	@UiField DivElement consNameTitle;
	
	public HistoryView() {
		initWidget(uiBinder.createAndBindUi(this));
		tabs = new TabsPanel();
		//container.setWidget(tabs);
	}

	@Override
	public void addTab(String header, IsWidget widget) {
		//tabs.addTab(header, widget);
	}

	@Override
	public void loadTabs() {
		tabs.load();
	}

	@Override
	public HasClickHandlers getRegionBackHandler() {
		return backToRegionLink;
	}

	@Override
	public void addChart(IsWidget chart) {
		chartContainer.setWidget(chart.asWidget());
	}

	@Override
	public void addDetail(IsWidget details) {
		detailsContainer.setWidget(details.asWidget());
	}

	@Override
	public void setHistoryTitle(String title) {
		consNameTitle.setInnerText(title);
	}
	
}
