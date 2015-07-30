package com.fa.yapp;

public interface PosCallback {
	public void setPos(double longi, double lat);
	public void stopLocating();
}
