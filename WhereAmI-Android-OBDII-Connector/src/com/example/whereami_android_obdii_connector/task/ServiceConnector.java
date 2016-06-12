package com.example.whereami_android_obdii_connector.task;

import org.xigma.whereami.portal.bo.VehicleInformation;

import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ServiceConnector extends AsyncTask<VehicleInformation, Void, String> {
	@Override
	protected String doInBackground(VehicleInformation... vInfo) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.add("rpm", getString(vInfo[0].getRpm()));
		params.add("speed", getString(vInfo[0].getSpeed()));
		params.add("engineTemperature", getString(vInfo[0].getEngineTemperature()));

		client.get("http://friendsnfamily-xigma.rhcloud.com/rest/postVehicle", params,
				new AsyncHttpResponseHandler());
		return "";
	}

	private String getString(Object object) {
		if (object == null)
			return "";
		else
			return String.valueOf(object);
	}
}
