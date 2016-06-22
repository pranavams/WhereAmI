package com.example.whereami_android_obdii_connector.task;

import org.xigma.whereami.portal.bo.VehicleInformation;

import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ServiceConnector extends AsyncTask<VehicleInformation, Void, String> {
	@Override
	protected String doInBackground(VehicleInformation... vInfo) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		if (vInfo[0].getRpm() == null)
			params.add("rpm", "0");
		else
			params.add("rpm", getString(vInfo[0].getRpm()));

		if (vInfo[0].getSpeed() == null)
			params.add("speed", "0");
		else
			params.add("speed", getString(vInfo[0].getSpeed()));

		if (vInfo[0].getEngineTemperature() == null)
			params.add("engineTemperature", "0.0");
		else
			params.add("engineTemperature", getString(vInfo[0].getEngineTemperature()));

		if (vInfo[0].getLatitude() == null) {
			params.add("latitude", "0.0");
			params.add("longitude", "0.0");
		} else {
			params.add("latitude", getString(vInfo[0].getLatitude()));
			params.add("longitude", getString(vInfo[0].getLongitude()));
		}
		Log.d("Parameters ", "Params " + params);
		client.get("http://friendsnfamily-xigma.rhcloud.com/rest/postVehicle", params, new AsyncHttpResponseHandler());
		return "";
	}

	private String getString(Object object) {
		if (object == null)
			return "";
		else
			return String.valueOf(object);
	}
}
