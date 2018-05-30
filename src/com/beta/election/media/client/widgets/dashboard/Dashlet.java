package com.beta.election.media.client.widgets.dashboard;

import com.beta.election.media.client.widgets.dashboard.handlers.DashletHandler;
import com.beta.election.media.client.widgets.dashboard.handlers.HasDashletHandlers;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class Dashlet extends Composite implements HasDashletHandlers{

	private static DashMapUiBinder uiBinder = GWT.create(DashMapUiBinder.class);
	private String module, dashTitle;
	private DashletHandler handler;
	
	interface DashMapUiBinder extends UiBinder<Widget, Dashlet> {
	}

	@UiField SimplePanel dashWidget;
	@UiField Image cancelButton, maximizeButton;
	@UiField DivElement dashletTitle;
	
	private Dashlet() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Dashlet(String title, String module){
		this();
		this.dashTitle = title;
		this.module = module;
		initListeners();
		initComponents();
	}
	
	private void initListeners(){
		cancelButton.sinkEvents(Event.ONCLICK);
		cancelButton.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onCancelClicked(module);
				}else{
					GWT.log("Cancel Handler is null");
				}
			}
		}, ClickEvent.getType());
		
		maximizeButton.sinkEvents(Event.ONCLICK);
		maximizeButton.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onExpandClicked(module);
				}else{
					GWT.log("Expand Handler is null");
				}
			}
		}, ClickEvent.getType());
	}
	
	private void initComponents(){
		//Image cancelImg = new Image("images/cancel.png");
		//Image expandImg = new Image("images/expand.png");
		
		cancelButton.setUrl("images/cancel.png");
		maximizeButton.setUrl("images/expand.png");
		
		dashletTitle.setInnerText(dashTitle);
	}
	
	public void setContent(Widget widget){
		dashWidget.setWidget(widget);
	}
	
	public void setDashTitle(String dashTitle){
		this.dashTitle = dashTitle;
		dashletTitle.setInnerText(dashTitle);
	}

	@Override
	public void setDashletHandler(DashletHandler handler) {
		this.handler = handler;
	}

}
