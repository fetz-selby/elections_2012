package com.beta.election.media.client.widgets;

import com.beta.election.media.shared.RegionModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SummaryRegionWidget extends Composite {

	private SummaryRegionWidgetEventHandler handler;
	private int id;
	private String name;
	private static SummaryRegionWidgetUiBinder uiBinder = GWT
			.create(SummaryRegionWidgetUiBinder.class);

	interface SummaryRegionWidgetUiBinder extends
			UiBinder<Widget, SummaryRegionWidget> {
	}

	public interface SummaryRegionWidgetEventHandler{
		void onLinkClicked(int id, String name);
	}
	
	@UiField Anchor linkAnchor;
	
	public SummaryRegionWidget(RegionModel model) {
		initWidget(uiBinder.createAndBindUi(this));
		this.id = model.getId();
		this.name = model.getRegionName();
		linkAnchor.setText(model.getRegionName());
		initEvent();
	}

	private void initEvent(){
		linkAnchor.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onLinkClicked(id, name);
				}
			}
		});
	}
	
	public void setSummaryRegionWidgetEventHandler(SummaryRegionWidgetEventHandler handler){
		this.handler = handler;
	}
}
