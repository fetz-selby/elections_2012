package com.beta.election.media.client;

import java.util.ArrayList;
import java.util.HashMap;

import com.beta.election.media.shared.AnalysisModel;
import com.beta.election.media.shared.CandidateModel;
import com.beta.election.media.shared.ChartObject;
import com.beta.election.media.shared.CorrespondentModel;
import com.beta.election.media.shared.PartyModel;
import com.beta.election.media.shared.PartySummaryObject;
import com.beta.election.media.shared.PollsModel;
import com.beta.election.media.shared.PrezUpdateModel;
import com.beta.election.media.shared.RegionModel;
import com.beta.election.media.shared.RegionalSummaryObject;
import com.beta.election.media.shared.SeatUpdateModel;
import com.beta.election.media.shared.UserModel;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("retrieve")
public interface RetrieveService extends RemoteService{	
	ArrayList<RegionModel> getRegions();
	HashMap<Integer, String> getAllCandidates(String year);
	ArrayList<CandidateModel> getCandidates(String type, String year, int constituencyId);
	ArrayList<PollsModel> getPolls();
	ArrayList<PollsModel> getPollsUpdate();
	ArrayList<CandidateModel> getModifiedCandidates(String type, String year);
	ArrayList<CandidateModel> getModifiedCandidatesByConstituency(String type, int constituencyId, String year);
	ArrayList<PartyModel> getAllParties();
	HashMap<String, String> getAllLocations();
	HashMap<Integer, String> getCandidatesMap(String year, int constituencyId);
	HashMap<Integer, String> getYearMap();
	
	HashMap<Integer, String> getParentConstituenciesByRegion(int regionId);

	HashMap<Integer, String> getConstituenciesByParentId(int parentConstituencyId);
	
	CandidateModel getModifiedCandidateByCandidateId(int candidateId, String year);
	CandidateModel getSingleCandidateFromConstituency(int constituencyId, String year, String type);
	CorrespondentModel getCorrespondent(String id);
	String getCandidateScore(String candidateId);
	String getCandidatesTotalVotes();
	String getDistrictId(String consId);
	ArrayList<ChartObject> getAllCandidatesUpdate(ArrayList<String> candidateIds);
	ArrayList<CandidateModel> getAllCandidatesByGlobalConstituencyId(int parentConstituencyId, String type);
	ArrayList<PrezUpdateModel> getPresidentialUpdate(String year);
	HashMap<String, String> getAllConstituencies(String year);
	HashMap<String, String> getAllConstituencies();
	HashMap<Integer, String> getConstituenciesByRegion(int regionId, String year);
	ArrayList<PartyModel> getFilteredYearParties(String year, String type);
	ArrayList<SeatUpdateModel> getSeatUpdate(String year);
	
	UserModel getUserDetails(String username, String password);
	boolean isUserValid(String username, String password);
	int getConstituencyByGlobalId(String year, int constituencyId);
	int getGlobalIdByConstituencyId(int constituencyId);
	int getTotalSeats(String year);
	int getTotalDeclaredSeats(String year);
	int getConstituencyIdWithGlobalIdAndYear(int parentConstituencyId, String year);
	
	ArrayList<AnalysisModel> getSeatAnalysis(int regionId, int year);
	ArrayList<RegionalSummaryObject> getPresRegionSummary(int partyId, String year);
	ArrayList<PartySummaryObject> getPresPartySummary(int regionId, String year);
	
	ArrayList<RegionalSummaryObject> getPaliaRegionSummary(int partyId, String year);
	ArrayList<PartySummaryObject> getPaliaPartySummary(int regionId, String year);
}
