package com.beta.election.media.client.widgets.dashboard;

import java.util.ArrayList;
import java.util.HashMap;

import com.beta.election.media.client.utils.JQPlotMultiBar;
import com.beta.election.media.client.widgets.dashboard.handlers.DashChartHandler;
import com.beta.election.media.client.widgets.dashboard.handlers.HasDashChartHandlers;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DashChart extends Composite implements HasDashChartHandlers{

	private DashChartHandler handler;
	private ArrayList<String> years;
	private HashMap<String, ArrayList<Double>> plotValues;
	
	private static DashChartUiBinder uiBinder = GWT
			.create(DashChartUiBinder.class);

	interface DashChartUiBinder extends UiBinder<Widget, DashChart> {
	}

	@UiField SimplePanel chartContainer;
	@UiField Image barChart, lineChart;
	
	public DashChart() {
		initWidget(uiBinder.createAndBindUi(this));
		initComponents();
		initListeners();
	}
	
	private void initComponents(){
		//Setting the icon
		lineChart.setUrl("images/line.png");
		barChart.setUrl("images/bar.png");
		
		chartContainer.getElement().setAttribute("id", "container");
		
	}
	
	private void initListeners(){
		barChart.sinkEvents(Event.ONCLICK);
		barChart.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onBarIconClicked();
				}
			}
		}, ClickEvent.getType());
		
		lineChart.sinkEvents(Event.ONCLICK);
		lineChart.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onLineIconClicked();
				}
			}
		}, ClickEvent.getType());
	}
	
	public void setChartMapValues(HashMap<String, ArrayList<Double>> plotValues){
		this.plotValues = plotValues;
	}
	
	public void setChartYearValues(ArrayList<String> yearValues){
		this.years = yearValues;
	}
	
	public void load(){
		doLoading();
	}
	

	private void doLoading(){
		
		JQPlotMultiBar multiBar = new JQPlotMultiBar();
		multiBar.setXValues(years);
		multiBar.setPlotMap(plotValues);
		
		chartContainer.setWidget(multiBar);
		multiBar.load();
	}
	
	@Override
	public void setDashChartHandler(DashChartHandler handler) {
		this.handler = handler;
	}

}
