package bluAndroid.bluAndroid.TestScripts;

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
import bluAndroid.bluAndroid.pageObjects.MyDetailsScreen;
import bluAndroid.bluAndroid.pageObjects.SignUpScreen;
import bluAndroid.bluAndroid.pageObjects.Wallet;
import bluAndroid.bluAndroid.util.BaseClass;
import bluAndroid.bluAndroid.util.CommonUtil;
import io.appium.java_client.AppiumDriver;

public class WalletTestRunner extends BaseClass {
	static AppiumDriver<WebElement> driver;
	LoginScreen ls;
	String mobileNo, password;
	MyDetailsScreen mds;
	Wallet w;
	@BeforeMethod
	public void preCondition() throws IOException

	{
		System.out.println("Set Up....");
		WalletTestRunner.driver = BaseClass.getAppCapabilities();
		mobileNo = CommonUtil.getPropertyValue("login", "mobileNo");
		password = CommonUtil.getPropertyValue("login", "password");
		ls = new LoginScreen(driver);
		ls.clickLoginLink();
		ls.bluLogin(mobileNo, password);
		mds=new MyDetailsScreen(driver);
		w=new Wallet(driver);
	}
	@Test
	public void tc01_topUpWallet()
	{
		System.out.println("tc01_topUpWallet");
		extentTest = extentReports.createTest("tc01_topUpWallet()");
		w.clickOnWalletIcon();
		w.clickOnTopUp();
	}
	@Test
	public void tc02_payemntMethods()
	{
		System.out.println("tc02_payemntMethods");
		extentTest = extentReports.createTest("tc02_payemntMethods");
		w.clickOnWalletIcon();
		w.clickOnPaymentMethods();
	}
	@Test
	public void tc03_transactionDetails()
	{
		System.out.println("tc03_transactionDetails");
		extentTest = extentReports.createTest("tc03_transactionDetails");
		w.clickOnWalletIcon();
		Assert.assertTrue(w.recentActivity().isDisplayed());
		w.clickOnSeeMore();
		w.clickOnSearchBox().sendKeys("Booking");
		w.transactionDetails();
		List<WebElement> list=driver.findElements(By.id("//android.widget.ScrollView[@resource-id='sg.com.blu.android.uat.uat.uat:id/nestedScroll']//android.widget.LinearLayout"));
		
		  System.out.println(list.size()); for(int i=0;i<list.size();i++) {
		  System.out.println(list.get(i).getText()); }
		 
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
