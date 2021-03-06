package com.hrdatabank.telegram.api;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.hrdatabank.telegram.entities.LinkedUserTarget;
import com.hrdatabank.telegram.entities.MessageTable;
import com.hrdatabank.telegram.entities.Wallet;
import com.hrdatabank.telegram.services.LinkedUserTargetService;
import com.hrdatabank.telegram.services.MessageTableService;
import com.hrdatabank.telegram.services.WalletService;

@Configuration
@ComponentScan(basePackages = { "com.hrdatabank.telegram.api" })
@EntityScan(basePackages = { "com.hrdatabank.telegram.entities" })
@EnableJpaRepositories(basePackages = { "com.hrdatabank.telegram.repositories" })
@ComponentScan(basePackages = { "com.hrdatabank.telegram.*" })
@EnableAsync
@SpringBootApplication
public class Main extends TelegramLongPollingBot implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private MessageTable messageToRetreive;
	TelegramBotsApi botsApi = new TelegramBotsApi();

	@Value("${bot.token}")
	private String token;

	@Value("${bot.username}")
	private String username;

	@Autowired
	MessageTableService messageTableService;
	@Autowired
	LinkedUserTargetService linkedUserTargetService;
	@Autowired
	WalletService walletService;

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

	public void sendMessageToChannel(String target, String content) {

		SendMessage privateMessage = new SendMessage();
		privateMessage.setChatId((long) Long.valueOf(target)).setText(content);
		try {
			sendMessage(privateMessage);
		} catch (TelegramApiException e) {
			logger.error("problem", e);
		}
	}

	public void savingMessages(Message messageTosave) {
		messageTableService.saveMessageTable(
				new MessageTable(messageTosave.getChatId().toString(), messageTosave.getFrom().getId().toString(),
						messageTosave.getDate().toString(), messageTosave.getText()));

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onUpdateReceived(Update update) {
		// We check if the update has a message and the message has text
		String answer = StaticVariables.FIRST_ANSWER.getValue() + encryptDecryptData(StaticVariables.ENCODE.getValue(),
				update.getMessage().getFrom().getId().toString());
		/***************************/

		// case of existing message on updates
		if (update.hasMessage() && update.getMessage().hasText()) {
			// case of chat with the bot directly
			savingMessages(update.getMessage());
			// if the user prints /wallet_check
			if (update.getMessage().getText().equalsIgnoreCase(StaticVariables.WALLET_CHECK_COMMAND.getValue())) {
				// if message is /wallet_check with idUser
				if (walletService.findByIDUserWallet(update.getMessage().getFrom().getId().toString()) != null) {
					sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
							StaticVariables.WALLET_CHECKING_MESSAGE.getValue()
									+ walletService.findByIDUserWallet(update.getMessage().getFrom().getId().toString())
											.getWalletCode());
				} else {
					sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
							StaticVariables.ERROR_CHECKING_WALLET.getValue());
				}

			}

			// error on wallet id saving
			if (update.getMessage().getText().equalsIgnoreCase(StaticVariables.WALLET_SAVE_COMMAND.getValue())) {
				// if message is /wallet with idUser
				sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
						StaticVariables.ERROR_SAVING_WALLET.getValue());
			}
			// only start command
			if (update.getMessage().getText().equals(StaticVariables.START.getValue())
					&& update.getMessage().getChatId() == (long) update.getMessage().getFrom().getId()) {
				sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
						StaticVariables.WELCOME_MESSAGE.getValue());
				return;
			}
			if (update.getMessage().getChatId() == (long) update.getMessage().getFrom().getId()) {
				// retreive message content
				String urlPattern = update.getMessage().getText();
				String[] parts = urlPattern.split(" ");
				String part1 = parts[0];
				String part2 = parts[1];
				// extract idUser by decoding using base64 encoding
				String idUser = encryptDecryptData(StaticVariables.DECODE.getValue(), part2);
				if (part1.equalsIgnoreCase(StaticVariables.START.getValue()))
					if (part2 != null && !part2.equalsIgnoreCase("")) {
						// if message is /start with idUser
						// send message welcome to bot and this is your target group, join it!!
						sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
								StaticVariables.WELCOME_MESSAGE.getValue());
						LinkedUserTarget linkedUserTarget = new LinkedUserTarget(
								update.getMessage().getFrom().getId().toString(), idUser, new Date());
						if (!update.getMessage().getFrom().getId().toString().equalsIgnoreCase(idUser))
							linkedUserTargetService.saveLinkedUserTarget(linkedUserTarget);
					} else if (part2 == null || part2.equalsIgnoreCase(""))
						sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
								StaticVariables.WELCOME_MESSAGE.getValue());

					else
						sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
								StaticVariables.WELCOME_MESSAGE.getValue());

				/**************** wallet case **********************/
				// if the user prints /wallet_save
				if (part1.equalsIgnoreCase(StaticVariables.WALLET_SAVE_COMMAND.getValue()) && part2 != null
						&& !part2.equalsIgnoreCase("")) {
					// if message is /wallet with idUser
					if (walletService.findByIDUserWallet(update.getMessage().getFrom().getId().toString()) == null) {
						Wallet wallet = new Wallet(update.getMessage().getFrom().getId().toString(), part2);
						walletService.saveWallet(wallet);

					} else {
						walletService.deleteWallet(
								walletService.findByIDUserWallet(update.getMessage().getFrom().getId().toString()));
						Wallet wallet = new Wallet(update.getMessage().getFrom().getId().toString(), part2);
						walletService.saveWallet(wallet);

					}
					sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
							StaticVariables.WALLET_SAVING_SUCCESSFULLY.getValue());
				}

				/************************ end wallet case ***********************/
				/********** start case finished *********/
				// handle messages bot into the group using id_room
				// returns thanks if you print /start
				// otherwise help message
			} else if (update.getMessage().getChatId().toString()
					.equalsIgnoreCase(StaticVariables.ID_ROOM_GROUP.getValue())) {
				if (update.getMessage().getText().contains(StaticVariables.START.getValue())) {
					sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
							StaticVariables.THANKS_MESSAGE.getValue());
					sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
							StaticVariables.WELCOME_MESSAGE.getValue());
				} else {
					return;
				}
			}
			// handle start command inside the bot
			// if (update.getMessage().getChatId() == (long)
			// update.getMessage().getFrom().getId()
			// && update.getMessage().getText().equals(StaticVariables.START.getValue()))
			// sendMessageToChannel(update.getMessage().getFrom().getId().toString(),
			// StaticVariables.WELCOME_MESSAGE.getValue());

		}

		// thanks message to joiner group
		// send target answer message with invitation link and some details
		update.getMessage().getNewChatMembers().forEach(e -> {
			List<LinkedUserTarget> linkedUserTargets = linkedUserTargetService
					.findLinkedUserTarget(e.getId().toString());
			if (!linkedUserTargets.isEmpty() && !linkedUserTargets.get(0).isSentInvitation()) {

				try {
					linkedUserTargetService.deleteLinkedUserTarget(linkedUserTargets.get(0));
					// turn the flag to true of sending invitation
					linkedUserTargets.get(0).setSentInvitation(true);
					linkedUserTargets.get(0).setInvitationDate(new Date());
					linkedUserTargetService.saveLinkedUserTarget(linkedUserTargets.get(0));
					sendMessageToChannel(linkedUserTargets.get(0).getFromUser(), StaticVariables.NOTIFICATION_MESSAGE
							.getValue()
							+ StaticVariables.WEBSITE_URL.getValue()
							+ encryptDecryptData(
									StaticVariables.ENCODE.getValue(), linkedUserTargets.get(0).getFromUser())
							+ StaticVariables.HOME_PAGE.getValue()
							+ (linkedUserTargetService
									.countInvitedLinkedUserTarget(linkedUserTargets.get(0).getFromUser()) * 10 + 10)
							+ StaticVariables.GETTING_GOLT.getValue());
				} catch (Exception e1) {
					logger.info("error in save, duplicate target telegram", e1);
				}

			}

			sendMessageToChannel(e.getId().toString(), StaticVariables.THANKS_MESSAGE.getValue());
			sendMessageToChannel(e.getId().toString(), answer);
		});

		/********* finish join group messages **********************/

	}

	public String encryptDecryptData(String strategy, String data) {
		String value;
		value = (strategy.equals(StaticVariables.ENCODE.getValue())) ? new String(Base64.encodeBase64(data.getBytes()))
				: new String(Base64.decodeBase64(data));
		return value;
	}

	@Override
	public String getBotUsername() {
		return username;
	}

	@Override
	public String getBotToken() {
		// Return bot token from BotFather
		return token;
	}

	public MessageTable getMessageToRetreive() {
		return messageToRetreive;
	}

	public void setMessageToRetreive(MessageTable messageToRetreive) {
		this.messageToRetreive = messageToRetreive;
	}

}
