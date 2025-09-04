package com.selfhealing.pages;
import com.selfhealing.core.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class BasePage {

    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }

    // ✅ Try each locator until one works
    protected WebElement findElementWithHealing(List<By> locators) {
        for (By locator : locators) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    System.out.println("[INFO] Found element with locator: " + locator);
                    return element;
                }
            } catch (Exception e) {
                System.out.println("[WARN] Locator failed: " + locator);
                // continue with next locator
            }
        }
        throw new RuntimeException(
                "Unable to locate element using primary and fallback locators: " + locators
        );
    }

    // ✅ Overloaded find() for single locator (optional)
    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected void type(List<By> locators, String text) {
        WebElement element = findElementWithHealing(locators);
        element.clear();
        element.sendKeys(text);
    }

    protected void click(List<By> locators) {
        WebElement element = findElementWithHealing(locators);
        element.click();
    }
}
