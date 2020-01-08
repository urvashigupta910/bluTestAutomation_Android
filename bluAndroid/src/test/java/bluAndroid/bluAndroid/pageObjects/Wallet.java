package bluAndroid.bluAndroid.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class Wallet {
	private static AppiumDriver<WebElement> driver;
		public Wallet()
		{
			
		}
		public Wallet(AppiumDriver<WebElement> driver)
		{
			this.driver=driver;
		}
		public void clickOnWalletIcon()
		{
			WebElement wallet= driver.findElement(By.id("sg.com.blu.android.uat:id/walletButton"));
			wallet.click();
		}
		public void clickOnTopUp()
		{
			WebElement topUp= driver.findElement(By.id("sg.com.blu.android.uat:id/topUpWalletButtonContainer"));
			topUp.click();
		}
		public void clickOnPaymentMethods()
		{
			WebElement paymentMethods= driver.findElement(By.id("sg.com.blu.android.uat:id/paymentMethodsButtonContainer"));
			paymentMethods.click();
		}
		public WebElement recentActivity()
		{
			WebElement recentActivity= driver.findElement(By.id("sg.com.blu.android.uat:id/recentActivityTextView"));
			return recentActivity;
		}
		public void clickOnSeeMore()
		{
			WebElement seeMore= driver.findElement(By.id("sg.com.blu.android.uat:id/seeMoreButton"));
			seeMore.click();
		}
		public WebElement clickOnSearchBox()
		{
			WebElement searchBox= driver.findElement(By.id("sg.com.blu.android.uat:id/searchEditText"));
			searchBox.click();
			return searchBox;
		}
		public void clickOnTransactions()
		{
			WebElement a=driver.findElement(By.id("android.widget.RelativeLayout[1]"));
			a.click();
		}
		public void transactionDetails()
		{
			WebElement list=driver.findElement(By.xpath("//android.widget.LinearLayout//android.widget.TextView[1]"));
			list.click();
		/*
		 * List<WebElement>
		 * list=driver.findElements(By.id("//android.widget.LinearLayout")); for(int
		 * i=0;i<list.size();i++) { System.out.println(list.get(i).getText()); }
		 */
		}
		public void selectValues()
		{
			WebElement topUpvalue=driver.findElement(By.xpath("//android.widget.LinearLayout[@resource-id='sg.com.blu.android.uat:id/topUpVolumeContainer']//android.widget.Button[1]"));
			topUpvalue.click();
		}
		public void clickOnSubmit()
		{
			WebElement submit=driver.findElement(By.id("sg.com.blu.android.uat:id/submitButton"));
			submit.click();
		}
}
