package rahulshettyacademy.Tests;
import rahulshettyacademy.TestComponents.Retry;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class ErrorValidations extends BaseTest{

	@Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException{

		String productName = "AUTOMATION 8";
		landingPage.login("mnmnsaajidh@gmail.com", "Saaj@123");
		//given incorrect email id
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

	}


	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException {
		
	String productName = "ZARA COAT 3";
	ProductCatalogue productCatalogue = landingPage.login("mnmsaajidh@gmail.com", "Saaj@1234");
	List<WebElement> products = productCatalogue.getProductList();
	productCatalogue.addProductToCart (productName);
	CartPage cartPage = productCatalogue.goToCartPage();
	Boolean match = cartPage.VerifyProductDisplay ("ZARA COAT 33");
	Assert.assertFalse (match);
	}
}
