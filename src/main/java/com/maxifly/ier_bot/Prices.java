package com.maxifly.ier_bot;

import com.maxifly.ier_bot.ggl_clnt.GetVal_Exception;
import com.maxifly.ier_bot.ggl_clnt.Quickstart;
import com.maxifly.ier_bot.ggl_clnt.model.PriceRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


import java.util.HashMap;
import java.util.Map;

public class Prices {
    @Autowired
    private Quickstart qs;

    private Logger logger = LoggerFactory.getLogger(Prices.class);

    @Cacheable(value = "prices", unless = "#result == null")
    public PriceRow getPrice(String code) throws GetVal_Exception {
        logger.info("get price");
        PriceRow price = get_price(code.toUpperCase());
        if (!isValidPrice(price)) return null;
        return price;
    }

    @CacheEvict(value = "prices", allEntries = true)
    public void flushAll() {
        logger.info("clear cache");
    }



    private PriceRow get_price(String itemCode) throws GetVal_Exception {
        Map<String, PriceRow> result = new HashMap<>();
        qs.findValue(result, itemCode);
        return result.get(itemCode.toUpperCase());
    }

    private boolean isValidPrice(PriceRow priceRow) {
        if (priceRow == null) return false;
        try {
//            NumberUtils.parseNumber(priceRow.getPrice(), Float.class, NumberFormat.getNumberInstance(new Locale("ru","RU")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
