package com.example.demo.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
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

    @DynamoDBAttribute(attributeName="nombre")
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @DynamoDBAttribute(attributeName="epigrafe")
    public Epigrafe getEpigrafe() { return epigrafe; }
    public void setEpigrafe(Epigrafe epigrafe) { this.epigrafe = epigrafe; }

    @DynamoDBAttribute(attributeName="item")
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
}
