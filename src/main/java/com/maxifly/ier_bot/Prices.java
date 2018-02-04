package com.maxifly.ier_bot;

import com.maxifly.ier_bot.ggl_clnt.GetVal_Exception;
import com.maxifly.ier_bot.ggl_clnt.Quickstart;
import com.maxifly.ier_bot.ggl_clnt.model.PriceRow;
import com.maxifly.ier_bot.tel_bot.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

public class Prices {
    @Autowired
    private Quickstart qs;

    private Logger logger = LoggerFactory.getLogger(Prices.class);

    @Cacheable(value = "prices", unless = "#result == null")
    public PriceRow getPrice(String code) throws GetVal_Exception {
        logger.info("get price");
        Map<String, PriceRow> prices = get_prices();
        PriceRow price = prices.get(code.toUpperCase());
        return price;
    }

    @CacheEvict(value = "prices", allEntries = true)
    public void flushAll() {
        logger.info("clear cache");
    }

    public Map<String, PriceRow> getAllPrice() throws GetVal_Exception {
        return get_prices();
        //todo КАК-ТО ПЕРЕДЕЛАТЬ
    }

    private Map<String, PriceRow> get_prices() throws GetVal_Exception {
        return qs.getAllValues();
    }

}
