package com.beta.election.media.server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.beta.election.media.client.RetrieveService;
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
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

@SuppressWarnings("serial")
public class RetrieveServiceImpl extends RemoteServiceServlet implements RetrieveService{
	private Connection con = DBConnection.getConnection();
	private Statement stmt;
	private static ArrayList<Integer> pollsIds = new ArrayList<Integer>();
	private ArrayList<String> canIds;
	
	@Override
	public ArrayList<RegionModel> getRegions() {
		
		try{
			stmt = (Statement) con.createStatement();
			ResultSet results = stmt.executeQuery("select name,id from regions");
			
			if(results != null){
				ArrayList<RegionModel> regions = new ArrayList<RegionModel>();
				while(results.next()){
					int id = results.getInt("id");
					String regionName = results.getString("name");
					RegionModel region = new RegionModel(regionName, id);
					regions.add(region);
				}
				return regions;
			}
					}catch(SQLException sql){
			sql.printStackTrace();
		}
		System.gc();
		return null;
	}

	@Override
	public ArrayList<CandidateModel> getCandidates(String typeCall, String year, int constituencyId) {
		
		System.out.println("type is "+typeCall+", year is "+year+", constituency id is "+constituencyId);
		
		canIds = new ArrayList<String>();
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select candidates.id, candidates.name as candidatename, candidates.avatar, candidates.group_type as type, candidates.votes, candidates.code, parties.name as partyname, parties.avatar as logo, parties.color, constituencies.name as constituencyname, constituencies.id as constituencyId from candidates, parties, constituencies where candidates.constituency_id = ? and candidates.year = ? and candidates.group_type = ? and candidates.votes > 0 and parties.id = candidates.party_id and constituencies.id = candidates.constituency_id");
			prstmt.setInt(1, constituencyId);
			prstmt.setString(2, year);
			prstmt.setString(3, typeCall);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<CandidateModel> candidates = new ArrayList<CandidateModel>();
				while(result.next()){
					int candidateId = result.getInt("id");
					String candidateName = result.getString("candidatename");
					String code = "";
					if(result.getString("code") == null){
						code = "";
					}else{
						code = result.getString("code");
					}
					String party = result.getString("partyname");
					String constituency = result.getString("constituencyname");
					int tmpConstituencyId = result.getInt("constituencyId");
					String avatarUrl = getImageUrl(result.getBytes("avatar"));
					String logo = getImageUrl(result.getBytes("logo"));
					String type = result.getString("type");
					int votes = result.getInt("votes");
					String partyColor = result.getString("color");
					String percentage = (((double)votes/(double)getConstituencyTotal(constituencyId, type))*100)+"";
					percentage = getApproximateValue(percentage)+"%";
					String formateVote = getFormattedText(votes);

					System.out.println("Percentage is "+percentage);
					
					CandidateModel candidate = new CandidateModel(candidateId, candidateName, votes, formateVote, code, party, tmpConstituencyId, constituency, avatarUrl, logo, type, year, partyColor);
					candidate.setPercentage(percentage);
					candidates.add(candidate);
					canIds.add(""+candidateId);
				}
				System.out.println("**** Candidates size is "+candidates.size());
				return candidates;
			}
						
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		System.gc();
		return null;
	}
	
	private String getImageUrl(byte[] blob){
		if(blob == null){
			return "data:image/png;base64,";
		}
		String base64 = new String(org.apache.commons.codec.binary.Base64.encodeBase64(blob));
		base64 = "data:image/png;base64,"+base64;
	    return base64;
	}

	@Override
	public ArrayList<PollsModel> getPollsUpdate() {
		ArrayList<PollsModel> polls = new ArrayList<PollsModel>();
		Statement stmt = null;
		try{
			stmt = (Statement) con.createStatement();
			ResultSet result = stmt.executeQuery("select polls.id, polls.constituency_id, polls.candidate_id, polls.correspondent_id, candidates.name as candidate, candidates.type as type, candidates.avatar as photo, constituencies.name as constituency, correspondents.name as correspondent, polls.votes, polls.ts, parties.name as party, parties.avatar as logo from polls, candidates, constituencies, correspondents, parties where candidates.id = polls.candidate_id and correspondents.id = polls.correspondent_id and constituencies.id = polls.constituency_id and parties.id = candidates.party_id order by polls.id desc");
			if(result != null){
				while(result.next()){
					int pollId = result.getInt("id");
					if(!pollsIds.contains(pollId)){
						pollsIds.add(pollId);
						
						String id = "" + pollId;
						String candidate = result.getString("candidate");
						String constituency = result.getString("constituency");
						String correspondent = result.getString("correspondent");
						String ts = result.getString("ts");
						int votes = result.getInt("votes");
						String candidateId = ""+result.getInt("candidate_id");
						String correspondentId = ""+result.getInt("correspondent_id");
						String constituencyId = ""+result.getInt("constituency_id");
						String partyName = result.getString("party");
						String logo = getImageUrl(result.getBytes("logo"));
						String photo = getImageUrl(result.getBytes("photo"));
						String type = result.getString("type");

						
						PollsModel poll = new PollsModel(id, candidate, constituency, correspondent, ts, votes, candidateId, constituencyId, correspondentId, partyName, logo, type);
						poll.setPhoto(photo);
						polls.add(poll);
					}
				}
			}
						
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		System.gc();
		return polls;
	}

	@Override
	public CorrespondentModel getCorrespondent(String consId) {
		CorrespondentModel correspondent = null;
		PreparedStatement prstmt = null;
		try{		
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select correspondents.id, correspondents.name, correspondents.constituency_id, correspondents.msisdn, correspondents.avatar, constituencies.name as constituency, locations.name as location, locations.lat, locations.lon, regions.name as region from correspondents, constituencies, locations, districts, regions where correspondents.constituency_id = ? and constituencies.id = correspondents.constituency_id and districts.id = constituencies.district_id and locations.id = districts.location_id and regions.id = locations.region_id");
			prstmt.setInt(1, Integer.parseInt(consId));
			ResultSet results = prstmt.executeQuery();
			if(results != null){
				while(results.next()){
					String tmpId = ""+results.getInt("id");
					String name = results.getString("name");
					String constituencyId = ""+results.getInt("constituency_id");
					String constituency = results.getString("constituency");
					String msisdn = results.getString("msisdn");
					String avatar = getImageUrl(results.getBytes("avatar"));
					String latitude = results.getString("lat");
					String longitude = results.getString("lon");
					String location = results.getString("location");
					String region = results.getString("region");
				
					correspondent = new CorrespondentModel(tmpId, name, constituency, constituencyId, msisdn, avatar, latitude, longitude, location, region);
				}
			}
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		System.gc();
		return correspondent;
	}

	@Override
	public ArrayList<PollsModel> getPolls() {
		ArrayList<PollsModel> polls = new ArrayList<PollsModel>();
		Statement stmt = null;
		try{
			stmt = (Statement) con.createStatement();
			ResultSet result = stmt.executeQuery("select polls.id, polls.constituency_id, polls.candidate_id, polls.correspondent_id, candidates.name as candidate, candidates.avatar as photo, candidates.type as type, constituencies.name as constituency, correspondents.name as correspondent, polls.votes, polls.ts, parties.name as party, parties.avatar as logo from polls, candidates, constituencies, correspondents, parties where candidates.id = polls.candidate_id and correspondents.id = polls.correspondent_id and constituencies.id = polls.constituency_id and parties.id = candidates.party_id order by polls.id desc");
			if(result != null){
				pollsIds.clear();
				while(result.next()){		
					String id = "" + result.getInt("id");
					String candidate = result.getString("candidate");
					String constituency = result.getString("constituency");
					String correspondent = result.getString("correspondent");
					String ts = result.getString("ts");
					int votes = result.getInt("votes");
					String candidateId = ""+result.getInt("candidate_id");
					String correspondentId = ""+result.getInt("correspondent_id");
					String constituencyId = ""+result.getInt("constituency_id");
					String partyName = result.getString("party");
					String logo = getImageUrl(result.getBytes("logo"));
					String photo = getImageUrl(result.getBytes("photo"));
					String type = result.getString("type");
					
					PollsModel poll = new PollsModel(id, candidate, constituency, correspondent, ts, votes, candidateId, constituencyId, correspondentId, partyName, logo, type);
					poll.setPhoto(photo);
					polls.add(poll);
					pollsIds.add(Integer.parseInt(id));
				}
			}
						
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		System.gc();
		return polls;
	}

	@Override
	public String getCandidateScore(String candidateId) {
		String votes = "0";
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select polls.votes from polls where candidate_id = ? order by polls.id desc limit 1");
			prstmt.setInt(1, Integer.parseInt(candidateId));
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					 votes = ""+result.getInt("votes");
				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		System.gc();
		return votes;
	}

	@Override
	public String getCandidatesTotalVotes() {
		int sum = 0;
		PreparedStatement prstmt = null;
		System.out.println("CAN length is "+canIds.size());
		for(String candidateId : canIds){
			try{
				prstmt = (PreparedStatement) con.prepareStatement("select polls.votes from candidates left join polls on candidates.id = polls.candidate_id where candidates.id = ? order by polls.id desc limit 1");
				prstmt.setInt(1, Integer.parseInt(candidateId));
				ResultSet result = prstmt.executeQuery();
				if(result != null){
					while(result.next()){
						String votes = ""+result.getInt("votes");
						if(votes.trim().equals("NULL") || votes == null){
							votes = "0";
						}
						sum += Integer.parseInt(votes);
					}
				}
			}catch(SQLException sql){
				sql.printStackTrace();
			}
			
		}
		System.out.println("Sum is "+sum);
		System.gc();
		return ""+sum;
	}

	@Override
	public ArrayList<ChartObject> getAllCandidatesUpdate(ArrayList<String> canIds) {
		ArrayList<ChartObject> updates = new ArrayList<ChartObject>();
		PreparedStatement prstmt = null;
		for(String canId : canIds){
			try{
				con.setAutoCommit(false);
				prstmt = (PreparedStatement) con.prepareStatement("select candidates.name, polls.votes from candidates left join polls on candidates.id = polls.candidate_id where candidates.id = ? order by polls.id desc limit 1");
				prstmt.setInt(1, Integer.parseInt(canId));
				ResultSet result = prstmt.executeQuery();
				if(result != null){
					while(result.next()){
						String name = result.getString("name");
						String value = "0";
						if(!((""+result.getInt("votes")).equals("NULL"))){
							value = ""+result.getInt("votes");
						}
					
						ChartObject chartValue = new ChartObject(name, value);
						System.out.println("From Chart call, Name is "+name+" and value is "+value);
						updates.add(chartValue);
					}
				}
			}catch(SQLException sql){
				sql.printStackTrace();
			}
		}
		System.gc();
		return updates;
	}

	@Override
	public HashMap<String, String> getAllConstituencies(String year) {
		try{
			stmt = (Statement) con.createStatement();
			ResultSet results = stmt.executeQuery("select id,name,year from constituencies where year ="+year);
			
			if(results != null){
				HashMap<String, String> tmp = new HashMap<String, String>();
				while(results.next()){
					String id = "" + results.getInt("id");
					String constituencyName = results.getString("name");
					
					tmp.put(id, constituencyName);
				}
				return tmp;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		System.gc();
		return null;
	}
	
	@Override
	public HashMap<String, String> getAllConstituencies() {
		HashMap<String, String> tmp = null;
		try{
			stmt = (Statement) con.createStatement();
			ResultSet results = stmt.executeQuery("select id,name,year from constituencies");
			
			if(results != null){
				tmp = new HashMap<String, String>();
				while(results.next()){
					String id = "" + results.getInt("id");
					String constituencyName = results.getString("name");
					String year = results.getString("year");
					
					tmp.put(id, constituencyName+"("+year+")");
				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		System.gc();
		return tmp;
	}
	

	@Override
	public String getDistrictId(String consId) {
	
		PreparedStatement prstmt = null;
			try{
				prstmt = (PreparedStatement) con.prepareStatement("select district_id from constituencies where id = ?");
				prstmt.setInt(1, Integer.parseInt(consId));
				ResultSet result = prstmt.executeQuery();
				if(result != null){
					while(result.next()){
						System.gc();
						return ""+result.getInt("district_id");
					}
				}	
			}catch(SQLException sql){
				sql.printStackTrace();
			}
			
		System.gc();
		return null;
	}

	@Override
	public boolean isUserValid(String username, String password) {
		PreparedStatement prstmt = null;
		try{
			prstmt = (PreparedStatement) con.prepareStatement("select id,name from users where email = ? and password = ?");
			prstmt.setString(1, username);
			prstmt.setString(2, password);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					System.gc();
					return true;
				}
			}	
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		
		System.gc();
		return false;
	}

	@Override
	public UserModel getUserDetails(String username, String password) {
		PreparedStatement prstmt = null;
		try{
			prstmt = (PreparedStatement) con.prepareStatement("select id,name,msisdn,email,avatar,level from users where email = ? and password = ?");
			prstmt.setString(1, username);
			prstmt.setString(2, password);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					String id = ""+result.getInt("id");
					String name = result.getString("name");
					String msisdn = result.getString("msisdn");
					String email = result.getString("email");
					String avatar = getImageUrl(result.getBytes("avatar"));
					String level = result.getString("level");
					
					UserModel user = new UserModel(id, name, email, msisdn, avatar, level);
					System.gc();
					return user;
				}
			}	
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		
	System.gc();		
	return null;
	}

	@Override
	public HashMap<String, String> getAllLocations() {
		Statement stmt = null;
		try{
			stmt = (Statement)con.createStatement();
			ResultSet result = stmt.executeQuery("select districts.id, locations.lat, locations.lon from districts, locations where locations.id = districts.location_id");
			if(result != null){
				HashMap<String, String> districtMap = new HashMap<String, String>();
				while(result.next()){
					String id = ""+result.getInt("id");
					String lat = result.getString("lat");
					String lon = result.getString("lon");

					districtMap.put(id, lat+" "+lon);
				}
				return districtMap;
			}
			
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}
	
	
	private String getFormattedText(int text){
		Locale locale = Locale.ENGLISH;
		NumberFormat formatter = NumberFormat.getNumberInstance(locale);
		
		return formatter.format(Double.parseDouble(text+""));
	}
	
	private int getConstituencyTotal(int constituencyId, String type){
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select sum(votes) as total from candidates where constituency_id = ? and group_type = ?");
			prstmt.setInt(1, constituencyId);
			prstmt.setString(2, type);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					System.out.println("Constituency id is "+constituencyId+", and total is "+result.getInt("total"));
					return result.getInt("total");
				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return 0;
	}
	
	private String getApproximateValue(String value){
		if(!(value.equals("") || value == null)){
			if(value.contains(".") && value.split("[.]")[1].length() > 3){
				String seq1 = value.split("[.]")[0];
				String seq2 = value.split("[.]")[1];
				 
				return seq1+"."+seq2.substring(0, 1);
			}else{
				return value;
			}
		}
		return "";
	}

	@Override
	public ArrayList<CandidateModel> getModifiedCandidates(String typeCall, String year) {
		System.out.println("Year is "+year);
		ArrayList<CandidateModel> polls = null;
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select candidates.id,candidates.name,candidates.code,candidates.constituency_id,candidates.avatar,candidates.group_type,candidates.year,candidates.votes,parties.name as party,parties.avatar as logo,parties.color,constituencies.name as constituency from candidates,constituencies,parties where parties.id = candidates.party_id and constituencies.id = candidates.constituency_id and candidates.votes > 0 and candidates.year = ? and candidates.group_type = ?");
			prstmt.setString(1, year);
			prstmt.setString(2, typeCall);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				polls = new ArrayList<CandidateModel>();
				while(result.next()){
					int candidateId = result.getInt("id");
					String candidateName = result.getString("name");
					String code = "";
					if(result.getString("code") == null){
						code = "";
					}else{
						code = result.getString("code");
					}					
					String type = result.getString("group_type");
					int constituencyId = result.getInt("constituency_id");
					String constituencyName = result.getString("constituency");
					String avatar = getImageUrl(result.getBytes("avatar"));
					String logo = getImageUrl(result.getBytes("logo"));
					String partyName = result.getString("party");
					int votes = result.getInt("votes");
					String tmpYear = result.getString("year");
					String partyColor = result.getString("color");
					
					//Get total votes and compute for percentage
					String percentage = (((double)votes/(double)getConstituencyTotal(constituencyId, type))*100)+"";
					percentage = getApproximateValue(percentage)+"%";
					String formateVote = getFormattedText(votes);

					System.out.println("Percentage is "+percentage);

					CandidateModel candidate = new CandidateModel(candidateId, candidateName, votes, formateVote, code, partyName, constituencyId, constituencyName, avatar, logo, type, tmpYear, partyColor);
					candidate.setPercentage(percentage);
					
					polls.add(candidate);
				}
				return polls;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<CandidateModel> getModifiedCandidatesByConstituency(String typeCall, 
			int constituencyId, String year) {
		System.out.println("Year is "+year);
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select candidates.id,candidates.name,candidates.code,candidates.constituency_id,candidates.avatar,candidates.group_type,candidates.year,candidates.votes,parties.name as party,parties.avatar as logo,parties.color,constituencies.name as constituency from candidates,constituencies,parties where candidates.constituency_id = ? and parties.id = candidates.party_id and constituencies.id = candidates.constituency_id and candidates.votes > 0 and candidates.year = ? and candidates.group_type = ?");
			prstmt.setInt(1, constituencyId);
			prstmt.setString(2, year);
			prstmt.setString(3, typeCall);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<CandidateModel> polls = new ArrayList<CandidateModel>();
				while(result.next()){
					int candidateId = result.getInt("id");
					String candidateName = result.getString("name");
					String code = "";
					if(result.getString("code") == null){
						code = "";
					}else{
						code = result.getString("code");
					}					
					String type = result.getString("group_type");
					int tmpConstituencyId = result.getInt("constituency_id");
					String constituencyName = result.getString("constituency");
					String avatar = getImageUrl(result.getBytes("avatar"));
					String logo = getImageUrl(result.getBytes("logo"));
					String partyName = result.getString("party");
					int votes = result.getInt("votes");
					String tmpYear = result.getString("year");
					String partyColor = "";
					if(result.getString("color") == null){
						partyColor = "";
					}else{
						partyColor = result.getString("color");
					}
					
					//Get total votes and compute for percentage
					String percentage = (((double)votes/(double)getConstituencyTotal(constituencyId, type))*100)+"";
					percentage = getApproximateValue(percentage)+"%";
					System.out.println("Percentage is "+percentage);
					String formateVote = getFormattedText(votes);


					CandidateModel candidate = new CandidateModel(candidateId, candidateName, votes, formateVote, code, partyName, tmpConstituencyId, constituencyName, avatar, logo, type, tmpYear, partyColor);
					candidate.setPercentage(percentage);
					
					polls.add(candidate);
				}
				return polls;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public CandidateModel getModifiedCandidateByCandidateId(int candidateId,
			String year) {
		System.out.println("Year is "+year);
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select candidates.id,candidates.name,candidates.code,candidates.constituency_id,candidates.avatar,candidates.group_type,candidates.year,candidates.votes,parties.name as party,parties.avatar as logo,parties.color,constituencies.name as constituency from candidates,constituencies,parties where candidates.id = ? and parties.id = candidates.party_id and constituencies.id = candidates.constituency_id and candidates.votes > 0 and candidates.year = ?");
			prstmt.setInt(1, candidateId);
			prstmt.setString(2, year);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					int tmpCandidateId = result.getInt("id");
					String candidateName = result.getString("name");
					String code = "";
					if(result.getString("code") == null){
						code = "";
					}else{
						code = result.getString("code");
					}
					String type = result.getString("group_type");
					int constituencyId = result.getInt("constituency_id");
					String constituencyName = result.getString("constituency");
					String avatar = getImageUrl(result.getBytes("avatar"));
					String logo = getImageUrl(result.getBytes("logo"));
					String partyName = result.getString("party");
					int votes = result.getInt("votes");
					System.out.println("Votes is "+votes);
					String tmpYear = result.getString("year");
					String partyColor = result.getString("color");
					
					//Get total votes and compute for percentage
					String percentage = (((double)votes/(double)getConstituencyTotal(constituencyId, type))*100)+"";
					percentage = getApproximateValue(percentage)+"%";
					String formateVote = getFormattedText(votes);


					System.out.println("Percentage is "+percentage);

					CandidateModel candidate = new CandidateModel(tmpCandidateId, candidateName, votes, formateVote, code, partyName, constituencyId, constituencyName, avatar, logo, type, tmpYear, partyColor);
					candidate.setPercentage(percentage);
					
					return candidate;
				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<Integer, String> getAllCandidates(String year) {
		PreparedStatement prstmt = null;
		HashMap<Integer, String> candidatesMap = null;
		
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select candidates.id,candidates.name,constituencies.name as constituency from candidates,constituencies where candidates.year = ? and constituencies.id = candidates.constituency_id");
			prstmt.setString(1, year);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				candidatesMap = new HashMap<Integer, String>();
				while(result.next()){
					int id = result.getInt("id");
					String name = result.getString("name");
					String constituency = result.getString("constituency");
					
					candidatesMap.put(id, name+" ("+constituency+") ");
				}
				return candidatesMap;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<Integer, String> getCandidatesMap(String year, int constituencyId) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select id,name from candidates where candidates.year = ? and candidates.constituency_id = ? and candidates.votes > 0");
			prstmt.setString(1, year);
			prstmt.setInt(2, constituencyId);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				HashMap<Integer, String> candidatesMap = new HashMap<Integer, String>();
				while(result.next()){
					int candidateId = result.getInt("id");
					String name = result.getString("name");
					
					candidatesMap.put(candidateId, name);
				}
				
				return candidatesMap;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public CandidateModel getSingleCandidateFromConstituency(
			int constituencyId, String year, String gType) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select candidates.id,candidates.name,candidates.code,candidates.constituency_id,candidates.avatar,candidates.group_type,candidates.year,candidates.votes,parties.name as party,parties.avatar as logo,parties.color,constituencies.name as constituency from candidates,constituencies,parties where candidates.constituency_id = ? and parties.id = candidates.party_id and constituencies.id = candidates.constituency_id and candidates.votes > 0 and candidates.year = ? and constituencies.year = ? and candidates.group_type = ? limit 1");
			prstmt.setInt(1, constituencyId);
			prstmt.setString(2, year);
			prstmt.setString(3, year);
			prstmt.setString(4, gType);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					int tmpCandidateId = result.getInt("id");
					String candidateName = result.getString("name");
					String code = "";
					if(result.getString("code") == null){
						code = "";
					}else{
						code = result.getString("code");
					}
					String type = result.getString("group_type");
					int tmpConstituencyId = result.getInt("constituency_id");
					String constituencyName = result.getString("constituency");
					String avatar = getImageUrl(result.getBytes("avatar"));
					String logo = getImageUrl(result.getBytes("logo"));
					String partyName = result.getString("party");
					int votes = result.getInt("votes");
					System.out.println("Votes is "+votes);
					String tmpYear = result.getString("year");
					String partyColor = result.getString("color");
					
					//Get total votes and compute for percentage
					String percentage = (((double)votes/(double)getConstituencyTotal(constituencyId, type))*100)+"";
					percentage = getApproximateValue(percentage)+"%";
					String formateVote = getFormattedText(votes);


					System.out.println("Percentage is "+percentage);

					CandidateModel candidate = new CandidateModel(tmpCandidateId, candidateName, votes, formateVote, code, partyName, tmpConstituencyId, constituencyName, avatar, logo, type, tmpYear, partyColor);
					candidate.setPercentage(percentage);
					
					return candidate;

				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<Integer, String> getYearMap() {
		Statement stmt = null;
		try{
			stmt = (Statement) con.createStatement();
			ResultSet result = stmt.executeQuery("select value,alias from years");
			if(result != null){
				HashMap<Integer, String> years = new HashMap<Integer, String>();
				while(result.next()){
					int value = result.getInt("value");
					String alias = result.getString("alias");
					
					years.put(value, alias);
				}
				return years;
			}
			
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<Integer, String> getConstituenciesByParentId(int parentConstituencyId) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select id,year from constituencies where parent_id = ?");
			prstmt.setInt(1, parentConstituencyId);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				HashMap<Integer, String> constituencyMap = new HashMap<Integer, String>();
				while(result.next()){
					int id = result.getInt("id");
					String year = result.getString("year"); 
					
					constituencyMap.put(id, year);
				}
				return constituencyMap;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<PartyModel> getAllParties() {
		Statement stmt = null;
		try{
			stmt = (Statement) con.createStatement();
			ResultSet results = stmt.executeQuery("select id,name,avatar from parties");
			if(results != null){
				ArrayList<PartyModel> parties = new ArrayList<PartyModel>();
				while(results.next()){
					int id = results.getInt("id");
					String name = results.getString("name");
					String logoUrl = getImageUrl(results.getBytes("avatar"));
					
					PartyModel party = new PartyModel(id, name, logoUrl);
					parties.add(party);
				}
				return parties;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<Integer, String> getParentConstituenciesByRegion(int regionId) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select id,name from parent_constituencies where region_id = ?");
			prstmt.setInt(1, regionId);
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				HashMap<Integer, String> constituencyMap = new HashMap<Integer, String>();
				while(result.next()){
					int constituencyId = result.getInt("id");
					String constituencyName = result.getString("name");
					
					constituencyMap.put(constituencyId, constituencyName);
				}
				return constituencyMap;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public int getConstituencyByGlobalId(String year, int constituencyId) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select id from constituencies where year = ? and parent_id = ?");
			prstmt.setString(1, year);
			prstmt.setInt(2, constituencyId);
			ResultSet results = prstmt.executeQuery();
			if(results != null){
				while(results.next()){
					return results.getInt("id");
				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return 0;
	}

	@Override
	public HashMap<Integer, String> getConstituenciesByRegion(int regionId, String year) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select constituencies.id, constituencies.name from constituencies,parent_constituencies,regions where regions.id = ? and parent_constituencies.region_id = regions.id and constituencies.parent_id = parent_constituencies.id and constituencies.year = ?");
			prstmt.setInt(1, regionId);
			prstmt.setString(2, year);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				HashMap<Integer, String> constituencyMap = new HashMap<Integer, String>();
				while(result.next()){
					int id = result.getInt("id");
					String name = result.getString("name");
					
					constituencyMap.put(id, name);
				}
				return constituencyMap;
			}
			
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public int getGlobalIdByConstituencyId(int constituencyId) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select parent_id from constituencies where id = ?");
			prstmt.setInt(1, constituencyId);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					return result.getInt("parent_id");
				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<CandidateModel> getAllCandidatesByGlobalConstituencyId(
			int parentConstituencyId, String groupType) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select candidates.id,candidates.name,candidates.code,candidates.constituency_id,candidates.avatar,candidates.group_type,candidates.year,candidates.votes,parties.name as party,parties.avatar as logo,parties.color,constituencies.name as constituency from candidates,constituencies,parent_constituencies,parties where candidates.group_type = ? and parties.id = candidates.party_id and constituencies.id = candidates.constituency_id and parent_constituencies.id = ? and constituencies.parent_id = parent_constituencies.id");
			prstmt.setString(1, groupType);
			prstmt.setInt(2, parentConstituencyId);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<CandidateModel> candidates = new ArrayList<CandidateModel>();
				while(result.next()){
					int tmpCandidateId = result.getInt("id");
					String candidateName = result.getString("name");
					String code = "";
					if(result.getString("code") == null){
						code = "";
					}else{
						code = result.getString("code");
					}
					String type = result.getString("group_type");
					int tmpConstituencyId = result.getInt("constituency_id");
					String constituencyName = result.getString("constituency");
					String avatar = getImageUrl(result.getBytes("avatar"));
					String logo = getImageUrl(result.getBytes("logo"));
					String partyName = result.getString("party");
					int votes = result.getInt("votes");
					System.out.println("Votes is "+votes);
					String tmpYear = result.getString("year");
					String partyColor = result.getString("color");
					
					//Get total votes and compute for percentage
					String percentage = (((double)votes/(double)getConstituencyTotal(tmpConstituencyId, type))*100)+"";
					percentage = getApproximateValue(percentage)+"%";
					String formateVote = getFormattedText(votes);


					System.out.println("Percentage is "+percentage);

					CandidateModel candidate = new CandidateModel(tmpCandidateId, candidateName, votes, formateVote, code, partyName, tmpConstituencyId, constituencyName, avatar, logo, type, tmpYear, partyColor);
					candidate.setPercentage(percentage);
					
					candidates.add(candidate);
				}
				
				return candidates;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<PrezUpdateModel> getPresidentialUpdate(String year) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select p.name,p.color,sum(c.votes) as total from parties as p, candidates as c where c.group_type='P' and c.year=? and p.id = c.party_id group by c.party_id");
			prstmt.setString(1, year);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<PrezUpdateModel> updateList = new ArrayList<PrezUpdateModel>();
				while(result.next()){
					String party = result.getString("name");
					String color = result.getString("color");
					int votes = result.getInt("total");
					
					PrezUpdateModel update = new PrezUpdateModel(party, color, votes);
					
					updateList.add(update);
				}
				return updateList;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<PartyModel> getFilteredYearParties(String year, String type) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select c.party_id, p.name, p.avatar from candidates as c, parties as p where c.year = ? and c.group_type = ? and c.party_id = p.id group by c.party_id");
			prstmt.setString(1, year);
			prstmt.setString(2, type);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<PartyModel> partyList = new ArrayList<PartyModel>();
				while(result.next()){
					int id = result.getInt("party_id");
					String name = result.getString("name");
					String logo = getImageUrl(result.getBytes("avatar"));
					
					PartyModel party = new PartyModel(id, name, logo);
					partyList.add(party);
				}
				return partyList;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public int getTotalSeats(String year) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select count(id) as total from constituencies where year = ?");
			prstmt.setString(1, year);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					return result.getInt("total");
				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getTotalDeclaredSeats(String year) {
		
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select count(id) as total from constituencies where year = ? and is_declared = 'Y'");
			prstmt.setString(1, year);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					return result.getInt("total");
				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<SeatUpdateModel> getSeatUpdate(String year) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select p.id, p.name as party, p.avatar, count(c.seat_won_id) as total from constituencies as c, parties as p where c.year = ? and c.is_declared = 'Y' and p.id = c.seat_won_id group by c.seat_won_id");
			prstmt.setString(1, year);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<SeatUpdateModel> seatList = new ArrayList<SeatUpdateModel>();
				while(result.next()){
					int id = result.getInt("id");
					String name = result.getString("party");
					String logo = getImageUrl(result.getBytes("avatar"));
					int counts = result.getInt("total");
					
					SeatUpdateModel update = new SeatUpdateModel(id, name, logo, counts);
					seatList.add(update);
				}
				return seatList;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return null;
	}

	@Override
	public int getConstituencyIdWithGlobalIdAndYear(int parentConstituencyId,
			String year) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select id from constituencies where parent_id = ? and year = ?");
			prstmt.setInt(1, parentConstituencyId);
			prstmt.setString(2, year);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				while(result.next()){
					return result.getInt("id");
				}
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}
		return 0;
	}

	@Override
	public ArrayList<AnalysisModel> getSeatAnalysis(int regionId, int year) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select par.id,par.name as party,count(par.name) as count from constituencies as cons,parties as par,parent_constituencies as parent_cons where parent_cons.region_id = ? and cons.parent_id = parent_cons.id and cons.year = ? and cons.seat_won_id = par.id group by party");
			prstmt.setInt(1, regionId);
			prstmt.setString(2, ""+year);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<AnalysisModel> seatList = new ArrayList<AnalysisModel>();
				while(result.next()){
					int id = result.getInt("id");
					String name = result.getString("party");
					int counts = result.getInt("count");
					
					AnalysisModel analysis = new AnalysisModel(""+id, name, counts);
					seatList.add(analysis);
				}
				return seatList;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}		return null;
	}

	@Override
	public ArrayList<RegionalSummaryObject> getPresRegionSummary(int partyId, String year) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select reg.id, reg.name as region, count(cons.name) as cons_count, sum(can.votes) as votes from constituencies as cons, regions as reg, parent_constituencies, candidates as can where cons.parent_id = parent_constituencies.id and parent_constituencies.region_id = reg.id and cons.year = ? and cons.id = can.constituency_id and can.group_type = 'P' and can.party_id = ? group by region");
			prstmt.setString(1, year);
			prstmt.setInt(2, partyId);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<RegionalSummaryObject> regionSummaryList = new ArrayList<RegionalSummaryObject>();
				while(result.next()){
					int id = result.getInt("id");
					String regionName = result.getString("region");
					int consCounts = result.getInt("cons_count");
					int votes = result.getInt("votes");
					
					System.out.println("id "+id+", regionName "+regionName+", counts "+consCounts+", votes "+votes);
					
					RegionalSummaryObject analysis = new RegionalSummaryObject(id, regionName);
					analysis.setConstituencyCount(consCounts);
					analysis.setVotes(votes);
					analysis.setFormattedVotes(getFormattedNumber("###,###", votes));
					regionSummaryList.add(analysis);
				}
				return regionSummaryList;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}	
		
		return null;
	}

	@Override
	public ArrayList<PartySummaryObject> getPresPartySummary(int regionId, String year) {
		
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select par.id,par.name as party,sum(can.votes) as votes, par.avatar, par.color from parties as par, constituencies as cons, parent_constituencies as par_cons, candidates as can where par.id = can.party_id and can.group_type = 'P' and can.constituency_id = cons.id and cons.year = ? and cons.parent_id = par_cons.id and par_cons.region_id = ? group by party");
			prstmt.setString(1, year);
			prstmt.setInt(2, regionId);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<PartySummaryObject> partySummaryList = new ArrayList<PartySummaryObject>();
				while(result.next()){
					int id = result.getInt("id");
					String partyName = result.getString("party");
					String partyColor = result.getString("color");
					int votes = result.getInt("votes");
					String avatar = getImageUrl(result.getBytes("avatar"));

					PartySummaryObject analysis = new PartySummaryObject(id, partyName, avatar, partyColor);
					analysis.setVotes(votes);
					analysis.setFormattedVotes(getFormattedNumber("###,###", votes));
					partySummaryList.add(analysis);
				}
				return partySummaryList;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}	
		
		return null;
	}

	@Override
	public ArrayList<RegionalSummaryObject> getPaliaRegionSummary(int partyId, String year) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select reg.id, reg.name as region, count(cons.seat_won_id) as seats_won from regions as reg, constituencies as cons, parent_constituencies as par_cons, parties as par where cons.parent_id = par_cons.id and par_cons.region_id = reg.id and cons.seat_won_id = par.id and cons.seat_won_id = ? and cons.year = ? and cons.is_declared = 'Y' group by region;");
			prstmt.setInt(1, partyId);
			prstmt.setString(2, year);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<RegionalSummaryObject> regionSummaryList = new ArrayList<RegionalSummaryObject>();
				while(result.next()){
					int id = result.getInt("id");
					String regionName = result.getString("region");
					int seats = result.getInt("seats_won");
					
					RegionalSummaryObject analysis = new RegionalSummaryObject(id, regionName);
					analysis.setSeats(seats);
					regionSummaryList.add(analysis);
				}
				return regionSummaryList;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}	
		
		return null;
	}

	@Override
	public ArrayList<PartySummaryObject> getPaliaPartySummary(int regionId, String year) {
		PreparedStatement prstmt = null;
		try{
			con.setAutoCommit(false);
			prstmt = (PreparedStatement) con.prepareStatement("select par.id,par.name as party,count(cons.seat_won_id) as seats_won, par.color, par.avatar from parties as par, constituencies as cons, parent_constituencies as par_cons, regions as reg where cons.seat_won_id = par.id and cons.parent_id = par_cons.id and par_cons.region_id = reg.id and cons.year = ? and cons.is_declared = 'Y' and par_cons.region_id = ? group by party;");
			prstmt.setString(1, year);
			prstmt.setInt(2, regionId);
			
			ResultSet result = prstmt.executeQuery();
			if(result != null){
				ArrayList<PartySummaryObject> partySummaryList = new ArrayList<PartySummaryObject>();
				while(result.next()){
					int id = result.getInt("id");
					String partyName = result.getString("party");
					String partyColor = result.getString("color");
					int seats = result.getInt("seats_won");
					String avatar = getImageUrl(result.getBytes("avatar"));

					PartySummaryObject analysis = new PartySummaryObject(id, partyName, avatar, partyColor);
					analysis.setSeats(seats);
					partySummaryList.add(analysis);
				}
				return partySummaryList;
			}
		}catch(SQLException sql){
			sql.printStackTrace();
		}	
		
		return null;
	}
	
	private String getFormattedNumber(String pattern, int number){
		DecimalFormat decimal = new DecimalFormat(pattern);
		return decimal.format(number);
	}
}
