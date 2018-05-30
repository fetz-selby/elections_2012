package com.beta.election.media.client.rpc;

import java.util.ArrayList;

import com.beta.election.media.shared.CandidateModel;
import com.beta.election.media.shared.ConstituencyModel;
import com.beta.election.media.shared.DistrictModel;
import com.beta.election.media.shared.PollsModel;
import com.beta.election.media.shared.RegionModel;

public interface RPCListener {
	void onRegionsLoad(ArrayList<RegionModel> regions);
	void onDistrictsLoad(ArrayList<DistrictModel> districts);
	void onConstituenciesLoad(ArrayList<ConstituencyModel> constituencies);
	void onCandidatesLoad(ArrayList<CandidateModel> candidates);
	void onPollsLoad(ArrayList<PollsModel> polls);
}
