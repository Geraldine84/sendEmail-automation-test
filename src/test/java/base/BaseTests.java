package base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import pages.HomePage;
import utils.UtilityTests;
import utils.SendMail;

public class BaseTests {

	protected static WebDriver driver;
	protected HomePage homePage;
	public static ExtentReports reports;
	public static ExtentSparkReporter  htmlReporter;
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testInfo = new ThreadLocal<ExtentTest>();
	public static String toAddress;

	@BeforeSuite
    @Parameters("groupReport")
	public void setUp( String groupReport) throws Exception {

		htmlReporter = new ExtentSparkReporter (new File(System.getProperty("user.dir") + groupReport));
		reports = new ExtentReports();
		reports.setSystemInfo("TEST ENVIRONMENT", "https://accounts.google.com/");
		reports.attachReporter(htmlReporter);
	}
	
	@BeforeClass
	public void setUp() {
		
		ExtentTest parent = reports.createTest(getClass().getName());
	    parentTest.set(parent);
		
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.get("https://accounts.google.com/");
		
		homePage = new HomePage(driver);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	@BeforeMethod(description = "fetch test cases name")
	public void register(Method method) throws InterruptedException {

		ExtentTest child = parentTest.get().createNode(method.getName());
		testInfo.set(child);
		testInfo.get().getModel().setDescription(UtilityTests.CheckBrowser());
		if (UtilityTests.isAlertPresents()) {
			driver.switchTo().alert().accept();
			Thread.sleep(1000);
		}
	}
	
	@AfterMethod(description = "to display the result after each test method")
	public void captureStatus(ITestResult result) throws IOException {
		for (String group : result.getMethod().getGroups())
			testInfo.get().assignCategory(group);
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = UtilityTests.addScreenshot();
			 testInfo.get().addScreenCaptureFromBase64String(screenshotPath);
			testInfo.get().fail(result.getThrowable());
			driver.navigate().refresh();
		}			
        else if (result.getStatus() == ITestResult.SKIP)
        	testInfo.get().skip(result.getThrowable());
        else
        	testInfo.get().pass(result.getName() +" Test passed");

		reports.flush();
	}
	
	@Parameters({"toMails", "groupReport"})
    @AfterSuite(description = "clean up report after test suite")
    public void cleanup(String toMails, String groupReport) {

        toAddress = System.getProperty("email_list", toMails);
        SendMail.ComposeGmail("SendEmail Report <gtbankuct.test.report@gmail.com>", toAddress, groupReport);

    }
	
}