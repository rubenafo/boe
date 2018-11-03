package com.example.demo.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;

public class BoeDocument {

    private String srcBoeEntry;
    private String boeDocId;
    private String text;

    @DynamoDBAttribute
    public String getSrcBoeEntry() { return srcBoeEntry; }
    public void setSrcBoeEntry(String srcBoeEntry) {
        this.srcBoeEntry = srcBoeEntry;
    }

    @DynamoDBIndexHashKey
    public String getBoeDocId() { return boeDocId; }
    public void setBoeDocId(String boeDocId) { this.boeDocId = boeDocId; }

    @DynamoDBAttribute
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
