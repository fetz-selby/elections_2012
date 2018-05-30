package com.beta.election.media.client.widgets;

import java.util.HashMap;

import com.google.gwt.maps.client.geom.LatLng;

public interface MapSearchWidgetHandler {
  void onSystemPointsChanged(String lat, String lon);
  void onGooglePointsFound(String lat, String lon);
  void onLocationsFound(HashMap<LatLng, String> pointsAndLocations);
  void onNewLocationEntered();
}
