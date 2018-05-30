package com.beta.election.media.client.dashboard.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.beta.election.media.client.constants.ModuleConstants;
import com.beta.election.media.client.widgets.dashboard.DashChart;
import com.beta.election.media.client.widgets.dashboard.DashMap;
import com.beta.election.media.client.widgets.dashboard.Dashlet;
import com.beta.election.media.client.widgets.dashboard.handlers.HasDashMapHandlers;
import com.beta.election.media.client.widgets.dashboard.handlers.HasDashletHandlers;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.beta.election.media.client.dashboard.presenter.*;


public class DashBoardView extends Composite implements DashBoardPresenter.Display{
	private DashMap dashMap;
	private Dashlet chartDashlet;
	private static DashBoardViewUiBinder uiBinder = GWT
			.create(DashBoardViewUiBinder.class);

	interface DashBoardViewUiBinder extends UiBinder<Widget, DashBoardView> {
	}

	@UiField
	DivElement clockDiv;
	@UiField
	SimplePanel chartContainer, mapContainer, otherContainer;

	public DashBoardView() {
		initWidget(uiBinder.createAndBindUi(this));
		initComponents();
	}

	private void initComponents() {
		// Set the chart
		chartDashlet = getDummyChart();
		chartContainer.setWidget(chartDashlet);
		
		dashMap = new DashMap();
		mapContainer.setWidget(dashMap);

	}

	private Dashlet getDummyChart() {
		Dashlet chartDash = new Dashlet("Election Chart", ModuleConstants.DASH_CHART);
		
		DashChart chart = new DashChart();
		ArrayList<String> year = new ArrayList<String>();
		year.add("1992");
		year.add("1996");
		year.add("2000");
		year.add("2004");
		year.add("2008");

		chart.setChartYearValues(year);

		HashMap<String, ArrayList<Double>> plotValues = new HashMap<String, ArrayList<Double>>();

		String[] parties = { "NPP", "NDC", "CPP", "PNC" };
		Double[][] values = { { 100.0, 200.2, 300.1, 200.0, 300.2 },
				{ 100.1, 200.3, 300.3, 398.9, 299.8 },
				{ 101.3, 222.2, 330.4, 405.9, 770.0 },
				{ 190.9, 678.3, 687.0, 908.0, 78.4 } };

		for (int i = 0; i < parties.length; i++) {
			List value = Arrays.asList(values[i]);
			ArrayList<Double> tmpValue = new ArrayList<Double>();

			for (Object doubles : value) {
				GWT.log("Double is " + (Double) doubles);
				tmpValue.add((Double) doubles);
			}

			plotValues.put(parties[i], tmpValue);
		}

		chart.setChartMapValues(plotValues);
		chartDash.setContent(chart);
		chart.load();
		return chartDash;
	}

	@Override
	public HasClickHandlers getMapClick() {
		return null;
	}

	@Override
	public HasDashletHandlers getMapDashletHandler() {
		return dashMap;
	}

	@Override
	public HasDashletHandlers getChartDashletHandler() {
		return chartDashlet;
	}

	@Override
	public HasDashMapHandlers getDashMapHandler() {
		return dashMap;
	}

}
