package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AnalysisModel implements IsSerializable{
	private String id, name;
	private int count;
	
	public AnalysisModel(String id, String name, int count){
		this.id = id;
		this.name = name;
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return count;
	}
	
}
