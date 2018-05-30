package com.beta.election.media.client;

import static com.google.gwt.query.client.GQuery.$;

import java.util.ArrayList;
import java.util.HashMap;

import com.beta.election.media.client.analysis.split.presenter.AnalysisSplitPresenter;
import com.beta.election.media.client.analysis.split.view.AnalysisSplitView;
import com.beta.election.media.client.candidates.presenter.CandidatesPresenter;
import com.beta.election.media.client.candidates.view.CandidatesView;
import com.beta.election.media.client.constants.ModuleConstants;
import com.beta.election.media.client.constants.URLConstants;
import com.beta.election.media.client.dashboard.presenter.DashBoardPresenter;
import com.beta.election.media.client.dashboard.view.DashBoardView;
import com.beta.election.media.client.events.AnalysisEvent;
import com.beta.election.media.client.events.CandidateLoadEvent;
import com.beta.election.media.client.events.CandidateNullingEvent;
import com.beta.election.media.client.events.CandidatesEvent;
import com.beta.election.media.client.events.ConstituencyChangeEvent;
import com.beta.election.media.client.events.DashBoardEvent;
import com.beta.election.media.client.events.DateChangeEvent;
import com.beta.election.media.client.events.DistrictChangeEvent;
import com.beta.election.media.client.events.HistoryEvent;
import com.beta.election.media.client.events.MapToRegionChangeEvent;
import com.beta.election.media.client.events.MapsEvent;
import com.beta.election.media.client.events.PaliamentaryEvent;
import com.beta.election.media.client.events.PointEvent;
import com.beta.election.media.client.events.PollChangeEvent;
import com.beta.election.media.client.events.PollLoadEvent;
import com.beta.election.media.client.events.PollsEvent;
import com.beta.election.media.client.events.PresidentialRadioEvent;
import com.beta.election.media.client.events.RegionAnalysisEvent;
import com.beta.election.media.client.events.RegionChangeEvent;
import com.beta.election.media.client.events.RegionLoadEvent;
import com.beta.election.media.client.events.RegionsEvent;
import com.beta.election.media.client.events.UserLogoutEvent;
import com.beta.election.media.client.handlers.AnalysisEventHandler;
import com.beta.election.media.client.handlers.CandidateLoadEventHandler;
import com.beta.election.media.client.handlers.CandidateNullingEventHandler;
import com.beta.election.media.client.handlers.CandidatesEventHandler;
import com.beta.election.media.client.handlers.ConstituencyChangeEventHandler;
import com.beta.election.media.client.handlers.DashBoardEventHandler;
import com.beta.election.media.client.handlers.DateChangeEventHandler;
import com.beta.election.media.client.handlers.DistrictChangeEventHandler;
import com.beta.election.media.client.handlers.HistoryEventHandler;
import com.beta.election.media.client.handlers.MapToRegionChangeEventHandler;
import com.beta.election.media.client.handlers.MapsEventHandler;
import com.beta.election.media.client.handlers.PaliamentaryEventHandler;
import com.beta.election.media.client.handlers.PointEventHandler;
import com.beta.election.media.client.handlers.PollChangeEventHandler;
import com.beta.election.media.client.handlers.PollLoadEventHandler;
import com.beta.election.media.client.handlers.PollsEventHandler;
import com.beta.election.media.client.handlers.PresidentialRadioHandler;
import com.beta.election.media.client.handlers.RegionAnalysisEventHandler;
import com.beta.election.media.client.handlers.RegionChangeEventHandler;
import com.beta.election.media.client.handlers.RegionLoadEventHandler;
import com.beta.election.media.client.handlers.RegionsEventHandler;
import com.beta.election.media.client.handlers.UserLogoutEventHandler;
import com.beta.election.media.client.history.presenter.HistoryPresenter;
import com.beta.election.media.client.history.view.HistoryView;
import com.beta.election.media.client.map.presenter.MapsPresenter;
import com.beta.election.media.client.map.view.MapsView;
import com.beta.election.media.client.polls.presenter.PollsPresenter;
import com.beta.election.media.client.polls.view.PollsView;
import com.beta.election.media.client.region.analysis.presenter.RegionAnalysisPresenter;
import com.beta.election.media.client.region.analysis.view.RegionAnalysisView;
import com.beta.election.media.client.region.presenter.RegionPresenter;
import com.beta.election.media.client.region.view.RegionView;
import com.beta.election.media.client.rpc.RPCListener;
import com.beta.election.media.client.system.SystemVariables;
import com.beta.election.media.client.widgets.DateWidget;
import com.beta.election.media.client.widgets.GenericSearchBox;
import com.beta.election.media.client.widgets.PresidentialUpdateChart;
import com.beta.election.media.client.widgets.SeatsWidget;
import com.beta.election.media.client.widgets.UserWidget;
import com.beta.election.media.client.widgets.UsersSearchBox;
import com.beta.election.media.shared.CandidateModel;
import com.beta.election.media.shared.RegionModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class AppController implements Presenter, URLConstants,
		ValueChangeHandler<String> {

	private RootPanel datePanel, leftPanel, userPanel, leftFilterPanel,
			rightFilterPanel, dashboard, polls, analysis, region, candidate,
			maps, history, presRadio, paliaRadio, prezUpdateChart, seatUpdateContainer, regionAnalysis;
	private RetrieveServiceAsync rpc;
	private HandlerManager eventBus;
	private HasWidgets container;
	private RPCListener listener;
	private HashMap<Integer, String> constituencySearchMap, candidateSearchMap;
	private SuggestBox constituencySearchWidget, candidateSearchWidget;
	private ArrayList<String> locationsPoints;
	private int candidateId;
	private String activeModule = "";
	private CandidateModel candidateModel;
	private SystemVariables system;
	private int accordionChecker = 0, regionSize = 0;
	private HashMap<String, HashMap<Integer, String>> accordionRegionMap;

	public AppController(RetrieveServiceAsync rpc, HandlerManager eventBus,
			RootPanel datePanel, RootPanel userPanel, RootPanel leftFilter,
			RootPanel rightFilter, RootPanel dashboard, RootPanel polls,
			RootPanel analysis, RootPanel region, RootPanel candidate,
			RootPanel map, RootPanel history, RootPanel presRadio, RootPanel paliaRadio, RootPanel prezUpdateChart, RootPanel seatUpdateContainer, RootPanel regionAnalysis) {
		this.datePanel = datePanel;
		// this.leftPanel = leftPanel;
		this.userPanel = userPanel;
		this.leftFilterPanel = leftFilter;
		this.rightFilterPanel = rightFilter;
		// Module elements
		this.dashboard = dashboard;
		this.polls = polls;
		this.analysis = analysis;
		this.region = region;
		this.candidate = candidate;
		this.maps = map;
		this.history = history;
		this.presRadio = presRadio;
		this.paliaRadio = paliaRadio;
		this.prezUpdateChart = prezUpdateChart;
		this.seatUpdateContainer = seatUpdateContainer;
		this.regionAnalysis = regionAnalysis;
		
		this.rpc = rpc;
		this.eventBus = eventBus;
		bind();
		initDOMEvent();
		doInitLoading();
		//showStartUpModule();
	}

	private void initDOMEvent() {
		com.google.gwt.user.client.Element dashboardElement = dashboard
				.getElement().cast();
		DOM.sinkEvents(dashboardElement, Event.ONCLICK);
		DOM.setEventListener(dashboardElement, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("Dashboard clicked !!!");
				eventBus.fireEvent(new DashBoardEvent());
			}
		});

		com.google.gwt.user.client.Element pollsElement = polls.getElement()
				.cast();
		DOM.sinkEvents(pollsElement, Event.ONCLICK);
		DOM.setEventListener(pollsElement, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("Polls clicked !!!");
				eventBus.fireEvent(new PollsEvent());
			}
		});

		com.google.gwt.user.client.Element analysisElement = analysis
				.getElement().cast();
		DOM.sinkEvents(analysisElement, Event.ONCLICK);
		DOM.setEventListener(analysisElement, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("Analysis clicked !!!");
				eventBus.fireEvent(new AnalysisEvent());
			}
		});

		com.google.gwt.user.client.Element regionElement = region.getElement()
				.cast();
		DOM.sinkEvents(regionElement, Event.ONCLICK);
		DOM.setEventListener(regionElement, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("Region clicked !!!");
				eventBus.fireEvent(new RegionsEvent());
			}
		});

		com.google.gwt.user.client.Element candidateElement = candidate
				.getElement().cast();
		DOM.sinkEvents(candidateElement, Event.ONCLICK);
		DOM.setEventListener(candidateElement, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("Candidate clicked !!!");
				eventBus.fireEvent(new CandidatesEvent());
			}
		});

		com.google.gwt.user.client.Element mapElement = maps.getElement()
				.cast();
		DOM.sinkEvents(mapElement, Event.ONCLICK);
		DOM.setEventListener(mapElement, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("Maps clicked !!!");
				eventBus.fireEvent(new MapsEvent());
			}
		});
		
		com.google.gwt.user.client.Element historyElement = history.getElement()
			.cast();
		DOM.sinkEvents(historyElement, Event.ONCLICK);
		DOM.setEventListener(historyElement, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("History clicked !!!");
				eventBus.fireEvent(new HistoryEvent(system.getConstituencyId(), system.getConstituencyName()));
			}
		});
		
		com.google.gwt.user.client.Element presRadioElement = presRadio.getElement()
		.cast();
		DOM.sinkEvents(presRadioElement, Event.ONCLICK);
		DOM.setEventListener(presRadioElement, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("Pres clicked !!!");
				eventBus.fireEvent(new PresidentialRadioEvent());
			}
		});
		
		com.google.gwt.user.client.Element paliaRadioElement = paliaRadio.getElement()
		.cast();
		DOM.sinkEvents(paliaRadioElement, Event.ONCLICK);
		DOM.setEventListener(paliaRadioElement, new EventListener() {

			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("Palia clicked !!!");
				eventBus.fireEvent(new PaliamentaryEvent());
			}
		});
		
		com.google.gwt.user.client.Element regionAnalysisElement = regionAnalysis.getElement().cast();
		DOM.sinkEvents(regionAnalysisElement, Event.ONCLICK);
		DOM.setEventListener(regionAnalysisElement, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				GWT.log("Region Analysis clicked !!!");
				eventBus.fireEvent(new RegionAnalysisEvent());
			}
		});
	}

	private void doInitLoading() {
		loadDatePanel();
		loadUserPanel();
		//doTypeInitSelection();
		//loadConstituencySearchBox();
		//loadCandidateSearchBox();
	}
	
	private void doTypeInitSelection(){
		eventBus.fireEvent(new PresidentialRadioEvent());
	}
	
	private void showStartUpModule(){
		eventBus.fireEvent(new RegionsEvent());
	}

	private void loadDatePanel() {
		ArrayList<Integer> datesList = new ArrayList<Integer>();
		datesList.add(1992);
		datesList.add(1996);
		datesList.add(2000);
		datesList.add(2004);
		datesList.add(2008);
		datesList.add(2012);
		datesList.add(2016);

		Presenter date = new DateWidget(eventBus, datesList);
		date.go(datePanel);
	}

	private void loadUserPanel() {
		Presenter user = new UserWidget(eventBus);
		user.go(userPanel);
	}

	private void loadCandidateSearchBox() {
		String year = "" + SystemVariables.getSystemInstance().getSystemYear();

		// Fetching all the candidates from the system filtering by the year
		rpc.getAllCandidates(year,
				new AsyncCallback<HashMap<Integer, String>>() {

					@Override
					public void onSuccess(HashMap<Integer, String> result) {
						if (result != null) {
							candidateSearchMap = new HashMap<Integer, String>();
							MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
							for (Integer key : result.keySet()) {
								oracle.add(result.get(key));
								candidateSearchMap.put(key, result.get(key));
								GWT.log("Key is " + key + " value is "
										+ result.get(key));
							}

							candidateSearchWidget = new SuggestBox(oracle,
									new UsersSearchBox());
							candidateSearchWidget
									.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

										@Override
										public void onSelection(
												SelectionEvent<Suggestion> event) {
											// Find current module and execute
											String text = event
													.getSelectedItem()
													.getReplacementString();
											candidateId = getSearchKey(text,
													CAN);
											//constituencyId = 0;
											reInitModule(false);
											resetSearchFilters();
										}
									});

							leftFilterPanel.clear();
							leftFilterPanel.add(candidateSearchWidget);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void loadConstituencySearchBox() {

		rpc.getAllConstituencies(SystemVariables.getSystemInstance().getSystemYear()+"", new AsyncCallback<HashMap<String, String>>() {

			@Override
			public void onSuccess(HashMap<String, String> result) {
				if (result != null) {
					constituencySearchMap = new HashMap<Integer, String>();
					MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
					for (String key : result.keySet()) {
						oracle.add(result.get(key));
						constituencySearchMap.put(Integer.parseInt(key),
								result.get(key));
						GWT.log("Cons Key is " + key + " value is "
								+ result.get(key));
					}
					constituencySearchMap.put(0, "All");
					oracle.add("All");

					constituencySearchWidget = new SuggestBox(oracle,
							new GenericSearchBox());
					constituencySearchWidget
							.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

								@Override
								public void onSelection(
										SelectionEvent<Suggestion> event) {
									// Find current module and execute
									String text = event.getSelectedItem()
											.getReplacementString();
									//constituencyId = getSearchKey(text, CONS);
									//backUpConstituencyId = constituencyId;
									
									reInitModule(false);
									resetSearchFilters();
								}
							});

					rightFilterPanel.clear();
					rightFilterPanel.add(constituencySearchWidget);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Call failed in retrieving constituencies !!!");
			}
		});
	}

	private int getSearchKey(String value, String type) {
		if (type.equals(CAN)) {
			for (Integer key : candidateSearchMap.keySet()) {
				String tmpValue = candidateSearchMap.get(key);
				if (value.equals(tmpValue)) {
					return key;
				}
			}
		} else if (type.equals(CONS)) {
			for (Integer key : constituencySearchMap.keySet()) {
				String tmpValue = constituencySearchMap.get(key);
				if (value.equals(tmpValue)) {
					return key;
				}
			}
		}
		return 0;
	}

	private void reInitModule(boolean isSystemChange) {
		
		//Execute Date change if true
		if (isSystemChange) {
			if (activeModule.equals(CANDIDATES)) {
				GWT.log("Candidate Module Trigged !!!");
				History.newItem(CANDIDATES);
				History.fireCurrentHistoryState();
			} else if (activeModule.equals(POLLS)) {
				GWT.log("Polls Module Trigged !!!");
				eventBus.fireEvent(new PollsEvent());
				History.fireCurrentHistoryState();
			} else if(activeModule.equals(HISTORY)){
				eventBus.fireEvent(new HistoryEvent(system.getParentConstituencyId(), system.getConstituencyName()));
				History.fireCurrentHistoryState();
			} else if(activeModule.equals(REGIONS)){
				eventBus.fireEvent(new RegionChangeEvent(system.getRegionId()));
				History.fireCurrentHistoryState();
			} else if(activeModule.equals(ANALYSIS)){
				eventBus.fireEvent(new AnalysisEvent());
				History.fireCurrentHistoryState();
			} else if(activeModule.equals(REGION_ANALYSIS)){
				eventBus.fireEvent(new RegionAnalysisEvent());
				History.fireCurrentHistoryState();
			}
			
			//Execute normally if no change on Date
		} else {
			
			if (activeModule.equals(CANDIDATES)) {
				GWT.log("Candidate Module Trigged !!!");
				History.newItem(CANDIDATES);
				History.fireCurrentHistoryState();
			} else if (activeModule.equals(POLLS)) {
				GWT.log("Polls Module Trigged !!!");
				//eventBus.fireEvent(new PollsEvent());
				History.newItem(POLLS);
				History.fireCurrentHistoryState();
			} else if(activeModule.equals(REGIONS)){
				GWT.log("In Region Search Module Trigged !!!");
				
				History.newItem(HISTORY);
				History.fireCurrentHistoryState();
			}
		}
	}

	private void highliteModule(String module) {
		String[] moduleId = { DASH_LI, CAN_LI, MAP_LI, REGION_LI, ANALYSIS_LI,
				POLL_LI, HISTORY_LI };
		String[] styles = {"dashboard", "ui", "forms", "tables", "charts", "inbox", "history"};

		for (int i = 0; i < moduleId.length; i++) {
			if (module.equals(moduleId[i])) {
				//String oldStyle = RootPanel.get(moduleId[i]).getElement().getAttribute("class");
				//String newStyle = "active_tab " + oldStyle;
				String newStyle = "active_tab i_32_"+styles[i];
				$("#" + moduleId[i]).attr("class", newStyle);
			} else {
				/*String oldStyle = RootPanel.get(moduleId[i]).getElement()
						.getAttribute("class");
				String newStyle = "";
				if (oldStyle.split("[\\s]").length == 2) {
					newStyle = oldStyle.split("[\\s]")[1];
				} else {
					newStyle = oldStyle.split("[\\s]")[0];
				}*/
				$("#" + moduleId[i]).attr("class", "i_32_"+styles[i]);
			}
		}

	}
	
	private void showInitialChart(){
		PresidentialUpdateChart chart = new PresidentialUpdateChart(rpc);
		chart.render();
		
		prezUpdateChart.clear();
		prezUpdateChart.add(chart);
	}
	
	private void showInitialSeat(){
		SeatsWidget seatWidget = new SeatsWidget(rpc);
		seatUpdateContainer.clear();
		seatUpdateContainer.add(seatWidget);
	}

	private void bind() {
		showInitialChart();
		showInitialSeat();
		
		History.addValueChangeHandler(this);
		system = SystemVariables.getSystemInstance();
		
		/**
		 * Initializing the left panel modules
		 */

		eventBus.addHandler(DashBoardEvent.TYPE, new DashBoardEventHandler() {

			@Override
			public void onDashboardClicked(DashBoardEvent event) {
				activeModule = DASHBOARD;
				setCollapse(true);
				History.newItem(DASHBOARD);
				//highliteModule(DASH_LI);
			}
		});

		eventBus.addHandler(PollsEvent.TYPE, new PollsEventHandler() {

			@Override
			public void onPollsClicked(PollsEvent event) {
				activeModule = POLLS;
				String[] filters = { "left_filter", "right_filter" };

				setCollapse(false);
				showFilters(filters);
				//highliteModule(POLL_LI);
				History.newItem(POLLS);
				History.fireCurrentHistoryState();
			}
		});

		eventBus.addHandler(AnalysisEvent.TYPE, new AnalysisEventHandler() {

			@Override
			public void onAnalysisClicked(AnalysisEvent event) {
				setCollapse(true);
				activeModule = ANALYSIS;
				hideFilters("left_filter", "right_filter");
				
				History.newItem(ANALYSIS);
				//highliteModule(ANALYSIS_LI);
			}
		});

		eventBus.addHandler(CandidatesEvent.TYPE, new CandidatesEventHandler() {

			@Override
			public void onCandidatesClicked(CandidatesEvent event) {
				setCollapse(false);
				activeModule = CANDIDATES;
				hideFilters("left_filter");
				showFilters("right_filter");

				//highliteModule(CAN_LI);
				History.newItem(CANDIDATES);
				History.fireCurrentHistoryState();

			}
		});

		eventBus.addHandler(RegionsEvent.TYPE, new RegionsEventHandler() {

			@Override
			public void onRegionsClicked(RegionsEvent event) {
				setCollapse(false);
				activeModule = REGIONS;
				hideFilters("left_filter");
				showFilters("right_filter");
				doAccordionLoading();
				//highliteModule(REGION_LI);
			}
		});

		eventBus.addHandler(MapsEvent.TYPE, new MapsEventHandler() {

			@Override
			public void onMapsClicked(MapsEvent event) {
				setCollapse(true);
				
				activeModule = MAPS;
				//highliteModule(MAP_LI);
				History.newItem(MAPS);
				//doLocationsCall();
			}
		});

		eventBus.addHandler(PollChangeEvent.TYPE, new PollChangeEventHandler() {

			@Override
			public void onPollChanged(PollChangeEvent event) {
				candidateModel = event.getModel();
				//backUpConstituencyId = event.getModel().getConstituencyId();
				if (candidateModel == null) {
					GWT.log("CandidateModel is NULL !!!");
					return;
				}
				eventBus.fireEvent(new CandidatesEvent());
			}
		});
		
		eventBus.addHandler(HistoryEvent.TYPE, new HistoryEventHandler() {
			
			@Override
			public void onHistoryClicked(HistoryEvent event) {
				setCollapse(false);
				activeModule = HISTORY;
				hideFilters("left_filter");
				showFilters("right_filter");
				GWT.log("Constituency id is "+event.getConstituencyId());
				//highliteModule(HISTORY_LI);
				History.newItem(HISTORY);
			}
		});
		
		eventBus.addHandler(RegionAnalysisEvent.TYPE, new RegionAnalysisEventHandler() {
			
			@Override
			public void onRegionAnalysisClicked(RegionAnalysisEvent event) {
				activeModule = REGION_ANALYSIS;
				History.newItem(REGION_ANALYSIS);
			}
		});

		/**
		 * Change events starts here
		 */

		eventBus.addHandler(RegionChangeEvent.TYPE,
				new RegionChangeEventHandler() {

					@Override
					public void onRegionChanged(RegionChangeEvent event) {
						SystemVariables.getSystemInstance().setRegionId(
								event.getRegionId());
					}
				});

		eventBus.addHandler(ConstituencyChangeEvent.TYPE,
				new ConstituencyChangeEventHandler() {

					@Override
					public void onConstituencyChanged(
							ConstituencyChangeEvent event) {
						SystemVariables.getSystemInstance().setConstituencyId(
								event.getConstituencyId());
					}
				});

		eventBus.addHandler(DistrictChangeEvent.TYPE,
				new DistrictChangeEventHandler() {

					@Override
					public void onDistrictChanged(DistrictChangeEvent event) {
						SystemVariables.getSystemInstance().setDistrictId(
								event.getDistrictId());
					}
				});

		eventBus.addHandler(DateChangeEvent.TYPE, new DateChangeEventHandler() {

			@Override
			public void onDateChanged(DateChangeEvent event) {
				SystemVariables.getSystemInstance().setCandidateYear(
						event.getDate());
				SystemVariables.getSystemInstance()
						.setPollYear(event.getDate());
				SystemVariables.getSystemInstance().setSystemYear(
						event.getDate());
				
				Timer timer = new Timer() {
					
					@Override
					public void run() {
						fetchNewConstituency();
					}
				};
				timer.schedule(ModuleConstants.DATE_DELAY);
				
			}
		});

		eventBus.addHandler(UserLogoutEvent.TYPE, new UserLogoutEventHandler() {

			@Override
			public void onUserLogout(UserLogoutEvent event) {
				History.newItem(LOGIN);
			}
		});

		/**
		 * Load Events starts from here
		 */

		eventBus.addHandler(RegionLoadEvent.TYPE, new RegionLoadEventHandler() {

			@Override
			public void onRegionLoadFired(RegionLoadEvent event) {
				// Make regions rpc call
				doRegionCall();
			}
		});

		eventBus.addHandler(CandidateLoadEvent.TYPE,
				new CandidateLoadEventHandler() {

					@Override
					public void onCandidateLoadFired(CandidateLoadEvent event) {
						// Set constituency id and make call
						SystemVariables.getSystemInstance().setConstituencyId(
								event.getConstituencyId());
						doCandidateCall();
					}
				});

		eventBus.addHandler(PollLoadEvent.TYPE, new PollLoadEventHandler() {

			@Override
			public void onPollsLoadFired(PollLoadEvent event) {
				// Set poll year and make call
				SystemVariables.getSystemInstance()
						.setPollYear(event.getYear());
			}
		});

		eventBus.addHandler(PointEvent.TYPE, new PointEventHandler() {

			@Override
			public void onPointClicked(PointEvent event) {
				// Get Point and work with it
				GWT.log("From APP, coordinate are " + event.getPoint());
			}
		});
		
		eventBus.addHandler(PresidentialRadioEvent.TYPE, new PresidentialRadioHandler() {
			
			@Override
			public void onPresidentialRadioClicked(PresidentialRadioEvent event) {
				//Set system candidate type to "P" and reload
				SystemVariables.getSystemInstance().setCandidateType(ModuleConstants.PRESIDENTIAL);
				reInitModule(true);
			}
		});
		
		eventBus.addHandler(PaliamentaryEvent.TYPE, new PaliamentaryEventHandler() {
			
			@Override
			public void onPaliamentaryRadioClicked(PaliamentaryEvent event) {
				//Set system candidate type to "M" and reload
				SystemVariables.getSystemInstance().setCandidateType(ModuleConstants.PALIAMENTARIAN);
				reInitModule(true);
			}
		});
		
		eventBus.addHandler(MapToRegionChangeEvent.TYPE, new MapToRegionChangeEventHandler() {
			
			@Override
			public void onMapToRegionChangeEventHandlerFired(
					MapToRegionChangeEvent event) {
					History.newItem(REGIONS);
			}
		});
		
		eventBus.addHandler(CandidateNullingEvent.TYPE, new CandidateNullingEventHandler() {
			
			@Override
			public void onCandidateNullingEventHandlerFired(CandidateNullingEvent event) {
				doNulling();
			}
		});
		
		system.setHandlerManager(eventBus);
		
		//doPeriodicUpdate();
		//doPeriodicSeatUpdate();
	}

	private void doAccordionLoading(){
		rpc.getRegions(new AsyncCallback<ArrayList<RegionModel>>() {
			
			@Override
			public void onSuccess(ArrayList<RegionModel> result) {
				if(result != null){
					accordionRegionMap = new HashMap<String, HashMap<Integer, String>>();
					regionSize = result.size();
					accordionChecker = 0;
					for(final RegionModel model : result){
						rpc.getConstituenciesByRegion(model.getId(), system.getSystemYear()+"", new AsyncCallback<HashMap<Integer,String>>() {

							@Override
							public void onSuccess(HashMap<Integer, String> result) {
								accordionChecker ++;
								if(result != null){
									accordionRegionMap.put(model.getId()+","+model.getRegionName(), result);
									if(accordionChecker == regionSize){
										History.newItem(REGIONS);
									}
								}
							}
							
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}
						});
						
					}
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void doRegionCall() {
		rpc.getRegions(new AsyncCallback<ArrayList<RegionModel>>() {

			@Override
			public void onSuccess(ArrayList<RegionModel> result) {
				if (result != null && listener != null) {
					listener.onRegionsLoad(result);
				} else {
					GWT.log("Null in region load !!!");
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Region call failed !!!");
			}
		});
	}


	private void doCandidateCall() {
		int constituencyId = SystemVariables.getSystemInstance()
				.getConstituencyId();
		String year = SystemVariables.getSystemInstance().getSystemYear() + "";
		rpc.getCandidates(SystemVariables.getSystemInstance().getCandidateType(), year, constituencyId,
				new AsyncCallback<ArrayList<CandidateModel>>() {

					@Override
					public void onSuccess(ArrayList<CandidateModel> result) {
						if (result != null && listener != null) {
							listener.onCandidatesLoad(result);
						} else {
							GWT.log("Null in candidate load !!!");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Candidate call failed !!!");
					}
				});
	}


	private void hideFilters(String... ids) {
		for (String id : ids) {
			$("#" + id).css("visibility", "hidden");
		}
	}

	private void showFilters(String... ids) {
		for (String id : ids) {
			$("#" + id).css("visibility", "visible");
		}
	}

	private void setCollapse(boolean isCollapse) {
		if (isCollapse) {
			$("#module_filter").css("display", "none");
		} else {
			$("#module_filter").css("display", "block");
		}
	}

	private void resetSearchFilters() {
		candidateId = 0;
		//constituencyId = 0;
		if (constituencySearchWidget != null && candidateSearchWidget != null) {
			constituencySearchWidget.setText("");
			candidateSearchWidget.setText("");
		}

	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		Presenter presenter = null;
		String token = event.getValue();

		if (token.equals(LOGIN)) {
			// Serve login screen
		} else if (token.equals(HOME)) {
			// Serve home screen
		} else if (token.equals(CANDIDATES)) {
			// Serve candidate
			GWT.log("$ Constituency ID IS "+system.getConstituencyId());
			presenter = new CandidatesPresenter(eventBus, rpc,
					new CandidatesView(), candidateModel, system.getConstituencyId());
		} else if (token.equals(ANALYSIS)) {
			// Serve analysis
			presenter = new AnalysisSplitPresenter(rpc, new AnalysisSplitView());
		} else if (token.equals(POLLS)) {
			// Serve polls
			presenter = new PollsPresenter(rpc, eventBus, new PollsView(),
					system.getConstituencyId(), candidateId);

		} else if (token.equals(MAPS)) {
			// Serve maps
			presenter = new MapsPresenter(new MapsView(), eventBus);
		} else if (token.equals(DASHBOARD)) {
			// Serve dashboard
			presenter = new DashBoardPresenter(new DashBoardView(), eventBus);
		} else if (token.equals(REGIONS)){
			//Serve Region
			presenter = new RegionPresenter(eventBus, rpc, new RegionView(), system.getRegionId(), accordionRegionMap);
		} else if (token.equals(HISTORY)){
			//Serve History
			presenter = new HistoryPresenter(system.getParentConstituencyId(), eventBus, rpc, new HistoryView());
		} else if(token.equals(REGION_ANALYSIS)){
			//Serve Region Analysis
			presenter = new RegionAnalysisPresenter(new RegionAnalysisView(), eventBus, rpc);
		}

		if (presenter != null) {
			presenter.go(container);
		}
		
	}
	
	private void doPeriodicUpdate(){
		int INTERVAL = (60*1000)*3;
		Timer update = new Timer() {
			
			@Override
			public void run() {
				PresidentialUpdateChart chart = new PresidentialUpdateChart(rpc);
				prezUpdateChart.clear();
				prezUpdateChart.add(chart);
				chart.render();
				
			}
		};
		
		update.scheduleRepeating(INTERVAL);
	}
	
	private void doPeriodicSeatUpdate(){
		int INTERVAL = (60*1000)*3;
		Timer update = new Timer() {
			
			@Override
			public void run() {
				SeatsWidget seatUpdate = new SeatsWidget(rpc);
				seatUpdateContainer.clear();
				seatUpdateContainer.add(seatUpdate);
			}
		};
		
		update.scheduleRepeating(INTERVAL);
	}
	
	private void fetchNewConstituency(){
		rpc.getConstituencyIdWithGlobalIdAndYear(system.getParentConstituencyId(), system.getSystemYear()+"", new AsyncCallback<Integer>() {
			
			@Override
			public void onSuccess(Integer result) {
				if(result > 0){
					system.setConstituencyId(result);
					reInitModule(true);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void doNulling(){
		candidateModel = null;
	}
	
	@Override
	public void go(HasWidgets screen) {
		this.container = screen;
		container.clear();

		if ("".equals(History.getToken())) {
			History.newItem(MAPS);
		} else {
			History.fireCurrentHistoryState();
		}
	}
}
