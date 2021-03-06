package com.example.whereami_android_obdii_connector;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import org.xigma.whereami.portal.bo.VehicleInformation;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.whereami_android_obdii_connector.task.ServiceConnector;
import com.github.pires.obd.commands.protocol.EchoOffCommand;
import com.github.pires.obd.commands.protocol.LineFeedOffCommand;
import com.github.pires.obd.commands.protocol.SelectProtocolCommand;
import com.github.pires.obd.commands.protocol.TimeoutCommand;
import com.github.pires.obd.enums.ObdProtocols;

@SuppressLint("ShowToast")
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends Activity {

	private BluetoothSocket socket = null;

	private Button button = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		button = (Button) findViewById(R.id.btnBlueToothDevice);

		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					showBlueToothSelector();
				} catch (Exception ex) {
					showErrorMessage(ex.getMessage() + " " + ex.getClass().getName(), "While Showing Bluetooth");
				}
			}
		});

		Button buttonExit = (Button) findViewById(R.id.btnStop);

		buttonExit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				finish();
				System.exit(0);
			}
		});

		final Button btnMonitor = (Button) findViewById(R.id.btnMonitor);
		btnMonitor.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					final LocationService lService = getLocationService();
					new Thread(new Runnable() {
						public void run() {
							OBDListener obdListener = new OBDListener(socket);
							VehicleInformation vInfo = new VehicleInformation();
							while (true) {
								vInfo = obdListener.getVehicleInfo();
								vInfo.setLocation(BigDecimal.valueOf(lService.getLatitude()), BigDecimal.valueOf(lService.getLongitude()));
								Log.d("Vehicle Info ", vInfo.toString());
								new ServiceConnector().execute(vInfo);
//								try {
//									Thread.sleep(10000);
//								} catch (InterruptedException ex) {
//
//								}
							}
						}
					}).start();
				} catch (Exception ex) {
					Log.e("MainActivity.btnMonitorListener", ex.toString());
					showErrorMessage(ex.getMessage() + " " + ex.getClass().getName(), "MainActivity.btnMonitorListener");
				}
			}

			private LocationService getLocationService() {
				LocationManager lManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				final LocationService lService = new LocationService();
				lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 	0, lService);
				lService.location = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				return lService;
			}
		});

	}

	private void showBlueToothSelector() {
		ArrayList<String> deviceStrs = new ArrayList<String>();
		final ArrayList<String> devices = new ArrayList<String>();

		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
		for (BluetoothDevice device : pairedDevices) {
			deviceStrs.add(device.getName() + " - " + device.getAddress());
			devices.add(device.getAddress());
		}

		// show list
		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.select_dialog_singlechoice, deviceStrs.toArray(new String[deviceStrs
				.size()]));

		alertDialog.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				int position = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
				connectToBluetoothDevice(devices.get(position));
			}

		});

		alertDialog.setTitle("Choose Bluetooth device");
		alertDialog.show();
	}

	public void connectToBluetoothDevice(String deviceAddress) {
		BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
		BluetoothDevice device = btAdapter.getRemoteDevice(deviceAddress);
		UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		try {
			socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
			socket.connect();
			initializeOBDAdaptor(socket);
		} catch (IOException e) {
			showErrorMessage(e.getMessage(), "WhereAmI");
		}
	}

	private void initializeOBDAdaptor(BluetoothSocket socket) throws IOException {
		try {
			new EchoOffCommand().run(socket.getInputStream(), socket.getOutputStream());
			new LineFeedOffCommand().run(socket.getInputStream(), socket.getOutputStream());
			new TimeoutCommand(5000).run(socket.getInputStream(), socket.getOutputStream());
			new SelectProtocolCommand(ObdProtocols.AUTO).run(socket.getInputStream(), socket.getOutputStream());
			this.button.setText("is BlueTooth Connected ? " + socket.isConnected());
		} catch (InterruptedException e) {
			showErrorMessage(e.getMessage(), "WhereAmI");
		}
	}


	public void showErrorMessage(String message, String title) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// continue with delete
					}
				}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing
					}
				}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}
}
