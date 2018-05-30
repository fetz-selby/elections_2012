package com.beta.election.media.client.widgets;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MapDoubleClickHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.event.MarkerMouseOverHandler;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MapSearchWidget extends Composite implements LocationCallback, HasMapSearchWidgetHandlers{
  private Geocoder geo;
  private MapSearchWidgetHandler handler;
  private LatLng centerSetter;
  private String sysLocation = "";
  private LatLng sysPoint;
  private HashMap<LatLng, String> coordinates;
  private static MapSearchUiBinder uiBinder = GWT.create(MapSearchUiBinder.class);
  private HasWidgets mapContainer;
  private int zoom = 7;
  private double sysLat = 0, sysLon = 0;
  private Marker systemMarker;
  private MapWidget map;
  private String locationId = "";
  private boolean flag = false;
  
  interface MapSearchUiBinder extends UiBinder<Widget, MapSearchWidget> {
  }

  @UiField SimplePanel widgetContainer;
  
  public MapSearchWidget() {
    initWidget(uiBinder.createAndBindUi(this));
    initMap();
    init();
  }
  
  public MapSearchWidget(HasWidgets container) {
    this();
    mapContainer = container;
    initMap();
    init();
  }
  
  private void init(){
   
 
    //widgetContainer.add(suggestBox);
  }
  
  private void initMap(){
    Maps.loadMapsApi("", "2", false, new Runnable() {
         public void run() {
           geo = new Geocoder();
         }
    });
  }
  
  private void fetchLocation(String locationName){
    geo.getLocations(locationName, this);
  }
  
  private void doLocationAnalysis(JsArray<Placemark> locations){
    coordinates = new HashMap<LatLng, String>();
    
    for(int i = 0; i < locations.length(); i++){
      Placemark pm = locations.get(i);
      LatLng point = pm.getPoint();
      String locationName = pm.getAddress()+" ("+pm.getState()+") "+", "+pm.getCountry();
      
      coordinates.put(point, locationName);
      
      handler.onGooglePointsFound(""+pm.getPoint().getLatitude(), ""+pm.getPoint().getLongitude());

      if(i == 0){
        centerSetter = pm.getPoint();
      }
    }
  
    //Send the list of locations
    
    if(locations.length() > 1){
      handler.onGooglePointsFound("", "");
    }
    renderMap();
  }
  
  private void extractPoint(String gis){
    if(gis.contains(",") && (!gis.equals(""))){
      String[] point = gis.split(",");
      sysLon = Double.parseDouble(point[0].substring(1));
      sysLat = Double.parseDouble(point[1].substring(0, point[1].indexOf(")")));
      sysPoint = LatLng.newInstance(sysLat, sysLon);
      
      handler.onSystemPointsChanged(""+sysLat, ""+sysLon);
      
      flag = true;
    }
  }
  
  private Marker getSystemMarker(){
     LatLng systemLocation = LatLng.newInstance(sysLat, sysLon);
     MarkerOptions markerOption = getMarkerOptions();
     markerOption.setIcon(getIcon());
     Marker sysMarker = new Marker(systemLocation, markerOption);
     sysMarker.addMarkerDragEndHandler(new MarkerDragEndHandler() {
       
       @Override
       public void onDragEnd(MarkerDragEndEvent event) {
         LatLng point = event.getSender().getLatLng();
         
         handler.onSystemPointsChanged(""+point.getLatitude(), ""+point.getLongitude());

       }
     });
     
     sysMarker.addMarkerClickHandler(new MarkerClickHandler() {
       
       @Override
       public void onClick(MarkerClickEvent event) {
         Marker mark = event.getSender();
         
         handler.onSystemPointsChanged(""+mark.getLatLng().getLatitude(), ""+mark.getLatLng().getLongitude());

       }
     });
     
     return sysMarker;
  }
  
  private Icon getIcon(){
    Icon icon = Icon.newInstance("images/green-marker.png");
    icon.setShadowURL("images/green-marker-shadow.png");
    icon.setIconSize(Size.newInstance(20, 28));
    icon.setShadowSize(Size.newInstance(28, 26));
    icon.setIconAnchor(Point.newInstance(6, 20));
    
    return icon;
  }
  
  private MarkerOptions getMarkerOptions(){
    final MarkerOptions markerOption = MarkerOptions.newInstance();
    markerOption.setDraggable(true);
    markerOption.setBouncy(true);
    markerOption.setDragCrossMove(true);
    
    return markerOption;
  }
  
  private MapWidget getMap(){
    final MapWidget map = new MapWidget();
    map.setCenter(centerSetter);
    map.setSize("100%", "100%");
    map.addControl(new LargeMapControl());
    map.setZoomLevel(zoom);
    map.setDraggable(true);
    map.setContinuousZoom(true);
    map.setScrollWheelZoomEnabled(true);
    
    return map;
  }
  
  private void renderMap(){
    map = getMap();
    
    for(LatLng point : coordinates.keySet()){
      final Marker marker = new Marker(point);
      
      marker.addMarkerMouseOverHandler(new MarkerMouseOverHandler() {
        
        @Override
        public void onMouseOver(MarkerMouseOverEvent event) {
          event.getSender().showMapBlowup();
        }
      });

      map.addOverlay(marker);
    }
    systemMarker = getSystemMarker();
    map.addOverlay(systemMarker);
    
    //Adding system's default location
    coordinates.put(sysPoint, sysLocation+" (Esoko)");
    
    handler.onLocationsFound(coordinates);
    showMap();
  }
  
  private void loadMapWithoutGoogle(){
    map = getMap();

    //Check if no configuration is found on the system
    if(flag){
      Marker marker = getSystemMarker();
      map.setCenter(LatLng.newInstance(sysLat, sysLon));
      map.addOverlay(marker);
      map.setZoomLevel(12);
      
      flag = false;
    }else{
      map.addMapDoubleClickHandler(new MapDoubleClickHandler() {
        
        @Override
        public void onDoubleClick(MapDoubleClickEvent event) {
          MarkerOptions options = getMarkerOptions();
          options.setIcon(getIcon());
          Marker marker = new Marker(event.getLatLng(), options);
          
          marker.addMarkerDragEndHandler(new MarkerDragEndHandler() {
            
            @Override
            public void onDragEnd(MarkerDragEndEvent event) {
              handler.onSystemPointsChanged(""+event.getSender().getLatLng().getLatitude(), ""+event.getSender().getLatLng().getLongitude());
            }
          });
          
          systemMarker = marker;
          map.addOverlay(marker);
        }
      });
    }
    
    showMap();
    handler.onGooglePointsFound("", "");
  }
  
  private void showMap(){
    if(mapContainer != null){
      mapContainer.clear();
      mapContainer.add(map);
    }
  }
  
  public void plotCoordinate(String lat, String lon){
    sysLat = Double.parseDouble(lat);
    sysLon = Double.parseDouble(lon);
    
    map.removeOverlay(systemMarker);
    systemMarker = getSystemMarker();
    map.addOverlay(systemMarker);
    handler.onSystemPointsChanged(""+sysLat, ""+sysLon);
  }


  @Override
  public void onFailure(int statusCode) {
    loadMapWithoutGoogle();
  }

  @Override
  public void onSuccess(JsArray<Placemark> locations) {

    if(locations != null){
      if(locations.length() == 1){
        zoom = 9;
      }
      doLocationAnalysis(locations);
    }    
  }

  public int getZoom() {
    return zoom;
  }

  public void setZoom(int zoom) {
    this.zoom = zoom;
  }
  
  public String getLocationId(){
    return locationId;
  }

  @Override
  public void addMapSearchHandlers(MapSearchWidgetHandler handler) {
    this.handler = handler;
  }

  @Override
  public void setMapContainer(HasWidgets container) {
    this.mapContainer = container;
  }
  
}
