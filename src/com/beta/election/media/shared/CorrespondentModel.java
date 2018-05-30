package com.beta.election.media.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CorrespondentModel implements IsSerializable{
	private String id, correspondentName, constituencyName, constituencyId, msisdn, avatarUrl, location, region, latitutde, longitude;
	
	public CorrespondentModel(){}
	public CorrespondentModel(String id, String correspondent, String constituency, String constituencyId, String msisdn, String url, String lat, String log, String location, String region){
		this.id = id;
		this.correspondentName = correspondent;
		this.constituencyName = constituency;
		this.constituencyId = constituencyId;
		this.msisdn = msisdn;
		this.avatarUrl = url;
		this.latitutde = lat;
		this.longitude = log;
		this.location = location;
		this.region = region;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorrespondentName() {
		return correspondentName;
	}
	public void setCorrespondentName(String correspondentName) {
		this.correspondentName = correspondentName;
	}
	public String getConstituencyName() {
		return constituencyName;
	}
	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getConstituencyId() {
		return constituencyId;
	}
	public void setConstituencyId(String constituencyId) {
		this.constituencyId = constituencyId;
	}
	public String getLatitutde() {
		return latitutde;
	}
	public void setLatitutde(String latitutde) {
		this.latitutde = latitutde;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
}
