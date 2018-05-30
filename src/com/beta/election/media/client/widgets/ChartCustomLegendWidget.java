package com.beta.election.media.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ChartCustomLegendWidget extends Composite {

  private static ChartCustomLegendWidgetUiBinder uiBinder = GWT.create(ChartCustomLegendWidgetUiBinder.class);

  interface ChartCustomLegendWidgetUiBinder extends UiBinder<Widget, ChartCustomLegendWidget> {
  }

  @UiField DivElement label, color;
  
  public ChartCustomLegendWidget(String color, String label) {
    initWidget(uiBinder.createAndBindUi(this));
    this.label.setInnerText(label);
    this.color.setAttribute("style", "background-color:"+color);
  }

}
