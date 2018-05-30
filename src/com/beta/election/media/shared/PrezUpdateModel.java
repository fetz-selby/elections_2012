package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PrezUpdateModel implements IsSerializable{
	private String name, color;
	private int votes;
	
	public PrezUpdateModel(){}
	public PrezUpdateModel(String name, String color, int votes){
		this.name = name;
		this.color = color;
		this.votes = votes;
	}
	public String getName() {
		return name;
	}
	public String getColor() {
		return color;
	}
	public int getVotes() {
		return votes;
	}
	
}
