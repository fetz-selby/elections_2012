package com.beta.election.media.client.system;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;

public class SystemVariables {

	private static SystemVariables system;
	private int constituencyId = 3, regionId, districtId, canYear, pollYear, systemYear, parentConstituencyId;
	private String candidateType = "", constituencyName = "";
	private HandlerManager eventBus;
	
	private SystemVariables(){}
	
	public static SystemVariables getSystemInstance(){
		if(system == null){
			system = new SystemVariables();
		}
		return system;
	}

	public void setHandlerManager(HandlerManager eventBus){
		this.eventBus = eventBus;
	}
	
	public int getConstituencyId() {
		return constituencyId;
	}

	public void setConstituencyId(int constituencyId) {
		this.constituencyId = constituencyId;
	}

	public int getRegionId() {
		return regionId;
	}

	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public int getCandidateYear() {
		return canYear;
	}

	public void setCandidateYear(int candidateYear) {
		this.canYear = candidateYear;
	}

	public int getPollYear() {
		return pollYear;
	}

	public void setPollYear(int pollYear) {
		this.pollYear = pollYear;
	}

	public int getSystemYear() {
		return systemYear;
	}

	public void setSystemYear(int systemYear) {
		this.systemYear = systemYear;
	}

	public String getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(String candidateType) {
		this.candidateType = candidateType;
	}

	public int getParentConstituencyId() {
		return parentConstituencyId;
	}

	public void setParentConstituencyId(int parentConstituencyId) {
		this.parentConstituencyId = parentConstituencyId;
	}
	
	public void fireChange(GwtEvent<?> event){
		eventBus.fireEvent(event);
	}

	public String getConstituencyName() {
		return constituencyName;
	}

	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}
	
}
