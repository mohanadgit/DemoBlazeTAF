package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignUpPage {
    private final GUIDriver driver;
    public HomePage homePage;
    public SignUpPage(GUIDriver driver) {
        this.driver = driver;
        this.homePage = new HomePage(driver);
    }
    //locators
    private final By signUpUsernameInput = By.id("sign-username");
    private final By signUpPasswordInput = By.id("sign-password");
    private final By signUpButton = By.xpath("//button[normalize-space()=\"Sign up\"]");
    private final By closeSignUpModalButton = By.xpath("//div[@id=\"signInModal\"]//button[@type=\"button\"][normalize-space()=\"Close\"]");
    private final By signUpModal = By.id("signInModal");

    //actions
    public SignUpPage navigate() {
        homePage.clickOnSignUpButton();
        return this;
    }
    public SignUpPage enterSignUpUsername(String username) {
        driver.element().type(signUpUsernameInput, username);
        return this;
    }
    public SignUpPage enterSignUpPassword(String password) {
        driver.element().type(signUpPasswordInput, password);
        return this;
    }
    public SignUpPage clickSignUpButton() {
        driver.element().click(signUpButton);
        return this;
    }
    public HomePage closeSignUpModal() {
        driver.element().click(closeSignUpModalButton);
        return new HomePage(driver);
    }


    //validations
    @Step("Verify sign up modal is displayed")
    public SignUpPage verifySignUpModalIsDisplayed() {
        driver.verification().isElementVisible(signUpModal);
        return this;
    }
    @Step("validate successful sign up alert message")
    public SignUpPage validateSuccessfulSignUpAlertMessage(String acutalMessage, String expectedMessage) {
        driver.verification().Equals(acutalMessage, expectedMessage, "Successful sign up alert message is not as expected");
        return this;
    }
    @Step("validate user already exist alert message")
    public SignUpPage validateUserAlreadyExistAlertMessage(String acutalMessage, String expectedMessage) {
        driver.verification().Equals(acutalMessage, expectedMessage, "User already exist alert message is not as expected");
        return this;
    }

}
