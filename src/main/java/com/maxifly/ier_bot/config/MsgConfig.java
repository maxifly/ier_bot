package com.maxifly.ier_bot.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MsgConfig{
        private Logger logger = (Logger) LoggerFactory.getLogger(AppConfig.class);
        @Bean
        MessageSource messageSource() {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("local/messages");
            return messageSource;
        }
}
