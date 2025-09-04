package com.selfhealing.tests;
import com.selfhealing.pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testLogin() {
        LoginPage loginPage = new LoginPage();

        // Step 1: Enter username & password using self-healing locators
        loginPage.enterUsername("tomsmith");
        loginPage.enterPassword("SuperSecretPassword!");
        loginPage.clickLogin();

        // Step 2: Validate login success
        // ✅ The-internet.herokuapp.com shows this success message after login
        By successMsgLocator = By.cssSelector(".flash.success");
        String successText = getDriver().findElement(successMsgLocator).getText();

        System.out.println("[INFO] Login message: " + successText);
        Assert.assertTrue(
                successText.contains("You logged into a secure area!"),
                "Login was not successful!"
        );
    }
}
