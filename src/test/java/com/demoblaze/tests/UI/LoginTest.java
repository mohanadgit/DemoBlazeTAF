package com.demoblaze.tests.UI;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.drivers.UITest;
import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.LoginPage;
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
@Feature("Login Functionality")
@Story("User Login with Valid and Invalid Credentials")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohanad")
@UITest
public class LoginTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();
    //Tests

    @Test
    @Description("Verify user can login with valid credentials")
    public void validLoginTC()
    {
        new LoginPage(driver).navigate()
                .enterLoginUsername(testData.getJsonData("validLogin.username"))
                .enterLoginPassword(testData.getJsonData("validLogin.password"))
                .clickLoginButton()
                .homePage
                .verifyUserIsLoggedIn(testData.getJsonData("validLogin.username"));
    }
    @Test
    @Description("Verify user cannot login with invalid username")
    public void inValidLoginUsingInvalidUsernameTC()
    {
        new LoginPage(driver).navigate()
                .enterLoginUsername(testData.getJsonData("invalidLogin.username"))
                .enterLoginPassword(testData.getJsonData("validLogin.password"))
                .clickLoginButton()
                .validateWrongUsernameAlertMessage(driver.alert().getAlertText(),testData.getJsonData("messages.wrongUsername"));
    }
    @Test
    @Description("Verify user cannot login with invalid password")
    public void inValidLoginUsingInvalidPasswordTC()
    {
        new LoginPage(driver).navigate()
                .enterLoginUsername(testData.getJsonData("validLogin.username"))
                .enterLoginPassword(testData.getJsonData("invalidLogin.password"))
                .clickLoginButton()
                .validateWrongPasswordAlertMessage(driver.alert().getAlertText(),testData.getJsonData("messages.wrongPassword"));
    }



    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("login-data");
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
