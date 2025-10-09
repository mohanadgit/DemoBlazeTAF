package com.demoblaze.tests.UI;

import com.demoblaze.apis.UserManagementAPI;
import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.drivers.UITest;
import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.SignUpPage;
import com.demoblaze.tests.BaseTest;
import com.demoblaze.utils.TimeManager;
import com.demoblaze.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import jdk.jfr.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Demoblaze")
@Feature("Sign Up Functionality")
@Story("User Sign Up with Valid and Invalid Credentials")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohanad")
@UITest
public class SignUpTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();

    //Tests
    @Test
    @Description("Verify user can sign up with valid credentials")
    public void validSignUpTC()
    {
        new SignUpPage(driver).navigate()
                .enterSignUpUsername(testData.getJsonData("credentials.username") + TimeManager.getSimpleTimestamp())
                .enterSignUpPassword(testData.getJsonData("credentials.password"))
                .clickSignUpButton()
                .validateSuccessfulSignUpAlertMessage(driver.alert().getAlertText(),testData.getJsonData("messages.successful"));
    }
    @Test
    @Description("Verify user cannot sign up with existing username")
    public void inValidSignUpUsingExistingUsernameTC()
    {
        new UserManagementAPI()
                .createRegisterUserAccount(
                        testData.getJsonData("credentials.username") + timestamp,
                        testData.getJsonData("credentials.password"))
                .verifyUserCreatedSuccessfully();
        new SignUpPage(driver).navigate()
                .enterSignUpUsername(testData.getJsonData("credentials.username")+ timestamp)
                .enterSignUpPassword(testData.getJsonData("credentials.password"))
                .clickSignUpButton()
                .validateUserAlreadyExistAlertMessage(driver.alert().getAlertText(),testData.getJsonData("messages.userExist"));
    }






    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("signup-data");
    }
    @BeforeMethod
    public void setUp() {
        driver = new GUIDriver();
        new HomePage(driver).navigate();
        //driver.browser().closeExtensionTab();
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
