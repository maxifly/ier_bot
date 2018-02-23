package com.maxifly.ier_bot.ggl_clnt.mapper;

import com.maxifly.ier_bot.ggl_clnt.model.PriceRow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class BondRowMapperTest {
    //@Autowired
    BondRowMapper bondRowMapper = new BondRowMapper();
    @Test
    public void parse() {
//        BondRowMapper bondRowMapper = new BondRowMapper();

        List<Object> allGood = new ArrayList<>();

        allGood.add("KUKU");
        allGood.add("DCP 2.7 04/01/19");
        allGood.add("DCP MIDSTREAM OPERATING");
        allGood.add("17.02.2018");
        allGood.add("99,284");
        allGood.add("99,146");
        allGood.add("99,424");
        allGood.add("3,359173");
        allGood.add("3,488405");
        allGood.add("3,230205");
        allGood.add("2,7");
        allGood.add("01.04.2019");
        allGood.add("10,5");
        allGood.add("USD");
        allGood.add("2");
        allGood.add("1,073792");
        allGood.add("Ba2");
        allGood.add("BB");
        allGood.add("BB+");
        allGood.add("US");
        allGood.add("2000");
        allGood.add("1,111567");
        allGood.add("CALLABLE");
        allGood.add("FIXED");
        allGood.add("COMPANY GUARNT");
        allGood.add("Pipelines");

        PriceRow priceRow = bondRowMapper.createPriceRow(allGood, LocalDateTime.now());

        assertEquals("KUKU", priceRow.getItemCode());
        assertEquals("99,284", priceRow.getLastTransactionPrice());

    }

    @Test
    public void parseNotAllColumn() {
//        BondRowMapper bondRowMapper = new BondRowMapper();

        List<Object> allGood = new ArrayList<>();

        allGood.add("KUKU");
        allGood.add("DCP 2.7 04/01/19");
        allGood.add("DCP MIDSTREAM OPERATING");
        allGood.add("17.02.2018");
        allGood.add(" #N/A N/A");
        allGood.add("99,146");
        allGood.add("99,424");
        allGood.add("3,359173");
        allGood.add("3,488405");
        allGood.add("3,230205");
        allGood.add("2,7");
        allGood.add("01.04.2019");
        allGood.add("10,5");
        allGood.add("USD");
        allGood.add("2");
        allGood.add("1,073792");
        allGood.add("Ba2");
        allGood.add("BB");
        allGood.add("BB+");
        allGood.add("US");
        allGood.add("2000");
        allGood.add("1,111567");
        allGood.add("CALLABLE");
        allGood.add("FIXED");
        allGood.add("COMPANY GUARNT");
        allGood.add("Pipelines");

        PriceRow priceRow = bondRowMapper.createPriceRow(allGood, LocalDateTime.now());

        assertEquals("KUKU", priceRow.getItemCode());
        assertNull(priceRow.getLastTransactionPrice());

    }
}
