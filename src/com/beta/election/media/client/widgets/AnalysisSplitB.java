package com.beta.election.media.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;

import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.constants.ModuleConstants;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.utils.JQPlotPie;
import com.beta.election.media.client.widgets.handlers.AnalysisCandidateHandler;
import com.beta.election.media.client.widgets.handlers.AnalysisYearNavigatorHandler;
import com.beta.election.media.shared.CandidateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class AnalysisSplitB extends Composite {

	private static AnalysisSplitBUiBinder uiBinder = GWT
			.create(AnalysisSplitBUiBinder.class);
	private RetrieveServiceAsync rpc;
	private HashMap<Integer, String> constituencyMap, candidatesMap;
	private HashMap<Integer, CandidateModel> candidateModelMap;
	private ArrayList<String> chartNameList;
	private ArrayList<Double> chartValuesList;
	
	private int constituencyId = 3, year;

	interface AnalysisSplitBUiBinder extends UiBinder<Widget, AnalysisSplitB> {
	}

	public AnalysisSplitB() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	DivElement constituencyField, votesField, percentageField;
	@UiField SimplePanel searchContainer, yearContainer, nameContainer, chartContainer;
	@UiField
	Image imageContainer, logoContainer;

	public AnalysisSplitB(RetrieveServiceAsync rpc, int constituencyId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rpc = rpc;
		if(constituencyId > 0){
			this.constituencyId = constituencyId;
		}
		initComponents();
	}

	private void initComponents() {
		initSearchWidget();
		initYearWidget();
	}

	private void initSearchWidget() {
		rpc.getAllConstituencies(new AsyncCallback<HashMap<String, String>>() {

			@Override
			public void onSuccess(HashMap<String, String> result) {
				if (result != null) {
					constituencyMap = new HashMap<Integer, String>();
					MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();

					for (String key : result.keySet()) {
						String value = result.get(key);
						oracle.add(value);
						GWT.log("Cons added is "+value);

						constituencyMap.put(Integer.parseInt(key), value);
					}

					SuggestBox constituencySuggest = new SuggestBox(oracle,
							new GenericSearchBox());
					constituencySuggest
							.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

								@Override
								public void onSelection(
										SelectionEvent<Suggestion> event) {
									String selectedText = event.getSelectedItem().getReplacementString();
									constituencyId = getSearchKey(selectedText);
									GWT.log("Constituency ID is "+constituencyId);
									reInitSplitDisplay(constituencyId);
								}
							});
					searchContainer.setWidget(constituencySuggest);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	private void reInitSplitDisplay(int constituencyId){
		//Load candidate widget
		if(constituencyId <= 0){
			GWT.log("ConstituencyID is 000");
			return;
		}
		rpc.getCandidates(SystemVariables.getSystemInstance().getCandidateType(), year+"", constituencyId, new AsyncCallback<ArrayList<CandidateModel>>() {
			
			@Override
			public void onSuccess(ArrayList<CandidateModel> result) {
				if(result != null){
					CandidateModel tmpModel = null;
					candidatesMap = new HashMap<Integer, String>();
					candidateModelMap = new HashMap<Integer, CandidateModel>();
					chartNameList = new ArrayList<String>();
					chartValuesList = new ArrayList<Double>();
					int counter = 0, modelCounter = 0;
					for(CandidateModel model : result){
						int id = model.getCandidateId();
						String name = model.getCandidateName();
						double vote = model.getVotes();
						
						candidatesMap.put(id, name);
						candidateModelMap.put(id, model);
						
						chartNameList.add(name);
						chartValuesList.add(vote);
						
						if(counter == 0){
							tmpModel = model;
							counter ++;
						}
						modelCounter ++;
					}
					if(tmpModel != null){
						initCandidateWidget(tmpModel.getCandidateName());
						reInitDisplay(tmpModel);
					}else{
						GWT.log("No data found !!!");
					}
					
					initPieChart();
					
					GWT.log("Model size is "+modelCounter);
					
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void reInitDisplay(CandidateModel model){
		if(model != null){
			percentageField.setInnerText(model.getPercentage());
			votesField.setInnerText(model.getFormattedVotes());
			constituencyField.setInnerText(model.getConstituencyName());
			if(model.getAvatar().equals(ModuleConstants.EMPTY_IMG) || model.getAvatar() == null){
				imageContainer.setUrl(ModuleConstants.DEFAULT_IMG);
			}else{
				imageContainer.setUrl(model.getAvatar());
			}
			logoContainer.setUrl(model.getLogo());
		}
	}
	
	private void initPieChart(){
		if(chartNameList != null && chartValuesList != null){
			JQPlotPie pie = new JQPlotPie("analysis_B");
			pie.setNames(chartNameList);
			pie.setValues(chartValuesList);
			
			chartContainer.setWidget(pie);
			
			pie.load();
		}
	}
	
	private void initCandidateWidget(String defaultName){
		AnalysisCandidateWidget candidateWidget = new AnalysisCandidateWidget(candidatesMap);
		candidateWidget.setAnalysisCandidateHandler(new AnalysisCandidateHandler() {
			
			@Override
			public void onForwardClicked(int candidateId) {
				reInitDisplay(candidateModelMap.get(candidateId));
			}
			
			@Override
			public void onBackClicked(int candidateId) {
				reInitDisplay(candidateModelMap.get(candidateId));
			}
		});
		candidateWidget.setInitName(defaultName);
		nameContainer.setWidget(candidateWidget);
	
	}
	
	private void initYearWidget(){
		rpc.getYearMap(new AsyncCallback<HashMap<Integer,String>>() {
			
			@Override
			public void onSuccess(HashMap<Integer, String> result) {
				if(result != null){
					
					AnalysisYearNavigator yearNav = new AnalysisYearNavigator(result);
					yearNav.setAnalysisYearNavigatorHandler(new AnalysisYearNavigatorHandler() {
						
						@Override
						public void onUpClicked(int tmpYear) {
							year = tmpYear;
							GWT.log("Year is "+tmpYear);
							reInitSplitDisplay(constituencyId);
						}
						
						@Override
						public void onDownClicked(int tmpYear) {
							year = tmpYear;
							GWT.log("Year is "+tmpYear);
							reInitSplitDisplay(constituencyId);
						}
					});
					yearContainer.setWidget(yearNav);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private int getSearchKey(String value) {
		for (Integer key : constituencyMap.keySet()) {
			String tmpValue = constituencyMap.get(key);
			if (value.equals(tmpValue)) {
				return key;
			}
		}
		return 0;
	}

}
