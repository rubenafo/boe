package com.example.demo.common.summary;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static com.example.demo.net.BoeFetcher.BOE_ROOT;

@DynamoDBDocument
public class URLItem {

    private String type;
    private int pdfPages;

    private int pdfBytes;
    private String absoluteUrl;

    private URLItem(String type, String relUrl, int pages, int byteSize) {
        this.type = type;
        this.absoluteUrl = relUrl;
        this.pdfBytes = byteSize;
        this.pdfPages = pages;
    }

    public static boolean isUrlItem(String nodeName) {
        return nodeName.equals("urlPdf") || nodeName.equals("urlXml") || nodeName.equals("urlHtm");
    }

    @DynamoDBAttribute
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    @DynamoDBAttribute
    public int getPdfPages() {
        return pdfPages;
    }

    public void setPdfPages(int pdfPages) {
        this.pdfPages = pdfPages;
    }

    @DynamoDBAttribute
    public int getPdfBytes() {
        return pdfBytes;
    }

    public void setPdfBytes(int pdfBytes) {
        this.pdfBytes = pdfBytes;
    }

    @DynamoDBAttribute
    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public static URLItem fromNode(Node xmlNode) {
        Element el = (Element) xmlNode;
        String name = el.getNodeName();
        String path = el.getFirstChild().getNodeValue();
        switch (name) {
            case "urlPdf":
                int sizeBytes = Integer.parseInt(el.getAttribute("szBytes"));
                int numPag = Integer.parseInt(el.hasAttribute("numPag") ?
                        el.getAttribute("numPag") : "0");
                return new URLItem("pdf", BOE_ROOT + path, numPag, sizeBytes);
            case "urlHtm":
                return new URLItem("html", BOE_ROOT + path, 0, 0);
            case "urlXml":
                return new URLItem("xml", BOE_ROOT + path, 0, 0);
            default:
                throw new RuntimeException("Invalid URLItem node name: " + name);
        }
    }
}
