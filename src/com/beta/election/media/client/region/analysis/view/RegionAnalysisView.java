package com.beta.election.media.client.region.analysis.view;

import com.beta.election.media.client.region.analysis.presenter.RegionAnalysisPresenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;


public class RegionAnalysisView extends Composite implements RegionAnalysisPresenter.Display{

	private static RegionAnalysisViewUiBinder uiBinder = GWT
			.create(RegionAnalysisViewUiBinder.class);

	interface RegionAnalysisViewUiBinder extends
			UiBinder<Widget, RegionAnalysisView> {
	}
	
	@UiField RadioButton partyRadio, regionRadio;
	@UiField SimplePanel tablePanel, chartPanel, datePanel;
	@UiField FlowPanel partyPanel, regionPanel;
	@UiField LabelElement titleLabel;


	public RegionAnalysisView() {
		initWidget(uiBinder.createAndBindUi(this));
		initComponents();
	}
	
	private void initComponents(){
		partyPanel.getElement().setAttribute("style", "display:none");
	}

	@Override
	public void setDateWidget(IsWidget widget) {
		datePanel.setWidget(widget);
	}

	@Override
	public void showRegions(boolean isShow) {
		if(isShow){
			regionPanel.getElement().setAttribute("style", "display: block");
			partyPanel.getElement().setAttribute("style", "display: none");
		}else{
			regionPanel.getElement().setAttribute("style", "display: none");
			partyPanel.getElement().setAttribute("style", "display: block");
		}
	}

	@Override
	public HasClickHandlers getPartyRadioHandler() {
		return partyRadio;
	}

	@Override
	public HasClickHandlers getRegionRadioHandler() {
		return regionRadio;
	}

	@Override
	public HasWidgets getRegionsPanel() {
		return regionPanel;
	}

	@Override
	public HasWidgets getPartyPanel() {
		return partyPanel;
	}

	@Override
	public void setTitleLabel(String label) {
		titleLabel.setInnerText(label);
	}

	@Override
	public HasOneWidget getChartPanel() {
		return chartPanel;
	}

	@Override
	public HasOneWidget getTablePanel() {
		return tablePanel;
	}

}
