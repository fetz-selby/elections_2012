package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChartObject implements IsSerializable {
	private String name, value;
	
	public ChartObject(){}
	
	public ChartObject(String name, String value){
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
