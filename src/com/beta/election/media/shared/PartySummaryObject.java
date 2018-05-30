package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PartySummaryObject implements IsSerializable{
	private int id, votes, seats;
	private String partyName, avatar, color, formattedVotes;
	
	public PartySummaryObject(){}
	public PartySummaryObject(int id, String partyName, String avatar, String color){
		this.id = id;
		this.partyName = partyName;
		this.avatar = avatar;
		this.color = color;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
	
}
