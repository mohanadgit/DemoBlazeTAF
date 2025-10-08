package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    private final String cartEndpoint = "/cart.html";
    private final GUIDriver driver;
    public CartPage(GUIDriver driver) {
        this.driver = driver;
    }
    //locators
    private final By cartTitle = By.cssSelector(".col-lg-8 h2");
    private final By placeOrderButton = By.xpath("//button[normalize-space()=\"Place Order\"]");
    private final By totalPriceLabel = By.id("totalp");
    private final By cartTable = By.cssSelector("#tbodyid");
    //actions
    public CartPage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseurl") +cartEndpoint);
        driver.element().waitForPageToLoad();
        return this;
    }
    public CheckoutPage clickOnPlaceOrderButton(){
        driver.element().click(placeOrderButton);
        return new CheckoutPage(driver);
    }
    public CartPage clickOnDeleteLink(String productName){
        By deleteLink = By.xpath("//td[normalize-space()='"+productName+"']/following-sibling::td/a[normalize-space()='Delete']");
        driver.element().click(deleteLink);
        driver.element().waitForPageToLoad();
        return this;
    }

    //validations
    @Step("Verify total sum is correct")
    public CartPage verifyTotalSumIsCorrect(String sum) {
        driver.element().waitForPageToLoad();
        driver.verification().Equals(driver.element().getText(totalPriceLabel),sum, "Total sum is not correct");
        return this;
    }

}
