package com.example.demo.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class BoeEntry {

    private String date;
    private Meta meta;
    private List<Diario> diarios;

    public BoeEntry(InputStream inStream) {
        this.diarios = new ArrayList<>();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(inStream);
            Element rootElement = document.getDocumentElement();
            List<Node> childrenList = Utils.clean(rootElement.getChildNodes());
            childrenList.forEach(child -> {
                switch (child.getNodeName()) {
                    case "meta":
                        this.meta = new Meta(child);
                        break;
                    case "diario":
                        diarios.add(new Diario(child));
                }
            });
            if (meta != null)
                this.date = "89";
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException("Error parsing input stream ", e);
        }
    }

    @DynamoDBHashKey(attributeName="date")
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @DynamoDBAttribute(attributeName="meta")
    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta;}
//
//    //@DynamoDBAttribute(attributeName="diarios")
//    public List<Diario> getDiarios() { return diarios; }
//    public void setDiarios(List<Diario> diarios) { this.diarios = diarios; }
}
