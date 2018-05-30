package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class HistoryModel implements IsSerializable{
	private String candidateId, candidateName, candidateUrl, constituencyName, constituencyId, totalVotes, year, partyName, partyUrl;
	
	public HistoryModel(){}
	public HistoryModel(String candidateId, String candidateName, String candidateUrl, String partyName, String partyUrl, String constituencyName, String constituencyId, String totalVotes, String year){
		this.candidateId = candidateId;
		this.candidateName = candidateName;
		this.candidateUrl = candidateUrl;
		
		this.partyName = partyName;
		this.partyUrl = partyUrl;
		
		this.constituencyId = constituencyId;
		this.constituencyName = constituencyName;
		
		this.totalVotes = totalVotes;
		this.year = year;
	}
	public String getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public String getCandidateUrl() {
		return candidateUrl;
	}
	public void setCandidateUrl(String candidateUrl) {
		this.candidateUrl = candidateUrl;
	}
	public String getConstituencyName() {
		return constituencyName;
	}
	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}
	public String getConstituencyId() {
		return constituencyId;
	}
	public void setConstituencyId(String constituencyId) {
		this.constituencyId = constituencyId;
	}
	public String getTotalVotes() {
		return totalVotes;
	}
	public void setTotalVotes(String totalVotes) {
		this.totalVotes = totalVotes;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getPartyUrl() {
		return partyUrl;
	}
	public void setPartyUrl(String partyUrl) {
		this.partyUrl = partyUrl;
	}
	
}
