package com.beta.election.media.client.utils;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;

public class JQPlotPie extends SimplePanel {
  private String title = "", id;
  private ArrayList<String> names;
  private ArrayList<Double> values;

  public JQPlotPie() {
    super.getElement().setAttribute("id", "container");
  }

  public JQPlotPie(String id) {
	super.getElement().setAttribute("id", id);
	this.id = id;
  }

  /**
   * Add a value to be plotted on the pie chart
   * @param value
   */

  public void addValue(double value) {
    if (values != null) {
      values.add(value);
    } else {
      values = new ArrayList<Double>();
      values.add(value);
    }
  }

  /**
   * Add a name to be associated with the value and as well as a legend.
   * @param name
   */

  public void addName(String name) {
    if (names != null) {
      names.add(name);
    } else {
      names = new ArrayList<String>();
      names.add(name);
    }
  }

  /**
   * Set the names to be shown on the pie. Note, all initially added names shall
   * be lost
   * @param names
   */
  public void setNames(ArrayList<String> names) {
    if (names != null) {
      this.names = names;
    }
  }

  /**
   * Set the values to be plotted on pie. Note, all initially added values shall
   * be lost
   * @param values
   */

  public void setValues(ArrayList<Double> values) {
    if (values != null) {
      this.values = values;
    }
  }

  // Java types are converted to native js types
  // for plotting
  private void doNativeConversion() {
    if (names.size() == values.size()) {
      JSFactory factory = new JSFactory();

      JsArray<JsArrayMixed> bigArray = factory.getMixArrayOfArray();
      for (int i = 0; i < names.size(); i++) {
        JsArrayMixed tmpArray = factory.getMixedArray();

        tmpArray.push(names.get(i));
        tmpArray.push(values.get(i));

        bigArray.push(tmpArray);
      }

      plotPie(bigArray, id);
    } else {
      GWT.log("Data pair error");
    }

  }

  // Reset's the collections
  private void reset() {
    names.clear();
    values.clear();
  }

  @SuppressWarnings("deprecation") public void load() {
    DeferredCommand.add(new Command() {

      @Override public void execute() {
        doNativeConversion();
        reset();
      }
    });
  }

  private native void plotPie(JsArray<JsArrayMixed> plotValues, String id)/*-{    
  			var local_id = 'container';
  			
  			if(id != null){
  				local_id = id;
  			}
  	
                                                               var plot8 = $wnd.$.jqplot(local_id, [plotValues], {
                                                               animate: !$wnd.$.jqplot.use_excanvas,
                                                               grid: {
                                                               drawBorder: false,
                                                               drawGridlines: false,
                                                               background: 'transparent',
                                                               shadow:false
                                                               },
                                                               cursor: {
                                                               style: 'default',
                                                               zoom : true,
                                                               dblClickReset: true,
                                                               clickReset : true,
                                                               looseZoom : true,
                                                               showTooltip: false,
                                                               tooltipOffset: 6,
                                                               showTooltipGridPosition: false,
                                                               showTooltipUnitPosition: false,
                                                               tooltipFormatString: '%.4P',
                                                               useAxesFormatters: true,
                                                               tooltipAxesGroups: [],
                                                               },
                                                               seriesDefaults:{
                                                               renderer:$wnd.$.jqplot.PieRenderer,
                                                               highlightMouseOver: true,
                                                               highlightMouseDown: true,
                                                               rendererOptions: {
                                                               showDataLabels: true
                                                               }
                                                               },
                                                               legend: {
                                                               show: true,
                                                               location: 'e',
                                                               }
                                                               }); 
                                                               }-*/;

}
