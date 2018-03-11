package com.hrdatabank.telegram.api;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.hrdatabank.telegram.entities.MessageTable;
import com.hrdatabank.telegram.services.MessageTableService;

@Configuration
@ComponentScan(basePackages = { "com.hrdatabank.telegram.api" })
@EntityScan(basePackages = { "com.hrdatabank.telegram.entities" })
@EnableJpaRepositories(basePackages = { "com.hrdatabank.telegram.repositories" })
@ComponentScan(basePackages = { "com.hrdatabank.telegram.services" })
@EnableAsync
@SpringBootApplication
public class Main extends TelegramLongPollingBot implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private MessageTable messageToRetreive;
	TelegramBotsApi botsApi = new TelegramBotsApi();
	public static final String WELCOMEMESSAGE = "Astonishing News! Your friend has just joined BAKU group!";

	public static final String THANKSMESSAGE = "ありがとうございました。";
	public static final String ENCODE = "ENCODE";
	public static final String DECODE = "DECODE";
	@Autowired
	MessageTableService messageTableService;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// Initialize Api Context
		ApiContextInitializer.init();
		registerbot();
	}

	public void registerbot() {

		try {
			botsApi.registerBot((TelegramLongPollingBot) this);
		} catch (TelegramApiException e) {
			logger.error("error", e);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text
		SendMessage messageToGroup = new SendMessage();
		String answer = "Thank you for joining BAKU group!\n" + "https://saehyungjung.typeform.com/to/lDpjGV?t="
				+ update.getMessage().getFrom().getId() + "}\n" + "Send your wallet code and grant 5BAK.\n" + "\n"
				+ "And you can grant another 5BAK/person if you invite your friend.\n"
				+ "Here is your invitiation link!\n" + "https://t.me/msgCheckerBot?start="
				+ encryptDecryptData(ENCODE, update.getMessage().getFrom().getId().toString());
		if (update.hasMessage() && update.getMessage().hasText()) {

			messageToRetreive = new MessageTable();

			messageToRetreive.setFromUser(update.getMessage().getFrom().getId().toString());
			messageToRetreive.setDate(update.getMessage().getDate().toString());
			messageToRetreive.setMessageIdTelegram(update.getMessage().getChatId().toString());
			messageToRetreive.setMessageText(update.getMessage().getText());
			messageTableService.saveMessageTable(messageToRetreive);

			// test if the message inside gogo group
			if (update.getMessage().getChatId().toString().equals("-1001194238685")) {
				messageToGroup = new SendMessage();
				messageToGroup.setChatId(update.getMessage().getChatId()).setText(THANKSMESSAGE);
				try {
					sendMessage(messageToGroup);
				} catch (TelegramApiException e) {
					logger.error("problem on send message", e);
				} finally {
					if (!update.getMessage().getNewChatMembers().isEmpty()) {
						update.getMessage().getNewChatMembers().get(update.getMessage().getNewChatMembers().size())
								.getId();
						SendMessage privateMessage = new SendMessage();

						privateMessage
								.setChatId((long) update.getMessage().getNewChatMembers()
										.get(update.getMessage().getNewChatMembers().size()).getId())
								.setText(WELCOMEMESSAGE);
						try {
							sendMessage(privateMessage);
						} catch (TelegramApiException e) {
							logger.error("problem", e);
						}
					}

				}
			} else {

				messageToGroup = new SendMessage();
				messageToGroup.setChatId(update.getMessage().getChatId()).setText(answer);

				try {
					sendMessage(messageToGroup); // Sending our message object to user

				} catch (TelegramApiException e) {
					logger.error("problem", e);
				}
			}
		}

	}

	public String encryptDecryptData(String strategy, String data) {
		String value;
		value = (strategy.equals(ENCODE)) ? new String(Base64.encodeBase64(data.getBytes()))
				: new String(Base64.decodeBase64(data));
		return value;
	}

	@Override
	public String getBotUsername() {
		return "msgCheckerBot";
	}

	@Override
	public String getBotToken() {
		// Return bot token from BotFather
		return "554633303:AAGhg6Eyankjit1JJrHiZ1w-6u0nT3iM0qM";
	}

	public MessageTable getMessageToRetreive() {
		return messageToRetreive;
	}

	public void setMessageToRetreive(MessageTable messageToRetreive) {
		this.messageToRetreive = messageToRetreive;
	}

}
