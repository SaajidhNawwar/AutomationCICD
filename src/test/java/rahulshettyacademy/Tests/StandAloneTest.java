package rahulshettyacademy.Tests;

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

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String productName = "AUTOMATION 8";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		driver.get("https://rahulshettyacademy.com/client/");
		
		LandingPage landingPage = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("mnmsaajidh@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Saaj@123");
		driver.findElement(By.id("login")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement addToCartBtn = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		addToCartBtn.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

		//Check if the added item is visible in the cart page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".cartSection h3")));
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		//Go to checkout page
		driver.findElement(By.xpath("//button[normalize-space()='Checkout']")).click();
		
		//Enter checkout details
		driver.findElement(By.xpath("//input[@value='4542 9931 9292 2293']")).sendKeys("1234123412341234");

		Actions a = new Actions (driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"india").build().perform();
				
		wait.until(ExpectedConditions.visibilityOfElementLocated (By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("(//button [contains(@class, 'ta-item')]) [2]")).click();
		driver.findElement(By.cssSelector(".action__submit")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated (By.cssSelector(".hero-primary")));
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();

	}

}
