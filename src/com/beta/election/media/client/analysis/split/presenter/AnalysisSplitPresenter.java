package com.beta.election.media.client.analysis.split.presenter;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.widgets.AnalysisSplit;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class AnalysisSplitPresenter implements Presenter{

	private RetrieveServiceAsync rpc;
	private Display view;
	
	public interface Display extends IsWidget{
		Widget asWidget();
		void setContainerA(IsWidget widget);
		void setContainerB(IsWidget widget);
	}
	
	public AnalysisSplitPresenter(RetrieveServiceAsync rpc, Display view){
		this.rpc = rpc;
		this.view = view;
	}
	
	private void bind(){
		AnalysisSplit splitA = new AnalysisSplit(rpc, SystemVariables.getSystemInstance().getConstituencyId());
		AnalysisSplit splitB = new AnalysisSplit(rpc, SystemVariables.getSystemInstance().getConstituencyId());
		
		view.setContainerA(splitA);
		view.setContainerB(splitB);
	}
	
	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		bind();
		screen.add(view.asWidget());
	}

}
