package com.example.demo.db;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.example.demo.common.BoeEntry;

import java.util.Arrays;
import java.util.Collections;

public class DbManager {

    public AmazonDynamoDB dbClient;

    public DbManager() {
        dbClient = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.EU_WEST_1)
                .build();
    }



    public static void main (String a[]) {
        DbManager mgr = new DbManager();
        BoeEntry boe = BoeFetcher.fromDate("20181001");
        //Foo foo = new Foo();
        DynamoDBMapper dynamo = new DynamoDBMapper(mgr.dbClient);
        DynamoDBMapperConfig config = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new TableNameOverride("boecontent")).build();
        dynamo.batchWrite(Arrays.asList(boe), Collections.emptyList(), config);
    }

    public static class Foo {

        private String fooName = "foo";
        private String date = "45";

        @DynamoDBAttribute(attributeName="date")
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        @DynamoDBAttribute(attributeName="foo")
        public String getFooName() {
            return fooName;
        }

        public void setFooName(String fooName) {
            this.fooName = fooName;
        }
    }
}
