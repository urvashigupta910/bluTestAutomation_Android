package bluAndroid.bluAndroid.TestScripts;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import bluAndroid.bluAndroid.pageObjects.LoginScreen;
import bluAndroid.bluAndroid.pageObjects.MenuScreen;
import bluAndroid.bluAndroid.pageObjects.MyDetailsScreen;
import bluAndroid.bluAndroid.util.BaseClass;
import bluAndroid.bluAndroid.util.CommonUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

public class BluLogoutTestRunner extends BaseClass {
	
	static AppiumDriver<WebElement> driver;
	LoginScreen ls;
	String mobileNo, password;
	MyDetailsScreen mds;
	MenuScreen ms;
	@BeforeMethod
	public void preCondition() throws IOException

	{
		System.out.println("Set Up....");
		BluLogoutTestRunner.driver = BaseClass.getAppCapabilities();
		mobileNo = CommonUtil.getPropertyValue("login", "mobileNo");
		password = CommonUtil.getPropertyValue("login", "password");
		ls = new LoginScreen(driver);
		ls.clickLoginLink();
		ls.bluLogin(mobileNo, password);
		mds=new MyDetailsScreen(driver);
		ms=new MenuScreen(driver);
	}
	
	@Test
	public void tc01_logout()
	{
		System.out.println("tc01_logout");
		extentTest = extentReports.createTest("tc01_logout()");
		ms.logout();
		WebElement logIn=driver.findElement(By.id("sg.com.blu.android.uat:id/log_in_btn"));
		WebElement signup=driver.findElement(By.id("sg.com.blu.android.uat:id/sign_up_btn"));
		assertTrue(logIn.isDisplayed());
		assertTrue(signup.isDisplayed());
	}
	@Test
	public void tc02_resetPassword()
	{
		System.out.println("tc01_logout");
		extentTest = extentReports.createTest("tc01_logout()");
		ms.clickOnSettings();
		WebElement resetPassword=driver.findElement(By.id("sg.com.blu.android.uat:id/reset_password_container"));
		resetPassword.click();
		WebElement currentPassword=driver.findElement(By.id("sg.com.blu.android.uat:id/current_password_inputField"));
		currentPassword.sendKeys("Admin@123");
		WebElement newPassword=driver.findElement(By.id("sg.com.blu.android.uat:id/new_password_inputField"));
		newPassword.sendKeys("Admin@12345");
		WebElement confirmPassword=driver.findElement(By.id("sg.com.blu.android.uat:id/confirm_password_inputField"));
		confirmPassword.sendKeys("Admin@12345");
		WebElement submitBtn=driver.findElement(By.id("sg.com.blu.android.uat:id/submit_btn"));
		submitBtn.click();
		WebElement backToHome=driver.findElement(By.id("sg.com.blu.android.uat:id/backToHomeButton"));
		assertTrue(backToHome.isDisplayed());
		backToHome.click();
		ms.clickOnSettings();
		resetPassword.click();
		currentPassword.sendKeys("Admin@12345");
		newPassword.sendKeys("Admin@123");
		confirmPassword.sendKeys("Admin@123");
		submitBtn.click();
		
	}
	public static void swipeInListFromLastToFirst(List<WebElement> list) {
		int items = list.size();
		//System.out.println("List Size is: "+ items);
		TouchAction action = new TouchAction(driver);
		PointOption p1 = new PointOption();
		org.openqa.selenium.Point centerOfFirstElement = ((MobileElement) list.get(0)).getCenter();
		org.openqa.selenium.Point centerOfLastElement = ((MobileElement) list.get(items - 1)).getCenter();
		//System.out.println("center of first element: "+centerOfFirstElement+ "  center of last element: "+centerOfLastElement);
		new TouchAction(driver).longPress(p1.point(centerOfFirstElement.x, centerOfFirstElement.y+300))
				.moveTo(p1.point(centerOfLastElement.x, centerOfLastElement.y+80)).release().perform();
		
	}
	@Test
	public void tc03_deactivateAccount()
	{
		System.out.println("tc01_logout");
		extentTest = extentReports.createTest("tc01_logout()");
		ms.clickOnSettings();
		WebElement deactivateAccount=driver.findElement(By.id("sg.com.blu.android.uat:id/deactivate_account_container"));
		List<WebElement> list=driver.findElements(By.className("android.widget.RadioGroup"));
		deactivateAccount.click();
		WebElement reason=driver.findElement(By.xpath("//android.widget.RadioGroup[@resource-id='sg.com.blu.android.uat:id/radioGroup']//android.widget.RelativeLayout"));
		reason.click();
		swipeInListFromLastToFirst(list);	 
	}
	@AfterMethod
	public void getResult(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.SKIP) {
			System.out.println("Skipped extent report");
			extentTest.skip(MarkupHelper.createLabel(testResult.getName() + " Test Case SKIPPED", ExtentColor.BLUE));
			extentTest.skip(testResult.getThrowable());
			// extentTest.log(Status.SKIP, MarkupHelper.createLabel(testResult.getName() + "
			// - Test Case Skipped", ExtentColor.ORANGE));
		} else if (testResult.getStatus() == ITestResult.FAILURE) {
			extentTest.fail(MarkupHelper.createLabel(testResult.getName() + " Test case FAILED due to below issues:",
					ExtentColor.RED));
			extentTest.fail(testResult.getThrowable());
			String screenshotPath = CommonUtil.takesScreenShotFailed(driver, testResult.getName());
			extentTest.addScreenCaptureFromPath(screenshotPath);
		} else if (testResult.getStatus() == ITestResult.SUCCESS) {
			extentTest.pass(MarkupHelper.createLabel(testResult.getName() + " Test Case PASSED", ExtentColor.GREEN));
			String screenshotPath = CommonUtil.takesScreenShot(driver, testResult.getName());

			extentTest.addScreenCaptureFromPath(screenshotPath);
		}

	}
}
