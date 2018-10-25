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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BoeDaily {


    public void readConfig(String configFile) throws ParserConfigurationException {
        try {
            DocumentBuilderFactory builderFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(
                    new FileInputStream(configFile));
            Element rootElement = document.getDocumentElement();
            NodeList nodes = rootElement.getChildNodes();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                switch (node.getNodeName()) {
                    case "meta":
                        new Meta(node);
                        break;
                    case "diario":
                        new Diario (node);
                }
                if (node instanceof Element) {
                    Element child = (Element) node;
                    NodeList nodes2 = child.getChildNodes();
                    //for (int j = 0; j < nodes2.getLength(); j++)
                    //System.out.println(nodes2.item(j));
                }
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public abstract class XmlElem {

        private Map<String, Object> elems = new HashMap<>();

        //public abstract void fromXml(String xmlString);
    }
}
