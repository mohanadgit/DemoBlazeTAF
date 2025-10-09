package com.demoblaze.tests.UI;

import com.demoblaze.apis.UserManagementAPI;
import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.LoginPage;
import com.demoblaze.pages.SignUpPage;
import com.demoblaze.tests.BaseTest;
import com.demoblaze.utils.TimeManager;
import com.demoblaze.utils.dataReader.JsonReader;
import io.qameta.allure.Description;
import org.testng.annotations.*;

public class LogoutTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();
    //Tests
    @Test
    @Description("Register new user ")
    public void cereateNewUser() {
        new SignUpPage(driver).navigate()
                .enterSignUpUsername(testData.getJsonData("validLogin.username")+timestamp)
                .enterSignUpPassword(testData.getJsonData("validLogin.tokenPass"))
                .clickSignUpButton()
                .validateSuccessfulSignUpAlertMessage(driver.alert().getAlertText(),testData.getJsonData("messages.successful"))
                .acceptAlert();

    }
    @Test(dependsOnMethods = "cereateNewUser")
    @Description("Login using the newly created user ")
    public void LoginUsingTheNewlyCreatedUser() {
        new LoginPage(driver)
                .navigate()
                .enterLoginUsername(testData.getJsonData("validLogin.username")+timestamp)
                .enterLoginPassword(testData.getJsonData("validLogin.tokenPass"))
                .clickLoginButton()
                .homePage
                .verifyUserIsLoggedIn(testData.getJsonData("validLogin.username")+timestamp);
    }
    @Test(dependsOnMethods = {"LoginUsingTheNewlyCreatedUser", "cereateNewUser"})
    @Description("Verify user can logout successfully")
    public void validLogoutTC()
    {
        new HomePage(driver)
                .navigate()
                .verifyUserIsLoggedIn(testData.getJsonData("validLogin.username")+timestamp)
                .clickOnLogOutButton()
                .verifyUserIsLoggedOut();
    }


    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("API-login-data");
        driver = new GUIDriver();
        new HomePage(driver).navigate();
    }
    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
