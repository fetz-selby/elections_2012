package com.beta.election.media.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.utils.JQPlotMultiBar;
import com.beta.election.media.shared.CandidateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class HistoryChartWidget2 extends Composite {
	private RetrieveServiceAsync rpc;
	private int parentConstituencyId = 0;
	private String type;
	private static HistoryChartWidget2UiBinder uiBinder = GWT
			.create(HistoryChartWidget2UiBinder.class);

	interface HistoryChartWidget2UiBinder extends
			UiBinder<Widget, HistoryChartWidget2> {
	}

	@UiField SimplePanel chartContainer;
	
	public HistoryChartWidget2(int parentConstituencyId, String type, RetrieveServiceAsync rpc) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parentConstituencyId = parentConstituencyId;
		this.type = type;
		this.rpc = rpc;
		doChartLoading();
	}

	private void doChartLoading(){
		rpc.getAllCandidatesByGlobalConstituencyId(parentConstituencyId, type, new AsyncCallback<ArrayList<CandidateModel>>() {
			
			@Override
			public void onSuccess(ArrayList<CandidateModel> result) {
				if(result != null){
					HashMap<String, ArrayList<Double>> chartMap = new HashMap<String, ArrayList<Double>>();
					
					ArrayList<String> partyList = getAllParties(result);
					ArrayList<String> yearList = getAllYears(result);
					//ArrayList<String> colorList = getPartyColors(result, partyList);
					
					for(String party : partyList){
						ArrayList<Double> valueList = new ArrayList<Double>();

						for(String year : yearList){
							int value = 0;
							for(CandidateModel model : result){
								if(model.getPartyName().equals(party) && model.getYear().equals(year)){
									value = model.getVotes();
								}
							}
							valueList.add(Double.parseDouble(value+""));
						}
						
						chartMap.put(party, valueList);
					}
					
					JQPlotMultiBar barChart = new JQPlotMultiBar();
					barChart.setPlotMap(chartMap);
					barChart.setXValues(yearList);
					//barChart.setColors(colorList);
					chartContainer.setWidget(barChart);
					
					barChart.load();
					
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private ArrayList<String> getAllParties(ArrayList<CandidateModel> candidateModelList){
		if(candidateModelList != null){
			TreeSet<String> partySet = new TreeSet<String>();
			
			for(CandidateModel model : candidateModelList){
				partySet.add(model.getPartyName().trim());
			}
			
			ArrayList<String> partyList = new ArrayList<String>();
			
			for(String party : partySet){
				partyList.add(party);
			}
			
			return partyList;
		}
		
		return null;
	}
	
	private ArrayList<String> getAllYears(ArrayList<CandidateModel> candidateModelList){
		if(candidateModelList != null){
			TreeSet<String> yearSet = new TreeSet<String>();
			
			for(CandidateModel model : candidateModelList){
				yearSet.add(model.getYear().trim());
			}
			
			ArrayList<String> yearList = new ArrayList<String>();
			
			for(String year : yearSet){
				yearList.add(year);
			}
			
			return yearList;
		}
		return null;
	}
	
	private ArrayList<String> getPartyColors(ArrayList<CandidateModel> candidateModelList, ArrayList<String> partyList){
		ArrayList<String> partyColorList = new ArrayList<String>();
		
		for(String party : partyList){
	candidate:	for(CandidateModel model : candidateModelList){
				if(model.getPartyName().trim().equals(party)){
					//Check if NULL or empty
					if(model.getPartyColor() == null || model.getPartyColor().equals("")||model.getPartyColor().equals("NULL")){
						partyColorList.add("#f3f3f3");
					}else{
						partyColorList.add(model.getPartyColor());
					}
					break candidate;
				}
			}
		}
		return partyColorList;
	}
	
}
