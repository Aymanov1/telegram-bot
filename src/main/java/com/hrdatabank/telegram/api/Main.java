package com.hrdatabank.telegram.api;

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
	public static final String WELCOMEMESSAGE = "Hello!!!. Please accept the invite by doing the following NOW:\n"
			+ "\n"
			+ "1. https://t.me/BSC_Blockchain_Store to open our Telegram channel and click JOIN GROUP on the next screen\n"
			+ "2. Click here to register for a free account on the following form:\n https://docs.google.com/forms/d/e/1FAIpQLSd3staanV3WP_SNVEkOY8nRM62PXsLoa0g7xhmgEjHu77Du_Q/viewform"
			+ "\n" + " Or our website: https://bcschain.io" + "\n" + "BSC community\n" + "check our group\n"
			+ "https://t.me/gogogrp";

	public static final String THANKSMESSAGE = "ありがとうございました。";

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
					SendMessage privateMessage = new SendMessage();

					privateMessage.setChatId((long) 460915355).setText(WELCOMEMESSAGE);
					try {
						sendMessage(privateMessage);
					} catch (TelegramApiException e) {
						logger.error("problem", e);
					}
				}
			} else {

				messageToGroup = new SendMessage();
				messageToGroup.setChatId(update.getMessage().getChatId()).setText(WELCOMEMESSAGE);

				try {
					sendMessage(messageToGroup); // Sending our message object to user

				} catch (TelegramApiException e) {
					logger.error("problem", e);
				}
			}
		}

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
