package com.demoblaze.utils.actions;

import com.demoblaze.utils.WaitManager;
import com.demoblaze.utils.logs.LogsManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.sql.DriverManager;
import java.time.Duration;
import java.util.List;

public class ElementActions {
    private final WebDriver driver;
    private WaitManager waitManager;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }


    public void waitForPageToLoad() {

        waitManager.fluentWait().until(d -> {
            try {
                JavascriptExecutor js = (JavascriptExecutor) d;

                /*// Step 1: Wait for DOM ready state
                String readyState = (String) js.executeScript("return document.readyState");
                if (!"complete".equals(readyState)) {
                    return false;
                }*/

                // Step 2 (optional): Wait for AJAX requests (if using jQuery)
                try {
                    Long activeAjax = (Long) js.executeScript("return window.jQuery ? jQuery.active : 0");
                    if (activeAjax != 0) {
                        return false;
                    }
                } catch (Exception ignored) {
                    // jQuery not present, ignore
                }

               /* // Step 3: Wait for product container to exist
                WebElement container = d.findElement(By.id("tbodyid"));
                if (container == null || !container.isDisplayed()) {
                    return false;
                }

                // Step 4: Wait for all product cards to be visible
                List<WebElement> products = d.findElements(By.cssSelector("#tbodyid .col-lg-4.col-md-6.mb-4"));
                if (products.isEmpty()) {
                    System.out.println("Products not loaded yet...");
                    return false;
                }

                for (WebElement product : products) {
                    if (!product.isDisplayed()) {
                        return false;
                    }
                }*/

                return true;
            } catch (Exception e) {
                return false;
            }
        });

        LogsManager.info("Page fully loaded and all products are visible.");
    }



    //Check if element exists in DOM and is displayed
    public boolean isElementPresent(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            boolean isDisplayed = element.isDisplayed();
            System.out.println("Element " + locator + " is displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            LogsManager.error("Failed to find element: " + locator, e.getMessage());
            return false;
        }
    }

    //Clicking
    public ElementActions click(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        // Wait until the element is stable (not moving)
                        Point initialLocation = element.getLocation();
                        LogsManager.info("initialLocation: " + initialLocation);
                        Point finalLocation = element.getLocation();
                        LogsManager.info("finalLocation: " + finalLocation);
                        if (!initialLocation.equals(finalLocation)) {
                            return false; // still moving, wait longer
                        }
                        element.click();
                        LogsManager.info("Clicked on element: " + locator);
                        return true;
                    } catch (Exception e) {
                        LogsManager.warn("Failed to click on element: " + locator, e.getMessage());
                        return false;
                    }
                }
        );
        return this;
    }

    //Typing
    public ElementActions type(By locator, String text) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.clear();
                        element.sendKeys(text);
                        LogsManager.info("Typed text '" + text + "' into element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }
    //hovering
    public ElementActions hover(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        new Actions(d).moveToElement(element).perform();
                        LogsManager.info("Hovered over element: " + locator);
                        return true;
                    } catch (Exception e) {
                        LogsManager.warn("Failed to click on element: " + locator, e.getMessage());
                        return false;
                    }
                }
        );
        return this;
    }

    //Getting text
    public String getText(By locator) {
        return waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        String msg = element.getText();
                        LogsManager.info("Retrieved text from element: " + locator + " - Text: " + msg);
                        return !msg.isEmpty() ? msg : null;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );
    }

    //upload file
    public ElementActions uploadFile(By locator,String filePath)
    {
        String fileAbsolute = System.getProperty("user.dir") + File.separator  + filePath ;
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolute);
                        LogsManager.info("Uploaded file: " + fileAbsolute + " to element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }


    //find an element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    //function to scroll to an element using js
    public void scrollToElementJS(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(""" 
                        arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"});""", findElement(locator));
    }

    //select from dropdown
    public ElementActions selectFromDropdown(By locator, String value) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        Select select = new Select(element);
                        select.selectByVisibleText(value);
                        LogsManager.info("Selected value '" + value + "' from dropdown: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

}
