package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserModel implements IsSerializable{

	private String id, name, avatar, email, msisdn, level;
	
	public UserModel(){}
	public UserModel(String id, String name, String email, String msisdn, String avatar, String level){
		this.id = id;
		this.name = name;
		this.email = email;
		this.msisdn = msisdn;
		this.avatar = avatar;
		this.level = level;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
