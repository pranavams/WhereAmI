package com.example.whereami_android_obdii_connector;

import org.xigma.whereami.portal.bo.VehicleInformation;

import android.bluetooth.BluetoothSocket;

import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.control.VinCommand;
import com.github.pires.obd.commands.engine.LoadCommand;
import com.github.pires.obd.commands.engine.RPMCommand;
import com.github.pires.obd.commands.temperature.AirIntakeTemperatureCommand;

public class OBDListener {
	private BluetoothSocket socket = null;

	public OBDListener(BluetoothSocket socket) {
		this.socket = socket;
	}

	private Integer getRPM() {
		RPMCommand engineRpmCommand = new RPMCommand();
		try {
			engineRpmCommand.run(socket.getInputStream(), socket.getOutputStream());
			return engineRpmCommand.getRPM();
		} catch (Exception e) {
			return null;
		}
	}

	private Integer getSpeed() {
		SpeedCommand speedCommand = new SpeedCommand();
		try {
			speedCommand.run(socket.getInputStream(), socket.getOutputStream());
			return speedCommand.getMetricSpeed();
		} catch (Exception e) {
			return null;
		}
	}

	private Float getEngineTemperature() {
		AirIntakeTemperatureCommand command = new AirIntakeTemperatureCommand();
		try {
			command.run(socket.getInputStream(), socket.getOutputStream());
			return command.getTemperature();
		} catch (Exception e) {
			return null;
		}
	}

	public VehicleInformation getVehicleInfo() {
		return new VehicleInformation().setRpm(getRPM()).setEngineTemperature(getEngineTemperature()).setSpeed(getSpeed())
				.setEngineLoad(getEngineLoad()).setVIN(getVIN());
	}

	private String getVIN() {
		VinCommand command = new VinCommand();
		try {
			command.run(socket.getInputStream(), socket.getOutputStream());
			return command.getFormattedResult();
		} catch (Exception e) {
			return null;
		}
	}

	private Float getEngineLoad() {
		LoadCommand command = new LoadCommand();
		try {
			command.run(socket.getInputStream(), socket.getOutputStream());
			return command.getPercentage();
		} catch (Exception e) {
			return null;
		}
	}

}
