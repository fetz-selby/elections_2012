package com.beta.election.media.shared;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RegionalSummaryObject implements IsSerializable{
	private int id, constituencyCount, votes, seats;
	private String name, formattedVotes;
	private HashMap<String, String> regionMap;
	private String[] fullNames = {"ashanti region", "greater accra region", "central region", "western region", "brong ahafo region", "volta region", "eastern region", "upper east region", "upper west region", "northern region"};
	private String[] shortNames = {"ashanti", "g. accra", "central", "western", "b. ahafo", "volta", "eastern", "u. east", "u. west", "northern"};
	public RegionalSummaryObject(){}
	public RegionalSummaryObject(int id, String name){
		this.id = id;
		this.name = name;
		doHashing();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getConstituencyCount() {
		return constituencyCount;
	}
	public void setConstituencyCount(int constituencyCount) {
		this.constituencyCount = constituencyCount;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName(){
		String tmpName = name.trim().toLowerCase();
		if(regionMap.containsKey(tmpName)){
			return regionMap.get(tmpName).toUpperCase();
		}
		return tmpName.split("[\\s]")[0].toUpperCase();
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public String getFormattedVotes() {
		return formattedVotes;
	}
	public void setFormattedVotes(String formattedVotes) {
		this.formattedVotes = formattedVotes;
	}
	private void doHashing(){
		regionMap = new HashMap<String, String>();
		for(int i = 0; i < fullNames.length; i++){
			regionMap.put(fullNames[i], shortNames[i]);
		}
	}
	
}
