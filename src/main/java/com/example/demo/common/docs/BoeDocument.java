package com.example.demo.common.docs;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.example.demo.common.summary.BoeEntry;
import com.example.demo.common.summary.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@DynamoDBDocument
public class BoeDocument {

    private String srcBoeId;
    private String boeDocId;

    //private MetaDatos metadatos; TODO
    //private Analisis analisis;
    private String text;

    @DynamoDBAttribute
    public String getSrcBoeId() { return srcBoeId; }
    public void setSrcBoeId(String srcBoeId) {
        this.srcBoeId = srcBoeId;
    }

    @DynamoDBAttribute
    public String getBoeDocId() { return boeDocId; }
    public void setBoeDocId(String boeDocId) { this.boeDocId = boeDocId; }

    @DynamoDBAttribute
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public BoeDocument (BoeEntry srcBoeId, String boeDocId, InputStream inStream) {
        this.srcBoeId = srcBoeId.getDiarios().get(0).getSumarioNbo().getId();
        this.boeDocId = boeDocId;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(inStream);
            Element rootElement = document.getDocumentElement();
            List<Node> childrenList = Utils.clean(rootElement.getChildNodes());
            childrenList.forEach(child -> {
                switch (child.getNodeName()) {
                    case "texto":
                            Texto t = new Texto(child);
                            this.text = t.getText();
                        break;
                }
            });
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException("Error parsing input stream ", e);
        }
    }
}
