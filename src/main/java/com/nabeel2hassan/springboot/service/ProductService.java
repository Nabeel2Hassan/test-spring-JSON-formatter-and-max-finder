package com.nabeel2hassan.springboot.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {

    private final ObjectMapper objectMapper;
    private int maxPrice = Integer.MIN_VALUE;
    private String productWithMaxPricePath = "";

    private ProductService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String printProducts() {
        try {
            JsonNode products = getProducts();
            // return products.toPrettyString();
            StringBuilder result = new StringBuilder();
            printNestedJson(products, 0, result);
            System.out.println(result);
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading products file.";
        }
    }
    public String printProductWithMaxPrice() {
        try {
            JsonNode products = getProducts();
            maxPricedProductPath(products, new StringBuilder());
            System.out.println(productWithMaxPricePath + ": " + maxPrice);
            return productWithMaxPricePath + ": " + maxPrice;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading products file.";
        }
    }
    
    private JsonNode getProducts() throws IOException {        
        InputStream inputStream = getClass().getResourceAsStream("/products.json");
        return objectMapper.readTree(inputStream);
    }

    private void printNestedJson(JsonNode node, int indent, StringBuilder result) {
        if(node.isObject()) {
            node.fieldNames().forEachRemaining(fieldName -> {
                String indentation = indent > 0 ? ".".repeat(indent) + " " : "";
                result.append(indentation).append(fieldName).append("\n");
                printNestedJson(node.get(fieldName), indent + 2, result);
            });
        }
    }

    public void maxPricedProductPath(JsonNode node, StringBuilder currentPath) {
        if(node.isObject()) {
            node.fieldNames().forEachRemaining(fieldName -> {
                StringBuilder newPath  = new StringBuilder(currentPath);
                if(newPath.length() > 0) {
                    newPath.append(" -> ");
                }
                newPath.append(fieldName);
                maxPricedProductPath(node.get(fieldName), newPath); 
            });
        } else if(node.isNumber()) {
            int value = node.asInt();
            if(value > maxPrice) {
                maxPrice = value;
                productWithMaxPricePath = currentPath.toString();
            }
        }
    }
}
