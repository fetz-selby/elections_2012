package com.beta.election.media.client.candidates.presenter;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.widgets.AnalysisMain;
import com.beta.election.media.shared.CandidateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class CandidatesPresenter implements Presenter{

	private Display view;
	private CandidateModel model;
	private RetrieveServiceAsync rpc;
	private boolean isModelNull = false;
	
	public interface Display extends IsWidget{
		Widget asWidget();
		void setCandidateWidget(Widget screen);
	}
	
	public CandidatesPresenter(HandlerManager eventBus, RetrieveServiceAsync rpc, Display view, CandidateModel model, int constituencyId){
		this.view = view;
		if(model == null){
			isModelNull = true;
		}
		this.model = model;
		this.rpc = rpc;
	}
	
	private void bind(){
		if(isModelNull){
			rpc.getSingleCandidateFromConstituency(SystemVariables.getSystemInstance().getConstituencyId(), SystemVariables.getSystemInstance().getSystemYear()+"", SystemVariables.getSystemInstance().getCandidateType(), new AsyncCallback<CandidateModel>() {
				
				@Override
				public void onSuccess(CandidateModel result) {
					if(result != null){
						GWT.log("Finding inner details "+result.getCandidateName());
						AnalysisMain mainView = new AnalysisMain(result, rpc);
						view.setCandidateWidget(mainView);
						isModelNull = false;
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					GWT.log("Single Candidate Request failed !!!");
				}
			});
		}else{
			AnalysisMain mainView = new AnalysisMain(model, rpc);
			view.setCandidateWidget(mainView);
		}
	}
	
	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		bind();
		screen.add(view.asWidget());
	}

}
