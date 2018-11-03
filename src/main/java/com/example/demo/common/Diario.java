package com.example.demo.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

@DynamoDBDocument
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

    @DynamoDBAttribute
    public String getNbo() { return nbo; }
    public void setNbo(String nbo) { this.nbo = nbo; }

    @DynamoDBAttribute
    public SumarioNBO getSumarioNbo() { return sumarioNbo; }
    public void setSumarioNbo(SumarioNBO sumarioNbo) { this.sumarioNbo = sumarioNbo; }

    @DynamoDBAttribute
    public List<Seccion> getSeccionList() { return seccionList; }
    public void setSeccionList(List<Seccion> seccionList) { this.seccionList = seccionList; }
}
