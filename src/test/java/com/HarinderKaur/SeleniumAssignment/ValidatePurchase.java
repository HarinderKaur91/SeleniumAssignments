
package com.HarinderKaur.SeleniumAssignment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ValidatePurchase {
	WebDriver wd;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\kaurh\\OneDrive\\Documents\\Drivers\\chromedriver.exe");
		wd = new ChromeDriver();
		wd.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
		wd.manage().window().maximize();
	}

	@Test

	public void validatePurchase() {
		WebElement emailInputField = wd.findElement(By.cssSelector("div.form-group:first-of-type input"));
		WebElement passwordInputField = wd.findElement(By.cssSelector("div.form-group:last-of-type input"));
		WebElement loginBtn = wd.findElement(By.cssSelector("input[type='submit']"));

		emailInputField.sendKeys("iron.man@gmail.com");
		passwordInputField.sendKeys("avengers.com");
		loginBtn.click();

		SoftAssert sfAssert = new SoftAssert();
		sfAssert.assertEquals(wd.getTitle(), "My Account", "User is not signed in");

		WebElement laptopAndNotebooks = wd
				.findElement(By.cssSelector("ul.nav.navbar-nav li.dropdown:nth-of-type(2) a.dropdown-toggle"));
		laptopAndNotebooks.click();
		sleep();

		WebElement showAllLaptops = wd.findElement(By.cssSelector("ul li.dropdown:nth-of-type(2) a.see-all"));
		showAllLaptops.click();
		sleep();

		sfAssert.assertEquals(wd.getTitle(), "Laptops & Notebooks");
		WebElement hpLaptop = wd.findElement(By.cssSelector(
				"div[class=\"product-layout product-grid col-lg-4 col-md-4 col-sm-6 col-xs-12\"]:first-of-type div.caption a"));
		hpLaptop.click();
		sleep();

		WebElement addToCart = wd.findElement(By.cssSelector("#button-cart:last-of-type"));
		addToCart.click();
		sleep();

		WebElement cartTotal = wd.findElement(By.cssSelector("#cart-total"));
		sfAssert.assertEquals(cartTotal.getText(), "1 item(s) - $100.00", "Item not added to the cart");
		sleep();
		cartTotal.click();

		WebElement checkOut = wd.findElement(By.cssSelector("p.text-right a:last-of-type"));
		checkOut.click();
		System.out.println("Checkout done");
		sleep();

		WebElement radioButtonForAddress = wd.findElement(By.cssSelector("input[value='new']"));
		radioButtonForAddress.click();
		sleep();
		
		WebElement firstName = wd.findElement(By.cssSelector("input[name='firstname']"));
		WebElement lastName = wd.findElement(By.cssSelector("input[name='lastname']"));
		WebElement address = wd.findElement(By.cssSelector("input[name='address_1']"));
		WebElement city = wd.findElement(By.cssSelector("input[name='city']"));
		WebElement postCode = wd.findElement(By.cssSelector("input[name='postcode']"));
		firstName.sendKeys("Harinder");
		lastName.sendKeys("Kaur");
		address.sendKeys("Alberta");
		city.sendKeys("Edmonton");
		postCode.sendKeys("T6T0Z1");
		selectElementbyVisibleText(wd.findElement(By.cssSelector("select[name='country_id']")), "Canada");
		sleep();

		selectElementbyVisibleText(wd.findElement(By.cssSelector("select[name='zone_id']")), "Nunavut");
		sleep();

		wd.findElement(By.cssSelector("input[value='Continue']")).click();
		WebElement radiobtnForExistingAddress = wd.findElement(By.cssSelector("input[value='existing']"));
		radiobtnForExistingAddress.click();

		sfAssert.assertTrue(radiobtnForExistingAddress.isSelected());
		sleep();

		WebElement continueBtnShipping = wd.findElement(By.cssSelector("input[id='button-shipping-address']"));
		continueBtnShipping.click();
		sleep();

		WebElement radioBtnFlatShippingRate = wd.findElement(By.cssSelector("input[name='shipping_method']"));
		radioBtnFlatShippingRate.click();

		sfAssert.assertTrue(radioBtnFlatShippingRate.isSelected());
		sleep();

		WebElement textArea = wd.findElement(By.cssSelector("textarea[name='comment']"));
		textArea.sendKeys("Expecting my laptop to be delivered soon");
		sleep();

		wd.findElement(By.cssSelector("input[id='button-shipping-method']")).click();// Continue Button
		sleep();

		WebElement codRadioBtn = wd.findElement(By.cssSelector("input[value='cod']"));
		if (!codRadioBtn.isSelected()) {
			codRadioBtn.click();
		}
		sleep();

		WebElement termsAndConditionsCheckBox = wd.findElement(By.cssSelector("input[name='agree']"));// Terms and
																										// Conditions
		boolean isCheckBoxSelected = termsAndConditionsCheckBox.isSelected();
		if (!isCheckBoxSelected) {
			termsAndConditionsCheckBox.click();
		}
		sleep();

		wd.findElement(By.cssSelector("input[id='button-payment-method']")).click();// Continue Button
		sleep();

		WebElement productName = wd.findElement(By.cssSelector("tbody td:nth-of-type(2) a"));
		WebElement productQuantity = wd.findElement(By.cssSelector("tbody td:nth-of-type(3) "));

		sfAssert.assertEquals(productName.getText(), "HP LP3065", "Product name not validated");
		sfAssert.assertEquals(productQuantity.getText(), "1", "Quantity is not 1");

		wd.findElement(By.cssSelector("input[id='button-confirm']")).click();// Confirm Button
		sleep();

		WebElement orderPlacedMsg = wd.findElement(By.cssSelector("#content h1"));
		sfAssert.assertEquals(orderPlacedMsg.getText(), "Your order has been placed!", "Order not placed");

	}

	public void sleep() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selectElementbyVisibleText(WebElement element, String text) {
		Select sc = new Select(element);
		sc.selectByVisibleText(text);
	}

	@AfterMethod
	public void tearDown() {
		// wd.close();
	}

}
