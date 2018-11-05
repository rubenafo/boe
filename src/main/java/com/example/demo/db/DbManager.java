package com.example.demo.db;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.example.demo.common.ExecMeter;
import com.example.demo.common.docs.BoeDocument;
import com.example.demo.common.summary.BoeEntry;
import com.example.demo.common.summary.Item;
import com.example.demo.net.BoeFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class DbManager {

    private Logger logger = LoggerFactory.getLogger(DbManager.class);

    private AmazonDynamoDB dbClient;
    private DynamoDBMapper dbMapper;
    private DynamoDBMapperConfig boeContentConfig;
    private DynamoDBMapperConfig boeDocsConfig;

    public DbManager() {
        dbClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.EU_WEST_1)
                .build();
        dbMapper = new DynamoDBMapper(dbClient);
        boeContentConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new TableNameOverride("boecontent")).build();
        boeDocsConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new TableNameOverride("boeDocs")).build();
    }

    public void fetchBoe(String date) {
        BoeEntry boe = BoeFetcher.fromDate(date);
        List<BoeDocument> boeDocs = BoeFetcher.getDocuments(boe);
        ExecMeter meter = ExecMeter.start();
        logger.info ("Storing BOE={}, docs={}", boe.getDate(), boeDocs.size());
        dbMapper.batchWrite(Arrays.asList(boe), Collections.emptyList(), boeContentConfig);
        List<DynamoDBMapper.FailedBatch> failed = dbMapper.batchWrite(boeDocs, Collections.emptyList(), boeDocsConfig);
        if (failed.isEmpty())
            logger.info("BOE successfully persisted time={} secs", meter.stop());
        else
            logger.info("BOE partially persisted time={} secs, failed={}", meter.stop(), failed.size());
    }

    public void fetchBoeByMonth (int month, int year) {
        LocalDate startingDate = LocalDate.of(year, month, 1);
        int monthLength = startingDate.getMonth().length(startingDate.isLeapYear());
        IntStream.range(1, monthLength+1).forEach(d -> {
            String date = String.valueOf(year) + String.format("%02d",month) + String.format("%02d", d);
            this.fetchBoe(date);
        });
    }

    public static void main(String a[]) {
        DbManager db = new DbManager();
        db.fetchBoeByMonth(10, 2018);
    }
}
