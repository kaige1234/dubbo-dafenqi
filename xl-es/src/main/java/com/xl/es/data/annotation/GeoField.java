package com.xl.es.data.annotation;

public class GeoField {
	private double lon;
	private double lat;
	public GeoField(){
		
	}
	public GeoField(double lon,double lat){
		this.lat=lat;
		this.lon=lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
}
