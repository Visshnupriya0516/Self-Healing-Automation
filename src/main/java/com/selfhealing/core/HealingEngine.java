package com.selfhealing.core;
import com.selfhealing.utils.LocatorUtils;
import com.selfhealing.utils.LoggerUtil;
import org.openqa.selenium.*;

import java.util.List;

public class HealingEngine {

    private final WebDriver driver;

    public HealingEngine(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement findElement(By primaryLocator, List<By> backupLocators) {
        try {
            return driver.findElement(primaryLocator);
        } catch (NoSuchElementException e) {
            LoggerUtil.getLogger().warn("Primary locator failed: {}", primaryLocator);
            // try provided fallbacks
            for (By by : backupLocators) {
                try {
                    LoggerUtil.getLogger().info("Trying fallback: {}", by);
                    return driver.findElement(by);
                } catch (NoSuchElementException ignore) {}
            }
            return healFromRepository(primaryLocator);
        }
    }

    private WebElement healFromRepository(By primaryLocator) {
        String key = primaryLocator.toString();
        List<By> repoFallbacks = LocatorUtils.getFallbacks(key);
        for (By fallback : repoFallbacks) {
            try {
                LoggerUtil.getLogger().info("Trying repo fallback: {}", fallback);
                return driver.findElement(fallback);
            } catch (NoSuchElementException ignore) {}
        }
        throw new NoSuchElementException("Unable to locate element using primary and fallback locators: " + primaryLocator);
    }
}
