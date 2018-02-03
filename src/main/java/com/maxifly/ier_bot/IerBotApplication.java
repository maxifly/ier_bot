package com.maxifly.ier_bot;

import com.maxifly.ier_bot.config.AppConfig;
import com.maxifly.ier_bot.tel_bot.SimpleBot;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@SpringBootApplication
public class IerBotApplication {
    static{
		ApiContextInitializer.init();
	}
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		SimpleBot bot = (SimpleBot) ctx.getBean("simpleBot");
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot(bot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

//		SpringApplication.run(IerBotApplication.class, args);
	}

}
