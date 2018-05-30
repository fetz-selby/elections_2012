package com.beta.election.media.client.widgets.dashboard;

import java.util.ArrayList;

import com.beta.election.media.client.constants.ModuleConstants;
import com.beta.election.media.client.constants.RegionConstants;
import com.beta.election.media.client.widgets.MapsInputWidget;
import com.beta.election.media.client.widgets.MapsInputWidgetHandler;
import com.beta.election.media.client.widgets.dashboard.handlers.DashMapHandler;
import com.beta.election.media.client.widgets.dashboard.handlers.HasDashMapHandlers;

public class DashMap extends Dashlet implements HasDashMapHandlers{

	private DashMapHandler handler;
	
	public DashMap() {
		super("Regions", ModuleConstants.DASH_MAP);
		initComponent();
	}
	
	private void initComponent(){
		MapsInputWidget map = new MapsInputWidget();
		
		ArrayList<String> regions = new ArrayList<String>();
		regions.add(RegionConstants.ASHANTI);
		regions.add(RegionConstants.BRONG_AHAFO);
		regions.add(RegionConstants.CENTRAL);
		regions.add(RegionConstants.EASTERN);
		regions.add(RegionConstants.GREATER_ACCRA);
		regions.add(RegionConstants.NORTHERN);
		regions.add(RegionConstants.UPPER_EAST);
		regions.add(RegionConstants.UPPER_WEST);
		regions.add(RegionConstants.VOLTA);
		regions.add(RegionConstants.WESTERN);
		
		map.setCordList(regions);
		map.setZoom(6);
		map.setMapSize("100%", "320px");
		setContent(map);
		map.load();
		map.addMapsInputWidgetHandler(new MapsInputWidgetHandler() {
			
			@Override
			public void onMarkerClicked(String lat, String lon) {
				if(handler != null){
					handler.onMapPointClicked(lat, lon);
				}
			}
		});
	}

	@Override
	public void setDashMapHandler(DashMapHandler handler) {
		this.handler = handler;
	}

}
