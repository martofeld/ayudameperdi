package com.ayudameperdi;

import java.io.IOException;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class Geoloc {

	private Context currentContext;
	private Criteria crit;
	int cooldown, meters;
	private LocationManager  locManager;
	private LocationListener locListener;
	public static Location currentLocation;
	private Geocoder geoc;
	private String retrAddress;
	private String gps_provider;
	private Handler handler;
	private MainActivity mainAct;

	public Geoloc(Context context, int m , int cd, MainActivity main)
	{
		mainAct = main;
		retrAddress = "/";
		meters = m;
		cooldown = cd;
		currentContext = context;
		locManager = (LocationManager)currentContext.getSystemService(Context.LOCATION_SERVICE);
		geoc = new Geocoder(currentContext);
		crit = new Criteria();
		crit.setAccuracy(Criteria.ACCURACY_FINE);
		gps_provider = locManager.getBestProvider(crit, true);

		locListener = new LocationListener(){

			@Override
			public void onLocationChanged(Location loc) {

				currentLocation = loc;
				mainAct.loadButton();
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {


			}

			@Override
			public void onProviderEnabled(String provider) {


			}

			@Override
			public void onProviderDisabled(String provider) {


			}};

			if(locManager.isProviderEnabled(locManager.getBestProvider(crit,true)))
			{
			gps_provider =  locManager.getBestProvider(crit, true);
			locManager.requestLocationUpdates(gps_provider, cooldown, meters, locListener);
			}
			else
			{
				Toast.makeText(currentContext, "GPS desactivado.", Toast.LENGTH_SHORT).show();
			}
	}


	public String getLocation()
	{
		String address = "";

		if(currentLocation!=null)
		{
			try
			{
				Address ads = geoc.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1).get(0);

				address+= ads.getAddressLine(0)+", ";
				if(ads.getAdminArea()!=null)
				{
					address+= ads.getAdminArea()+", ";
				}
				else if(ads.getLocality()!=null)
				{
					address+= ads.getLocality()+", ";
				}
				address+= ads.getCountryName()+".";	

			}
			catch(IOException IOex)
			{
				address = " las coordenadas en el siguiente link. Estoy sin internet";
			}
			address += " http://maps.google.com/?q="+currentLocation.getLatitude()+","+currentLocation.getLongitude();
			return address;
		}
		else
		{
			return "/";
		}

	}




}
