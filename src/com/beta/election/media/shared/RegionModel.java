package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RegionModel implements IsSerializable{
	private String regionName;
	private int id;
	
	public RegionModel(){}
	
	public RegionModel(String regionName, int id){
		this.regionName = regionName;
		this.id = id;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
