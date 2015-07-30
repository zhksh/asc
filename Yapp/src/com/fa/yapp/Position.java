package com.fa.yapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.location.*;

public class Position 
	{
	
	LocationManager locationManager ;
	LocationListener locationListener;
	PosCallback posCallback;
	String provider;
	
	

	public Position(Context context){	
		
		locationListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				updateParams();
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				
			}
		};
		
		locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);			
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
        String dString = locationManager.getProviders(true).toString();
        updateParams();
        System.out.print(dString);
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);			
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        
        
        
        
	}
	
	public double[] getCurrentCoords(){
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        return new double[] {location.getLongitude(),location.getLatitude()};
	}
	
	public void updateParams(){
		double[] coords = getCurrentCoords();
        //MainActivity.updateCoords(coords[0],coords[1]);
        System.out.print(coords.toString()+"öööööööööö\n");
        posCallback.setPos(coords[0], coords[1]);		
	}
	
	        
    
    
	
}
