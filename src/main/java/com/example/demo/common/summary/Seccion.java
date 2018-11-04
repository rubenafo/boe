package com.example.demo.common.summary;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

@DynamoDBDocument
public class Seccion {

    private String numero;
    private String nombre;
    private List<Departamento> dptos;

    public Seccion(Node xmlNode) {
        Element el = (Element) xmlNode;
        this.numero = el.getAttribute("numero");
        this.nombre = el.getAttribute("nombre");
        List<Node> childNodes = Utils.clean(el.getChildNodes());
        this.dptos = new ArrayList<>(childNodes.size());
        childNodes.forEach(child -> {
           if (child.getNodeName().equals("departamento"))
               dptos.add(new Departamento(child));
           else
               throw new RuntimeException("Invalid childName: " + child.getNodeName());
        });
    }

    @DynamoDBAttribute
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    @DynamoDBAttribute
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @DynamoDBAttribute
    public List<Departamento> getDptos() { return dptos; }
    public void setDptos(List<Departamento> dptos) { this.dptos = dptos; }
}
