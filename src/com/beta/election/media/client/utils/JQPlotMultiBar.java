package com.beta.election.media.client.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.SimplePanel;

public class JQPlotMultiBar extends SimplePanel{
  
  
  //values to be plotted  on the bar
  private HashMap<String, ArrayList<Double>> plotValues;
  
  //values on the x-axis
  private ArrayList<String> xLabels, colors;
  
  //title of your Chart
  private String title = "";
  
  
  public JQPlotMultiBar(){
    super.getElement().setAttribute("id", "container");
    super.getElement().setAttribute("style", "font-size: 19px;color: white;font-weight: bold");
  }
  
  /**
   * Initialize the object and set the title of the Multiple Bar Chart
   * @param title
   */
  public JQPlotMultiBar(String title){
    this();
    this.title = title;
  }
  
  /**
   * Execution starts
   */
  
  @SuppressWarnings("deprecation")
  public void load(){
    DeferredCommand.add(new Command() {
      
      @Override
      public void execute() {
        doNativeConversion();
        reset();
      }
    });
  }
  
  /**
   * Add a xValue or name to be shown on the x-axis
   * @param xValue
   */
  public void addXValue(String xValue){
    if(xValue != null){
      if(xLabels != null){
        xLabels.add(xValue);
      }else{
        xLabels = new ArrayList<String>();
        xLabels.add(xValue);
      }
      
    }
  }
  
  /**
   * xValues represent the labels to be shown on the x-axis
   * eg. days, weeks, months, etc.
   * @param xValues
   * {@link ArrayList}
   */
  public void setXValues(ArrayList<String> xValues){
    if(xValues != null){
      xLabels = xValues;
    }
  }
  
  
  public void setColors(ArrayList<String> colors){
	  this.colors = colors;
  }
  
  /**
   * Clear all the labels on the x-axis and the plotted values
   */
  private void reset(){
    plotValues.clear();
    xLabels.clear();
  }
  
  /**
   * plotValue is a {@link HashMap} that takes a {@link String}
   * as the key and an {@link ArrayList} as the value.
   * eg. the key can be a name of a location, commodity or anything
   * and the values are a list of {@link Double}'s, each value contained
   * in the list is a representation of the of the xValues. eg. if list is
   * [2.3, 3.4, 5.0, 2.1] and xValues is [2001, 2002, 2003, 2004]. means 2.3=>2001, 
   * 3.4=>2002, 5.0=>2003 and so on ..
   * @param plotValue {@link HashMap}
   */
  public void setPlotMap(HashMap<String, ArrayList<Double>> plotValue){
    if(plotValue != null){
     this.plotValues = plotValue;
    }
  }
  
  public void setBarTitle(String title){
    this.title = title;
  }
  
  
  //Converts all Java data-types to native 
  //Java-script types. 
  
  private void doNativeConversion(){
    if(plotValues != null && xLabels != null){

      JSFactory factory = new JSFactory();
      JsArrayString jsXValues = factory.getStringJsArray();
      JsArrayMixed jsYValues = factory.getMixedArray();
      JsArrayString jsBarLabels = factory.getStringJsArray();
      JsArrayString jsBarColors = factory.getStringJsArray();
      

        //Grab the container of the plotted values, and converts them to
        //native js types.
        for(String name : plotValues.keySet()){
          jsBarLabels.push(name);
          ArrayList<Double> values = plotValues.get(name);
          
          //After here jsNumber may look similar to [1.0, 2.0, 3.0]
          JsArrayNumber jsNumber = factory.getNumberJsArray();
          for(Double value : values){
            jsNumber.push(value);
          }
          jsYValues.push(jsNumber);
        }
      
      //After here, the jsXValues may look similar to ['a', 'b', 'c', 'd'];
      for(String label : xLabels){
        jsXValues.push(label);
      }
      
      //Setting the colors
//      for(String color : colors){
//    	jsBarColors.push(color);
//      }
      
      plotBar(jsYValues, jsXValues, jsBarLabels, title);

    }else{
      GWT.log("Values are null!!");
    }
  }
  
  
  
  private native void plotBar(JsArrayMixed plotValues, JsArrayString xValues, JsArrayString barLabels, String title)/*-{
        plot2 = $wnd.$.jqplot('container', plotValues, {
            animate : !$wnd.$.jqplot.use_excanvas,
            seriesDefaults: {
                renderer:$wnd.$.jqplot.BarRenderer,
                pointLabels: { show: true },
            },
            
            grid:{
                borderWidth: 0.0
            },
            
            legend: {
                show: true,
                location: 'e',
                placement: 'outside',
                labels: barLabels
            },
            
            //seriesColors: ["#b82347", "#99b956", "#9556b8", "#95d3b8"],
            //seriesColors: colors,
            title: {
                text: title
            },
            
            axes: {
                xaxis: {
                    renderer: $wnd.$.jqplot.CategoryAxisRenderer,
                    ticks: xValues
                },
                yaxis: {
                    renderer: $wnd.$.jqplot.LinearAxisRenderer,
                    //pointLabels: { show: true }
                    
                }
            }
        
        });
  }-*/;
}
