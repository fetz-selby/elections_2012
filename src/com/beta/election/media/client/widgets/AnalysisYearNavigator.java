package com.beta.election.media.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.widgets.handlers.AnalysisYearNavigatorHandler;
import com.beta.election.media.client.widgets.handlers.HasAnalysisYearNavigatorHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class AnalysisYearNavigator extends Composite implements
		HasAnalysisYearNavigatorHandler {
	private AnalysisYearNavigatorHandler handler;
	private HashMap<Integer, String> yearsMap;
	private ArrayList<String> yearsList;
	private int currentIndex, year;
	private String currentYear;

	private static AnalysisYearNavigatorUiBinder uiBinder = GWT
			.create(AnalysisYearNavigatorUiBinder.class);

	interface AnalysisYearNavigatorUiBinder extends
			UiBinder<Widget, AnalysisYearNavigator> {
	}

	@UiField
	DivElement yearText;
	@UiField
	Image upButton, downButton;

	public AnalysisYearNavigator(HashMap<Integer, String> yearsMap) {
		initWidget(uiBinder.createAndBindUi(this));
		this.yearsMap = yearsMap;
		initListeners();
		doExtraction();
		initComponents();
	}

	private void initListeners() {
		upButton.sinkEvents(Event.ONCLICK);
		upButton.addHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					doMove(true);
					handler.onUpClicked(year);
					GWT.log("Up clicked !!!");
				}
			}
		}, ClickEvent.getType());

		downButton.sinkEvents(Event.ONCLICK);
		downButton.addHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					doMove(false);
					handler.onDownClicked(year);
					GWT.log("Down clicked !!!");

				}
			}
		}, ClickEvent.getType());
	}
	
	private void initComponents(){
		//Set to show the first element
		if(yearsList != null){
			yearText.setInnerText(yearsList.get(0));
		}
	}
	
	private void doExtraction(){
		if(yearsMap != null){
			yearsList = new ArrayList<String>();
			TreeSet<Integer> yearSort = new TreeSet<Integer>();
			for(Integer key : yearsMap.keySet()){
				yearSort.add(key);
			}
			
			for(Integer key : yearSort){
				yearsList.add(yearsMap.get(key));
			}
		}
	}

	private void doMove(boolean isMove) {
		if (isMove && yearsList != null && isIncrementWithinRange((currentIndex + 1))) {
			currentIndex ++;
			GWT.log("Incremented !!!");
		}else if(!isMove && yearsList != null && isDecrementWithinRange((currentIndex - 1))){
			currentIndex --;
			GWT.log("Decremented !!!");
		}
		
		currentYear = yearsList.get(currentIndex);
		year = getCandidateId(currentYear);
		yearText.setInnerText(yearsList.get(currentIndex));
	}
	
	private int getCandidateId(String yearAlias){
		if(yearsMap.containsValue(yearAlias)){
			for(Integer key : yearsMap.keySet()){
				String tmpAlias = yearsMap.get(key);
				if(tmpAlias.equals(yearAlias)){
					return key;
				}
			}
		}
		return 0;
	}
	
	private boolean isIncrementWithinRange(int index){
		int length = yearsList.size() - 1;
		if(index <= length){
			return true;
		}
		return false;
	}
	
	private boolean isDecrementWithinRange(int index){
		if(index >= 0){
			return true;
		}
		return false;
	}
	
	private void initToDefault(){
		int systemDate = SystemVariables.getSystemInstance().getSystemYear();
		if(yearsMap.containsKey(systemDate)){
			int index = yearsList.indexOf(yearsMap.get(systemDate));
			GWT.log("*** Index is "+index);
			for(int i = 0; i < index; i++){
				upButton.fireEvent(new ClickEvent(){});
				GWT.log("&&& Up Fired Clicked !!!");
			}
		}
	}

	@Override
	public void setAnalysisYearNavigatorHandler(
			AnalysisYearNavigatorHandler handler) {
		this.handler = handler;
		initToDefault();
	}

}
