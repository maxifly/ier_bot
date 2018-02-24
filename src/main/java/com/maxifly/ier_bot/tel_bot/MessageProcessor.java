package com.maxifly.ier_bot.tel_bot;

import com.maxifly.ier_bot.Prices;
import com.maxifly.ier_bot.ggl_clnt.GetVal_Exception;
import com.maxifly.ier_bot.ggl_clnt.Quickstart;
import com.maxifly.ier_bot.ggl_clnt.model.PriceRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

public class MessageProcessor {
    @Autowired
    Prices prices;
    @Autowired
    ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(MessageProcessor.class);

    public String getPrice(String[] msg_tokens) {
        if (msg_tokens.length < 2) {
            return "Не указан код";
        }
        String code = msg_tokens[1];
        try {
            PriceRow price = prices.getPrice(code.toUpperCase());

            if (price == null) {
                return code + ": Данные не найдены";
            } else {
                return applicationContext.getMessage("msg.price",
                        (new Object[]{price.getName(),
                                price.getBidPrice(),
                                price.getAskPrice(),
                                price.getLastTransactionPrice(),
                                price.getLastTransactionDate()}),
                        Locale.getDefault());
            }
        } catch (GetVal_Exception e) {
            return "Ошибка получения данных";
        }
    }

    public String getInfo(String[] msg_tokens) {
        if (msg_tokens.length < 2) {
            return "Не указан код";
        }
        String code = msg_tokens[1];
        try {
            PriceRow price = prices.getPrice(code.toUpperCase());

            if (price == null) {
                return code + ": Данные не найдены";
            } else {
                return applicationContext.getMessage("msg.info",
                        (new Object[]{price.getName(),
                                price.getCurrency(),
                                price.getBidPrice(),
                                price.getAskPrice(),
                                price.getLastTransactionPrice(),
                                price.getCoupon(),
                        price.getAvgDuration(),
                        price.getMidYTM(),
                        price.getCountry(),
                        price.getGroup()}),
                        Locale.getDefault());
            }
        } catch (GetVal_Exception e) {
            return "Ошибка получения данных";
        }
    }


    public String clear() {
        prices.flushAll();
        return "Данные сброшены";
    }

    public String getAllPrice_Resp() {
        StringBuilder sb = new StringBuilder("\n");
        LocalDateTime localDateTime = null;
        try {
            Map<String, PriceRow> prices = this.prices.getAllPrice();
            for (Map.Entry<String, PriceRow> entry : prices.entrySet()) {
                if (localDateTime == null) localDateTime = entry.getValue().getInfo_date();
                if (localDateTime.compareTo(entry.getValue().getInfo_date()) < 0) {
                    localDateTime = entry.getValue().getInfo_date();
                }
                sb.append(entry.getValue().getItemCode());
                sb.append(": ");

                sb.append("\n");
            }

            if (prices.size() == 0) {
                return "Все цены:\n нет данных";
            } else {
                return "Все цены:" + "на дату: " + localDateTime + sb.toString();
            }
        } catch (GetVal_Exception e) {
            return "Ошибка получения данных";
        }

    }

//    private Map<String, PriceRow> get_prices() throws GetVal_Exception {
//        return qs.getAllValues();
//    }

}
