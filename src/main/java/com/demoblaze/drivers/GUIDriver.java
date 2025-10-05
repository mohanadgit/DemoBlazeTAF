package com.demoblaze.drivers;

import com.demoblaze.utils.actions.AlertActions;
import com.demoblaze.utils.actions.BrowserActions;
import com.demoblaze.utils.actions.ElementActions;
import com.demoblaze.utils.actions.FrameActions;
import com.demoblaze.utils.dataReader.PropertyReader;
import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.validations.Validation;
import com.demoblaze.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private final String browser = PropertyReader.getProperty("browserType");
    private  ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public GUIDriver()
    {
        LogsManager.info("Initializing GUIDriver with browser: " + browser);
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for browser: " + browserType);
        AbstractDriver abstractDriver = browserType.getDriverFactory(); //local
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public ElementActions element() {
        return new ElementActions(get());
    }
    public BrowserActions browser() {
        return new BrowserActions(get());
    }
    public FrameActions frame() {
        return new FrameActions(get());
    }
    public AlertActions alert() {
        return new AlertActions(get());
    }
    //soft assertions
    public Validation validation() {
        return new Validation(get());
    }
    // hard assertions
    public Verification verification() {
        return new Verification(get());
    }
    public WebDriver get() {
        return driverThreadLocal.get();
    }

    public  void quitDriver() {
        driverThreadLocal.get().quit();
    }
}
