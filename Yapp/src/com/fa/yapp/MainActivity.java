package com.fa.yapp;


import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity
	implements OnParamsChangeListener{

	static final int NOTIFICATION_ID = 1776;
	static final int UPDATE_FREQ = 1000;
	static final int MIN_DIST = 0;
	public CollectionPagerAdapter collectionPagerAdapter;
	ViewPager viewPager;
	private static Context context ;
    LocationListener locationListener;
    LocationManager locationManager ;
    String provider;
	public double[] coordinates_tmp;
	
	
	public void onParamsChange(Parameter params){
		ObjectFragment f = (ObjectFragment) collectionPagerAdapter.getItem(0);
		if (f != null){	
			params.setLat(coordinates_tmp[0]);
			params.setLong(coordinates_tmp[1]);
			f.replaceParams(params);
			System.out.print(params.debug());
		}
		else {
			System.out.print("Fragment nicht gefunden"+ params.debug());
		}		
	}
	
	public static Context getAppContext() {
        return MainActivity.context;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		MainActivity.context = getApplicationContext();
		
		setContentView(R.layout.main_viewer);		
		
		locationListener = new LocationListener() {
					
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
						//updateParamswithCoords();
						
					}
					
					@Override
					public void onProviderEnabled(String provider) {
						
					}
					
					@Override
					public void onProviderDisabled(String provider) {
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_FREQ, MIN_DIST, locationListener);						
						
					}
					
					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						
					}
				};
		locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);			
		// Register the listener with the Location Manager to receive location updates
		String provi_gps = LocationManager.GPS_PROVIDER;
		String provi_network = LocationManager.NETWORK_PROVIDER;
		locationManager.requestLocationUpdates(provi_gps, UPDATE_FREQ, MIN_DIST, locationListener);
		
		ObjectFragment ascFragment = new ObjectFragment();
		Bundle ascArgs = new Bundle();
		ascArgs.putInt("key", R.layout.asc_page);
		ascFragment.setArguments(ascArgs);
		
		ObjectFragment ascUserInput = new ObjectFragment();
		Bundle userInputArgs = new Bundle();
		userInputArgs.putInt("key", R.layout.prop_page);
		ascUserInput.setArguments(userInputArgs);	
		
		final ObjectFragment[] pages = {ascFragment,ascUserInput};		
		
		collectionPagerAdapter = new CollectionPagerAdapter(getSupportFragmentManager(), pages);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(collectionPagerAdapter);
		
		//updateParamswithCoords();
	} 	
	
	
	public void updateParamswithCoords(){
		ObjectFragment f = (ObjectFragment) collectionPagerAdapter.getItem(0);
		 getCurrentCoords();
		if (f != null){
			f.params.setLat(coordinates_tmp[0]);
			f.params.setLong(coordinates_tmp[1]);			
		}		
	}
	
	public void getCurrentCoords(){
		// Register the listener with the Location Manager to receive location updates
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);
        System.out.print("PROVIDER -----------" + provider+" \n");
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        this.coordinates_tmp = new double[] {location.getLongitude(),location.getLatitude()};
	}
	
	protected void DisplayNotification(String title, String message) {
		NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification note = new Notification(R.drawable.ic_launcher, title,
				System.currentTimeMillis());
		PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(
				this, MainActivity.class), 0);
		note.setLatestEventInfo(this, title, message, intent); 
		notifManager.notify(NOTIFICATION_ID, note);

	}

	public void showOnToast(String msg) {
		Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	public void onPause() {
		super.onPause();
		
	}

	public void onResume() {
		super.onResume();
	}

	public void exitApp(View view) {
		onDestroy();
		finish();
	}

}
