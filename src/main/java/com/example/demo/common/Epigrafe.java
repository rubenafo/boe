package com.example.demo.common;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Epigrafe {

    private String nombre;
    private List<Item> items;

    public Epigrafe(Node xmlNode) {
        Element el = (Element) xmlNode;
        this.nombre = el.getAttribute("nombre");
        List<Node> childNodes = Utils.clean(el.getChildNodes());
        this.items = new ArrayList<>(childNodes.size());
        childNodes.forEach(child -> {
            if (child.getNodeName().equals("item"))
                this.items.add(new Item(child));
            else
                throw new RuntimeException("Invalid childName: " + child.getNodeName());
        });
    }
}
