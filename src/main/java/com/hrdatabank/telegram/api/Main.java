package com.hrdatabank.telegram.api;

import java.io.InvalidObjectException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

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
import org.telegram.telegrambots.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
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
			// logger.error("error", e);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text

		if (update.hasMessage() && update.getMessage().hasText()) {
			// Set variables
			String userFirstName = update.getMessage().getChat().getFirstName();
			String userLastName = update.getMessage().getChat().getLastName();
			String username = update.getMessage().getChat().getUserName();
			long userId = update.getMessage().getChat().getId();
			String messageText = update.getMessage().getText();
			long chatId = update.getMessage().getChatId();
			String answer = "hello " + userFirstName + " \n" + messageText + " in chat room : " + chatId;
			SendMessage message = new SendMessage() // Create a message object object
					.setChatId(chatId).setText(answer);
			logger.info(update.getMessage().getChat().getFirstName());

			messageToRetreive = new MessageTable();

			messageToRetreive.setFromUser(userFirstName + " " + userLastName + " : Username= " + username);
			messageToRetreive.setDate(update.getMessage().getDate().toString());
			messageToRetreive.setMessageIdTelegram(String.valueOf(userId));
			messageToRetreive.setMessageText(messageText);
			messageTableService.saveMessageTable(messageToRetreive);
			try {
				sendMessage(message); // Sending our message object to user

			} catch (TelegramApiException e) {

			}
		}
	}

	@Override
	public String getBotUsername() {
		// Return bot username
		// If bot username is @MyAmazingBot, it must return 'MyAmazingBot'
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
