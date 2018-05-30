package com.beta.election.media.client.widgets;

import java.util.ArrayList;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.SimplePanel;

public class MapsInputWidget extends SimplePanel implements HasMapsInputWidgetHandler{

	private MapsInputWidgetHandler handler;
	private ArrayList<String> cordList;
	private String width = "767px", height = "582px";
	private int zoom = 7;
	
	public ArrayList<String> getCordList() {
		return cordList;
	}

	public void setCordList(ArrayList<String> cordList) {
		this.cordList = cordList;
	}
	
	public void setMapSize(String width, String height){
		this.width = width;
		this.height = height;
	}
	
	public void setZoom(int zoom){
		this.zoom  = zoom;
	}

	public void load(){
		Maps.loadMapsApi("", "2", false, new Runnable() {
	         public void run() {
	        	 if(cordList != null){
		        	 initMap(cordList);
	        	 }
	         }
	    });
	}
	
	private void initMap(ArrayList<String> coordinates){
		 MapWidget map = getMap();
		 
		 for(String location : coordinates){
			 //The location is comma separated
			 //LatLng point = LatLng.newInstance(Double.parseDouble(location.split("[\\s]+")[0]), Double.parseDouble(location.split("[\\s]+")[1]));
			 LatLng point = LatLng.newInstance(Double.valueOf(location.split("[\\s]+")[0]), Double.valueOf(location.split("[\\s]+")[1]));

			 MarkerOptions options = getMarkerOptions();
			 options.setIcon(getIcon());
			 
			 Marker marker = new Marker(point, options);
			 marker.addMarkerClickHandler(new MarkerClickHandler() {
				
				@Override
				public void onClick(MarkerClickEvent event) {
					if(handler != null){
						handler.onMarkerClicked(""+event.getSender().getLatLng().getLatitude(), ""+event.getSender().getLatLng().getLongitude());
					}
				}
			});
			 
			 //marker.addMarkerInfoWindowOpenHandler(handler)
			 map.addOverlay(marker);
			 setWidget(map);
		 }
		 
	}
	
	private MarkerOptions getMarkerOptions() {
		final MarkerOptions markerOption = MarkerOptions.newInstance();
		markerOption.setDraggable(false);
		markerOption.setBouncy(true);
		markerOption.setDragCrossMove(false);

		return markerOption;
	}
	
	private Icon getIcon(){
	    Icon icon = Icon.newInstance("images/green-marker.png");
	    icon.setShadowURL("images/green-marker-shadow.png");
	    icon.setIconSize(Size.newInstance(20, 28));
	    icon.setShadowSize(Size.newInstance(28, 26));
	    icon.setIconAnchor(Point.newInstance(6, 20));
	    
	    return icon;
	  }
	
	private MapWidget getMap(){
	    final MapWidget map = new MapWidget();
	    map.setCenter(LatLng.newInstance(8.0, -0.7));
	    map.setSize(width, height);
	    map.addControl(new LargeMapControl());
	    map.setZoomLevel(zoom);
	    map.setDraggable(false);
	    map.setContinuousZoom(true);
	    map.setScrollWheelZoomEnabled(true);
	    
	    return map;
	  }

	@Override
	public void addMapsInputWidgetHandler(MapsInputWidgetHandler handler) {
		this.handler = handler;
	}
}
