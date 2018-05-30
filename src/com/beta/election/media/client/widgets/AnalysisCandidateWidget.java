package com.beta.election.media.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;

import com.beta.election.media.client.widgets.handlers.AnalysisCandidateHandler;
import com.beta.election.media.client.widgets.handlers.HasAnalysisCandidateWidgetHandler;
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

public class AnalysisCandidateWidget extends Composite implements HasAnalysisCandidateWidgetHandler{
	private AnalysisCandidateHandler handler;
	private HashMap<Integer, String> candidatesMap;
	private ArrayList<String> candidatesList;
	private int currentIndex = 0, candidateId;
	private String currentCandidate;
	private static AnalysisCandidateWidgetUiBinder uiBinder = GWT
			.create(AnalysisCandidateWidgetUiBinder.class);

	interface AnalysisCandidateWidgetUiBinder extends
			UiBinder<Widget, AnalysisCandidateWidget> {
	}

	@UiField DivElement canName;
	@UiField Image backButton, forwardButton;
	
	public AnalysisCandidateWidget(HashMap<Integer, String> candidatesMap) {
		initWidget(uiBinder.createAndBindUi(this));
		this.candidatesMap = candidatesMap;
		initListener();
		doExtraction();
		initComponents();
	}
	
	private void initListener(){
		backButton.sinkEvents(Event.ONCLICK);
		backButton.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					doMove(false);
					GWT.log("Back id is "+candidateId);
					handler.onBackClicked(candidateId);
				}
			}
		}, ClickEvent.getType());
		
		forwardButton.sinkEvents(Event.ONCLICK);
		forwardButton.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					doMove(true);
					GWT.log("Forward id is "+candidateId);
					handler.onForwardClicked(candidateId);
				}
			}
		}, ClickEvent.getType());
	}
	
	private void initComponents(){
		//Set to show the first element
		if(candidatesList != null && candidatesList.size() > 0){
			doCandidateNameSetting(candidatesList.get(0), canName);
		}
	}
	
	private void doExtraction(){
		if(candidatesMap != null){
			candidatesList = new ArrayList<String>();
			for(Integer key : candidatesMap.keySet()){
				String candidate = candidatesMap.get(key);
				candidatesList.add(candidate);
				
				GWT.log("Candidate name is "+candidate);
			}
			GWT.log("CandidateList size is "+candidatesList.size());
		}
	}

	private void doMove(boolean isMove) {
		if (isMove && candidatesList != null && isIncrementWithinRange((currentIndex + 1))) {
			currentIndex ++;
			GWT.log("Index incremented !!!, index is "+currentIndex);
		}else if(!isMove && candidatesList != null && isDecrementWithinRange((currentIndex - 1))){
			currentIndex --;
			GWT.log("Index decremented !!!, index is "+currentIndex);
		}
		
		currentCandidate = candidatesList.get(currentIndex);
		candidateId = getCandidateId(currentCandidate);
		//canName.setInnerText(candidatesList.get(currentIndex));
		doCandidateNameSetting(candidatesList.get(currentIndex), canName);
	}
	
	private boolean isIncrementWithinRange(int index){
		int length = candidatesList.size() - 1;
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
	
	private int getCandidateId(String candidateName){
		if(candidatesMap.containsValue(candidateName)){
			for(Integer key : candidatesMap.keySet()){
				String tmpCandidateName = candidatesMap.get(key);
				if(tmpCandidateName.equals(candidateName)){
					return key;
				}
			}
		}
		return 0;
	}
	
	private void doCandidateNameSetting(String candiateName, DivElement div){
		final int NAME_LENGTH_LIMIT = 22;
		
		if(candiateName.length() > NAME_LENGTH_LIMIT){
			div.setInnerHTML("<marquee direction=\"left\">"+candiateName+"</marquee>");
		}else{
			div.setInnerText(candiateName);
		}
	}
	
	public void setInitName(String name){
		doCandidateNameSetting(name, canName);
	}
	
	@Override
	public void setAnalysisCandidateHandler(AnalysisCandidateHandler handler) {
		this.handler = handler;
	}

}
