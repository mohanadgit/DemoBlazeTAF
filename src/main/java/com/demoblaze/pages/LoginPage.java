package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage {
    private final GUIDriver driver;
    public HomePage homePage;
    public LoginPage(GUIDriver driver) {
        this.driver = driver;
        this.homePage = new HomePage(driver);
    }

    //locators
    private final By loginUsernameInput = By.id("loginusername");
    private final By loginPasswordInput = By.id("loginpassword");
    private final By loginButton = By.xpath("//button[normalize-space()=\"Log in\"]");
    private final By closeLoginModalButton = By.xpath("//div[@id=\"logInModal\"]//button[@type=\"button\"][normalize-space()=\"Close\"]");
    private final By loginModal = By.id("logInModal");

    //actions

    public LoginPage navigate() {
        homePage.clickOnLogInButton();
        return this;
    }
    public LoginPage enterLoginUsername(String username) {
        driver.element().type(loginUsernameInput, username);
        return this;
    }
    public LoginPage enterLoginPassword(String password) {
        driver.element().type(loginPasswordInput, password);
        return this;
    }
    public LoginPage clickLoginButton() {
        driver.element().click(loginButton);
        return this;
    }
    public HomePage closeLoginModal() {
        driver.element().click(closeLoginModalButton);
        return new HomePage(driver);
    }



    //validations
    @Step("Verify login modal is displayed")
    public LoginPage verifyLoginModalIsDisplayed() {
        driver.verification().isElementVisible(loginModal);
        return this;
    }

    @Step("validate wrong username alert message")
    public LoginPage validateWrongUsernameAlertMessage(String acutalMessage, String expectedMessage) {
        driver.verification().Equals(acutalMessage, expectedMessage, "Wrong username alert message is not as expected");
        return this;
    }
    @Step("validate wrong password alert message")
    public LoginPage validateWrongPasswordAlertMessage(String acutalMessage, String expectedMessage) {
        driver.verification().Equals(acutalMessage, expectedMessage, "Wrong password alert message is not as expected");
        return this;
    }
}
