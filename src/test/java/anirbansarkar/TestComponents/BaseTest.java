package anirbansarkar.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import anirbansarkar.pageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class BaseTest {

	public WebDriver driver;
	public LandingPage lp;
	Properties prop;

	public WebDriver initializeDriver() throws IOException {

		prop = new Properties();
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java/anirbansarkar//resources//GlobalData.properties");
		prop.load(file);
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");
		// prop.getProperty("browser");

		if (browserName.contains("chrome")) {

			ChromeOptions options = new ChromeOptions();
			// Chrome driver initialization code
			WebDriverManager.chromedriver().setup();
			if (browserName.contains("headless")) {
				options.addArguments("--headless=new");
				options.addArguments("--disable-gpu");

			    options.addArguments("--window-size=1920,1080");

			    options.addArguments("--no-sandbox");

			    options.addArguments("--disable-dev-shm-usage");

			    options.addArguments("--start-maximized");

			    options.addArguments("--disable-infobars");

			    options.addArguments("--disable-extensions");
			}

			driver = new ChromeDriver(options);
			//driver.manage().window().setSize(new Dimension(1440, 900));// Recommended for
			// headless for flaky tests

		} else if (browserName.equalsIgnoreCase("firefox")) {
			// Firefox driver initialization code

		} else if (browserName.equalsIgnoreCase("edge")) {

			// Edge driver initialization code
			System.setProperty("webdriver.edge.driver", "edgeDriver.exe");
			driver = new EdgeDriver();

		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;

	}

	public List<HashMap<String, String>> getJSONToData(String filepath) throws IOException {

		String jsonData = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);

		// String to HashMap - Jackson Databind dependency in pom.xml

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonData,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;

	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File destPath = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileHandler.copy(src, destPath);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";

	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {

		driver = initializeDriver();
		lp = new LandingPage(driver);
		prop = new Properties();
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java/anirbansarkar//resources//GlobalData.properties");
		prop.load(file);
		String url = prop.getProperty("url");
		lp.goTo(url);
		return lp;

	}

	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {

		driver.close();

	}

}
