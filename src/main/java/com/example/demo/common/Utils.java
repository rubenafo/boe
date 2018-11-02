package com.example.demo.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Node> clean(NodeList nodeList) {
        List cleanItems = new ArrayList(20);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            if (child.hasChildNodes() || child.hasAttributes())
                cleanItems.add(child);
        }
        return cleanItems;
    }

    public class DimensionTypeConverter implements DynamoDBTypeConverter<String, LocalDate> {

        @Override
        public String convert(LocalDate object) {
            return null;
        }

        @Override
        public LocalDate unconvert(String object) {
            return null;
        }
    }
}
