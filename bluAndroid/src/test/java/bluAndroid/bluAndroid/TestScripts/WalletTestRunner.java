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
		String walletBalance=w.walletAmount().getText();
		float f = Float.parseFloat(walletBalance);
		float f1=f+5;
		String floatingpoint = String.valueOf(f1);
		w.clickOnTopUp();
		w.selectValues();
		w.selectPreferredMethod();
		w.clickOnSubmit();
		Assert.assertTrue(w.cardTextView().isDisplayed());
		Assert.assertTrue(w.cardImageView().isDisplayed());
		//Assert.assertEquals(w.dateTime().getText(), CommonUtil.getCorrectTimeDate());
		System.out.println(w.topUpSuccessMsg().getText());
		Assert.assertEquals(w.topUpSuccessMsg().getText(), "Top Up Successful");
		Assert.assertTrue(w.dateTime().isDisplayed());
		Assert.assertTrue(w.tickImageView().isDisplayed());
		Assert.assertEquals(w.topUpAmount().getText(), "5.00");
		w.clickOnclose();
		String updatedValue=w.walletAmount().getText();
		//float f1 = Float.parseFloat(walletBalance);
		//float updatedValue1=f+5;
		Assert.assertEquals(updatedValue, floatingpoint);
		System.out.println(walletBalance+"  "+updatedValue);
	}
	@Test
	public void tc02_paymentMethods()
	{
		System.out.println("tc02_payemntMethods");
		extentTest = extentReports.createTest("tc02_payemntMethods");
		w.clickOnWalletIcon();
		w.clickOnPaymentMethods();
		w.clickOnAddPaymentMethod();
		//w.enterCardNumber().click();
		w.enterCardNumber().sendKeys("42424242424242420420");
		//w.enterExpiry().sendKeys("0420");
		w.enterCVC().click();
		w.enterCVC().sendKeys("123");
		w.clickOnConfirm();
		Assert.assertTrue(w.cardTextView1().isDisplayed());
		Assert.assertEquals(w.cardTextView1().getText(), "4242 (expires 04/20)");
	}
	@Test
	public void tc02_removePaymentMethods()
	{
		System.out.println("tc02_payemntMethods");
		extentTest = extentReports.createTest("tc02_payemntMethods");
		w.clickOnWalletIcon();
		w.clickOnPaymentMethods();
		w.clickOnRemovePaymentMethod();
		Assert.assertTrue(w.removePaymentmethodTitle().isDisplayed());
		w.clickOnDeleteCardButton();
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
		List<WebElement> list1=driver.findElements(By.xpath("//android.widget.ScrollView[@resource-id='sg.com.blu.android.uat:id/nestedScroll']//android.widget.LinearLayout//android.widget.LinearLayout//android.widget.TextView[1]"));
		
		System.out.println(list1.size());
		for (int i = 0; i < list1.size(); i++) {
			System.out.println(list1.get(i).getText());
			if(i==0)
				Assert.assertEquals(list1.get(0).getText(), "Transaction ID");
			if(i==1)
				Assert.assertEquals(list1.get(1).getText(), "Date");
			if(i==2)
				Assert.assertEquals(list1.get(2).getText(), "Activity");
			if(i==3)
				Assert.assertEquals(list1.get(3).getText(), "Parcel reference");		
		}
		 
	}
	@Test
	public void tc04_sendReceipt()
	{
		System.out.println("tc04_sendReceipt");
		extentTest = extentReports.createTest("tc04_sendReceipt");
		System.out.println("tc03_transactionDetails");
		extentTest = extentReports.createTest("tc03_transactionDetails");
		w.clickOnWalletIcon();
		Assert.assertTrue(w.recentActivity().isDisplayed());
		w.clickOnSeeMore();
		w.clickOnSearchBox().sendKeys("Booking");
		w.transactionDetails();
		w.clickOnSendReceipt();
		Assert.assertTrue(w.sendReceiptMsg().isDisplayed());
		w.clickOnOK();
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
