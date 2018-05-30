package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConstituencyModel implements IsSerializable{
	String constituencyName, id;
	
	public ConstituencyModel(){}
	
	public ConstituencyModel(String constituencyName, String id){
		this.constituencyName = constituencyName;
		this.id = id;
	}

	public String getConstituencyName() {
		return constituencyName;
	}

	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
