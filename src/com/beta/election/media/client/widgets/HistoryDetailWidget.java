package com.beta.election.media.client.widgets;

import java.util.HashMap;

import com.beta.election.media.client.RetrieveServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class HistoryDetailWidget extends Composite {

	private int parentConstituencyId;
	private RetrieveServiceAsync rpc;
	private static HistoryDetailWidgetUiBinder uiBinder = GWT
			.create(HistoryDetailWidgetUiBinder.class);

	interface HistoryDetailWidgetUiBinder extends
			UiBinder<Widget, HistoryDetailWidget> {
	}
	
	@UiField SimplePanel detailContainer;

	public HistoryDetailWidget(int parentConstituencyId, RetrieveServiceAsync rpc) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rpc = rpc;
		this.parentConstituencyId = parentConstituencyId;
		initComponent();
	}
	
	private void initComponent(){
		rpc.getConstituenciesByParentId(parentConstituencyId, new AsyncCallback<HashMap<Integer,String>>() {
			
			@Override
			public void onSuccess(HashMap<Integer, String> result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
