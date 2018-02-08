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
import org.springframework.util.NumberUtils;


import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Prices {
    @Autowired
    private Quickstart qs;

    private Logger logger = LoggerFactory.getLogger(Prices.class);

    @Cacheable(value = "prices", unless = "#result == null")
    public PriceRow getPrice(String code) throws GetVal_Exception {
        logger.info("get price");
        Map<String, PriceRow> prices = get_prices();
        PriceRow price = prices.get(code.toUpperCase());
        if (!isValidPrice(price)) return null;
        return price;
    }

    @CacheEvict(value = "prices", allEntries = true)
    public void flushAll() {
        logger.info("clear cache");
    }

    public Map<String, PriceRow> getAllPrice() throws GetVal_Exception {
        Map<String, PriceRow> result = new TreeMap<>();

        for (Map.Entry<String, PriceRow> row : get_prices().entrySet()) {
            if (isValidPrice(row.getValue())) result.put(row.getKey(),row.getValue());
        }

//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return result;
        //todo КАК-ТО ПЕРЕДЕЛАТЬ. Может кешировать как-то?
    }

    private Map<String, PriceRow> get_prices() throws GetVal_Exception {
        Map<String, PriceRow> result = new HashMap<>();
        qs.getAllValues(result);
        return result;
    }

    private boolean isValidPrice(PriceRow priceRow) {
        if (priceRow == null) return false;
        try {
            NumberUtils.parseNumber(priceRow.getPrice(), Float.class, NumberFormat.getNumberInstance(new Locale("ru","RU")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
