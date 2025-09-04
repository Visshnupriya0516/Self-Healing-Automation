package com.selfhealing.tests;

import com.selfhealing.pages.LoginPage;
import com.selfhealing.utils.ScreenshotUtil;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Authentication Module")
@Feature("Login Feature")
@Story("User Login Validation")
public class LoginTest extends BaseTest {

    @Test(description = "Verify user can login successfully")
    @Description("This test verifies that a valid user is able to log in and sees a success message.")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogin() {
        LoginPage loginPage = new LoginPage();

        loginPage.enterUsername("tomsmith");
        loginPage.enterPassword("SuperSecretPassword!");
        loginPage.clickLogin();

        By successMsgLocator = By.cssSelector(".flash.success");
        String successText = getDriver().findElement(successMsgLocator).getText();

        attachScreenshot("After login validation");

        System.out.println("[INFO] Login message: " + successText);
        Assert.assertTrue(
                successText.contains("You logged into a secure area!"),
                "Login was not successful!"
        );
    }

    @Attachment(value = "{stepName}", type = "image/png")
    private byte[] attachScreenshot(String stepName) {
        return ScreenshotUtil.takeScreenshot();
    }
}
