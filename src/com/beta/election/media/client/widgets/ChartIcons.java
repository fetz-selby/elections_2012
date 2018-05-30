package com.beta.election.media.client.widgets;

import com.beta.election.media.client.constants.ModuleConstants;
import com.beta.election.media.client.widgets.handlers.HasChartIconsEventHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ChartIcons extends Composite implements HasChartIconsEventHandler{

	private ChartIconsEventHandler handler;
	private static ChartIconsUiBinder uiBinder = GWT
			.create(ChartIconsUiBinder.class);

	interface ChartIconsUiBinder extends UiBinder<Widget, ChartIcons> {
	}
	
	public interface ChartIconsEventHandler{
		void onChartIconClicked(String value);
	}

	@UiField Image barIcon, pieIcon, lineIcon;
	
	public ChartIcons() {
		initWidget(uiBinder.createAndBindUi(this));
		initComponent();
		initEvent();
	}
	
	private void initComponent(){
		barIcon.setUrl("images/bar_png.png");
		pieIcon.setUrl("images/pie_png.png");
		lineIcon.setUrl("images/line_png.png");
	}

	private void initEvent(){
		barIcon.sinkEvents(Event.ONCLICK);
		barIcon.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onChartIconClicked(ModuleConstants.BAR);
				}
			}
		}, ClickEvent.getType());
		
		pieIcon.sinkEvents(Event.ONCLICK);
		pieIcon.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onChartIconClicked(ModuleConstants.PIE);
				}				
			}
		}, ClickEvent.getType());
		
		lineIcon.sinkEvents(Event.ONCLICK);
		lineIcon.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onChartIconClicked(ModuleConstants.LINE);
				}					
			}
		}, ClickEvent.getType());
	}
	
	@Override
	public void setChartIconsEventHandler(ChartIconsEventHandler handler){
		this.handler = handler;
	}
}
