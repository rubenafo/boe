package com.example.demo.parsing;

import com.example.demo.common.docs.BoeDocument;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class BoeDocumentTest {

    private static String ROOT_FOLDER = "/home/ruben/ws/boe/demo/src/test/java/com/example/demo/parsing/";

    @Test
    public void parseBoeDocument() throws FileNotFoundException {
        BoeDocument boeDoc = new BoeDocument(new FileInputStream(ROOT_FOLDER + "document.xml"));
        Assert.assertTrue(boeDoc.getText().length() == 191);
    }
}
