package com.example.cherrypickserver.article.domain;

import com.example.cherrypickserver.article.exception.IndustryNotFoundException;

public enum Industry {
	STEEL("steel", "철강"),
	PETROLEUM_CHEMICAL("Petroleum/Chemical", "석유&화학"),
	OIL_REFINING("oilrefining", "정유"),
	SECONDARY_BATTERY("secondarybattery", "2차 전지"),
	SEMICONDUCTOR("Semiconductor", "반도체"),
	DISPLAY("Display", "디스플레이"),
	MOBILE("Mobile", "휴대폰"),
	IT("It", "IT"),
	CAR("car", "자동차"),
	SHIPBUILDING("Shipbuilding", "조선"),
	SHIPPING("Shipping", "해운"),
	FNB("FnB", "F&B"),
	RETAIL_DISTRIBUTION("RetailDistribution", "소매유통"),
	CONSTRUCTION("Construction", "건설"),
	HOTEL_TRAVEL("HotelTravel", "호텔&여행&항공"),
	FIBER_CLOTHING("FiberClothing", "섬유&의류");

	private final String value;

	private final String valueGpt;

	Industry(String value, String valueGpt) {
		this.value = value;
		this.valueGpt = valueGpt;
	}

	public String getValue() {
		return value;
	}

	public String getValueGpt() {
		return valueGpt;
	}

	public static Industry fromValue(String value) {
		for (Industry industry : Industry.values()) {
			if (industry.value.equalsIgnoreCase(value)) {
				return industry;
			}
		}
		throw new IndustryNotFoundException();
	}
}
