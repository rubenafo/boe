package com.example.demo.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
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
                        new MetaSection(node);
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
