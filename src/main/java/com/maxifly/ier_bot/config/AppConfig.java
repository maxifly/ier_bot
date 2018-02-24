package com.maxifly.ier_bot.config;

import com.maxifly.ier_bot.Prices;
import com.maxifly.ier_bot.ScheduledTasks;
import com.maxifly.ier_bot.ggl_clnt.mapper.BondRowMapper;
import com.maxifly.ier_bot.ggl_clnt.model.PriceRow;
import com.maxifly.ier_bot.tel_bot.BotRegister;
import com.maxifly.ier_bot.ggl_clnt.Quickstart;
import com.maxifly.ier_bot.tel_bot.MessageProcessor;
import com.maxifly.ier_bot.tel_bot.SimpleBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
//@PropertySource({
//        "classpath:bot.properties",
//        "classpath:application.properties"
//})
@EnableCaching
@EnableScheduling
public class AppConfig {
    private Logger logger = LoggerFactory.getLogger(AppConfig.class);

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

    @Value("${prices.cache.flush.delay}")
    String testD;

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("prices");
    }



    @Bean()
    public SimpleBot simpleBot() {
        logger.debug("botUserName: {}",botUserName);
        logger.info("testD: {}",testD);
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
    public ScheduledTasks scheduledTasks() {
        return new ScheduledTasks();
    }
    @Bean()
    public Prices prices() {
        return new Prices();
    }


    @Bean
    public Quickstart quickstart() {
        Quickstart qs = new Quickstart();
        qs.setSpreadsheetId(sheetId);
        qs.setRange(sheetRange);
        qs.setBondRowMapper(new BondRowMapper());
        return qs;
    }

    @Bean
    public BotRegister botRegister() {
        return new BotRegister();
    }

    @Bean
    public BondRowMapper bondRowMapper() {
        return new BondRowMapper();
    }


}
