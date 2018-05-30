package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CandidateModel implements IsSerializable{
	private String candidateName, code, partyName, constituencyName, avatar, logo, type, year, percentage, formattedVotes, partyColor;
	private int candidateId, constituencyId, votes;
	
	public CandidateModel(){}
	
	public CandidateModel(int candidateId, String candidateName, int votes, String formattedVotes, String code, String partyName, int constituencyId, String constituencyName, String avatar, String logo, String type, String year, String partyColor){
		this.candidateId = candidateId;
		this.candidateName = candidateName;
		this.votes = votes;
		this.code = code;
		this.partyName = partyName;
		this.constituencyId = constituencyId;
		this.constituencyName = constituencyName;
		this.avatar = avatar;
		this.logo = logo;
		this.type = type;
		this.year = year;
		this.formattedVotes = formattedVotes;
		this.partyColor = partyColor;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getConstituencyName() {
		return constituencyName;
	}

	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	public int getConstituencyId() {
		return constituencyId;
	}

	public void setConstituencyId(int constituencyId) {
		this.constituencyId = constituencyId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getFormattedVotes() {
		return formattedVotes;
	}

	public void setFormattedVotes(String formattedVotes) {
		this.formattedVotes = formattedVotes;
	}

	public String getPartyColor() {
		return partyColor;
	}

	public void setPartyColor(String partyColor) {
		this.partyColor = partyColor;
	}
	
}
