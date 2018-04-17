package com.hrdatabank.telegram.api;

public enum StaticVariables {
	/* gol id group*/ ID_ROOM_GROUP("-1001262127609"),
	NOTIFICATION_MESSAGE("速報です！あなたのご友人がGOLの公式グループに参加しました。\r\n" + 
			"\r\n" + "10GOLTゲットしました。\r\n" + 
			"\r\n" + "こちらのリンクを使い、更に多くのGOLTを集めましょう！ \n"),
	WELCOME_MESSAGE("ありがとうございます！\n" + "下記のURLからGOLの公式テレグラムグループに参加して、10GOLTをゲットして下さい！\n"
							+ "https://t.me/globalopenleagjp"),
	HELP_MESSAGE("Help! please contact an admin"),
	FIRST_ANSWER("GOLの公式グループに参加いただきありがとうございます！\n" + "10GOLTを贈呈致します。\n" + "\n"
				+ "※ 後日、GOLTを贈呈する為のウォレットアドレスを入力するフォームをお送り致します。\n" + "\n" + "また、ご友人をGOLに誘ってみませんか？\n"
				+ "下記のURLからご友人の方がGOLの公式グループに参加しましたら、その都度更に10GOLTを贈呈します。\n" + "\n" + "もちろん、参加されたご友人にも10GOLTを贈呈致します。\n"
				+ "http://jp.goltoken.io/?start="),
	THANKS_MESSAGE("ありがとうございました。"),
	WEBSITE_URL(" http://jp.goltoken.io/?start="),
	HOME_PAGE("このホームページ経由から申し込みがあったら、あなたに報酬が入ります。\n"
			+ "報酬を受取るウォレットは“/wallet_save 受け取りウォレットID”コマンドで指定することができます。"
			+ "\n今までで合計"),
	GETTING_GOLT("GOLTをゲットしています。"),
	WALLET_COMMAND("/wallet"),
	WALLET_CHECK_COMMAND("/wallet_check"),
	WALLET_SAVE_COMMAND("/wallet_save"),
	WALLET_CHECKING_MESSAGE("現在保存されているウォレットIDは以下の通りです。"),
	ERROR_SAVING_WALLET("ウォレットの保存に失敗しました。/wallet_saveの後にウォレットIDを入れてください。\r\n" + 
			"\r\n" + 
			"例：“/wallet_save 受け取りウォレットID”"),
	ERROR_CHECKING_WALLET("現在保存されているウォレットがありません。“/wallet_save 受け取りウォレットID”コマンドで受け取りウォレットを指定することができます。"),	
	ENCODE("ENCODE"), DECODE("DECODE"), START("/start"),
	WALLET_SAVING_SUCCESSFULLY("ウォレットが保存されました。もう一回“/wallet_save 受け取りウォレットID”コマンドで受け取りウォレットを変更することができます。");

	private String value = "";

	private StaticVariables(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
