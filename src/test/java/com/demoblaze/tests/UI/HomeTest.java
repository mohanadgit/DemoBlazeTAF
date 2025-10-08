package com.demoblaze.tests.UI;

import com.demoblaze.drivers.GUIDriver;
import com.demoblaze.drivers.UITest;
import com.demoblaze.pages.HomePage;
import com.demoblaze.tests.BaseTest;
import jdk.jfr.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@UITest
public class HomeTest extends BaseTest {


    //Tests
    //TODO: Add more test cases catefgories

    @Test
    @Description("Verify finding product")
    public void findProductTC()
    {
        new HomePage(driver).navigate()
                .clickOnProduct("MacBook Pro");
    }




    //Configurations
    /*@BeforeClass
    protected void preCondition() {
        testData = new JsonReader("login-data");
    }*/
    @BeforeMethod
    public void setUp() {
        driver = new GUIDriver();
        new HomePage(driver).navigate();
        //driver.browser().closeExtensionTab();
    }

    @AfterMethod
    public void tearDown() {
       driver.quitDriver();
    }


}
