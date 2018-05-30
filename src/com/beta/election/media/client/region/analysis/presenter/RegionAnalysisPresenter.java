package com.beta.election.media.client.region.analysis.presenter;

import java.util.ArrayList;
import java.util.HashMap;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.constants.ModuleConstants;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.utils.JQPlotSingleBar;
import com.beta.election.media.client.widgets.AnalysisYearNavigator;
import com.beta.election.media.client.widgets.PartyTableWidget;
import com.beta.election.media.client.widgets.SummaryPartyWidget;
import com.beta.election.media.client.widgets.TableWidget;
import com.beta.election.media.client.widgets.SummaryPartyWidget.SummaryPartyWidgetEventHandler;
import com.beta.election.media.client.widgets.SummaryRegionWidget;
import com.beta.election.media.client.widgets.SummaryRegionWidget.SummaryRegionWidgetEventHandler;
import com.beta.election.media.client.widgets.handlers.AnalysisYearNavigatorHandler;
import com.beta.election.media.shared.PartyModel;
import com.beta.election.media.shared.PartySummaryObject;
import com.beta.election.media.shared.RegionModel;
import com.beta.election.media.shared.RegionalSummaryObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class RegionAnalysisPresenter implements Presenter{

	private Timer timer;
	private RegionAnalysisPresenter self = this;
	private Display view;
	private int year = 1996, regionId = 1, partyId = 1;
	private HandlerManager eventBus;
	private RetrieveServiceAsync rpc;
	private boolean isRegion = false;
	
	public interface Display extends IsWidget{
		Widget asWidget();
		void setDateWidget(IsWidget widget);
		void showRegions(boolean isShow);
		void setTitleLabel(String label);
		HasClickHandlers getPartyRadioHandler();
		HasClickHandlers getRegionRadioHandler();
		HasWidgets getRegionsPanel();
		HasWidgets getPartyPanel();
		HasOneWidget getChartPanel();
		HasOneWidget getTablePanel();
	}
	
	public RegionAnalysisPresenter(Display view, HandlerManager eventBus, RetrieveServiceAsync rpc){
		this.view = view;
		this.eventBus = eventBus;
		this.rpc = rpc;
	}
	
	private void bind(){
		rpc.getYearMap(new AsyncCallback<HashMap<Integer,String>>() {
			
			@Override
			public void onSuccess(HashMap<Integer, String> result) {
				AnalysisYearNavigator yearNav = new AnalysisYearNavigator(result);
				yearNav.setAnalysisYearNavigatorHandler(new AnalysisYearNavigatorHandler() {
					
					@Override
					public void onUpClicked(int year) {
						self.year = year;
						doDataLoading();
					}
					
					@Override
					public void onDownClicked(int year) {
						self.year = year;
						doDataLoading();
					}
				});
				
				view.setDateWidget(yearNav);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
		view.getPartyRadioHandler().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.showRegions(false);
				setTitle("");
				isRegion = true;
				view.getChartPanel().setWidget(new SimplePanel());
				view.getTablePanel().setWidget(new SimplePanel());
			}
		});
		
		view.getRegionRadioHandler().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.showRegions(true);
				setTitle("");
				isRegion = false;
				view.getChartPanel().setWidget(new SimplePanel());
				view.getTablePanel().setWidget(new SimplePanel());			}
		});
		
		doRegionsLoading();
		doPartyLoading();
	}
	
	private void setTitle(String label){
		view.setTitleLabel(label);
	}
	
	private void doRegionsLoading(){
		view.getRegionsPanel().clear();
		rpc.getRegions(new AsyncCallback<ArrayList<RegionModel>>() {
			
			@Override
			public void onSuccess(ArrayList<RegionModel> result) {
				if(result != null){
					for(RegionModel model : result){
						SummaryRegionWidget widget = new SummaryRegionWidget(model);
						initSummaryRegionWidgetEvent(widget);
						view.getRegionsPanel().add(widget);
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
			}
		});
	}
	
	private void doPartyLoading(){
		view.getPartyPanel().clear();
		rpc.getAllParties(new AsyncCallback<ArrayList<PartyModel>>() {
			
			@Override
			public void onSuccess(ArrayList<PartyModel> result) {
				if(result != null){
					for(PartyModel model : result){
						SummaryPartyWidget widget = new SummaryPartyWidget(model);
						initSummaryPartyWidgetEvent(widget);
						view.getPartyPanel().add(widget);
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initSummaryPartyWidgetEvent(final SummaryPartyWidget widget){
		widget.setSummaryPartyWidgetEventHandler(new SummaryPartyWidgetEventHandler() {
			
			@Override
			public void onIconClicked(int id, String name) {
				setTitle(name);
				partyId = id;
				doInvokeCall();
			}
		});
	}
	
	private void initSummaryRegionWidgetEvent(final SummaryRegionWidget widget){
		widget.setSummaryRegionWidgetEventHandler(new SummaryRegionWidgetEventHandler() {
			
			@Override
			public void onLinkClicked(int id, String name) {
				setTitle(name);
				regionId = id;
				doInvokeCall();
			}
		});
	}
	
	private void doDataLoading(){
		if(timer != null){
			timer.cancel();
		}
		timer = new Timer() {
			
			@Override
			public void run() {
				doInvokeCall();
			}
		};
		
		timer.schedule(2000);
	}
	
	private void doInvokeCall(){
		String type = SystemVariables.getSystemInstance().getCandidateType();

		if(type.equals(ModuleConstants.PRESIDENTIAL)){
			if(isRegion){
				doPresRegionCall();
			}else{
				doPresPartyCall();
			}
		}else if(type.equals(ModuleConstants.PALIAMENTARIAN)){
			if(isRegion){
				doPaliaRegionCall();
			}else{
				doPaliaPartyCall();
			}
		}
	}
	
	private void doPresRegionCall(){
		rpc.getPresRegionSummary(partyId, year+"", new AsyncCallback<ArrayList<RegionalSummaryObject>>() {
			
			@Override
			public void onSuccess(ArrayList<RegionalSummaryObject> result) {
				if(result != null){
					JQPlotSingleBar barChart = new JQPlotSingleBar();
					FlowPanel tablePanel = new FlowPanel();

					for(RegionalSummaryObject object : result){
						GWT.log("name "+object.getShortName());

						barChart.addName(object.getShortName());
						barChart.addValue(object.getVotes());
						
						tablePanel.add(new TableWidget(object.getShortName(), object.getFormattedVotes()));

					}
					
					barChart.load();
					view.getChartPanel().setWidget(barChart);
					view.getTablePanel().setWidget(tablePanel);

				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void doPresPartyCall(){
		rpc.getPresPartySummary(regionId, year+"", new AsyncCallback<ArrayList<PartySummaryObject>>() {
			
			@Override
			public void onSuccess(ArrayList<PartySummaryObject> result) {
				if(result != null){
					JQPlotSingleBar barChart = new JQPlotSingleBar();
					FlowPanel tablePanel = new FlowPanel();
					tablePanel.getElement().setAttribute("style", "padding-left: 10px;");

					for(PartySummaryObject object : result){
						barChart.addName(object.getPartyName());
						barChart.addValue(object.getVotes());
						barChart.addColor(object.getColor());
						tablePanel.add(new PartyTableWidget(object.getAvatar(), object.getPartyName(), object.getFormattedVotes()));
					}
					
					barChart.load();
					view.getChartPanel().setWidget(barChart);
					view.getTablePanel().setWidget(tablePanel);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void doPaliaRegionCall(){
		rpc.getPaliaRegionSummary(partyId, year+"", new AsyncCallback<ArrayList<RegionalSummaryObject>>() {
			
			@Override
			public void onSuccess(ArrayList<RegionalSummaryObject> result) {
				if(result != null){
					JQPlotSingleBar barChart = new JQPlotSingleBar();
					FlowPanel tablePanel = new FlowPanel();

					for(RegionalSummaryObject object : result){

						barChart.addName(object.getShortName());
						barChart.addValue(object.getSeats());
						
						tablePanel.add(new TableWidget(object.getShortName(), ""+object.getSeats()));
					}
					
					barChart.load();
					view.getChartPanel().setWidget(barChart);
					view.getTablePanel().setWidget(tablePanel);

				}				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void doPaliaPartyCall(){
		rpc.getPaliaPartySummary(regionId, year+"", new AsyncCallback<ArrayList<PartySummaryObject>>() {
			
			@Override
			public void onSuccess(ArrayList<PartySummaryObject> result) {
				if(result != null){
					JQPlotSingleBar barChart = new JQPlotSingleBar();
					FlowPanel tablePanel = new FlowPanel();
					tablePanel.getElement().setAttribute("style", "padding-left: 10px;");
					
					for(PartySummaryObject object : result){
						barChart.addName(object.getPartyName());
						barChart.addValue(object.getSeats());
						barChart.addColor(object.getColor());
						
						tablePanel.add(new PartyTableWidget(object.getAvatar(), object.getPartyName(), object.getSeats()+""));
					}
					
					barChart.load();
					view.getChartPanel().setWidget(barChart);
					view.getTablePanel().setWidget(tablePanel);
				}				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		bind();
		screen.add(view.asWidget());
	}

}
