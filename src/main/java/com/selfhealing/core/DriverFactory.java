package com.selfhealing.core;
import com.selfhealing.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver() {
        try {
            if (driver.get() != null) {
                LoggerUtil.getLogger().warn("Driver already initialized for this thread.");
                return;
            }

            String browser = ConfigManager.get("browser");
            boolean isRemote = Boolean.parseBoolean(ConfigManager.get("remote"));
            boolean isHeadless = Boolean.parseBoolean(ConfigManager.get("headless"));

            LoggerUtil.getLogger().info("Initializing driver. Browser={}, Remote={}, Headless={}", browser, isRemote, isHeadless);

            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = new ChromeOptions();
                if (isHeadless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--disable-gpu");
                }
                options.addArguments("--window-size=1920,1080");

                if (isRemote) {
                    String gridUrl = ConfigManager.get("gridUrl");
                    LoggerUtil.getLogger().info("Connecting to Selenium Grid at {}", gridUrl);
                    driver.set(new RemoteWebDriver(new URL(gridUrl), options));
                } else {
                    driver.set(new ChromeDriver(options));
                }
            } else {
                LoggerUtil.getLogger().error("Unsupported browser: {}", browser);
                throw new RuntimeException("Unsupported browser: " + browser);
            }

            LoggerUtil.getLogger().info("Driver initialized successfully.");
        } catch (Exception e) {
            LoggerUtil.getLogger().error("Failed to initialize WebDriver: {}", e.getMessage(), e);
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
                LoggerUtil.getLogger().info("Driver closed and removed from ThreadLocal.");
            } else {
                LoggerUtil.getLogger().warn("No WebDriver instance found for this thread.");
            }
        } catch (Exception e) {
            LoggerUtil.getLogger().error("Error while quitting driver: {}", e.getMessage(), e);
        }
    }
}
