package anirbansarkar.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import anirbansarkar.pageObjects.LandingPage;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class BaseTest2 {

	public WebDriver driver;
	public LandingPage lp;
	Properties prop;

	@Parameters("browser")
	@BeforeMethod(alwaysRun = true)
	public LandingPage initializeDriver(String browserName) throws IOException, URISyntaxException {

		if (browserName.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			driver = new RemoteWebDriver(new URI("http://192.168.31.117:4444").toURL(), options);
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		lp = new LandingPage(driver);
		prop = new Properties();
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java/anirbansarkar//resources//GlobalData.properties");
		prop.load(file);
		String url = prop.getProperty("url");
		lp.goTo(url);
		return lp;

	}

	@Parameters("browser")
	@BeforeMethod
	public void setup(String browserName) throws Exception {

		if (browserName.equalsIgnoreCase("chrome")) {
			ChromeOptions options = new ChromeOptions();
			driver = new RemoteWebDriver(new URI("http://localhost:4444").toURL(), options);
		}

		driver.manage().window().maximize();
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

	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {

		driver.close();

	}

}
