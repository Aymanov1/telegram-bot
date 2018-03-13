package com.hrdatabank.telegram.api;

public enum StaticVariables {

	NOTIFICATION_MESSAGE("速報です！あなたのご友人がGOLの公式グループに参加しました。\r\n" + 
			"\n" + 	"こちらのリンクを使い、更に多くのGOLTを集めましょう！"),
	WELCOME_MESSAGE("ありがとうございます！\n" + 
			"下記のURLからGOLの公式テレグラムグループに参加して、10GOLTをゲットして下さい！\n" + 
			"https://t.me/gogogrp"),
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
