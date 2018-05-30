package com.beta.election.media.client.widgets;

import java.util.HashMap;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;

public class AccordionPanel extends FlowPanel {
	private int index = 0;
	private HashMap<Integer, Integer> indexMap;
	
	public AccordionPanel() {
		getElement().setAttribute("id", "accordion");
		getElement().setAttribute("style", "font-size: 76.5%;");
		indexMap = new HashMap<Integer, Integer>();
	}
	
	private void constructHeader(int id, String headerName){
		HTML html = new HTML("<h3><a href=\"#\">"+headerName+"</a></h3>");
		add(html);
		indexMap.put(id, index);
		index ++;
	}
	
	private void constructContent(IsWidget widget){
		FlowPanel panel = new FlowPanel();
		panel.add(widget);
		
		add(panel);
	}
	
	public void addSection(int id, String header, IsWidget widget){
		constructHeader(id, header);
		constructContent(widget);
	}
	
	@SuppressWarnings("deprecation")
	public void load(){
		DeferredCommand.add(new Command() {
			
			@Override
			public void execute() {
				doNativeLoad();
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public void showActive(final int id){
		if(indexMap != null){
			if(indexMap.get(id) > 0){
				DeferredCommand.add(new Command() {
					
					@Override
					public void execute() {
						doNativeShowActive(indexMap.get(id));
					}
				});
			}
		}
	}
	
	private native void doNativeShowActive(int index)/*-{
		$wnd.$( "#accordion" ).accordion( "option", "active", index);
	}-*/;

	
	private native void doNativeLoad()/*-{
		$wnd.$('#accordion').accordion();
	}-*/;

}
