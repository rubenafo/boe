package com.example.demo.common;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class Diario {

    private String nbo;
    private SumarioNBO sumarioNbo;
    private List<Seccion> seccionList;

    public Diario (Node xmlNode) {
        Element el = (Element) xmlNode;
        nbo = el.getAttribute("nbo");
        List<Node> childNodes = Utils.clean(el.getChildNodes());
        this.seccionList = new ArrayList<>(childNodes.size());
        childNodes.forEach(child -> {
            if (child.getNodeName().equals("sumario_nbo"))
                this.sumarioNbo = new SumarioNBO(child);
            else if (child.getNodeName().equals("seccion"))
                seccionList.add(new Seccion(child));
            else
                throw new RuntimeException("sumario_nbo expected, " + child.getNodeName() + " found");
        });
    }
}