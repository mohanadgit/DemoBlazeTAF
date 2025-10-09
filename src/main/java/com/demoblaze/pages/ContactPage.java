package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ContactPage {
    private final GUIDriver driver;
    private final HomePage homePage;
    public ContactPage(GUIDriver driver) {
        this.driver = driver;
        this.homePage = new HomePage(driver);
    }
    //locators
    private final By recepientEmailField = By.id("recipient-email");
    private final By userNameField = By.id("recipient-name");
    private final By messageField = By.id("message-text");
    private final By sendMessageButton = By.xpath("//button[normalize-space()=\"Send message\"]");
    private final By closeButton = By.xpath("//div[@id=\"exampleModal\"]//button[@type=\"button\"][normalize-space()=\"Close\"]");

    //actions
    public ContactPage navigate(){
        homePage.navigate().clickOnContactButton();
        return this;
    }
    public ContactPage enterAllContactFields(String email, String name, String message){
        driver.element().type(recepientEmailField,email);
        driver.element().type(userNameField,name);
        driver.element().type(messageField,message);
        return this;
    }
    public ContactPage clickSendMessageButton(){
        driver.element().click(sendMessageButton);
        return this;
    }
    public ContactPage clickCloseButton(){
        driver.element().click(closeButton);
        return this;
    }
    //validations
    @Step("Validate successful message alert")
    public HomePage validateSuccessfulMessageAlert(String actualAlertMessage, String expectedAlertMessage){
        driver.verification().Equals(actualAlertMessage,expectedAlertMessage,"Unuccessful message");
        return new HomePage(driver);
    }
}
