package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class BoeFetcher {

    private static final Logger logger = Logger.getLogger(BoeFetcher.class.getName());
    private static final DateTimeFormatter DAILY_BOE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String BOE_DAILY_URL = "https://www.boe.es/diario_boe/xml.php?id=BOE-S-##";

    //  BoeDaily boe = BoeFetcher.fromDate("20181001");
    // BoeDaily boe = BoeFetcher.fromFile("/home/ruben/ws/boe/demo/src/main/java/com/example/demo/common/test.xml");

    public static void main(String args[]) throws ParserConfigurationException, JsonProcessingException {

    }

    public static BoeDaily fromFile(String path) {
        try {
            return new BoeDaily(new FileInputStream(path));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException("Error fetching " + path);
        }
    }

    public static BoeDaily fromDate(String yymmdd) {
        LocalDate.parse(yymmdd, DAILY_BOE_DATE);
        String urlStr = BOE_DAILY_URL.replace("##", yymmdd);
        try {
            logger.info("fetching " + urlStr);
            InputStream inputStream = new URL(urlStr).openStream();
            return new BoeDaily(inputStream);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException("Invalid URL:" + urlStr);
        }
    }

    public static String toJson(BoeDaily boe) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            return objectMapper.writeValueAsString(boe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing boe entry: " + e.getMessage());
        }
    }
}
