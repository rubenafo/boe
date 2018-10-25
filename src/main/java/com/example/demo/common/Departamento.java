package com.example.demo.common;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

public class Departamento {

    private String nombre;
    private Epigrafe epigrafe;
    private Item item;

    public Departamento(Node xmlNode) {
        Element el = (Element) xmlNode;
        this.nombre = el.getAttribute("nombre");
        List<Node> childNodes = Utils.clean(el.getChildNodes());
        childNodes.forEach(child -> {
            if (child.getNodeName().equals("epigrafe"))
                this.epigrafe = new Epigrafe(child);
            else if (child.getNodeName().equals("item"))
                this.item = new Item(child);
            else
                throw new RuntimeException("Invalid childName: " + child.getNodeName());
        });
    }
}
