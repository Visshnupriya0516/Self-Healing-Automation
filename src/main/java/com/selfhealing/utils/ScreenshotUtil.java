package com.selfhealing.utils;

import com.selfhealing.core.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "target/screenshots/";

    /**
     * Captures a screenshot and saves it to a file.
     * Returns the file path (useful for logs or manual debugging).
     */
    public static String takeScreenshotToFile() {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver == null) {
                LoggerUtil.getLogger().warn("Driver is null, cannot capture screenshot.");
                return null;
            }

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Create directory if it doesn't exist
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destPath = SCREENSHOT_DIR + "screenshot_" + timestamp + ".png";
            File dest = new File(destPath);

            FileUtils.copyFile(src, dest);
            LoggerUtil.getLogger().info("Screenshot saved at: {}", dest.getAbsolutePath());

            return dest.getAbsolutePath();
        } catch (IOException e) {
            LoggerUtil.getLogger().error("Error saving screenshot: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Captures screenshot and returns it as byte array.
     * Used for Allure attachment.
     */
    public static byte[] takeScreenshot() {
        try {
            WebDriver driver = DriverFactory.getDriver();
            if (driver == null) {
                LoggerUtil.getLogger().warn("Driver is null, cannot capture screenshot.");
                return new byte[0];
            }
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            LoggerUtil.getLogger().error("Failed to capture screenshot as bytes: {}", e.getMessage(), e);
            return new byte[0];
        }
    }
}
