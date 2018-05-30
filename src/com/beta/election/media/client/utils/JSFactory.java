package com.beta.election.media.client.utils;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;

public class JSFactory {

  public native JsArrayInteger getIntJsArray()/*-{
    return new Array();
  }-*/;
  
  public native JsArrayString getStringJsArray()/*-{
    return new Array();
  }-*/;
  
  public native JsArrayMixed getMixedArray()/*-{
    return new Array();
  }-*/;
  
  public native JsArrayNumber getNumberJsArray()/*-{
    return new Array();
  }-*/;
  
  public native JsArray<JsArrayNumber> getNumArrayOfArray()/*-{
    return new Array();
  }-*/;
  
  public native JsArray<JsArrayMixed> getMixArrayOfArray()/*-{
    return new Array();
  }-*/;
}
