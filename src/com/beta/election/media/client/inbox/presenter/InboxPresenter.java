package com.beta.election.media.client.inbox.presenter;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.RetrieveServiceAsync;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class InboxPresenter implements Presenter{
	private HandlerManager eventBus;
	private RetrieveServiceAsync rpc;
	private Display view;
	
	public interface Display extends IsWidget{
		Widget asWidget();
		void addPoll(Widget pollWidget);
	}
	
	public InboxPresenter(Display view, HandlerManager eventBus, RetrieveServiceAsync rpc){
		this.view = view;
		this.eventBus = eventBus;
		this.rpc = rpc;
		
		bind();
	}
	
	private void bind(){
		
	}

	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		screen.add(view.asWidget());
	}
}
