package com.beta.election.media.client.utils;

import com.beta.election.media.client.widgets.ChartCustomLegendWidget;
import com.google.gwt.user.client.ui.FlowPanel;

public class ChartCustomLegend extends FlowPanel {

  public ChartCustomLegend() {
    setSize("100%", "auto");
    getElement().setAttribute("style", "padding-top:1px;");
  }
  
  public void addLegend(String label, String color){
    ChartCustomLegendWidget widget = new ChartCustomLegendWidget(color, label);
    add(widget);
  }
  
  public void clearLegend(){
	  super.clear();
  }
  
  public FlowPanel getLegend(){
    return this;
  }

}
