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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RetrieveServiceAsync {

	void getAllCandidatesUpdate(ArrayList<String> candidateIds,
			AsyncCallback<ArrayList<ChartObject>> callback);

	void getAllConstituencies(AsyncCallback<HashMap<String, String>> callback);

	void getCandidateScore(String candidateId, AsyncCallback<String> callback);

	void getCandidates(String type, String year, int constituencyId,
			AsyncCallback<ArrayList<CandidateModel>> callback);

	void getCandidatesTotalVotes(AsyncCallback<String> callback);

	void getCorrespondent(String id, AsyncCallback<CorrespondentModel> callback);

	void getDistrictId(String consId, AsyncCallback<String> callback);

	void getPolls(AsyncCallback<ArrayList<PollsModel>> callback);

	void getPollsUpdate(AsyncCallback<ArrayList<PollsModel>> callback);

	void getRegions(AsyncCallback<ArrayList<RegionModel>> callback);

	void getUserDetails(String username, String password,
			AsyncCallback<UserModel> callback);

	void isUserValid(String username, String password,
			AsyncCallback<Boolean> callback);

	void getAllCandidates(String year,
			AsyncCallback<HashMap<Integer, String>> callback);

	void getAllLocations(AsyncCallback<HashMap<String, String>> callback);

	void getModifiedCandidates(String type, String year,
			AsyncCallback<ArrayList<CandidateModel>> callback);

	void getModifiedCandidatesByConstituency(String type, int constituencyId,
			String year, AsyncCallback<ArrayList<CandidateModel>> callback);

	void getModifiedCandidateByCandidateId(int candidateId, String year,
			AsyncCallback<CandidateModel> callback);

	void getCandidatesMap(String year, int constituencyId,
			AsyncCallback<HashMap<Integer, String>> callback);

	void getSingleCandidateFromConstituency(int constituencyId, String year,
			String type, AsyncCallback<CandidateModel> callback);

	void getYearMap(AsyncCallback<HashMap<Integer, String>> callback);

	void getAllConstituencies(String year,
			AsyncCallback<HashMap<String, String>> callback);

	void getConstituenciesByParentId(int parentConstituencyId,
			AsyncCallback<HashMap<Integer, String>> callback);

	void getAllParties(AsyncCallback<ArrayList<PartyModel>> callback);

	void getParentConstituenciesByRegion(int regionId,
			AsyncCallback<HashMap<Integer, String>> callback);

	void getConstituenciesByRegion(int regionId, String year,
			AsyncCallback<HashMap<Integer, String>> callback);

	void getConstituencyByGlobalId(String year, int constituencyId,
			AsyncCallback<Integer> callback);

	void getGlobalIdByConstituencyId(int constituencyId,
			AsyncCallback<Integer> callback);

	void getAllCandidatesByGlobalConstituencyId(int parentConstituencyId,
			String type, AsyncCallback<ArrayList<CandidateModel>> callback);

	void getPresidentialUpdate(String year,
			AsyncCallback<ArrayList<PrezUpdateModel>> callback);

	void getFilteredYearParties(String year, String type,
			AsyncCallback<ArrayList<PartyModel>> callback);

	void getTotalSeats(String year, AsyncCallback<Integer> callback);

	void getTotalDeclaredSeats(String year, AsyncCallback<Integer> callback);

	void getSeatUpdate(String year,
			AsyncCallback<ArrayList<SeatUpdateModel>> callback);

	void getConstituencyIdWithGlobalIdAndYear(int parentConstituencyId,
			String year, AsyncCallback<Integer> callback);

	void getSeatAnalysis(int regionId, int year,
			AsyncCallback<ArrayList<AnalysisModel>> callback);

	void getPresRegionSummary(int partyId, String year,
			AsyncCallback<ArrayList<RegionalSummaryObject>> callback);

	void getPresPartySummary(int regionId, String year,
			AsyncCallback<ArrayList<PartySummaryObject>> callback);

	void getPaliaRegionSummary(int partyId, String year,
			AsyncCallback<ArrayList<RegionalSummaryObject>> callback);

	void getPaliaPartySummary(int regionId, String year,
			AsyncCallback<ArrayList<PartySummaryObject>> callback);

}
