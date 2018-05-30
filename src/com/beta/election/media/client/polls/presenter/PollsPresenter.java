package com.beta.election.media.client.polls.presenter;

import java.util.ArrayList;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.events.PollChangeEvent;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.widgets.PollWidget;
import com.beta.election.media.client.widgets.PollWidgetHandler;
import com.beta.election.media.shared.CandidateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class PollsPresenter implements Presenter{

	private RetrieveServiceAsync rpc;
	private HandlerManager eventBus;
	private Display view;
	private SystemVariables system;
	private int candidateId, constituencyId;
	
	public interface Display extends IsWidget{
		Widget asWidget();
		void addPollsWidget(PollWidget widget);
		void doLoad();
	}

	public PollsPresenter(RetrieveServiceAsync rpc, HandlerManager eventBus, Display view , int constituencyId, int candidateId){
		this.rpc = rpc;
		this.view = view;
		this.eventBus = eventBus;
		//this.candidateId = candidateId;
		this.constituencyId = constituencyId;
	}
	
	private void bind(){
		system = SystemVariables.getSystemInstance();
		String year = ""+system.getSystemYear();
		
		if(candidateId > 0){
			rpc.getModifiedCandidateByCandidateId(candidateId, year, new AsyncCallback<CandidateModel>() {
				
				@Override
				public void onSuccess(CandidateModel result) {
					if(result != null){
						
						GWT.log("Candidate details "+result.getCandidateId()+" "+result.getCandidateName());
						
						PollWidget widget = new PollWidget(result);
						doPollEventHandling(widget);
						view.addPollsWidget(widget);
					}
					view.doLoad();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}else if(constituencyId > 0){
			rpc.getModifiedCandidatesByConstituency(system.getCandidateType(), constituencyId, year, new AsyncCallback<ArrayList<CandidateModel>>() {
				
				@Override
				public void onSuccess(ArrayList<CandidateModel> result) {
					if(result != null){
						for(CandidateModel model : result){
							PollWidget widget = new PollWidget(model);
							doPollEventHandling(widget);
							view.addPollsWidget(widget);
						}
						view.doLoad();
					}					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}else{
		
		rpc.getModifiedCandidates(system.getCandidateType(), year, new AsyncCallback<ArrayList<CandidateModel>>() {
			@Override
			public void onSuccess(ArrayList<CandidateModel> result) {
				if(result != null){
					for(CandidateModel model : result){
						GWT.log("Model details "+model.getCandidateId()+model.getCandidateName()+model.getCode()+model.getPartyName());
						PollWidget widget = new PollWidget(model);
						doPollEventHandling(widget);
						view.addPollsWidget(widget);
					}
					view.doLoad();
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		}
	}
	
	private void doPollEventHandling(PollWidget widget){
		widget.setPollWidgetHandler(new PollWidgetHandler() {
			
			@Override
			public void onPollClicked(CandidateModel model) {
				GWT.log("Poll clicked, candidate id is "+model.getCandidateId());
				eventBus.fireEvent(new PollChangeEvent(model));
			}
		});
	}
	
	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		bind();
		screen.add(view.asWidget());
	}
}
