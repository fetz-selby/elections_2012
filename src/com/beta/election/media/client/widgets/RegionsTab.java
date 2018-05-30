package com.beta.election.media.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RegionsTab extends Composite {

	private RegionTabEventHandler handler;
	private static int anchorCounter = 0;
	private static RegionsTabUiBinder uiBinder = GWT
			.create(RegionsTabUiBinder.class);

	interface RegionsTabUiBinder extends UiBinder<Widget, RegionsTab> {
	}

	interface RegionTabEventHandler{
		void onTabClicked(String name, String value);
	}
	
	interface NewStyle extends CssResource{
		String liStyle();
		String aStyle();
	}
	
	@UiField UListElement ulElement;
	@UiField NewStyle newStyle;
	
	public RegionsTab() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void addTab(String name, String value){
		Element li = getLIElement();
		Element a = getAnchorElement();
		
		a.setInnerText(name);
		a.setAttribute("value", value);
		
		li.appendChild(a);
		
		ulElement.appendChild(li);
	}
	
	private void doNativeEventHandling(String name, String value){
		if(handler != null){
			handler.onTabClicked(name, value);
		}
	}
	
	private Element getAnchorElement(){
		Element a = DOM.createElement("a");
		a.setClassName(newStyle.aStyle());
		a.setId("tab_"+anchorCounter);
		doEventLoading("tab_"+anchorCounter);
		
		anchorCounter ++;
		return a;
	}
	
	private Element getLIElement(){
		Element li = DOM.createElement("li");
		li.setClassName(newStyle.liStyle());
		
		return li;
	}
	
	private void doEventLoading(final String id){
		DeferredCommand.add(new Command() {
			
			@Override
			public void execute() {
				doNativeEvent(id);
			}
		});
	}
	
	public void setRegionTabEventHandler(RegionTabEventHandler handler){
		this.handler = handler;
	}
	
	private native void doNativeEvent(String id)/*-{
		
		var app = this;
		$wnd.$("#"+id).click(function(){
			var value = $(this).attr("value");
			var name = $(this).text();
			
			app.@com.beta.election.media.client.widgets.RegionsTab::doNativeEventHandling(Ljava/lang/String;Ljava/lang/String;)(name,value);
		});
	}-*/;


}
