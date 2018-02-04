package com.maxifly.ier_bot.ggl_clnt.model;

import java.time.LocalDateTime;

public class PriceRow {
    private final String itemCode;
    private final String price;
    private final LocalDateTime info_date;

    public PriceRow(String itemCode, String price, LocalDateTime info_date) {
        this.itemCode = itemCode;
        this.price = price;
        this.info_date = info_date;
    }

    public PriceRow(String itemCode, String price) {
        this(itemCode, price, LocalDateTime.now());
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getPrice() {
        return price;
    }

    public LocalDateTime getInfo_date() {
        return info_date;
    }

    @Override
    public String toString() {
        return "PriceRow{" +
                "itemCode='" + itemCode + '\'' +
                ", price='" + price + '\'' +
                ", info_date=" + info_date +
                '}';
    }
}
