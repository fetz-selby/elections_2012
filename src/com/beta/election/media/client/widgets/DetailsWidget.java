package com.beta.election.media.client.widgets;

import java.util.ArrayList;
import java.util.TreeSet;

import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.events.PollChangeEvent;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.widgets.handlers.HistoryWidgetHandler;
import com.beta.election.media.shared.CandidateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DetailsWidget extends Composite {

	private RetrieveServiceAsync rpc;
	private HandlerManager eventBus;
	private int parentConstituencyId;
	private static DetailsWidgetUiBinder uiBinder = GWT
			.create(DetailsWidgetUiBinder.class);

	interface DetailsWidgetUiBinder extends UiBinder<Widget, DetailsWidget> {
	}

	@UiField SimplePanel container;
	
	public DetailsWidget(HandlerManager eventBus, RetrieveServiceAsync rpc, int parentConstituencyId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parentConstituencyId = parentConstituencyId;
		this.rpc = rpc;
		this.eventBus = eventBus;
		doLoading();
	}
	
	private void doLoading(){
		String type = SystemVariables.getSystemInstance().getCandidateType();
		rpc.getAllCandidatesByGlobalConstituencyId(parentConstituencyId, type, new AsyncCallback<ArrayList<CandidateModel>>() {
			
			@Override
			public void onSuccess(ArrayList<CandidateModel> result) {
				if(result != null){
					//AccordionPanel accordion = new AccordionPanel();
					TabsPanel tabs = new TabsPanel();
					
					ArrayList<String> arrangedYear = getArrangedYears(result);
					for(String year : arrangedYear){
						
						FlowPanel panel = new FlowPanel();
						panel.getElement().setAttribute("style", "width:700px;height:100px;overflow:auto;");
						
						for(CandidateModel model : result){
							String modelYear = model.getYear();
							if(modelYear.equals(year)){
								
								HistoryWidget historyWidget = new HistoryWidget(model);
								historyWidget.setHistoryWidgetHandler(new HistoryWidgetHandler() {
									
									@Override
									public void onHistoryWidgetClicked(CandidateModel model) {
										//eventBus.fireEvent(new PollChangeEvent(model));
									}
								});
								panel.add(historyWidget);
							}
						}
						
						//accordion.addSection(0, year, panel);
						//accordion.load();
						
						tabs.addTab(year, panel);
					}
					//accordion.load();
					tabs.load();
					//container.setWidget(accordion);
					container.setWidget(tabs);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private ArrayList<String> getArrangedYears(ArrayList<CandidateModel> candidates){
		if(candidates != null){
			TreeSet<String> yearSet = new TreeSet<String>();
			for(CandidateModel model : candidates){
				yearSet.add(model.getYear());
			}
			
			ArrayList<String> arrangedYearList = new ArrayList<String>();
			for(String year : yearSet){
				arrangedYearList.add(year);
			}
			
			return arrangedYearList;
		}
		
		return null;
	}

}
