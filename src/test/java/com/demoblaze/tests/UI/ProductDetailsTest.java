package com.demoblaze.tests.UI;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.drivers.UITest;
import com.demoblaze.pages.HomePage;
import com.demoblaze.tests.BaseTest;
import com.demoblaze.utils.dataReader.JsonReader;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UITest
public class ProductDetailsTest extends BaseTest {

    private final String productKey = "product2"; // Key to identify the product in the JSON file
    //Tests
    @Test
    @Description("Verify product details without login")
    public void verifyProductDetailsWithoutLogin() {
        String productName = testData.getJsonData(productKey+".name");
        String expectedDescription = testData.getJsonData(productKey+".description");
        String expectedPrice = testData.getJsonData(productKey+".price")+" *includes tax";
        new HomePage(driver)
                .clickOnProduct(productName)
                .verifyProductDetails(productName, expectedPrice, expectedDescription);
    }

    @Test
    @Description("Add product to cart without login")
    public void addProductToCartWithoutLogin() {
        String productName = testData.getJsonData(productKey+".name");
        new HomePage(driver)
                .clickOnProduct(productName)
                .clickOnAddToCartButton()
                .validateAddToCartAlertMessage(driver.alert().getAlertText(), testData.getJsonData("messages.productAdded"));
    }


    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("products-data");
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
