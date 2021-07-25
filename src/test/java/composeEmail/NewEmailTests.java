package composeEmail;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTests;
import pages.DashboardPage;
import pages.NewEmailPage;
import utils.UtilityTests;

public class NewEmailTests extends BaseTests{
	
	@Test
	public void testSendEmail() throws FileNotFoundException, IOException, ParseException, InterruptedException {
		
		File path = null;
		File classpathRoot = new File(System.getProperty("user.dir"));
		path = new File(classpathRoot, "resources/data.config.json");
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser.parse(new FileReader(path));
		JSONObject envs = (JSONObject) config.get("ComposeEmail");

		String email = (String) envs.get("email");
		String body = (String) envs.get("body");
		String subject = (String) envs.get("subject");
		
		UtilityTests.testTitle("Send mail to: " + email);
		
		NewEmailPage newEmailPage = dashboardPage.clickComposeButton();
		newEmailPage.setRecipient(email);
		newEmailPage.setSubject(subject);
		newEmailPage.setBody(body);
		DashboardPage dashpage = newEmailPage.clickSendButton();
		assertTrue(dashpage.getSubjectText().contains("Hi"), "Body is incorrect");
		testInfo.get().log(Status.INFO, dashboardPage.assertEmailText() + " found");
	}

}
