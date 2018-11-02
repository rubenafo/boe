package com.example.demo.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class URLItem {

    public enum Type {PDF, HTML, XML}

    private Type type;
    private int pdfPages;
    private int pdfBytes;
    private String relativeUrl;

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

    @DynamoDBAttribute(attributeName="pdfPages")
    public int getPdfPages() { return pdfPages; }
    public void setPdfPages(int pdfPages) { this.pdfPages = pdfPages; }

    @DynamoDBAttribute(attributeName="pdfBytes")
    public int getPdfBytes() { return pdfBytes; }
    public void setPdfBytes(int pdfBytes) { this.pdfBytes = pdfBytes; }

    @DynamoDBAttribute(attributeName="relativeUrl")
    public String getRelativeUrl() { return relativeUrl; }
    public void setRelativeUrl(String relativeUrl) { this.relativeUrl = relativeUrl; }

    public static URLItem fromNode(Node xmlNode) {
        Element el = (Element) xmlNode;
        String name = el.getNodeName();
        String path = el.getFirstChild().getNodeValue();
        switch (name) {
            case "urlPdf":
                int sizeBytes = Integer.parseInt(el.getAttribute("szBytes"));
                int numPag = Integer.parseInt( el.hasAttribute("numPag")?
                        el.getAttribute("numPag") : "0");
                return new URLItem(Type.PDF, path, numPag, sizeBytes);
            case "urlHtm":
                return new URLItem(Type.HTML, path, 0, 0);
            case "urlXml":
                return new URLItem(Type.XML, path, 0, 0);
            default:
                throw new RuntimeException("Invalid URLItem node name: " + name);
        }
    }
}
