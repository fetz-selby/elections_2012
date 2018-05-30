package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SeatUpdateModel implements IsSerializable{
	private String name, logo;
	private int id, counts;
	
	public SeatUpdateModel(){}
	public SeatUpdateModel(int id, String name, String logo, int counts){
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.counts = counts;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	
}
