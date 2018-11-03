package com.example.demo.db;

import com.example.demo.common.BoeDocument;
import com.example.demo.common.BoeEntry;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Logger;

public class BoeFetcher {

    private static final Logger logger = Logger.getLogger(BoeFetcher.class.getName());
    private static final DateTimeFormatter DAILY_BOE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static final String BOE_ROOT = "https://www.boe.es";
    private static final String BOE_DAILY_URL = BOE_ROOT + "/diario_boe/xml.php?id=BOE-S-##";


    //  BoeEntry boe = BoeFetcher.fromDate("20181001");
    // BoeEntry boe = BoeFetcher.fromFile("/home/ruben/ws/boe/demo/src/main/java/com/example/demo/common/test.xml");

    public static BoeEntry fromFile(String path) {
        try {
            return new BoeEntry(new FileInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException("Error fetching " + path);
        }
    }

    public static BoeEntry fromDate(String yymmdd) {
        LocalDate.parse(yymmdd, DAILY_BOE_DATE);
        String urlStr = BOE_DAILY_URL.replace("##", yymmdd);
        try {
            logger.info("fetching " + urlStr);
            InputStream inputStream = new URL(urlStr).openStream();
            return new BoeEntry(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Invalid URL:" + urlStr);
        }
    }
}
