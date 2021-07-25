package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	private WebDriver driver;
	private By emailField = By.id("identifierId");
	private By passwordField = By.name("password");
	private By nextButton = By.xpath("//button/span");
	private By text = By.linkText("Inbox");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setEmail(String email) {
		driver.findElement(emailField).clear();
		driver.findElement(emailField).sendKeys(email);
		driver.findElement(nextButton).click();
	}

	public void setPassword(String password) throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(passwordField).clear();
		driver.findElement(passwordField).sendKeys(password);
	}
	
	public DashboardPage clickLoginButton() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		driver.findElement(nextButton).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(text));
		return new DashboardPage(driver);
	}
	
	
	
}
