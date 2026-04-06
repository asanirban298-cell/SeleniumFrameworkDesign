package anirbansarkar.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import anirbansarkar.Utilities.AbstractComponents;

public class RegistrationPage extends AbstractComponents {

	WebDriver driver;

	public RegistrationPage(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "firstName")
	WebElement firstName;

	@FindBy(id = "lastName")
	WebElement lastName;

	@FindBy(id = "userEmail")
	WebElement userEmail;

	@FindBy(id = "userMobile")
	WebElement userMobile;

	@FindBy(xpath = "//select[@formcontrolname='occupation']")
	WebElement occupationDropdown;

	@FindBy(xpath = "//input[@value='Male']")
	WebElement genderMale;

	@FindBy(xpath = "//input[@value='Female']")
	WebElement genderFemale;

	@FindBy(id = "userPassword")
	WebElement userPassword;

	@FindBy(id = "confirmPassword")
	WebElement confirmPassword;

	@FindBy(xpath = "//input[@formcontrolname='required']")
	WebElement ageCheckbox;

	@FindBy(id = "login")
	WebElement registerButton;

	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMsg;

	// Fill First Name
	public void fillFirstName(String fname) {

		firstName.sendKeys(fname);

	}

	// Fill Last Name
	public void fillLastName(String lname) {

		lastName.sendKeys(lname);

	}

	// Fill Email
	public void fillEmail(String email) {

		userEmail.sendKeys(email);

	}

	// Fill Phone Number
	public void fillPhoneNumber(String mobile) {

		userMobile.sendKeys(mobile);

	}

	// Select Occupation from Dropdown
	public void selectOccupation(String occupation) {

		occupationDropdown.click();
		WebElement occupationOption = driver
				.findElement(By.xpath("//option[contains(text(),'" + occupation + "')]"));
		occupationOption.click();

	}

	// Select Gender - Male
	public void selectGenderMale() {

		genderMale.click();

	}

	// Select Gender - Female
	public void selectGenderFemale() {

		genderFemale.click();

	}

	// Fill Password
	public void fillPassword(String pwd) {

		userPassword.sendKeys(pwd);

	}

	// Fill Confirm Password
	public void fillConfirmPassword(String pwd) {

		confirmPassword.sendKeys(pwd);

	}

	// Check Age Confirmation Checkbox
	public void checkAgeConfirmation() {

		ageCheckbox.click();

	}

	// Complete Registration with All Fields
	public LandingPage registerUser(String fname, String lname, String email, String mobile, String occupation,
			String gender, String pwd) {

		fillFirstName(fname);
		fillLastName(lname);
		fillEmail(email);
		fillPhoneNumber(mobile);
		selectOccupation(occupation);

		if (gender.equalsIgnoreCase("Male")) {
			selectGenderMale();
		} else if (gender.equalsIgnoreCase("Female")) {
			selectGenderFemale();
		}

		fillPassword(pwd);
		fillConfirmPassword(pwd);
		checkAgeConfirmation();
		registerButton.click();
		LandingPage lp = new LandingPage(driver);
		return lp;

	}

	// Get Error Message
	public String getErrorMessage() {

		waitForWebElementToAppear(errorMsg);
		String errorMessage = errorMsg.getText();
		return errorMessage;

	}

}
