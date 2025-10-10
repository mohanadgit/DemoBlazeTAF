package com.demoblaze.tests.UI;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.drivers.UITest;
import com.demoblaze.pages.CheckoutPage;
import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.LoginPage;
import com.demoblaze.tests.BaseTest;
import com.demoblaze.utils.TimeManager;
import com.demoblaze.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Demoblaze")
@Feature("End-to-End Test")
@Story("Complete end-to-end user journey from account creation to logout")
@Severity(SeverityLevel.CRITICAL)
@Owner("Mohanad")
@UITest
public class E2ETest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimestamp();

    @Test
    @Description("Create a new user account")
    public void cerateNewUser() {
        new HomePage(driver).clickOnSignUpButton()
                .enterSignUpUsername(testData.getJsonData("credentials.username") + timestamp)
                .enterSignUpPassword(testData.getJsonData("credentials.password"))
                .clickSignUpButton()
                .validateSuccessfulSignUpAlertMessage(driver.alert().getAlertText(),testData.getJsonData("messages.successful"))
                .acceptAlert();
    }

    @Test(dependsOnMethods = {"cerateNewUser"})
    @Description("Login with the newly created user account")
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
    @Description("Verify cart total sum, delete a product, and proceed to checkout")
    public void verifyCartTotalSumAndDeleteProduct() {
        new HomePage(driver).clickOnCartButton()
                .verifyTotalSumIsCorrect("2280")
                .clickOnDeleteLink(testData.getJsonData("product2.name"))
                .verifyTotalSumIsCorrect("1460")
                .clickOnPlaceOrderButton();
    }
    @Test(dependsOnMethods = {"verifyCartTotalSumAndDeleteProduct", "addProductsToCart", "loginWithRegisteredUser", "cerateNewUser"})
    @Description("Fill in checkout form and complete the purchase")
    public void fillInCheckoutFormAndPurchase() {
        new CheckoutPage(driver)
                .enterCheckoutDetails(testData.getJsonData("checkout.name"),
                        testData.getJsonData("checkout.country"),
                        testData.getJsonData("checkout.city"),
                        testData.getJsonData("checkout.creditCard"),
                        testData.getJsonData("checkout.month"),
                        testData.getJsonData("checkout.year"))
                .clickPurchaseButton()
                .isSuccessMessageDisplayed()
                .clickOkButton()
                .verifyUserIsLoggedIn(testData.getJsonData("credentials.username") + timestamp);
    }
    @Test(dependsOnMethods = {"fillInCheckoutFormAndPurchase", "verifyCartTotalSumAndDeleteProduct", "addProductsToCart", "loginWithRegisteredUser", "cerateNewUser"})
    @Description("Logout the user")
    public void logoutUser() {
        new HomePage(driver)
                .navigate()
                .verifyUserIsLoggedIn(testData.getJsonData("credentials.username")+timestamp)
                .clickOnLogOutButton()
                .verifyUserIsLoggedOut();
    }

    //Configurations
    @BeforeClass
    protected void setUp() {
        testData = new JsonReader("Checkout-data");
        driver = new GUIDriver(); //blank 0
        new HomePage(driver).navigate();
    }

    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
