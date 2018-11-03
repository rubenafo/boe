package com.example.demo.common.children;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DynamoDBDocument
public class Item {

    private String id;
    private String titulo;
    private Map<String, URLItem> urls;

    public Item(Node xmlNode) {
        Element el = (Element) xmlNode;
        this.id = el.getAttribute("id");
        this.urls = new HashMap<>();
        List<Node> childNodes = Utils.clean(el.getChildNodes());
        childNodes.forEach(child -> {
            if (child.getNodeName().equals("titulo")) {
                this.titulo = child.getFirstChild().getNodeValue();
            } else if (URLItem.isUrlItem(child.getNodeName())) {
                URLItem urlItem = URLItem.fromNode(child);
                this.urls.put(urlItem.getType(), urlItem);
            } else
                throw new RuntimeException("Invalid childName: " + child.getNodeName());
        });
    }

    @DynamoDBAttribute
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @DynamoDBAttribute
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    @DynamoDBAttribute
    public Map<String, URLItem> getUrls() { return urls; }
    public void setUrls(Map<String, URLItem> urls) { this.urls = urls; }

}
