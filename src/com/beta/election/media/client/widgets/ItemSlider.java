package com.beta.election.media.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import com.beta.election.media.client.widgets.handlers.HasItemSliderEventHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.LabelElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ItemSlider extends Composite implements HasItemSliderEventHandler{

	private ItemSliderEventHandler handler;
	private ArrayList<String> itemList;
	private int itemIndex = 0;
	private HashMap<String, String> itemMap;
	private static ItemSliderUiBinder uiBinder = GWT
			.create(ItemSliderUiBinder.class);

	interface ItemSliderUiBinder extends UiBinder<Widget, ItemSlider> {
	}
	
	public interface ItemSliderEventHandler{
		void onItemSelected(String item, String value);
	}

	@UiField Image backIcon, forwardIcon;
	@UiField LabelElement display;
	
	public ItemSlider(HashMap<String, String> itemMap) {
		this.itemMap = itemMap;
		initWidget(uiBinder.createAndBindUi(this));
		initEvent();
		initComponent();
	}
	
	private void initEvent(){
		final int SECONDS = 2000;
		
		backIcon.sinkEvents(Event.ONCLICK);
		backIcon.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Timer backTimer;
				if((itemIndex-1) >= 0){
					itemIndex --;
					
					if(itemIndex == 0){
						hideBackIcon(true);
					}else{
						hideBackIcon(false);
					}
					
					display.setInnerText(itemList.get(itemIndex));
					backTimer = new Timer() {
						
						@Override
						public void run() {
							if(handler != null){
								handler.onItemSelected(itemList.get(itemIndex), getValue(itemList.get(itemIndex)));
							}
						}
					};
					backTimer.cancel();
					backTimer.schedule(SECONDS);
				}				
			}
		}, ClickEvent.getType());
		
		forwardIcon.sinkEvents(Event.ONCLICK);
		forwardIcon.addHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Timer forwardTimer;

				if((itemIndex+1) <= (itemList.size()-1)){
					itemIndex ++;
					
					if(itemIndex == itemList.size()-1){
						hideForwardIcon(true);
					}else{
						hideForwardIcon(false);
					}
					
					display.setInnerText(itemList.get(itemIndex));
					forwardTimer = new Timer() {
						
						@Override
						public void run() {
							if(handler != null){
								handler.onItemSelected(itemList.get(itemIndex), getValue(itemList.get(itemIndex)));
							}
						}
					};
					forwardTimer.cancel();
					forwardTimer.schedule(SECONDS);
				}
			}
		}, ClickEvent.getType());
		
	}
	
	private void initComponent(){
		backIcon.setUrl("images/br_prev.png");
		forwardIcon.setUrl("images/br_next.png");
		
		if(itemMap != null){
			itemList = getArrangedItems(itemMap);
			display.setInnerText(itemList.get(itemIndex));
			
			if(handler != null){
				handler.onItemSelected(itemList.get(itemIndex), getValue(itemList.get(itemIndex)));
			}
		}
		
	}

	private ArrayList<String> getArrangedItems(HashMap<String, String> itemMap){
		if(itemMap != null){
			TreeSet<String> treeSet = new TreeSet<String>();
			for(String item : itemMap.keySet()){
				treeSet.add(itemMap.get(item));
			}
			
			ArrayList<String> itemList = new ArrayList<String>();
			for(String item : treeSet){
				itemList.add(item);
			}
			
			return itemList;
		}
		return null;
	}
	
	private void hideBackIcon(boolean isHide){
		if(isHide){
			backIcon.getElement().setAttribute("style", "visibility: hidden;");
		}else{
			backIcon.getElement().setAttribute("style", "visibility: visible;");
		}
	}
	
	private void hideForwardIcon(boolean isHide){
		if(isHide){
			forwardIcon.getElement().setAttribute("style", "visibility: hidden;");
		}else{
			forwardIcon.getElement().setAttribute("style", "visibility: visible;");
		}
	}
	
	private String getValue(String item){
		for(String key : itemMap.keySet()){
			String tmpItem = itemMap.get(key);
			if(item.equals(tmpItem)){
				return key;
			}
		}
		return null;
	}
	
	@Override
	public void setItemSliderEventHandler(ItemSliderEventHandler handler){
		this.handler = handler;
	}
}
