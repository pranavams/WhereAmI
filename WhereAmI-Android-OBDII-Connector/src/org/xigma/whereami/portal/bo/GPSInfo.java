package org.xigma.whereami.portal.bo;

import java.math.BigDecimal;

public class GPSInfo {

	@Override
	public String toString() {
		return "GPSInfo [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

	private BigDecimal latitude;
	private BigDecimal longitude;

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public GPSInfo(BigDecimal latitude, BigDecimal longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
