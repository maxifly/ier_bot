package com.maxifly.ier_bot.ggl_clnt.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PriceRow {
    private final String itemCode; // ISIN
    private  String ticker;
    private  String name; // Краткое наименование
    private  String lastTransactionDate; // Дата последней сделки
    private  String lastTransactionPrice; // цена последней сделки (Last)
    private  String bidPrice; // Цена спроса Bid Price (decimal)
    private  String askPrice; // Цена предложения Ask Price (decimal)
    private  String midYTM; // Средняя доходность к погашению Mid YTM
    private  String bidYTM; // Доходность по спросу Bid YTM
    private  String askYTM; // Доходность по предложению Ask YTM
    private  String coupon; // Купон
    private  String repayment; // Погашение
    private  String nKD; // НКД
    private  String currency; // Валюта
    private  String paymentCount; // кол-во выплат
    private  String avgDuration; // Средняя модельная дюрация
    private  String moodys;
    private  String sP;
    private  String fitch;
    private  String country; // Страна риска
    private  String minLot; // мин. Лот
    private  String repaymentYear; // лет до погашения
    private  String repaymentType ; // тип погашения
    private  String couponType; // тип купона
    private  String debt; // долг
    private  String group; // Группа
    private final LocalDateTime info_date;

    public PriceRow(String itemCode, LocalDateTime info_date) {
        this.itemCode = itemCode;
        this.info_date = info_date;
    }

    public PriceRow(String itemCode) {
        this(itemCode,  LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "PriceRow{" +
                "itemCode='" + itemCode + '\'' +
                ", name='" + name + '\'' +
                ", info_date=" + info_date +
                '}';
    }
}
