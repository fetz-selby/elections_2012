package com.beta.election.media.client.utils;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.SimplePanel;

public class JQPlotLine extends SimplePanel {
  private String title = "", yaxisLabel, xaxisLabel;
  private ArrayList<String> legendNames, names;
  private ArrayList<ArrayList<Double>> values;

  public JQPlotLine() {
    super.getElement().setAttribute("id", "container");
  }

  public JQPlotLine(String title) {
    this();
    this.title = title;
  }

  @SuppressWarnings("deprecation") public void load() {
    DeferredCommand.add(new Command() {

      @Override public void execute() {
        doNativeConversion();
        reset();
      }
    });
  }

  /**
   * Add a name to represent a list of values for a line. eg name => [1.0, 4.0,
   * 2.9. 2.0]
   * @param name
   */

  public void addLegendName(String name) {
    if (legendNames != null) {
      legendNames.add(name);
    } else {
      legendNames = new ArrayList<String>();
      legendNames.add(name);
    }
  }

  /**
   * Set the names to represent each line values that are to be plotted or
   * plotted. Names are assigned according to the indexes in the list
   * @param names
   */

  public void setLegendNames(ArrayList<String> names) {
    if (names != null) {
      this.legendNames = names;
    }
  }

  /**
   * Add a list of {@link Double}'s that represents a plotting values for line
   * graph. eg. [1.0, 2.0, 4.1, 6.8]
   * @param value
   */

  public void addValue(ArrayList<Double> value) {
    if (values != null) {
      values.add(value);
    } else {
      values = new ArrayList<ArrayList<Double>>();
      values.add(value);
    }
  }

  public void addXName(String xname) {
    if (names != null) {
      names.add(xname);
    } else {
      names = new ArrayList<String>();
      names.add(xname);
    }
  }

  public void setXNames(ArrayList<String> names) {
    if (names != null) {
      this.names = names;
    }
  }

  /**
   * Set the values to plot the line graph. Note initially added ones shall be
   * lost from the list. This list will look similiarly as [[3.0, 2.1, 1.0,
   * 5.2],[2.6, 1.0, 0.3, 1.4],[2.1, 3.0, 2.1, 0.4]]
   * @param values
   */

  public void setValues(ArrayList<ArrayList<Double>> values) {
    if (values != null) {
      this.values = values;
    }
  }

  /**
   * Sets the ChartTitle
   * @param title
   */

  public void setChartTitle(String title) {
    this.title = title;
  }

  /**
   * Set the label name that will be shown beside the y-axis
   * @param yaxisLabel
   */

  public void setYAxisLabel(String yaxisLabel) {
    this.yaxisLabel = yaxisLabel;
  }

  /**
   * Set the label name that will be shown below the x-axis
   * @param xaxisLabel
   */

  public void setXAxisLabel(String xaxisLabel) {
    this.xaxisLabel = xaxisLabel;
  }

  // Java to native javascript conversion starts
  // from here.

  private String getSlant(String text) {
    return "<html><body><span style=\"padding-top: 10px; padding-right: 13px; padding-bottom: 21px;transform:rotate(-60deg);-moz-transform: rotate(-60deg); -webkit-transform: rotate(-60deg); -ms-transform: rotate(-60deg); -o-transform: rotate(-60deg)\">" + text + "</span></body></html>";
  }

  private void doNativeConversion() {
    JSFactory jsFactory = new JSFactory();

    // Converting the list names to a native js strings
    // and store it in a js string array.
    JsArrayString jsLegendNames = null;
    if (legendNames != null && legendNames.size() > 0) {
      jsLegendNames = jsFactory.getStringJsArray();
      for (int i = 0; i < legendNames.size(); i++) {
        jsLegendNames.push(legendNames.get(i));
      }
    } else {
      GWT.log("Legends are null or empty");
    }


    // Converting the x label names to js implementation
    JsArrayString jsNames = null;
    if (names != null && names.size() > 0) {
      jsNames = jsFactory.getStringJsArray();
      for (int i = 0; i < names.size(); i++) {
        jsNames.push(getSlant(names.get(i)));
      }
    } else {
      GWT.log("Names are null or empty");
    }


    // Converting the list values of doubles to native js number
    // and store it in a js number array
    JsArray<JsArrayNumber> jsValues = null;
    if (values != null && values.size() > 0) {
      jsValues = jsFactory.getNumArrayOfArray();
      for (int i = 0; i < values.size(); i++) {
        JsArrayNumber jsValue = jsFactory.getNumberJsArray();

        for (Double value : values.get(i)) {
          jsValue.push(value);
        }

        jsValues.push(jsValue);
      }
    } else {
      GWT.log("Values are null or empty");
    }

    if (jsValues != null && jsLegendNames != null && jsNames != null) {
      plotLine(jsValues, jsLegendNames, jsNames, title, xaxisLabel, yaxisLabel);
    } else {
      GWT.log("Null values not allowed for plotting");
    }

  }

  // Reset's the collection
  private void reset() {
    legendNames.clear();
    names.clear();
    values.clear();
  }

  // private native void plotLine()/*-{
  private native void plotLine(JsArray<JsArrayNumber> values, JsArrayString legend, JsArrayString xlabels, String title, String xLabel, String yLabel)/*-{
                                                                                                                                                      //var plot2 = $wnd.$.jqplot ('container', [[3,7,9,1,5,3,8,2,5]], {
                                                                                                                                                      var plot2 = $wnd.$.jqplot('container', values, {
                                                                                                                                                      title : title,
                                                                                                                                                      animate : !$wnd.$.jqplot.use_excanvas,
                                                                                                                                                      axesDefaults : {
                                                                                                                                                      labelRenderer : $wnd.$.jqplot.CanvasAxisLabelRenderer,
                                                                                                                                                      },

                                                                                                                                                      seriesDefaults : {
                                                                                                                                                      rendererOptions : {
                                                                                                                                                      smooth : true
                                                                                                                                                      }
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
                                                                                                                                                      legend : {
                                                                                                                                                      show : true,
                                                                                                                                                      location : 'e',
                                                                                                                                                      placement : 'outside',
                                                                                                                                                      labels : legend
                                                                                                                                                      },
                                                                                                                                                      //title:{
                                                                                                                                                      //        text: xlabels
                                                                                                                                                      //},
                                                                                                                                                      
                                                                                                                                                      axes : {
                                                                                                                                                      xaxis : {
                                                                                                                                                      renderer : $wnd.$.jqplot.CategoryAxisRenderer,
                                                                                                                                                      label : xLabel,
                                                                                                                                                      ticks : xlabels,
                                                                                                                                                      min: 0.0
                                                                                                                                                      },
                                                                                                                                                      yaxis : {
                                                                                                                                                      //renderer : $wnd.$.jqplot.CategoryAxisRenderer,
                                                                                                                                                      //label : yLabel
                                                                                                                                                      }
                                                                                                                                                      }
                                                                                                                                                      });
                                                                                                                                                      }-*/;

}
