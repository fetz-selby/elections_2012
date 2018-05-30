package com.beta.election.media.client.widgets;

import java.util.ArrayList;

import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.shared.SeatUpdateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SeatsWidget extends Composite {
	final String CURRENT_YEAR = "2012", BLACK_LIST="NONE";
	private RetrieveServiceAsync rpc;
	private String overallSeats = "", declaredSeats = "";
	private static SeatsWidgetUiBinder uiBinder = GWT
			.create(SeatsWidgetUiBinder.class);

	interface SeatsWidgetUiBinder extends UiBinder<Widget, SeatsWidget> {
	}

	@UiField SimplePanel detailPanel;
	@UiField LabelElement declaredCount, overallCount, declaredLabel, slash;
	
	public SeatsWidget(RetrieveServiceAsync rpc) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rpc = rpc;
		initComponent();
	}

	private void initComponent(){
		
		rpc.getTotalSeats(CURRENT_YEAR, new AsyncCallback<Integer>() {
			
			@Override
			public void onSuccess(Integer result) {
				if(result > 0){
					overallSeats = ""+result;
					findDeclaredSeats();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void findDeclaredSeats(){
		rpc.getTotalDeclaredSeats(CURRENT_YEAR, new AsyncCallback<Integer>() {
			
			@Override
			public void onSuccess(Integer result) {
				if(result != null){
					declaredSeats = ""+result;
					findPartySeatUpdate();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void findPartySeatUpdate(){
		rpc.getSeatUpdate(CURRENT_YEAR, new AsyncCallback<ArrayList<SeatUpdateModel>>() {
			
			@Override
			public void onSuccess(ArrayList<SeatUpdateModel> result) {
				if(result != null){
					int counter = 0;
					FlowPanel container = new FlowPanel();
					container.getElement().setAttribute("style", "width:100%;height:206px");
					for(SeatUpdateModel model : result){
						counter ++;
						SeatletWidget seatlet = new SeatletWidget(model);
						if(!model.getName().equalsIgnoreCase(BLACK_LIST)){
							container.add(seatlet);
						}
					}
					if(counter > 0){
						declaredCount.setInnerText(declaredSeats);
						overallCount.setInnerText(overallSeats);
						detailPanel.setWidget(container);
						initTileLabels();
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initTileLabels(){
		final String TITLE = "DECLARED SEAT : ", SLASH = "/";
		
		declaredLabel.setInnerText(TITLE);
		slash.setInnerText(SLASH);
	}
}
