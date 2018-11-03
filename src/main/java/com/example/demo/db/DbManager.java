package com.example.demo.db;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.example.demo.common.BoeEntry;
import com.example.demo.common.children.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DbManager {

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
        dbMapper.batchWrite(Arrays.asList(boe), Collections.emptyList(), boeContentConfig);
        List<Item> docs = boe.getItems();
        System.out.println(docs.size());
    }

    public static void main(String a[]) {
        //dynamo.batchWrite(Arrays.asList(boe), Collections.emptyList(), config);
        DbManager db = new DbManager();
        db.fetchBoe("20181001");
    }
}
