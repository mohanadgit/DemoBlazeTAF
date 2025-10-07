package com.demoblaze.media;

import com.demoblaze.utils.TimeManager;
import com.demoblaze.utils.logs.LogsManager;
import com.demoblaze.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.awt.*;
import java.awt.image.BufferedImage;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenshotsManager {

    public static final String SCREENSHOTS_PATH = "test-output/screenshots/";

    // Take full-page screenshot
    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Try normal Selenium screenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotFile.getAbsolutePath());
            LogsManager.info("Capturing Screenshot Succeeded");
        }
        catch (UnhandledAlertException e) {
            // Handle case where alert is open
            LogsManager.warn("Alert detected during screenshot: capturing full screen instead...");
            captureFullScreenWithAlert(screenshotName);
        }
        catch (Exception e) {
            LogsManager.error("Failed to Capture Screenshot " + e.getMessage());
        }
    }

    // Take screenshot of a specific element
    public static void takeElementScreenshot(WebDriver driver, By elementSelector) {
        try {
            String ariaName = driver.findElement(elementSelector).getAccessibleName();
            File screenshotSrc = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);
            File screenshotFile = new File(SCREENSHOTS_PATH + ariaName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            AllureAttachmentManager.attachScreenshot(ariaName, screenshotFile.getAbsolutePath());
            LogsManager.info("Capturing Element Screenshot Succeeded");
        }
        catch (UnhandledAlertException e) {
            LogsManager.warn("Alert detected during element screenshot: capturing full screen instead...");
            captureFullScreenWithAlert("alert_element_screenshot");
        }
        catch (Exception e) {
            LogsManager.error("Failed to Capture Element Screenshot " + e.getMessage());
        }
    }

    // Fallback: capture full screen (including alert)
    private static void captureFullScreenWithAlert(String screenshotName) {
        try {
            Robot robot = new Robot();
            java.awt.Rectangle screenRect = new java.awt.Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);

            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "_alert-" + TimeManager.getTimestamp() + ".png");
            ImageIO.write(screenFullImage, "png", screenshotFile);

            AllureAttachmentManager.attachScreenshot(screenshotName + "_alert", screenshotFile.getAbsolutePath());
            LogsManager.info("Captured full-screen screenshot (with alert).");
        } catch (Exception ex) {
            LogsManager.error("Failed to Capture Full-Screen Alert Screenshot " + ex.getMessage());
        }
    }
}
