package com.beta.election.media.client.history.presenter;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.events.RegionsEvent;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.widgets.DetailsWidget;
import com.beta.election.media.client.widgets.HistoryChartWidget2;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class HistoryPresenter implements Presenter{

	private Display view;
	private HandlerManager eventBus;
	private RetrieveServiceAsync rpc;
	private int constituencyId;
	
	public interface Display extends IsWidget{
		Widget asWidget();
		HasClickHandlers getRegionBackHandler();
		void addTab(String header, IsWidget widget);
		void addChart(IsWidget chart);
		void addDetail(IsWidget details);
		void setHistoryTitle(String title);
		void loadTabs();
	}
	
	public HistoryPresenter(int constituencyId, HandlerManager eventBus, RetrieveServiceAsync rpc, Display view){
		this.constituencyId = constituencyId;
		this.eventBus = eventBus;
		this.rpc = rpc;
		this.view = view;
	}
	
	private void bind(){
		view.setHistoryTitle(SystemVariables.getSystemInstance().getConstituencyName().toUpperCase());
		
		HistoryChartWidget2 chart = new HistoryChartWidget2(constituencyId, SystemVariables.getSystemInstance().getCandidateType(), rpc);
		view.addChart(chart);
		
		DetailsWidget details = new DetailsWidget(eventBus, rpc, constituencyId);
		view.addDetail(details);
		
		view.getRegionBackHandler().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new RegionsEvent());
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
