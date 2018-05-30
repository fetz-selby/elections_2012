package com.beta.election.media.client.widgets;

import com.beta.election.media.client.constants.ModuleConstants;
import com.beta.election.media.shared.CandidateModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class PollWidget extends Composite implements HasPollWidgetHandlers{

	private PollWidgetHandler handler;
	private CandidateModel model;
	private static PollWidgetUiBinder uiBinder = GWT
			.create(PollWidgetUiBinder.class);

	interface PollWidgetUiBinder extends UiBinder<Widget, PollWidget> {
	}

	@UiField DivElement votes, consField, nameField, party;
	@UiField Image logoField, avatarField;
	
	public PollWidget(String candidateUrl, String logoUrl, String candidateName, String party, int candidateId, String constituencyName, int constituencyId, int score) {
		initWidget(uiBinder.createAndBindUi(this));
		initListener();
		//initComponents(candidateUrl, logoUrl, candidateName, party, candidateId, constituencyName, constituencyId, score);
	}
	
	public PollWidget(CandidateModel model){
		this.model = model;
		initWidget(uiBinder.createAndBindUi(this));
		initListener();
		initComponents();
	}
	
	private void initListener(){
		this.sinkEvents(Event.ONCLICK);
		this.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(handler != null){
					handler.onPollClicked(model);
				}else{
					GWT.log("Handler is NULL");
				}
			}
		}, ClickEvent.getType());
	}

	private void initComponents(){
		logoField.setUrl(model.getLogo());
		if(model.getAvatar().equals(ModuleConstants.EMPTY_IMG) && model.getType().equals(ModuleConstants.PALIAMENTARIAN)){
			avatarField.setUrl(ModuleConstants.DEFAULT_IMG);
			GWT.log("In default !!!");
		}else if( model.getType().equals(ModuleConstants.PALIAMENTARIAN) && !model.getAvatar().equals(ModuleConstants.EMPTY_IMG)){
			avatarField.setUrl(model.getAvatar());
			GWT.log("Image already attacked !!!");
		}
		
		if(model.getCode() != null && model.getCode().length() > 2 && model.getType().equals(ModuleConstants.PRESIDENTIAL)){
			GWT.log("IN IMAGE SEARCH ...!!");
			avatarField.setUrl(ModuleConstants.PRESIDENTIAL_FOLDER+model.getCode()+ModuleConstants.IMAGE_EXTENSION);
		}else if((model.getCode() == null || model.getCode().length() <= 1) && model.getType().equals(ModuleConstants.PRESIDENTIAL)){
			GWT.log("USE DEFAULT FOR PREZ");
			avatarField.setUrl(ModuleConstants.DEFAULT_IMG);
		}
		
		consField.setInnerText(model.getConstituencyName());
		nameField.setInnerText(model.getCandidateName());
		votes.setInnerText(model.getFormattedVotes());
		this.party.setInnerText(model.getPartyName());
	}

	@Override
	public void setPollWidgetHandler(PollWidgetHandler handler) {
		this.handler = handler;
	}
}
