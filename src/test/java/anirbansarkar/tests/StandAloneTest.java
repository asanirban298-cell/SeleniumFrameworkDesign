package anirbansarkar.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import anirbansarkar.pageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String url = "https://rahulshettyacademy.com/client";
		driver.manage().window().maximize();
		driver.get(url);

		String email = "asanirban298@gmail.com";
		String pwd = "sA2!091959";
		LandingPage lp = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys(email);
		driver.findElement(By.id("userPassword")).sendKeys(pwd);
		driver.findElement(By.id("login")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		String order = "ZARA COAT 3";
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(order)).findFirst()
				.orElse(null);
		WebElement addToCart = prod.findElement(By.cssSelector(".card-body button:last-of-type"));
		js.executeScript("arguments[0].click();", addToCart);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		WebElement cartButton = driver.findElement(By.xpath("//button[contains(@routerlink,'cart')]"));
		js.executeScript("arguments[0].click();", cartButton);

		List<WebElement> cartProducts = driver.findElements(By.xpath("//*[@class='cartSection']/h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equals(order));
		Assert.assertTrue(match);
		driver.findElement(By.xpath("//*[@class='totalRow']/button")).click();

		String country = "India";
		driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys(country);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='ng-star-inserted']")));
		List<WebElement> options = driver.findElements(By.xpath("//span[@class='ng-star-inserted']"));
		for (WebElement option : options) {
			if (option.getText().trim().equalsIgnoreCase(country)) {
				option.click();
				break;
			}
		}
		driver.findElement(By.xpath("//a[contains(@class,'action__submit')]")).click();
		String ordrConfirMsg = driver.findElement(By.xpath("//*[@class='hero-primary']")).getText();
		Assert.assertTrue(ordrConfirMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.close();

	}

}
