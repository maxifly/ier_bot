package com.maxifly.ier_bot.ggl_clnt;

import com.maxifly.ier_bot.tel_bot.SimpleBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.generics.BotSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

public class BotRegister {
    private final Logger logger = LoggerFactory.getLogger(BotRegister.class);

    private List<BotSession> sessions = new ArrayList<>();

    @Autowired
    private SimpleBot bot;

    static {
        ApiContextInitializer.init();
    }

    @PostConstruct
    public void start() {
        logger.info("Starting auto config for telegram bots");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            sessions.add(telegramBotsApi.registerBot(bot));
        } catch (TelegramApiException e) {
            logger.error("Failed to register bot {} due to error {}", bot.getBotUsername(), e.getMessage());

        }
    }

    @PreDestroy
    public void stop() {
        sessions.stream().forEach(session -> {
            if (session != null) {
                session.stop();
            }
        });
    }


}
