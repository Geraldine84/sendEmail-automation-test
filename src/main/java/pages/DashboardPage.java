package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

	private WebDriver driver;
	private By emailText = By.xpath("//div[4]/div/div[2]/div[2]");
	private By userButton = By.cssSelector("img.gb_Ca.gbii");
	private By closeModalField = By.xpath("//header/div[2]/div[2]/div[2]");
	private By composeButton = By.cssSelector("div.T-I.T-I-KE.L3");
	private By subjectText = By.xpath("//td[5]/div/div/div[2]/span/span");
	
	public DashboardPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String assertEmailText() {
		driver.findElement(userButton).click();
		return driver.findElement(emailText).getText();
	}
	
	public void closeUserDetailsModal() {
		driver.findElement(closeModalField).click();
	}
	
	public NewEmailPage clickComposeButton() {
		driver.findElement(composeButton).click();
		return new NewEmailPage(driver);
	}

	public String getSubjectText() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(subjectText));
		Thread.sleep(500);
		return driver.findElement(subjectText).getText();
	}
	
}
