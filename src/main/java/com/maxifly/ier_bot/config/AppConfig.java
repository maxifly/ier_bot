package com.maxifly.ier_bot.config;

import com.maxifly.ier_bot.ggl_clnt.Quickstart;
import com.maxifly.ier_bot.tel_bot.SimpleBot;
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

    @Value("${sheet.id}")
    String sheetId;
    @Value("${sheet.range}")
    String sheetRange;



    @Bean()
    public SimpleBot simpleBot() {
        SimpleBot sb = new SimpleBot();
        sb.setBot_Token(this.botToken);
        sb.setBot_Username(this.botUserName);
        return sb;
    }

    @Bean
    public Quickstart quickstart(){
        Quickstart qs = new Quickstart();
        qs.setSpreadsheetId(sheetId);
        qs.setRange(sheetRange);
        return qs;
    }
}
