package com.demoblaze.apis;

import com.demoblaze.utils.dataReader.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private  static String baseURI = PropertyReader.getProperty("baseurlapi");
    private Builder() {
        // Private constructor to prevent instantiation
    }
    //build request specification
    public static RequestSpecification getUserManagementRequestSpecification(Map<String, ?> formParams) {
        return  new RequestSpecBuilder().setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .setBody(formParams)
                .build();
    }
}