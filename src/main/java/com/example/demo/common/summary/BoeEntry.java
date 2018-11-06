package com.example.demo.common.summary;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.example.demo.common.summary.Diario;
import com.example.demo.common.summary.Item;
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
import java.util.ArrayList;
import java.util.List;

@DynamoDBDocument
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
                this.date = meta.getFecha().toString();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new RuntimeException("Error parsing input stream ", e);
        }
    }

    @DynamoDBHashKey
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @DynamoDBAttribute
    public Meta getMeta() { return meta; }
    public void setMeta(Meta meta) { this.meta = meta;}

    @DynamoDBAttribute
    public List<Diario> getDiarios() { return diarios; }
    public void setDiarios(List<Diario> diarios) { this.diarios = diarios; }

    @DynamoDBIgnore
    public List<Item> getItems () {
        List<Item> items = new ArrayList<>();
        this.diarios.forEach(d -> {
            d.getSeccionList().forEach(s -> {
                s.getDptos().forEach(dp -> {
                    if (dp.getEpigrafe() != null)
                        items.addAll(dp.getEpigrafe().getItems());
                   items.addAll(dp.getItems());
                });
            });
        });
        return items;
    }
}
