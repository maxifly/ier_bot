package com.maxifly.ier_bot.tel_bot;

import com.maxifly.ier_bot.rights.RightsCSVChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.telegram.telegrambots.api.objects.User;

public class UserChecker {
    private Logger logger = LoggerFactory.getLogger(UserChecker.class);

    @Autowired
    RightsCSVChecker rightsChecker;

    @Cacheable(value = "rights", key="#id", unless = "#result == false")
    public Boolean isUserHaveRight(Integer id) {
        logger.info("isUserHaveRight {}", id);
        return false;
    }

    @Cacheable(value = "rights", key="#id", unless = "#result == false")
    public boolean checkUserPhone(Integer id, String phone) {
        //TODO Это заглушка
        Boolean result = rightsChecker.isPhoneExists(phone);

        return result;
    }
}
