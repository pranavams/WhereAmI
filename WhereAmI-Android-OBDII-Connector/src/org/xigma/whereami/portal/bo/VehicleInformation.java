package org.xigma.whereami.portal.bo;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class VehicleInformation {
	private Timestamp updatedOn = null;
	private Integer rpm, speed;
	private Float engineTemperature;
	private GPSInfo location;
	private Float engineLoad;
	private Float fuelLevel;
	private String vin;

	public GPSInfo getLocation() {
		if (location == null)
			return new GPSInfo(null, null);
		return location;
	}

	@Override
	public String toString() {
		return "VehicleInformation [updatedOn=" + updatedOn + ", rpm=" + rpm + ", speed=" + speed + ", engineTemperature="
				+ engineTemperature + ", location=" + location + ", engineLoad=" + engineLoad + ", fuelLevel=" + fuelLevel + ", vin=" + vin
				+ "]";
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public VehicleInformation setLocation(GPSInfo location) {
		if (location == null)
			this.location = new GPSInfo(null, null);
		else
			this.location = location;
		return this;
	}

	public VehicleInformation setLocation(BigDecimal latitude, BigDecimal longitude) {
		this.location = new GPSInfo(latitude, longitude);
		return this;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public VehicleInformation setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public Integer getRpm() {
		return rpm;
	}

	public VehicleInformation setRpm(Integer rpm) {
		this.rpm = rpm;
		return this;
	}

	public Integer getSpeed() {
		return speed;
	}

	public VehicleInformation setSpeed(Integer speed) {
		this.speed = speed;
		return this;
	}

	public Float getEngineTemperature() {
		return engineTemperature;
	}

	public VehicleInformation setEngineTemperature(Float engineTemperature) {
		this.engineTemperature = engineTemperature;
		return this;
	}

	public BigDecimal getLatitude() {
		return this.getLocation().getLatitude();
	}

	public BigDecimal getLongitude() {
		return this.getLocation().getLongitude();
	}

	public VehicleInformation setRpm(String parameter) {
		try {
			return setRpm(Integer.parseInt(parameter));
		} catch (Exception ex) {
			return this;
		}
	}

	public VehicleInformation setSpeed(String parameter) {
		try {
			return setSpeed(Integer.parseInt(parameter));
		} catch (Exception ex) {
			return this;
		}
	}

	public VehicleInformation setEngineTemperature(String parameter) {
		try {
			return setEngineTemperature(Float.parseFloat(parameter));
		} catch (Exception ex) {
			return this;
		}
	}

	public VehicleInformation setEngineLoad(Float engineLoad) {
		this.engineLoad = engineLoad;
		return this;
	}

	public VehicleInformation setFuelLevel(Float fuelLevel) {
		this.fuelLevel = fuelLevel;
		return this;
	}

	public Float getEngineLoad() {
		return engineLoad;
	}

	public Float getFuelLevel() {
		return fuelLevel;
	}

	public VehicleInformation setVIN(String vin) {
		this.vin = vin;
		return this;
	}
}
