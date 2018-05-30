package com.beta.election.media.client.map.presenter;

import java.util.ArrayList;
import java.util.HashMap;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.constants.RegionConstants;
import com.beta.election.media.client.events.MapToRegionChangeEvent;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.widgets.MapsInputWidget;
import com.beta.election.media.client.widgets.MapsInputWidgetHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class MapsPresenter implements Presenter{
	private final static String[] REGIONS_POINTS = {RegionConstants.ASHANTI,RegionConstants.BRONG_AHAFO,RegionConstants.CENTRAL,RegionConstants.EASTERN,RegionConstants.GREATER_ACCRA,RegionConstants.NORTHERN, RegionConstants.UPPER_EAST,RegionConstants.UPPER_WEST,RegionConstants.VOLTA,RegionConstants.WESTERN};
	private HashMap<String, Integer> regionsMap;
	private HandlerManager eventBus;
	private Display view;
	
	public interface Display extends IsWidget{
		Widget asWidget();
		void setMap(MapsInputWidget widget);
	}
	
	public MapsPresenter(Display view, HandlerManager eventBus){
		this.eventBus = eventBus;
		this.view = view;
	}
	
	private void bind(){
		initComponent();
	}
	
	private void initComponent(){
		MapsInputWidget map = new MapsInputWidget();
		
		ArrayList<String> regions = new ArrayList<String>();
		regionsMap = new HashMap<String, Integer>();
		
		for(int i = 0; i < REGIONS_POINTS.length; i++){
			regions.add(REGIONS_POINTS[i]);
			regionsMap.put(REGIONS_POINTS[i], i+1);
		}
		
		map.setCordList(regions);
		map.setZoom(7);
		map.setMapSize("780px", "588px");
		
		map.addMapsInputWidgetHandler(new MapsInputWidgetHandler() {
			
			@Override
			public void onMarkerClicked(String lat, String lon) {
				String point = lat+" "+lon;
				if(regionsMap.containsKey(point)){
					GWT.log("********* REGION ID IS "+regionsMap.get(point));
					SystemVariables.getSystemInstance().setRegionId(regionsMap.get(point));
					eventBus.fireEvent(new MapToRegionChangeEvent());
				}
			}
		});
		
		view.setMap(map);
		map.load();
	}


	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		bind();
		screen.add(view.asWidget());
	}
}
