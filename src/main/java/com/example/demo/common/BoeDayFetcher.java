package com.example.demo.common;

import javax.sound.midi.MetaEventListener;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import static com.sun.java.accessibility.util.EventID.ITEM;

public class BoeDayFetcher {

    public void date(String date) {

    }



    public static void main (String args[]) throws ParserConfigurationException {
        BoeDaily b = new BoeDaily();
        b.readConfig("/home/ruben/ws/boe/demo/src/main/java/com/example/demo/common/test.xml");
    }
}
