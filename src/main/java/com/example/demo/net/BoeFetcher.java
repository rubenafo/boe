package com.example.demo.net;

import com.example.demo.common.ExecMeter;
import com.example.demo.common.docs.BoeDocument;
import com.example.demo.common.summary.BoeEntry;
import com.example.demo.common.summary.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;;
import java.util.stream.Collectors;

public class BoeFetcher {

    private static final Logger logger = LoggerFactory.getLogger(BoeFetcher.class);
    private static final DateTimeFormatter DAILY_BOE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static final String BOE_ROOT = "https://www.boe.es";
    private static final String BOE_DAILY_URL = BOE_ROOT + "/diario_boe/xml.php?id=BOE-S-##";

    private static int BOE_DELAY_MSECS = 50;

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
            ExecMeter meter = ExecMeter.start();
            logger.info("Fetching {}", urlStr);
            InputStream inputStream = new URL(urlStr).openStream();
            BoeEntry boe = new BoeEntry(inputStream);
            logger.info("BOE={} fetched ({} secs)", yymmdd, meter.stop());
            return boe;
        } catch (IOException e) {
            throw new RuntimeException("Invalid URL:" + urlStr);
        }
    }

    public static List<BoeDocument> getDocuments(BoeEntry boe) {
        List<BoeDocument> boeDocs = new ArrayList<>();
        List<Item> items = boe.getItems();
        logger.info("Fetching {} docs", items.size());
        ExecMeter meter = ExecMeter.start();
        items.forEach(i -> {
            String url = i.getXML().getAbsoluteUrl();
            try (InputStream inputStream = new URL(url).openStream()) {
                BoeDocument doc = new BoeDocument(boe, i.getId(), inputStream);
                boeDocs.add(doc);
                Thread.sleep(BOE_DELAY_MSECS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("Fetched boeDocs={} ({} secs)", boeDocs.size(), meter.stop());
        return boeDocs;
    }
}
