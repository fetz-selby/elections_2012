package com.beta.election.media.client.widgets;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;

public class TabsPanel extends FlowPanel{
	private Element ul;
	private int counter = 1;
	private FlowPanel mainContentPanel;
	
	public TabsPanel(){
		getElement().setAttribute("id", "tabs");
		initElement();
	}
	
	private void initElement(){
		ul = DOM.createElement("ul");
		mainContentPanel = new FlowPanel();
	}
	
	private void constructHeader(String header){
		Element li = DOM.createElement("li");
		Element a = DOM.createAnchor();
		
		a.setAttribute("href", "#tabs-"+counter);
		a.setInnerText(header);

		li.appendChild(a);
		ul.appendChild(li);
		
	}
	
	private void constructContent(IsWidget widget){
		FlowPanel contentPanel = new FlowPanel();
		contentPanel.getElement().setAttribute("id", "tabs-"+counter);
		
		contentPanel.add(widget);
		mainContentPanel.add(contentPanel);
	}
	
	public void addTab(String header, IsWidget widget){
		constructHeader(header);
		constructContent(widget);
		
		counter ++;
	}
	
	private void finalizeConstruction(){
		HTML ulString = new HTML(ul.getString());
		add(ulString);
		
		add(mainContentPanel);
	}
	
	@SuppressWarnings("deprecation")
	public void load(){
		finalizeConstruction();
		DeferredCommand.add(new Command() {
			@Override
			public void execute() {
				doNative();
			}
		});
	}
	
	private native void doNative()/*-{
		$wnd.$( "#tabs" ).tabs();
	}-*/;

}
