package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class productDetailsPage {
    private final GUIDriver driver;
    HomePage homePage;
    public productDetailsPage(GUIDriver driver) {
        this.driver = driver;
        this.homePage = new HomePage(driver);
    }

    //locators
    private final By productTitle = By.cssSelector(".name");
    private final By productPrice = By.cssSelector(".price-container");
    private final By productDescription = By.cssSelector("div[id=\"more-information\"] p");
    private final By addToCartButton = By.xpath("//a[normalize-space()=\"Add to cart\"]");

    //actions
    @Step("CLICK ON ADD TO CART BUTTON")
    public productDetailsPage clickOnAddToCartButton(){
        driver.element().click(addToCartButton);
        return this;
    }

    //getters
    @Step("GET PRODUCT TITLE")
    public String getProductTitle(){
        return driver.element().getText(productTitle);
    }
    @Step("GET PRODUCT PRICE")
    public String getProductPrice(){
        return driver.element().getText(productPrice);
    }
    @Step("GET PRODUCT DESCRIPTION")
    public String getProductDescription(){
        return driver.element().getText(productDescription);
    }
    //validations
    @Step("VERIFY ADD TO CART ALERT MESSAGE")
    public productDetailsPage validateAddToCartAlertMessage(String acutalMessage, String expectedMessage) {
        driver.verification().Equals(acutalMessage, expectedMessage, "Add to cart alert message is not as expected");
        return this;
    }
    @Step("Verify Prodct details")
    public productDetailsPage verifyProductDetails(String expectedTitle, String expectedPrice, String expectedDescription) {
        driver.verification().Equals(getProductTitle(), expectedTitle, "Product title is not as expected");
        driver.verification().Equals(getProductPrice(), expectedPrice, "Product price is not as expected");
        driver.verification().Equals(getProductDescription(), expectedDescription, "Product description is not as expected");
        return this;
    }


}
