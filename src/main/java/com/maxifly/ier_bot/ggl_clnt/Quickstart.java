package com.maxifly.ier_bot.ggl_clnt;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.maxifly.ier_bot.ggl_clnt.mapper.BondRowMapper;
import com.maxifly.ier_bot.ggl_clnt.model.PriceRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.log4j.Logger;
//import org.apache.log4j.spi.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class Quickstart {
    //    Logger logger = Logger.getLogger(Quickstart.class);
    Logger logger = LoggerFactory.getLogger(Quickstart.class);

    private String spreadsheetId;
    private String range;
    private BondRowMapper bondRowMapper;


    /**
     * Application name.
     */
    private static final String APPLICATION_NAME =
            "Google Sheets API Java Quickstart";

    /**
     * Directory to store user credentials for this application.
     */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart");

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     * <p>
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws Exception {
        // Load client secrets.
        InputStream in =
                Quickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     *
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws Exception {
        Credential credential = authorize();

        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void setSpreadsheetId(String spreadsheetId) {
        this.spreadsheetId = spreadsheetId;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public void setBondRowMapper(BondRowMapper bondRowMapper) {
        this.bondRowMapper = bondRowMapper;
    }

    /**
     * Получить все записи из книги
     *
     * @param result
     * @throws GetVal_Exception
     */
    public void getAllValues(Map<String, PriceRow> result) throws GetVal_Exception {
        findValue(result, null);
    }

    /**
     * Получить запись только об определенном товаре
     *
     * @param result
     * @param itemCode - регистронезависимый код товара
     * @throws GetVal_Exception
     */
    public void findValue(Map<String, PriceRow> result, String itemCode) throws GetVal_Exception {
        try {
            Sheets service = getSheetsService();

            ValueRange response = service.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.size() == 0) {
                logger.info("No data found.");
            } else {
                LocalDateTime localDateTime = LocalDateTime.now();
                int i = 0;
                for (List row : values) {
                    // Print columns A and E, which correspond to indices 0 and 4.
                    PriceRow priceRow = bondRowMapper.createPriceRow(row, localDateTime);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Get row: " + priceRow);
                    }
                    // Пока решаю, что коды регистронезависимые


                    if (itemCode != null && itemCode.equals(priceRow.getItemCode().toUpperCase())) {
                        result.put(priceRow.getItemCode().toUpperCase(), priceRow);
                        break;
                    } else if (itemCode == null) {
                        result.put(priceRow.getItemCode().toUpperCase(), priceRow);
                    }

                }
                logger.info("Get {} rows. Found {} rows.", values.size(), result.size());
            }
        } catch (Exception ioe) {
            logger.error("Exception when get values", ioe);
            throw new GetVal_Exception("Exception when get values", ioe);
        }
    }

}
