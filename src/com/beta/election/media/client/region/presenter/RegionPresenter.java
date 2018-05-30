package com.beta.election.media.client.region.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.beta.election.media.client.Presenter;
import com.beta.election.media.client.RetrieveServiceAsync;
import com.beta.election.media.client.events.HistoryEvent;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.widgets.ConstituencyWidget;
import com.beta.election.media.client.widgets.handlers.ConstituencyWidgetListener;
import com.beta.election.media.shared.RegionModel;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class RegionPresenter implements Presenter{

	private Display view;
	private HandlerManager eventBus;
	private RetrieveServiceAsync rpc;
	private int regionId = 0;
	//private int regionCounter = 0;
	private HashMap<String, HashMap<Integer, String>> accordionData;
	
	public interface Display extends IsWidget{
		Widget asWidget();
		void addSection(int id, String header, IsWidget widget);
		void setActiveTab(int regionId);
		void load();
	}
	
	public RegionPresenter(HandlerManager eventBus, RetrieveServiceAsync rpc, Display view, int regionId, HashMap<String, HashMap<Integer, String>> accordionData){
		this.view = view;
		this.eventBus = eventBus;
		this.rpc = rpc;
		this.regionId = regionId;
		this.accordionData = accordionData;
	}
	
	private void bind(){
		doInit();
	}
	
	private void doInit(){
		if(accordionData != null){
			for(String idAndName : accordionData.keySet()){
				String id = idAndName.split("[,]")[0];
				String regionName = idAndName.split("[,]")[1];
				
				HashMap<Integer, String> constituencyMap = accordionData.get(idAndName);
//				FlowPanel panel = new FlowPanel();
//				panel.getElement().setAttribute("style", "width:725px;");
//				for(Integer consId : constituencyMap.keySet()){
//					String constituencyName = constituencyMap.get(consId);
//					ConstituencyWidget widget = new ConstituencyWidget(consId, constituencyName, new ConstituencyWidgetListener() {
//						
//						@Override
//						public void onConstituencyWidgetClicked(int id, final String constituencyName) {
//							SystemVariables.getSystemInstance().setConstituencyId(id);
//							rpc.getGlobalIdByConstituencyId(id, new AsyncCallback<Integer>() {
//								
//								@Override
//								public void onSuccess(Integer result) {
//									if(result > 0){
//										SystemVariables.getSystemInstance().setParentConstituencyId(result);
//										SystemVariables.getSystemInstance().setConstituencyName(constituencyName);
//										eventBus.fireEvent(new HistoryEvent(result, constituencyName));
//									}
//								}
//								
//								@Override
//								public void onFailure(Throwable caught) {
//									// TODO Auto-generated method stub
//									
//								}
//							});
//						}
//					});
//					panel.add(widget);
//				}
				
//				doAccordionConstruction(Integer.parseInt(id), regionName, panel);
				doAccordionConstruction(Integer.parseInt(id), regionName, getArrangedConsWidget(constituencyMap));

			}
			if(regionId > 0){
				view.setActiveTab(regionId);
			}
		}
	}
	
	private FlowPanel getArrangedConsWidget(HashMap<Integer, String> idAndNameMap){
		if(idAndNameMap != null){
			FlowPanel panel = new FlowPanel();
			panel.getElement().setAttribute("style", "width:725px;");

			TreeSet<String> nameSet = new TreeSet<String>();
			for(Integer key : idAndNameMap.keySet()){
				String name = idAndNameMap.get(key);
				nameSet.add(name.trim());
			}
			
			if(nameSet.size() == idAndNameMap.size()){
				ArrayList<Integer> idList = new ArrayList<Integer>();
				ArrayList<String> nameList = new ArrayList<String>();
				
				for(String name : nameSet){
					inner: for(Integer id : idAndNameMap.keySet()){
						if(idAndNameMap.get(id).trim().equals(name)){
							idList.add(id);
							nameList.add(name);
							break inner;
						}
					}
				}
				if(nameList.size() == idList.size()){
					for(int i = 0; i < nameList.size(); i++){
						ConstituencyWidget consWidget = new ConstituencyWidget(idList.get(i), nameList.get(i), new ConstituencyWidgetListener() {
							
							@Override
							public void onConstituencyWidgetClicked(int id, final String constituencyName) {
								SystemVariables.getSystemInstance().setConstituencyId(id);
								rpc.getGlobalIdByConstituencyId(id, new AsyncCallback<Integer>() {
									
									@Override
									public void onSuccess(Integer result) {
										if(result > 0){
											SystemVariables.getSystemInstance().setParentConstituencyId(result);
											SystemVariables.getSystemInstance().setConstituencyName(constituencyName);
											eventBus.fireEvent(new HistoryEvent(result, constituencyName));
										}
									}
									
									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										
									}
								});
							}
						});
						panel.add(consWidget);
					}
				}
				
			}
			return panel;
		}
		return null;
	}
	
	
//	private void doWidgetsLoading(final RegionModel region){
//		//Get all parent constituencies
//		rpc.getConstituenciesByRegion(region.getId(), ""+SystemVariables.getSystemInstance().getSystemYear(), new AsyncCallback<HashMap<Integer,String>>() {
//			
//			@Override
//			public void onSuccess(HashMap<Integer, String> result) {
//				if(result != null){
//					final FlowPanel container = new FlowPanel();
//					container.getElement().setAttribute("style", "width:725px;overflow-x:hidden;");
//					for(Integer id : result.keySet()){
//						ConstituencyWidget widget = new ConstituencyWidget(id, result.get(id), new ConstituencyWidgetListener() {
//							
//							@Override
//							public void onConstituencyWidgetClicked(int id, final String constituencyName) {
//								SystemVariables.getSystemInstance().setConstituencyId(id);
//								rpc.getGlobalIdByConstituencyId(id, new AsyncCallback<Integer>() {
//									
//									@Override
//									public void onSuccess(Integer result) {
//										if(result > 0){
//											SystemVariables.getSystemInstance().setParentConstituencyId(result);
//											SystemVariables.getSystemInstance().setConstituencyName(constituencyName);
//											eventBus.fireEvent(new HistoryEvent(result, constituencyName));
//										}
//									}
//									
//									@Override
//									public void onFailure(Throwable caught) {
//										// TODO Auto-generated method stub
//										
//									}
//								});
//							}
//						});
//						
//						container.add(widget);
//					}
//					doAccordionConstruction(region.getId(), region.getRegionName(), container);
//					regionCounter --;
//					if(regionCounter == 1){
//						if(regionId > 0){
//							view.setActiveTab(regionId);
//						}
//					}
//				}
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//	}
	
	private void doAccordionConstruction(int id, String header, IsWidget container){
		view.addSection(id, header, container);
		view.load();
	}
	
	@Override
	public void go(HasWidgets screen) {
		screen.clear();
		bind();
		screen.add(view.asWidget());
	}

}
