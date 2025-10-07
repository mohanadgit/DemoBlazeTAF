package com.demoblaze.apis;

import com.demoblaze.utils.actions.AlertActions;
import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagementAPI {
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;

    public UserManagementAPI() {
        // Constructor
        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    //endpoints
    private static final String signUp_endpoint = "/signup";
    private static final String login_endpoint = "/login";

    //api methods
    @Step("Create a new user account with full details")
    public UserManagementAPI createRegisterUserAccount(String username, String password)
    {
        Map <String, String> formParams = new HashMap<>();
        formParams.put("username", username);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .post(signUp_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }
    @Step("Login with user account")
    public UserManagementAPI loginUserAccount(String username, String password)
    {
        Map <String, String> formParams = new HashMap<>();
        formParams.put("username", username);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .post(login_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    //validations
    @Step("Verify user created successfully")
    public UserManagementAPI verifyUserCreatedSuccessfully() {
        System.out.println("Response body: " + response.asString());
        System.out.println("Content-Type: " + response.getContentType());
        verification.Equals(response.jsonPath().get("message"), null, "User is not created successfully");
        return this;
    }

}
