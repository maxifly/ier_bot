package com.maxifly.ier_bot.config;

import com.maxifly.ier_bot.ggl_clnt.Quickstart;
import com.maxifly.ier_bot.tel_bot.MessageProcessor;
import com.maxifly.ier_bot.tel_bot.SimpleBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "classpath:bot.properties",
        "classpath:application.properties"
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

    @Value("${sheet.column.code}")
    Integer columnCode;
    @Value("${sheet.column.price}")
    Integer columnPrice;



    @Bean()
    public SimpleBot simpleBot() {
        SimpleBot sb = new SimpleBot();
        sb.setBot_Token(this.botToken);
        sb.setBot_Username(this.botUserName);
        return sb;
    }

    @Bean()
    public MessageProcessor messageProcessor() {
        return new MessageProcessor();
    }

    @Bean
    public Quickstart quickstart(){
        Quickstart qs = new Quickstart();
        qs.setSpreadsheetId(sheetId);
        qs.setRange(sheetRange);
        qs.setCodeColumn(columnCode);
        qs.setPriceColumn(columnPrice);
        return qs;
    }
}
