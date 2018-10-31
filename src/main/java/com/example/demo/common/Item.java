package com.example.demo.common;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {

    private String id;
    private String titulo;
    private Map<URLItem.Type, URLItem> urls;

    public Item(Node xmlNode) {
        Element el = (Element) xmlNode;
        this.id = el.getAttribute("id");
        this.urls = new HashMap<>();
        List<Node> childNodes = Utils.clean(el.getChildNodes());
        childNodes.forEach(child -> {
            if (child.getNodeName().equals("titulo")) {
                this.titulo = child.getNodeValue();
            } else if (URLItem.isUrlItem(child.getNodeName())) {
                URLItem urlItem = URLItem.fromNode(child);
                this.urls.put(urlItem.getType(), urlItem);
            } else
                throw new RuntimeException("Invalid childName: " + child.getNodeName());
        });
    }
}
