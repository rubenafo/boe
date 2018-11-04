package com.example.demo.common.summary;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {

        @Override
        public String convert(LocalDate date) {
            return date.toString();
        }

        @Override
        public LocalDate unconvert(String object) {
            return LocalDate.parse(object);
        }
    }

    public static String toJson(BoeEntry boe) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            return objectMapper.writeValueAsString(boe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing boe entry: " + e.getMessage());
        }
    }
}
