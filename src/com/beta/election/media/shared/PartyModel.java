package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class PartyModel implements IsSerializable{
	private String name, logoUrl;
	private int id;
	
	public PartyModel(){}
	
	public PartyModel(int id, String name, String url){
		this.id = id;
		this.name = name;
		this.logoUrl = url;
	}

	public String getName() {
		return name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public int getId() {
		return id;
	}
}
