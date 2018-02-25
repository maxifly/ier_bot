package com.maxifly.ier_bot.rights;

import com.maxifly.ier_bot.tel_bot.SimpleBot;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Data
public class RightsCSVChecker {
    private Logger logger = LoggerFactory.getLogger(RightsCSVChecker.class);
    private final String csvFile;
    private final String cvsSplitBy = ",";

    public boolean isPhoneExists(String phone) {

        String line = "";
        Boolean result = false;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while (!result && (line = br.readLine()) != null) {

                // use comma as separator
                String[] row = line.split(cvsSplitBy);
                logger.debug(row[0]);
                if (phone.equals(row[0])) {
                    result = true;
                    logger.info("Phone {} found in file {}", phone, csvFile);
                }
            }

        } catch (IOException e) {
            logger.error("Error when find phone " + phone + " in file " + csvFile,
                    e);
        }

        if (!result) {
            logger.warn("Phone {} not found in file {}", phone, csvFile);
        }

        return result;
    }
}
