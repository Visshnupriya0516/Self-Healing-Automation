package com.selfhealing.pages;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import com.selfhealing.utils.ScreenshotUtil;
import com.selfhealing.utils.LoggerUtil;
import org.openqa.selenium.By;
import java.util.Arrays;
import java.util.List;

public class LoginPage extends BasePage {

    private final List<By> usernameLocators = Arrays.asList(
            By.id("wrong-id"),
            By.id("username"),
            By.xpath("//input[@name='username']")
    );

    private final List<By> passwordLocators = Arrays.asList(
            By.id("password"),
            By.name("password"),
            By.xpath("//input[@type='password']")
    );

    private final List<By> loginButtonLocators = Arrays.asList(
            By.id("loginBtn"),
            By.cssSelector("button[type='submit']"),
            By.xpath("//button[contains(text(),'Login')]")
    );

    @Step("Enter username: {username}")
    public void enterUsername(String username) {
        LoggerUtil.getLogger().info("Typing username...");
        type(usernameLocators, username);
        attachScreenshot("After entering username");
    }

    @Step("Enter password: [HIDDEN]")
    public void enterPassword(String password) {
        LoggerUtil.getLogger().info("Typing password...");
        type(passwordLocators, password);
        attachScreenshot("After entering password");
    }

    @Step("Click on login button")
    public void clickLogin() {
        LoggerUtil.getLogger().info("Clicking login button...");
        click(loginButtonLocators);
        attachScreenshot("After clicking login");
    }

    @Attachment(value = "{stepName}", type = "image/png")
    private byte[] attachScreenshot(String stepName) {
        return ScreenshotUtil.takeScreenshot();
    }
}
