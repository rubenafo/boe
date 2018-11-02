package com.example.demo.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumarioNBO {

    private String id;
    private Map<URLItem.Type, URLItem> items;

    public SumarioNBO(Node xmlNode) {
        this.items = new HashMap<>();
        Element el = (Element) xmlNode;
        id = el.getAttribute("id");
        List<Node> childNodes = Utils.clean(el.getChildNodes());
        childNodes.forEach(child -> {
            if (URLItem.isUrlItem(child.getNodeName())) {
                URLItem item = URLItem.fromNode(child);
                items.put(item.getType(), item);
            }
        });
    }

    @DynamoDBAttribute(attributeName="id")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @DynamoDBAttribute(attributeName="items")
    public Map<URLItem.Type, URLItem> getItems() { return items; }
    public void setItems(Map<URLItem.Type, URLItem> items) { this.items = items; }
}
