package com.example.demo.common.docs;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.example.demo.common.summary.Diario;
import com.example.demo.common.summary.Meta;
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

public class BoeDocument {

    private String srcBoeEntry;
    private String boeDocId;

    //private MetaDatos metadatos; TODO
    //private Analisis analisis;
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

    public BoeDocument (InputStream inStream) {
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
