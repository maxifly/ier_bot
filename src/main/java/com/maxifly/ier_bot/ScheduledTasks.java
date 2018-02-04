package com.maxifly.ier_bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


public class ScheduledTasks {
    @Autowired
    private Prices prices;
    private Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(fixedDelayString = "${prices.cache.flush.delay}")
    public void flushPrices() {
      logger.debug("flush");
      prices.flushAll();
    }

}
