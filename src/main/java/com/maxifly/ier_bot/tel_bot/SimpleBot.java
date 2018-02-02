package com.maxifly.ier_bot.tel_bot;

import com.maxifly.ier_bot.ggl_clnt.Quickstart;
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
    private Quickstart qs;


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
            System.out.println(message.getText());
            if (message.getText().equals("/help"))
                sendMsg(message, "Привет, я робот");
            else if (message.getText().equals("/price")) {
//                sendMsg(message, "price");
                sendMsg(message, qs.getAllValues());
            } else
                sendMsg(message, "Я не знаю что ответить на это");
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
