package com.demoblaze.pages;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.utils.dataReader.PropertyReader;
import com.demoblaze.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage {
    private final GUIDriver driver;
    public HomePage(GUIDriver driver) {
        this.driver = driver;
    }
    //locators
    private final By homeButon = By.xpath("//li[@class=\"nav-item active\"]//a[@class=\"nav-link\"]");
    private final By contactButon = By.xpath("//a[normalize-space()=\"Contact\"]");
    private final By aboutUsButon = By.xpath("//a[normalize-space()=\"About us\"]");
    private final By cartButon = By.xpath("//a[@id=\"cartur\"]");
    private final By logInButon = By.xpath("//a[@id=\"login2\"]");
    private final By signUpButon = By.xpath("//a[@id=\"signin2\"]");
    private final By userNameLabel = By.id("nameofuser");
    private final By logputButon = By.id("logout2");
    private final By phoneCategory = By.cssSelector("body > div:nth-child(6) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(2)");
    private final By laptopCategory = By.cssSelector("body > div:nth-child(6) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(3)");
    private final By monitorCategory = By.cssSelector("body > div:nth-child(6) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(4)");
    private final By nextButton = By.id("next2");
    private final By previousButton = By.id("prev2");

    //Dynamic locators
    String productName = "Samsung galaxy s6";

    By nameLocator = By.xpath("//a[@class='hrefch' and normalize-space(text())='" + productName + "']");
    By priceLocator = By.xpath("//a[@class='hrefch' and normalize-space(text())='" + productName + "']/ancestor::div[contains(@class,'card')]//h5");
    By descLocator  = By.xpath("//a[@class='hrefch' and normalize-space(text())='" + productName + "']/ancestor::div[contains(@class,'card')]//p");


    //actions
    public HomePage navigate(){
        driver.browser().navigateTo(PropertyReader.getProperty("baseurl"));
        driver.element().waitForPageToLoad();
        return this;
    }
    public HomePage clickOnHomeButton(){
        driver.element().click(homeButon);
        return this;
    }
    public ContactPage clickOnContactButton(){
        driver.element().click(contactButon);
        return new ContactPage(driver);
    }
    public AboutusPage clickOnAboutUsButton(){
        driver.element().click(aboutUsButon);
        return new AboutusPage(driver);
    }
    public CartPage clickOnCartButton(){
        driver.element().click(cartButon);
        return new CartPage(driver);
    }
    public LoginPage clickOnLogInButton(){
        driver.element().click(logInButon);
        return new LoginPage(driver);
    }
    public SignUpPage clickOnSignUpButton(){
        driver.element().click(signUpButon);
        return new SignUpPage(driver);
    }
    public HomePage clickOnLogOutButton(){
        driver.element().click(logputButon);
        driver.element().waitForPageToLoad();
        return this;
    }
    public HomePage clickOnPhoneCategory(){
        driver.element().click(phoneCategory);
        return this;
    }
    public HomePage clickOnLaptopCategory(){
        driver.element().click(laptopCategory);
        return this;
    }
    public HomePage clickOnMonitorCategory(){
        driver.element().click(monitorCategory);
        return this;
    }
    public HomePage clickOnNextButton(){
        driver.element().click(nextButton);
        return this;
    }
    public HomePage clickOnPreviousButton(){
        driver.element().click(previousButton);
        return this;
    }
    public productDetailsPage clickOnProduct(String productName){
        By productLink = By.xpath("//a[@class='hrefch' and normalize-space(text())='" + productName + "']");
        By priceLocator = By.xpath("//a[@class='hrefch' and normalize-space(text())='" + productName + "']/ancestor::div[contains(@class,'card')]//h5");
        By descLocator  = By.xpath("//a[@class='hrefch' and normalize-space(text())='" + productName + "']/ancestor::div[contains(@class,'card')]//p");
        while(!driver.element().isElementPresent(productLink)){
            System.out.println("Product with name '" + productName + "' not found on this page.");
            if(driver.element().isElementPresent(nextButton)){
                System.out.println("Navigating to the next page to search for the product.");
                driver.element().click(nextButton);
                driver.element().waitForPageToLoad();
            }
            else{
                LogsManager.info("Product with name '" + productName + "' not found.");
                return null;
            }
        }
        System.out.println("Product with name '" + productName + "' found." + " Price: " + driver.element().getText(priceLocator) + " Description: " + driver.element().getText(descLocator));
        driver.element().click(productLink);
        driver.element().waitForPageToLoad();
        return new productDetailsPage(driver);
    }


    //validations
    @Step("Verify that user is logged in with username {username}")
    public HomePage verifyUserIsLoggedIn(String username) {
        driver.verification().Equals("Welcome " + username, driver.element().getText(userNameLabel), "Username is not correct");
        return this;
    }
    @Step("Verify that user is logged out")
    public HomePage verifyUserIsLoggedOut() {
        driver.element().waitForPageToLoad();
        driver.verification().isElementVisible(logInButon);
        return this;
    }
}