package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewEmailPage {
	
	private WebDriver driver;
	private By toField = By.name("to");
	private By subjectField = By.name("subjectbox");
	private By bodyField = By.xpath("//td[2]/div[2]/div");
	private By sendButton = By.xpath("//div[4]/table/tbody/tr/td/div/div[2]/div");
	
	public NewEmailPage(WebDriver driver) {
		this.driver = driver;
	}

	public void setRecipient(String email) {
		driver.findElement(toField).clear();
		driver.findElement(toField).sendKeys(email);
	}

	public void setSubject(String subject) throws InterruptedException {
		driver.findElement(subjectField).clear();
		driver.findElement(subjectField).sendKeys(subject);
	}
	
	public void setBody(String body) throws InterruptedException {
		driver.findElement(bodyField).clear();
		driver.findElement(bodyField).sendKeys(body);
	}
	
	public DashboardPage clickSendButton() {
		driver.findElement(sendButton).click();
		return new DashboardPage(driver);
	}
}
