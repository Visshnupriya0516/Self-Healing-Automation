package com.selfhealing.pages;

import org.openqa.selenium.By;
import java.util.Arrays;
import java.util.List;

public class LoginPage extends BasePage {

    // Primary + fallback locators for username field
    private final List<By> usernameLocators = Arrays.asList(
            By.id("wrong-id"),                    // intentionally broken to test healing
            By.id("username"),                    // correct locator on demo site
            By.xpath("//input[@name='username']")  // fallback locator
    );

    // Primary + fallback locators for password field
    private final List<By> passwordLocators = Arrays.asList(
            By.id("password"),                     // correct locator
            By.name("password"),
            By.xpath("//input[@type='password']")
    );

    // Primary + fallback locators for login button
    private final List<By> loginButtonLocators = Arrays.asList(
            By.id("loginBtn"),                     // intentionally wrong to simulate healing
            By.cssSelector("button[type='submit']"), // correct locator
            By.xpath("//button[contains(text(),'Login')]")
    );

    public void enterUsername(String username) {
        type(usernameLocators, username);
    }

    public void enterPassword(String password) {
        type(passwordLocators, password);
    }

    public void clickLogin() {
        click(loginButtonLocators);
    }
}
