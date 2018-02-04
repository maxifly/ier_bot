package com.maxifly.ier_bot.tel_bot;

import com.maxifly.ier_bot.ggl_clnt.Quickstart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class SimpleBot extends TelegramLongPollingBot {
    @Autowired
    private MessageProcessor messageProcessor;

    private Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    static String HELP_STRING =
            "Supported commands: \n /help \n /allprice \n /price yourCode";
//    public static void main(String[] args) {
//        ApiContextInitializer.init();
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//        try {
//            telegramBotsApi.registerBot(new SimpleBot());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }

    private String bot_Username;
    private String bot_Token;


    public void setBot_Username(String bot_Username) {
        this.bot_Username = bot_Username;
    }

    public void setBot_Token(String bot_Token) {
        this.bot_Token = bot_Token;
    }

    @Override
    public String getBotUsername() {
        return this.bot_Username;
    }

    @Override
    public String getBotToken() {
        return this.bot_Token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            logger.info("Message: <" + message.getText() + ">");

            String[] msg_tockens;
            msg_tockens = message.getText().split("\\s+");

            if (msg_tockens.length == 0) {
                msg_tockens = new String[]{"/help"};
            }

            switch (msg_tockens[0]) {
                case "/help":
                    sendMsg(message, HELP_STRING);
                    break;
                case "/allprice":
                    sendMsg(message, messageProcessor.getAllPrice_Resp());
                break;
                case "/price":
                    sendMsg(message, messageProcessor.getPrice(msg_tockens));
                    break;
                default:
                    sendMsg(message, "Я не знаю что ответить на это");
            }
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
