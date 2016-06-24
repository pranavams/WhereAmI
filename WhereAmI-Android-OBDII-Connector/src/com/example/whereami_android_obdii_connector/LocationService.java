package com.example.whereami_android_obdii_connector;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

public class LocationService extends Service implements LocationListener {
	Location location; // Location

	public double getLongitude() {
		if (this.location != null)
			return this.location.getLongitude();
		return 0;
	}

	/**
	 * Function to get latitude
	 * */
	public double getLatitude() {
		if (this.location != null)
			return this.location.getLatitude();
		return 0;
	}

	@Override
	public void onLocationChanged(Location location) {
		if(location != null)
			this.location = location;
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}