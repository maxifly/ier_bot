package com.maxifly.ier_bot.tel_bot;

import com.maxifly.ier_bot.ggl_clnt.Quickstart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Contact;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SimpleBot extends TelegramLongPollingBot {
    @Autowired
    private MessageProcessor messageProcessor;
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    UserChecker userChecker;

    private Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    static String HELP_STRING =
            "Supported commands: \n /help \n /price yourISIN \n /info yourISIN";

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


        if (message != null) {


            if (message.hasText()) {
                logger.info("Message: <" + message.getText() + ">");

                String[] msg_tockens;
                msg_tockens = message.getText().split("\\s+");

                if (msg_tockens.length == 0) {
                    msg_tockens = new String[]{"/help"};
                }

                // Команды, которые можно отдать без прав
                if (msg_tockens[0] == "/start" || msg_tockens[0] == "/help") {
                    sendMsg(message, HELP_STRING);
                } else {
                    // Остальные операции требуют наличия прав
                    if (!userChecker.isUserHaveRight(message.getFrom().getId())) {
                        logger.info("User {} can not have rights.", message.getFrom().getId());
                        requestContact(message);
                    } else {
                        switch (msg_tockens[0]) {
                            case "/price":
                                sendMsg(message, messageProcessor.getPrice(msg_tockens));
                                break;
                            case "/info":
                                sendMsg(message, messageProcessor.getInfo(msg_tockens));
                                break;
                            case "/clear":
                                sendMsg(message, messageProcessor.clear());
                                break;
                            default:
                                sendMsg(message, applicationContext.getMessage("msg.unknown_message", null, Locale.getDefault()));
                        }
                    }


                }


            } else if (message.getContact() != null) {
                Contact contact = message.getContact();
                logger.info("user {} sent his contact info.",
                        contact.getUserID());
                Boolean haveRights = userChecker.checkUserPhone(contact.getUserID(), contact.getPhoneNumber());
                String text = (haveRights) ?
                        applicationContext.getMessage("msg.have_rights", null, Locale.getDefault()) :
                        applicationContext.getMessage("msg.no_rights", null, Locale.getDefault());

                clearKeyboard(message, text);
            }
        }
    }


    private void requestContact(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(applicationContext.getMessage("msg.request.contact", null, Locale.getDefault()));
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
//        ReplyKeyboardRemove replyKeyboard = new ReplyKeyboardRemove();
        KeyboardButton ikb = new KeyboardButton();
        ikb.setRequestContact(true);
        ikb.setText(applicationContext.getMessage("button.contact", null, Locale.getDefault()));
        KeyboardRow kbr = new KeyboardRow();
        kbr.add(ikb);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(kbr);

        // ikb.setText("kuku");
//        ikb.
//        List<KeyboardButton> kbs_list = new ArrayList<>();
//        List<List<KeyboardButton>> kbs_l2 = new ArrayList<>();
//        kbs_list.add(ikb);
//        kbs_l2.add(kbs_list);
        replyKeyboard.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboard);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void clearKeyboard(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
//        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        ReplyKeyboardRemove replyKeyboard = new ReplyKeyboardRemove();
//        KeyboardButton ikb = new KeyboardButton();
//        ikb.setRequestContact(true);
//        ikb.setText(applicationContext.getMessage("button.contact", null, Locale.getDefault()));
//        KeyboardRow kbr = new KeyboardRow();
//        kbr.add(ikb);
//        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//        keyboardRowList.add(kbr);

        // ikb.setText("kuku");
//        ikb.
//        List<KeyboardButton> kbs_list = new ArrayList<>();
//        List<List<KeyboardButton>> kbs_l2 = new ArrayList<>();
//        kbs_list.add(ikb);
//        kbs_l2.add(kbs_list);
//        replyKeyboard.setKeyboard(keyboardRowList);
        sendMessage.setReplyMarkup(replyKeyboard);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
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
