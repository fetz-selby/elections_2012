package com.beta.election.media.client.widgets;

import java.util.ArrayList;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.events.DateChangeEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class DateWidget extends Composite implements Presenter{

	private ArrayList<Integer> dateList;
	private HandlerManager eventBus;
	private int cursor;
	private static DateWidgetUiBinder uiBinder = GWT
			.create(DateWidgetUiBinder.class);

	interface DateWidgetUiBinder extends UiBinder<Widget, DateWidget> {
	}

	@UiField DivElement backArrow, frontArrow, dateText;
	
	private DateWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public DateWidget(HandlerManager eventBus, ArrayList<Integer> dateList){
		this();
		this.eventBus = eventBus;
		this.dateList = dateList;
		initListeners();
		initDate();
		advanceDate();
	}
	
	private void initListeners(){
		com.google.gwt.user.client.Element backArrowElement = backArrow.cast();
		DOM.sinkEvents(backArrowElement, Event.ONCLICK);
		DOM.setEventListener(backArrowElement, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				changeDateBack();
			}
		});
		
		com.google.gwt.user.client.Element frontArrowElement = frontArrow.cast();
		DOM.sinkEvents(frontArrowElement, Event.ONCLICK);
		DOM.setEventListener(frontArrowElement, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				changeDateForward();
			}
		});
	}
	
	private void initDate(){
		if(dateList != null && dateList.size() > 0){
			dateText.setInnerText(""+dateList.get(1));
			cursor = 0;
		}
	}
	
	private void changeDateForward(){
		if(dateList.size() - 1 > (cursor + 1)){
			cursor++;
			showArrows();
			dateText.setInnerText(""+dateList.get(cursor));
			eventBus.fireEvent(new DateChangeEvent(dateList.get(cursor)));
			GWT.log("Fired date is "+dateList.get(cursor));

		}else{
			frontArrow.setAttribute("style", "visibility: hidden;");
		}
	}
	
	private void changeDateBack(){
		if((cursor - 1) > 0){
			cursor --;
			showArrows();
			dateText.setInnerText(""+dateList.get(cursor));
			eventBus.fireEvent(new DateChangeEvent(dateList.get(cursor)));
			GWT.log("Fired date is "+dateList.get(cursor));

		}else{
			backArrow.setAttribute("style", "visibility: hidden;");
		}
	}
	
	private void showArrows(){
		backArrow.setAttribute("style", "visibility: visible;");
		frontArrow.setAttribute("style", "visibility: visible;");
	}
	
	private void advanceDate(){
		changeDateForward();
	}

	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		screen.add(asWidget());
	}
}
