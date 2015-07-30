package com.fa.yapp;

import android.app.Activity;

import java.lang.reflect.Method;

import com.fa.yapp.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

	public class ObjectFragment extends Fragment
		{
		
	    public static final String ARG_OBJECT = "key";
	    public boolean run = true;
	    private View rootView;
	    Parameter params = new Parameter();
	    OnParamsChangeListener mCallback;
	    
	   

	    @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        
	        // This makes sure that the container activity has implemented
	        // the callback interface. If not, it throws an exception
	        try {
	            mCallback = (OnParamsChangeListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString()
	                    + " must implement ThatParamsListener");
	        }
	    }
	    
	    
	    @Override
	    public View onCreateView(LayoutInflater inflater,
	            ViewGroup container, Bundle savedInstanceState) {
	        // The last two arguments ensure LayoutParams are inflated
	        // properly.
	    	Bundle args = getArguments();
	    	rootView = inflater.inflate(args.getInt(ARG_OBJECT), container, false);
	    	
	    	params.init();
	    	params.setTimezone("America/New_York");
	    	
	    	switch(args.getInt(ARG_OBJECT)){
		    	case R.layout.asc_page :
		    		
		    		calcAsc();
		    		break;
		    	case R.layout.prop_page :
		    		DatePicker dPicker = (DatePicker) rootView.findViewById(R.id.datePicker);
		    		dPicker.init(params.getYear(), params.getMonth(), params.getDay(), new OnDateChangedListener(){
		    			 @Override
		    		     public void onDateChanged(DatePicker view, 
	    		    		 int year, int monthOfYear,int dayOfMonth) {
		    				 params.setMonth(monthOfYear);
		    				 params.setDay(dayOfMonth);
		    				 params.setYear(year);
		    				 mCallback.onParamsChange(params);
	    			 		}
		    			});
		    		
		    		TimePicker tPicker = (TimePicker) rootView.findViewById(R.id.timePicker);
		    		tPicker.setIs24HourView(DateFormat.is24HourFormat(getActivity()));
		    		tPicker.setCurrentHour(params.getHour());
		    		tPicker.setCurrentMinute(params.getMinute());
		    		tPicker.setOnTimeChangedListener(new OnTimeChangedListener(){		    	
		    			 @Override
		    		     public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
	    				 		params.setHour(hourOfDay);
	    				 		params.setMinute(minute);
	    				 		mCallback.onParamsChange(params);
	    			 		}
		    			});
		    		disableCalendarView(dPicker);
		    		prcUserInput( dPicker);
		    		break;
	    	}
			
	        return rootView;
	    }	    
	    
	    public static void disableCalendarView(DatePicker dPicker) {			
			int currentapiVersion = android.os.Build.VERSION.SDK_INT;
			if (currentapiVersion >= 11) {
			  try {
			    Method m = dPicker.getClass().getMethod("setCalendarViewShown", boolean.class);
			    m.invoke(dPicker, false);
			  }
			  catch (Exception e) {} // eat exception in our case
			}
		}
	    
	    private static void prcUserInput(DatePicker dPicker){		    	
	    	
	    }    
	    
	    public String[] printAscInfoToArray() {
	    	//SternzeitAusParameter spAusParameter = new SternzeitAusParameter(11.58333, 48.13333, 2014, 4, 19, 20, 20, 1, 0, false);
	    	params.setTs(params.getTs() + 1000);
	    	params.debug();
	    	System.out.print("\n"+params.getTimezone()+" in Loop");
	    	
			SternzeitAusTimestamp sz = new SternzeitAusTimestamp(
					
					params.getLong(),//Längengrad
					params.getLat(),//Breitengrad
					params.getTs(),//timestamp in millis
					params.getTimezone(),//Zeitzone
					params.getSummertime()
										);//Sommerzeit
			System.out.print(" Winkel: "+sz.getWinkel());
			return new String[] { "" + sz.formatSekunden(sz.getSternzeit()) + "",
					"" + params.getDateString() + " "+params.getTimeString(),
					"" + sz.getJD() + "", "" + sz.getAsc() + "",
					"" + sz.getLängengrad() + "", "" + sz.getBreitengrad() + "","" + sz.getAscAstronomisch()};
		}
	    
	    
	    public void replaceParams (Parameter p){
	    	params = p;
	    }
	    
	    //PosCallback Interface  
	    
	    
	    
	    
	    //Loop
	    private void calcAsc(){
	    	final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					String[] asc_dataStrings = msg.getData().getStringArray(
							"asc_data");
					Bundle ascDataBundle = new Bundle();
					ascDataBundle.putStringArray("asc_data", asc_dataStrings);
					
					TextView sidView = (TextView) rootView.findViewById(R.id.sidtime);
					TextView locView = (TextView) rootView.findViewById(R.id.loctime);
					TextView jdView = (TextView) rootView.findViewById(R.id.jdtag);
					TextView ascView = (TextView) rootView.findViewById(R.id.asc);
					TextView ascAstronomischView = (TextView) rootView.findViewById(R.id.ascAstronomisch);
					TextView langenView = (TextView) rootView.findViewById(R.id.langen); //umlaute mag er nicht
					TextView breitenView = (TextView) rootView.findViewById(R.id.breiten);
					sidView.setText("Mittlere Ortsternzeit: " + asc_dataStrings[0]);
					locView.setText("local: " + asc_dataStrings[1]);
					jdView.setText("Jd: " + asc_dataStrings[2]);
					ascView.setText(asc_dataStrings[3]);
					ascAstronomischView.setText("("+asc_dataStrings[6]+")");
					langenView.setText("für Längengrad: " + asc_dataStrings[4]
							+ "");
					breitenView.setText("für Breitengrad: " + asc_dataStrings[5]
							+ "");
				}
			};

			Thread t = new Thread() {
				@Override
				public void run() {
					Looper.prepare();
					while (run) {
						String[] dataStringArray = printAscInfoToArray();
						if (!dataStringArray[1].equals(null)
								&& !dataStringArray[1].equals("")) {
							Message msgObj = handler.obtainMessage();
							Bundle b = new Bundle();
							b.putStringArray("asc_data", dataStringArray);
							msgObj.setData(b);
							handler.sendMessage(msgObj);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {

							}
						} else {
							//showOnToast("Fehler bei der Aszendentenbrechnung");
						}
					}
				}
			};
			t.start();
		}

		
	    
	    
	}