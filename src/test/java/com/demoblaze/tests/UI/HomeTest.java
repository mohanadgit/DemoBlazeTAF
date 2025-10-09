package com.demoblaze.tests.UI;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.drivers.UITest;
import com.demoblaze.pages.ContactPage;
import com.demoblaze.pages.HomePage;
import com.demoblaze.tests.BaseTest;
import com.demoblaze.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Demoblaze")
@Feature("Home Page")
@Story("Home Page Test")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohanad")
@UITest
public class HomeTest extends BaseTest {


    //Tests

    @Test
    @Description("Navigate Mobile Categorie")
    public void navigateToMobileCategory() {
        new HomePage(driver)
                .navigate()
                .clickOnPhoneCategory();
    }
    @Test
    @Description("Navigate Laptop Categorie")
    public void navigateToLaptopCategory() {
        new HomePage(driver)
                .navigate()
                .clickOnLaptopCategory();
    }
    @Test
    @Description("Navigate to Monitor Categorie")
    public void navigateToMonitorCategory() {
        new HomePage(driver)
                .navigate()
                .clickOnMonitorCategory();
    }
    @Test
    @Description("Navigate Contact Page and Send Message")
    public void navigateToContactPage() {
        new ContactPage(driver)
                .navigate()
                .enterAllContactFields(testData.getJsonData("email"),testData.getJsonData("name"),testData.getJsonData("message"))
                .clickSendMessageButton()
                .validateSuccessfulMessageAlert(driver.alert().getAlertText(),testData.getJsonData("successfulMessageAlert"));
    }


    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("Contact-data");
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
