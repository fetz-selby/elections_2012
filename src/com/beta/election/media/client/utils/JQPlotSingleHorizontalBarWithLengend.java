package com.beta.election.media.client.utils;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class JQPlotSingleHorizontalBarWithLengend extends Composite {
	private String title = "", xLabel = "", yLabel = "";
	private ArrayList<String> names, colors;
	private ArrayList<Double> values;
	private ChartCustomLegend legendCreator;

	
	private static JQPlotSingleHorizontalBarWithLengendUiBinder uiBinder = GWT
			.create(JQPlotSingleHorizontalBarWithLengendUiBinder.class);

	interface JQPlotSingleHorizontalBarWithLengendUiBinder extends
			UiBinder<Widget, JQPlotSingleHorizontalBarWithLengend> {
	}

	@UiField SimplePanel legend, chart;
	
	public JQPlotSingleHorizontalBarWithLengend() {
		initWidget(uiBinder.createAndBindUi(this));
		chart.getElement().setAttribute("id", "horizontalchart");
	}

	public JQPlotSingleHorizontalBarWithLengend(String title) {
		this();
		this.title = title;
	}

	@SuppressWarnings("deprecation")
	public void load() {
		DeferredCommand.add(new Command() {

			@Override
			public void execute() {
				doNativeConversion();
				doLegendShowing();
				reset();
			}
		});
	}

	public void addName(String name) {
		if (names != null) {
			names.add(name);
		} else {
			names = new ArrayList<String>();
			names.add(name);
		}
	}

	public void addValue(double value) {
		if (values != null) {
			values.add(value);
		} else {
			values = new ArrayList<Double>();
			values.add(value);
		}
	}
	
	public void addColor(String color){
		if (colors != null) {
			colors.add(color);
		} else {
			colors = new ArrayList<String>();
			colors.add(color);
		}
	}

	public void setXLabel(String xlabel) {
		this.xLabel = xlabel;
	}

	public void setYLabel(String ylabel) {
		this.yLabel = ylabel;
	}

	public void setValues(ArrayList<Double> values) {
		if (values != null) {
			this.values = values;
		}
	}

	public void setNames(ArrayList<String> names) {
		if (names != null) {
			this.names = names;
		}
	}

	private void doNativeConversion() {
		//final int MAX = 10;
		JSFactory factory = new JSFactory();

		if (names.size() == 1 || values.size() == 1) {
			// Get the values

			String tmpName = names.get(0);
			String tmpColor = colors.get(0);
			double tmpValue = values.get(0);

			// Clear the list
			names.clear();
			values.clear();
			colors.clear();

			// Add dummy date before and after the real values
			names.add("");
			names.add(tmpName);
			names.add("");

			values.add(0.0);
			values.add(tmpValue);
			values.add(0.0);
			
			colors.add("#FFFFFF");
			colors.add(tmpColor);
			colors.add("#FFFFFF");
		}

		if (names.size() == values.size()) {
			legendCreator = new ChartCustomLegend();
			JsArrayNumber jsValues = factory.getNumberJsArray();
			JsArrayString jsNames = factory.getStringJsArray();
			JsArrayString jsColors = factory.getStringJsArray();


			for (int i = 0; i < names.size(); i++) {
				jsNames.push(names.get(i));
				jsValues.push(values.get(i));
				jsColors.push(colors.get(i));
				
				legendCreator.addLegend(names.get(i), colors.get(i));
				GWT.log("************************");
				GWT.log("Name is "+names.get(i)+" value is "+values.get(i)+" color is "+colors.get(i));
				GWT.log("************************");
			}

			//plotBar(jsValues, jsNames, jsColors, xLabel, yLabel);
			doNativeRendering(jsValues, jsNames, jsColors, xLabel, yLabel);

		} else {
			GWT.log("Values and Names pair do not match");
		}
	}
	
	private void doLegendShowing(){
		legend.setWidget(legendCreator);
	}

//	private String getSlant(String text) {
//		return "<html><body><span style=\"padding-top: 10px; padding-right: 13px; padding-bottom: 21px;transform:rotate(-60deg); -moz-transform: rotate(-60deg); -webkit-transform: rotate(-60deg); -ms-transform: rotate(-60deg); -o-transform: rotate(-60deg)\">"
//				+ text + "</span></body></html>";
//	}

	// Reset's the collection
	private void reset() {
		names.clear();
		values.clear();
		colors.clear();
	}

	private native void hideAxisLines()/*-{
		$wnd.$('jqplot-grid-canvas').hide();
	}-*/;

	
	private native void plotBar(JsArrayNumber values, JsArrayString names, JsArrayString colors, 
			String xLabel, String yLabel)/*-{
		$wnd.$.jqplot.config.enablePlugins = true;

		plot1 = $wnd.$.jqplot('horizontalchart', [ values ], {
			// Only animate if we're not using excanvas (not in IE 7 or IE 8)..
			animate : !$wnd.$.jqplot.use_excanvas,
			seriesColors : colors,
			seriesDefaults : {
				renderer : $wnd.$.jqplot.BarRenderer,
				//markerRenderer : $wnd.$.jqplot.MarkerRenderer,
				rendererOptions:{
					varyBarColor: true,
					//barDirection: 'horizontal',
					barPadding: 15
								},
//				grid : {
//					drawBorder : false,
//					drawGridlines : false,
//					background : 'transparent',
//					shadow : false
//				},
				pointLabels : {
					show : false,
					//formatter : $wnd.$.jqplot.DefaultTickFormatter,
					//formatString : '%d'
				}
			},
			
			axes : {
//				xaxis : {
//					//renderer : $wnd.$.jqplot.CategoryAxisRenderer,
//					//ticks : names,
//					//label : xLabel,
//				},
				yaxis : {
					renderer: $wnd.$.jqplot.CategoryAxisRenderer,
					//ticks : names,
					showTicks: true,
				}
			},
			axesDefaults:{
				tickRenderer: $wnd.$.jqplot.CanvasAxisTickRenderer,
				tickOptions :{
					angle : -50,
					fontSize: '17pt',
					textColor: 'white',
					showGridline: false,
					enableFontSupport: true,
				}
			},
			grid: {
					drawGridLines: false,
					gridLineColor: 'transparent',
					background: 'transparent',
					borderColor: 'transparent',
					borderWidth: 0.0,
				},
			
		});
		$wnd.$('#horizontalchart>.jqplot-grid-canvas').hide();
		$wnd.$('#horizontalchart>.jqplot-xaxis').hide();
		$wnd.$('#horizontalchart>.jqplot-yaxis').hide();
		
	}-*/;

	
	private native void doNativeRendering(JsArrayNumber values, JsArrayString names, JsArrayString colors, String xLabel, String yLabel)/*-{
		$wnd.$(document).ready(function(){
        $wnd.$.jqplot.config.enablePlugins = true;
         
        plot1 = $wnd.$.jqplot('horizontalchart', [values], {
            // Only animate if we're not using excanvas (not in IE 7 or IE 8)..
            animate: $wnd.$.jqplot.use_excanvas,
            seriesColors : colors,
            seriesDefaults:{
                renderer:$wnd.$.jqplot.BarRenderer,
                rendererOptions:{
					varyBarColor: true,
					//barDirection: 'horizontal',
					barPadding: 3,
					barWidth: 15,
					//groups: 4,
					//barMargin: 5
								},
                pointLabels: { show: false }
            },
            axes: {
                xaxis: {
                    renderer: $wnd.$.jqplot.CategoryAxisRenderer,
                    ticks: names
                }
            },
            highlighter: { show: false }
        });
     
    });
		$wnd.$('#horizontalchart>.jqplot-grid-canvas').hide();
		$wnd.$('#horizontalchart>.jqplot-xaxis').hide();
		$wnd.$('#horizontalchart>.jqplot-yaxis').hide();
	}-*/;
 
}
