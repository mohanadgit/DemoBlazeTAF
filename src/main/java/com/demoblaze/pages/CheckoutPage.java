package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    private final GUIDriver driver;
    public CheckoutPage(GUIDriver driver) {
        this.driver = driver;
    }
    //locators
    private final By nameField = By.id("name");
    private final By countryField = By.id("country");
    private final By cityField = By.id("city");
    private final By creditCardField = By.id("card");
    private final By monthField = By.id("month");
    private final By yearField = By.id("year");
    private final By purchaseButton = By.xpath("//button[normalize-space()=\"Purchase\"]");
    private final By closeButton = By.xpath("//div[@id=\"orderModal\"]//button[@type=\"button\"][normalize-space()=\"Close\"]");
    private final By successMessage = By.xpath("//h2[normalize-space()=\"Thank you for your purchase!\"]");
    private final By okButton = By.xpath("//button[normalize-space()=\"OK\"]");



    //actions
    public CheckoutPage enterCheckoutDetails(String name, String country, String city, String creditCard, String month, String year) {
        driver.element().type(nameField, name);
        driver.element().type(countryField, country);
        driver.element().type(cityField, city);
        driver.element().type(creditCardField, creditCard);
        driver.element().type(monthField, month);
        driver.element().type(yearField, year);
        return this;
    }
    public CheckoutPage clickPurchaseButton() {
        driver.element().click(purchaseButton);
        return this;
    }
    public CartPage clickCloseButton() {
        driver.element().click(closeButton);
        return new CartPage(driver);
    }
    public HomePage clickOkButton() {
        driver.element().click(okButton);
        return new HomePage(driver);
    }

    //validations
    @Step("Verify success message is displayed")
    public CheckoutPage isSuccessMessageDisplayed() {
        driver.verification().isElementVisible(successMessage);
        return this;
    }

}
