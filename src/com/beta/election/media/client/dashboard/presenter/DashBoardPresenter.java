package com.beta.election.media.client.dashboard.presenter;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.widgets.dashboard.handlers.DashMapHandler;
import com.beta.election.media.client.widgets.dashboard.handlers.DashletHandler;
import com.beta.election.media.client.widgets.dashboard.handlers.HasDashMapHandlers;
import com.beta.election.media.client.widgets.dashboard.handlers.HasDashletHandlers;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class DashBoardPresenter implements Presenter{
	private Display view;
	private HandlerManager eventBus;
	
	public interface Display extends IsWidget{
		Widget asWidget();
		HasClickHandlers getMapClick();
		HasDashletHandlers getMapDashletHandler();
		HasDashletHandlers getChartDashletHandler();
		HasDashMapHandlers getDashMapHandler();
	}
	
	public DashBoardPresenter(Display view, HandlerManager eventBus){
		this.view = view;
		this.eventBus = eventBus;
		bind();
	}

	private void bind(){
		view.getMapDashletHandler().setDashletHandler(new DashletHandler() {
			
			@Override
			public void onExpandClicked(String module) {
				GWT.log("Map expand clicked !!!");
			}
			
			@Override
			public void onCancelClicked(String module) {
				GWT.log("Map cancelled clicked !!!");
			}
		});
		
		view.getChartDashletHandler().setDashletHandler(new DashletHandler() {
			
			@Override
			public void onExpandClicked(String module) {
				GWT.log("Chart expand clicked !!!");
			}
			
			@Override
			public void onCancelClicked(String module) {
				GWT.log("Chart cancelled clicked !!!");
			}
		});
		
		view.getDashMapHandler().setDashMapHandler(new DashMapHandler() {
			
			@Override
			public void onMapPointClicked(String lat, String lon) {
				GWT.log("Latitude is "+lat+" and lon is "+lon);
			}
		});
	}
	
	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		screen.add(view.asWidget());
	}
}
