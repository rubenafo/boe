package com.example.demo.common;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class URLItem {

    public enum Type {PDF, HTML, XML}

    private Type type;
    private int pdfPages;
    private int pdfBytes;
    private String relativeUrl;

    public static URLItem fromNode(Node xmlNode) {
        Element el = (Element) xmlNode;
        String name = el.getNodeName();
        switch (name) {
            case "urlPdf":
                int sizeBytes = Integer.parseInt(el.getAttribute("szBytes"));
                int numPag = Integer.parseInt( el.hasAttribute("numPag")?
                        el.getAttribute("num") : "0");
                return new URLItem(Type.PDF, el.getNodeValue(), numPag, sizeBytes);
            case "urlHtm":
                return new URLItem(Type.HTML, el.getNodeValue(), 0, 0);
            case "urlXml":
                return new URLItem(Type.XML, el.getNodeValue(), 0, 0);
            default:
                throw new RuntimeException("Invalid URLItem node name: " + name);
        }
    }

    public URLItem(Type type, String relUrl, int pages, int byteSize) {
        this.type = type;
        this.relativeUrl = relUrl;
        this.pdfBytes = byteSize;
        this.pdfPages = pages;
    }

    public static boolean isUrlItem(String nodeName) {
        return nodeName.equals("urlPdf") || nodeName.equals("urlXml") || nodeName.equals("urlHtm");
    }

    public Type getType () {
        return this.type;
    }

}