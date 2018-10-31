package com.example.demo.common;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BoeFetcher {

    private static final DateTimeFormatter DAILY_BOE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final String BOE_DAILY_URL = "https://www.boe.es/diario_boe/xml.php?id=BOE-S-##";

    public static void main(String args[]) throws ParserConfigurationException {
        BoeDaily boe = BoeFetcher.fromDate("20181001");
    }

    // https://www.boe.es/diario_boe/xml.php?id=BOE-S-20181009
    public static BoeDaily fromDate (String yymmdd) {
        LocalDate date = LocalDate.parse(yymmdd, DAILY_BOE_DATE);
        String urlStr = BOE_DAILY_URL.replace("##", yymmdd);
        try {
            InputStream inputStream = new URL(urlStr).openStream();
            return new BoeDaily(inputStream);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new RuntimeException("Invalid URL:" + urlStr);
        }
    }
}
