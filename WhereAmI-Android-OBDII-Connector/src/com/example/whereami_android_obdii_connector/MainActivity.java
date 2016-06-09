package com.example.whereami_android_obdii_connector;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		showBlueToothSelector();
	}

	private void showBlueToothSelector() {
		ArrayList<String> deviceStrs = new ArrayList<String>();
		final ArrayList<String> devices = new ArrayList<String>();

		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice device : pairedDevices) {
				deviceStrs.add(device.getName() + "\n" + device.getAddress());
				devices.add(device.getAddress());
			}
		}

		// show list
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.select_dialog_singlechoice,
				deviceStrs.toArray(new String[deviceStrs.size()]));

		alertDialog.setSingleChoiceItems(adapter, -1,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						int position = ((AlertDialog) dialog).getListView()
								.getCheckedItemPosition();
						String deviceAddress = devices.get(position);
						// TODO save deviceAddress
					}
				});

		alertDialog.setTitle("Choose Bluetooth device");
		alertDialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
