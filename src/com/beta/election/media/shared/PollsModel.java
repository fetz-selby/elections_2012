package com.beta.election.media.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.IsSerializable;

public class PollsModel implements IsSerializable{
	private String id, candidate, constituency, correspondent, ts, canId, consId, corresId, party, logo, photo, type;
	int votes;
	
	public PollsModel(){}
	
	public PollsModel(String id, String candidate, String constituency, String correspondent, String ts, int votes, String candidate_id, String constituency_id, String correspondent_id, String partyName, String logoUrl, String type){
		this.id = id;
		this.candidate = candidate;
		this.constituency = constituency;
		this.correspondent = correspondent;
		this.ts = ts;
		this.votes = votes;	
		canId = candidate_id;
		consId = constituency_id;
		corresId = correspondent_id;
		party = partyName;
		logo = logoUrl;
		this.type = type;
		
		GWT.log("Logo is INNN "+logo);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	public String getConstituency() {
		return constituency;
	}

	public void setConstituency(String constituency) {
		this.constituency = constituency;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public String getCorrespondent() {
		return correspondent;
	}

	public void setCorrespondent(String correspondent) {
		this.correspondent = correspondent;
	}

	public String getCanId() {
		return canId;
	}

	public void setCanId(String canId) {
		this.canId = canId;
	}

	public String getConsId() {
		return consId;
	}

	public void setConsId(String consId) {
		this.consId = consId;
	}

	public String getCorresId() {
		return corresId;
	}

	public void setCorresId(String corresId) {
		this.corresId = corresId;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
