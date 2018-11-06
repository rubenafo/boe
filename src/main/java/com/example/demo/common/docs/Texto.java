package com.example.demo.common.docs;

import com.example.demo.common.summary.Utils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

public class Texto {

    private String text;

    public Texto(Node child) {
        Element el = (Element) child;
        List<Node> childNodes = Utils.clean(el.getChildNodes());
        StringBuffer buffer = new StringBuffer();
        childNodes.forEach(c -> {
            buffer.append(c.getFirstChild().getNodeValue()).append("; ");
        });
        this.text = buffer.toString();
    }

    public String getText () {
        return this.text;
    }
}
