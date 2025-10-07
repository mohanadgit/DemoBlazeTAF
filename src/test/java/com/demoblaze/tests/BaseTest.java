package com.demoblaze.tests;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.drivers.WebDriverProvider;
import com.demoblaze.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {
    protected GUIDriver driver;
    protected JsonReader testData;


    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
