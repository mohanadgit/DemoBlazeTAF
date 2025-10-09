package com.demoblaze.tests.UI;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.drivers.UITest;
import com.demoblaze.pages.CartPage;
import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.LoginPage;
import com.demoblaze.pages.SignUpPage;
import com.demoblaze.tests.BaseTest;
import com.demoblaze.utils.TimeManager;
import com.demoblaze.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.*;

@Epic("Demoblaze")
@Feature("Cart Functionality")
@Story("Add Products to Cart and Verify Total")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohanad")
@UITest
public class CartTest  extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();

    @Test
    @Description("Create a new user for testing")
    public void cerateNewUser() {
        new HomePage(driver).clickOnSignUpButton()
                .enterSignUpUsername(testData.getJsonData("credentials.username") + timestamp)
                .enterSignUpPassword(testData.getJsonData("credentials.password"))
                .clickSignUpButton()
                .validateSuccessfulSignUpAlertMessage(driver.alert().getAlertText(),testData.getJsonData("messages.successful"))
                .acceptAlert();
    }

    @Test(dependsOnMethods = {"cerateNewUser"})
    @Description("Login with the registered user")
    public void loginWithRegisteredUser() {
        new LoginPage(driver).navigate()
                .enterLoginUsername(testData.getJsonData("credentials.username")+timestamp)
                .enterLoginPassword(testData.getJsonData("credentials.password"))
                .clickLoginButton()
                .homePage
                .verifyUserIsLoggedIn(testData.getJsonData("credentials.username")+timestamp);
    }

    @Test(dependsOnMethods = {"loginWithRegisteredUser", "cerateNewUser"})
    @Description("Add multiple products to the cart and verify their details")
    public void addProductsToCart() {
        new HomePage(driver).navigate()
                .clickOnProduct(testData.getJsonData("product1.name"))
                .verifyProductDetails(testData.getJsonData("product1.name"),
                        testData.getJsonData("product1.price")+" *includes tax",
                        testData.getJsonData("product1.description"))
                .clickOnAddToCartButton()
                .validateAddToCartAlertMessage(driver.alert().getAlertText(), testData.getJsonData("messages.productAdded"))
                .acceptAlert()
                .navigate()
                .clickOnProduct(testData.getJsonData("product2.name"))
                .verifyProductDetails(testData.getJsonData("product2.name"),
                        testData.getJsonData("product2.price")+" *includes tax",
                        testData.getJsonData("product2.description"))
                .clickOnAddToCartButton()
                .validateAddToCartAlertMessage(driver.alert().getAlertText(), testData.getJsonData("messages.productAdded"))
                .acceptAlert()
                .navigate()
                .clickOnProduct(testData.getJsonData("product3.name"))
                .verifyProductDetails(testData.getJsonData("product3.name"),
                        testData.getJsonData("product3.price")+" *includes tax",
                        testData.getJsonData("product3.description"))
                .clickOnAddToCartButton()
                .validateAddToCartAlertMessage(driver.alert().getAlertText(), testData.getJsonData("messages.productAdded"))
                .acceptAlert();
    }

    @Test(dependsOnMethods = {"addProductsToCart", "loginWithRegisteredUser", "cerateNewUser"})
    @Description("Verify cart total sum and delete a product from the cart")
    public void verifyCartTotalSumAndDeleteProduct() {
        new HomePage(driver).clickOnCartButton()
                .verifyTotalSumIsCorrect("2280")
                .clickOnDeleteLink(testData.getJsonData("product2.name"))
                .verifyTotalSumIsCorrect("1460")
                .clickOnPlaceOrderButton();
    }


    //Configurations
    @BeforeClass
    protected void setUp() {
        testData = new JsonReader("cart-data");
        driver = new GUIDriver(); //blank 0
        //driver.browser().closeExtensionTab();
        new HomePage(driver).navigate();
    }

    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
