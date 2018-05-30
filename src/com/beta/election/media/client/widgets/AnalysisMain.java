package com.beta.election.media.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;

import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.constants.ModuleConstants;
import com.beta.election.media.client.events.CandidateNullingEvent;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.utils.JQPlotSingleBar;
import com.beta.election.media.client.widgets.handlers.AnalysisCandidateHandler;
import com.beta.election.media.shared.CandidateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class AnalysisMain extends Composite {
	private CandidateModel model;
	private RetrieveServiceAsync rpc;
	private HashMap<String, Integer> candidateChartMap;
	private HashMap<Integer, String> candidateMap;
	private HashMap<Integer, CandidateModel> modelMap;
	private SystemVariables system;
	//private JQPlotPie pieChart;
	private JQPlotSingleBar barChart;
	private String initName = "";
	
	private static AnalysisViewUiBinder uiBinder = GWT
			.create(AnalysisViewUiBinder.class);

	interface AnalysisViewUiBinder extends UiBinder<Widget, AnalysisMain> {
	}
	@UiField SimplePanel nameWidget, chartContainer;

	@UiField Image avatar, logo;
	@UiField DivElement votesScore, votesPercentage, consWidget, imageDiv, detailsDiv;
	
	public AnalysisMain(CandidateModel model, RetrieveServiceAsync rpc) {
		this.rpc = rpc;
		this.model = model;
		if(model != null){
			this.initName = model.getCandidateName();
		}
		
		initWidget(uiBinder.createAndBindUi(this));
		initComponents();
	}
	
	private void initComponents(){
		system = SystemVariables.getSystemInstance();
		
		rpc.getCandidates(system.getCandidateType(), ""+system.getSystemYear(), system.getConstituencyId(), new AsyncCallback<ArrayList<CandidateModel>>() {
			
			@Override
			public void onSuccess(ArrayList<CandidateModel> result) {
				if(result != null){
					candidateMap = new HashMap<Integer, String>();
					candidateChartMap = new HashMap<String, Integer>();
					modelMap = new HashMap<Integer, CandidateModel>();
					//pieChart = new JQPlotPie();
					barChart = new JQPlotSingleBar();
					for(CandidateModel model : result){

						candidateMap.put(model.getCandidateId(), model.getCandidateName());
						candidateChartMap.put(model.getCandidateName(), model.getVotes());
						
						modelMap.put(model.getCandidateId(), model);
						
						//pieChart.addName(model.getCandidateName());
						//pieChart.addValue(Double.parseDouble(""+model.getVotes()));
						
						barChart.addName(model.getPartyName());
						barChart.addValue(Double.parseDouble(""+model.getVotes()));
						barChart.addColor(model.getPartyColor());
					}
					
					initNameWidget();
					//chartContainer.setWidget(pieChart);
					chartContainer.setWidget(barChart);
					
					barChart.load();
					//pieChart.load();
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initNameWidget(){
		if(candidateMap != null){
		AnalysisCandidateWidget candidateWidget = new AnalysisCandidateWidget(candidateMap);
		candidateWidget.setAnalysisCandidateHandler(new AnalysisCandidateHandler() {
			
			@Override
			public void onForwardClicked(int candidateId) {
				//Grab candidate and show
				initView(modelMap.get(candidateId));
			}
			
			@Override
			public void onBackClicked(int candidateId) {
				initView(modelMap.get(candidateId));
			}
		});
		
		candidateWidget.setInitName(initName);
		GWT.log("InitName is "+initName);
		
		nameWidget.setWidget(candidateWidget);
		initView(model);
		}else{
			GWT.log("Candidates Map is NULL !!!");
		}
	}
	
	private void initView(CandidateModel model){
		votesScore.setInnerText(model.getFormattedVotes());
		votesPercentage.setInnerText(model.getPercentage());
		doConstituencyNameSetting(model.getConstituencyName(), consWidget);
		if(model.getAvatar().equals(ModuleConstants.EMPTY_IMG) && model.getType().equals(ModuleConstants.PALIAMENTARIAN)){
			avatar.setUrl(ModuleConstants.DEFAULT_IMG);
		}else if(model.getType().equals(ModuleConstants.PALIAMENTARIAN) && !model.getAvatar().equals(ModuleConstants.EMPTY_IMG)){
			avatar.setUrl(model.getAvatar());
		}
		
		if(model.getCode() != null && model.getType().equals(ModuleConstants.PRESIDENTIAL) && model.getCode().length() > 2){
			avatar.setUrl(ModuleConstants.PRESIDENTIAL_FOLDER + model.getCode() + ModuleConstants.IMAGE_EXTENSION);
		}else if((model.getCode() == null || model.getCode().length() <= 1) && model.getType().equals(ModuleConstants.PRESIDENTIAL)){
			avatar.setUrl(ModuleConstants.DEFAULT_IMG);
		}
		
		logo.setUrl(model.getLogo());
		system.fireChange(new CandidateNullingEvent());
		showComponents();
	}
	
	private void doConstituencyNameSetting(String constituency, DivElement div){
		final int NAME_LENGTH_LIMIT = 25;
		
		if(constituency.length() > NAME_LENGTH_LIMIT){
			//HTML marquee = new HTML("<marquee direction=\"left\">"+constituency+"</marquee>");
			div.setInnerHTML("<marquee direction=\"left\">"+constituency+"</marquee>");
			//div.appendChild(marquee.getElement());
		}else{
			div.setInnerText(constituency);
		}
	}
	
	private void showComponents(){
		imageDiv.setAttribute("style", "visibility:visible;");
		detailsDiv.setAttribute("style", "visibility:visible;");
	}
}
