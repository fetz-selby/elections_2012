package com.beta.election.media.client.widgets;

import java.util.ArrayList;

import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.utils.JQPlotSingleHorizontalBarWithLengend;
import com.beta.election.media.shared.PrezUpdateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class PresidentialUpdateChart extends Composite {
	private RetrieveServiceAsync rpc;
	private static PresidentialUpdateChartUiBinder uiBinder = GWT
			.create(PresidentialUpdateChartUiBinder.class);

	interface PresidentialUpdateChartUiBinder extends
			UiBinder<Widget, PresidentialUpdateChart> {
	}

	@UiField HTMLPanel chartContainer;
	
	public PresidentialUpdateChart(RetrieveServiceAsync rpc) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rpc = rpc;
	}

	private void doChartRendering(){
		rpc.getPresidentialUpdate("2012", new AsyncCallback<ArrayList<PrezUpdateModel>>() {
			
			@Override
			public void onSuccess(ArrayList<PrezUpdateModel> result) {
				if(result != null){
					JQPlotSingleHorizontalBarWithLengend bar = new JQPlotSingleHorizontalBarWithLengend();
					for(PrezUpdateModel model : result){
						bar.addName(model.getName());
						GWT.log("************************");
						GWT.log("Name is "+model.getName());
						if(model.getColor() == null){
							bar.addColor("white");
							GWT.log("COLOR was NULL");
						}else{
							bar.addColor(model.getColor());
							GWT.log("COLOR is "+model.getColor());
						}
						bar.addValue(Double.parseDouble(model.getVotes()+""));
						GWT.log("Value is "+model.getVotes());
						GWT.log("************************");

					}
					if(result.size() > 0 && isGreaterThanZero(result)){
						chartContainer.clear();
						chartContainer.add(bar);
						bar.load();
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private boolean isGreaterThanZero(ArrayList<PrezUpdateModel> prezlist){
		int sum = 0;
		for(PrezUpdateModel model : prezlist){
			sum += model.getVotes();
		}
		
		if(sum > 0){
			return true;
		}
		return false;
	}
	
	public void render(){
		doChartRendering();
	}
}
