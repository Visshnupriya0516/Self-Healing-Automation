package com.selfhealing.tests;

import com.selfhealing.core.DriverFactory;
import com.selfhealing.core.ConfigManager;
import com.selfhealing.utils.LoggerUtil;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        try {
            LoggerUtil.getLogger().info("Initializing WebDriver...");
            DriverFactory.initDriver();

            if (getDriver() == null) {
                LoggerUtil.getLogger().error("Driver initialization failed. Aborting test setup.");
                throw new RuntimeException("WebDriver was not initialized.");
            }

            String baseUrl = ConfigManager.get("baseUrl");
            if (baseUrl == null || baseUrl.isEmpty()) {
                LoggerUtil.getLogger().warn("No baseUrl found in config.properties. Skipping navigation.");
            } else {
                getDriver().get(baseUrl);
                LoggerUtil.getLogger().info("Navigated to base URL: {}", baseUrl);
            }
        } catch (Exception e) {
            LoggerUtil.getLogger().error("Exception during test setup: {}", e.getMessage(), e);
            throw new RuntimeException("BaseTest setup failed", e);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            if (getDriver() != null) {
                LoggerUtil.getLogger().info("Closing WebDriver...");
                DriverFactory.quitDriver();
            } else {
                LoggerUtil.getLogger().warn("WebDriver was not initialized. Nothing to quit.");
            }
        } catch (Exception e) {
            LoggerUtil.getLogger().error("Exception during tearDown: {}", e.getMessage(), e);
        }
    }

    // ✅ Added getter method so tests can access WebDriver safely
    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    @Step("Open URL: {url}")
    public void openBaseUrl(String url) {
        if (getDriver() != null && url != null && !url.isEmpty()) {
            getDriver().get(url);
            LoggerUtil.getLogger().info("Navigated to URL: {}", url);
        } else {
            LoggerUtil.getLogger().error("Cannot open URL. Either driver is null or URL is empty.");
        }
    }
}
