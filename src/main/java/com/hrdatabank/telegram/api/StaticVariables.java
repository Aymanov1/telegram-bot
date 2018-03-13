package com.hrdatabank.telegram.api;

public enum StaticVariables {

	NOTIFICATION_MESSAGE("Astonishing News! Your friend has just joined BAKU group!"),
	WELCOME_MESSAGE("Hello!\nJoin BakuGroup and get 5BAK\n https://t.me/gogogrp"),
	HELP_MESSAGE("Help! please contact an admin"),
	THANKS_MESSAGE("ありがとうございました。"),
	ID_ROOM_GROUP("-1001194238685"),	
	ENCODE("ENCODE"),
	DECODE("DECODE");

	private String value = "";

	private StaticVariables(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
