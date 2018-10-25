package com.example.demo.common;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Seccion {

    private String numero;
    private String nombre;
    private List<Departamento> departamentos;

    public Seccion(Node xmlNode) {
        Element el = (Element) xmlNode;
        this.numero = el.getAttribute("nombre");
        this.nombre = el.getAttribute("nombre");
        List<Node> childNodes = Utils.clean(el.getChildNodes());
        this.departamentos = new ArrayList<>(childNodes.size());
        childNodes.forEach(child -> {
           if (child.getNodeName().equals("departamento"))
               departamentos.add(new Departamento(child));
           else
               throw new RuntimeException("Invalid childName: " + child.getNodeName());
        });
    }
}
