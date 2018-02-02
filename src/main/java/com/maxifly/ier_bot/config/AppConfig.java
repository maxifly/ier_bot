package com.maxifly.ier_bot.config;

import com.maxifly.ier_bot.SimpleBot;
import com.maxifly.ier_bot.TT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "classpath:bot.properties",
        "classpath:application.properties" //if same key, this will 'win'
})
public class AppConfig {

    @Value("${bot.username}")
    String botUserName;

    @Value("${bot.token}")
    String botToken;



    @Bean()
    public TT retTT() {
        return new TT();
    }

    @Bean()
    public SimpleBot simpleBot() {
        SimpleBot sb = new SimpleBot();
        sb.setBot_Token(this.botToken);
        sb.setBot_Username(this.botUserName);
        return sb;
    }
}
