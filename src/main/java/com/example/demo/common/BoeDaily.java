package com.example.demo.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoeDaily {

    private Meta meta;
    private List<Diario> diarios;

    public BoeDaily(String configFile) throws ParserConfigurationException, IOException, SAXException {
        this(new FileInputStream(configFile));
    }

    public BoeDaily(InputStream inStream) throws ParserConfigurationException, IOException, SAXException {
        this.diarios = new ArrayList<>();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(inStream);
            Element rootElement = document.getDocumentElement();
            List<Node> childrenList = Utils.clean(rootElement.getChildNodes());
            childrenList.forEach(child -> {
                switch (child.getNodeName()) {
                    case "meta":
                        this.meta = new Meta(child);
                        break;
                    case "diario":
                        new Diario(child);
                }
            });
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
