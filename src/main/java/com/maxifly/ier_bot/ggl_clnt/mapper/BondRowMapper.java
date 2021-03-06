package com.maxifly.ier_bot.ggl_clnt.mapper;

import com.maxifly.ier_bot.ggl_clnt.model.PriceRow;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BondRowMapper {
    private final int columnISIN = 0;
    private final int columnTicker = 1;
    private final int columnName = 2; // Краткое наименование
    private final int columnLastTransactionDate = 3; // Дата последней сделки
    private final int columnLastTransactionPrice = 4; // цена последней сделки (Last)
    private final int columnBidPrice = 5; // Цена спроса Bid Price (decimal)
    private final int columnAskPrice = 6; // Цена предложения Ask Price (decimal)
    private final int columnMidYTM = 7; // Средняя доходность к погашению Mid YTM
    private final int columnBidYTM = 8; // Доходность по спросу Bid YTM
    private final int columnAskYTM = 9; // Доходность по предложению Ask YTM
    private final int columnCoupon = 10; // Купон
    private final int columnRepayment = 11; // Погашение
    private final int columnNKD = 12; // НКД
    private final int columnCurrency = 13; // Валюта
    private final int columnPaymentCount = 14; // кол-во выплат
    private final int columnAvgDuration = 15; // Средняя модельная дюрация
    private final int columnMoodys = 16;
    private final int columnSP = 17;
    private final int columnFitch = 18;
    private final int columnCountry = 19; // Страна риска
    private final int columnMinLot = 20; // мин. Лот
    private final int columnRepaymentYear = 21; // лет до погашения
    private final int columnRepaymentType = 22; // тип погашения
    private final int columnCouponType = 23; // тип купона
    private final int columnDebt = 24; // долг
    private final int columnGroup = 25; // Группа

//TODO Прикрутить валидацию
    public PriceRow createPriceRow(List<Object> row, LocalDateTime localDateTime) {
        PriceRow priceRow = new PriceRow((String) row.get(columnISIN), localDateTime);
        priceRow.setTicker(convertWithValidate((String) row.get(columnTicker)));
        priceRow.setName(convertWithValidate((String) row.get(columnName)));
        priceRow.setLastTransactionDate(convertWithValidate((String) row.get(columnLastTransactionDate)));
        priceRow.setLastTransactionPrice(convertWithValidate((String) row.get(columnLastTransactionPrice)));
        priceRow.setBidPrice(convertWithValidate((String) row.get(columnBidPrice)));
        priceRow.setAskPrice(convertWithValidate((String) row.get(columnAskPrice)));
        priceRow.setMidYTM(convertWithValidate((String) row.get(columnMidYTM)));
        priceRow.setBidYTM(convertWithValidate((String) row.get(columnBidYTM)));
        priceRow.setAskYTM(convertWithValidate((String) row.get(columnAskYTM)));
        priceRow.setCoupon(convertWithValidate((String) row.get(columnCoupon)));
        priceRow.setRepayment(convertWithValidate((String) row.get(columnRepayment)));
        priceRow.setNKD(convertWithValidate((String) row.get(columnNKD)));
        priceRow.setCurrency(convertWithValidate((String) row.get(columnCurrency)));
        priceRow.setPaymentCount(convertWithValidate((String) row.get(columnPaymentCount)));
        priceRow.setAvgDuration(convertWithValidate((String) row.get(columnAvgDuration)));
        priceRow.setMoodys(convertWithValidate((String) row.get(columnMoodys)));
        priceRow.setSP(convertWithValidate((String) row.get(columnSP)));
        priceRow.setFitch(convertWithValidate((String) row.get(columnFitch)));
        priceRow.setCountry(convertWithValidate((String) row.get(columnCountry)));
        priceRow.setMinLot(convertWithValidate((String) row.get(columnMinLot)));
        priceRow.setRepaymentYear(convertWithValidate((String) row.get(columnRepaymentYear)));
        priceRow.setCouponType(convertWithValidate((String) row.get(columnCouponType)));
        priceRow.setDebt(convertWithValidate((String) row.get(columnDebt)));
        priceRow.setGroup(convertWithValidate((String) row.get(columnGroup)));
        return priceRow;
    }

    private String convertWithValidate(String source) {
        if (source == null) return null;
        if (source.trim().startsWith("#")) return null;
        return source.trim();

    }
}
