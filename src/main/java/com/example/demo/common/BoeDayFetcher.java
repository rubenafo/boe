package com.example.demo.common;

import javax.xml.parsers.ParserConfigurationException;

public class BoeDayFetcher {

    public static void main(String args[]) throws ParserConfigurationException {
        BoeDaily b = new BoeDaily();
        b.readConfig("/home/ruben/ws/boe/demo/src/main/java/com/example/demo/common/test.xml");
    }
}
