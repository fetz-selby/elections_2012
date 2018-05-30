package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DistrictModel implements IsSerializable{
	String districtName, id;
	
	public DistrictModel(){}
	
	public DistrictModel(String districtName, String id){
		this.districtName = districtName;
		this.id = id;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
