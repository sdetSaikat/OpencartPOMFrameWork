package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utilities.CsvUtil;
import com.qa.opencart.utilities.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic No:100-Design Test pages for opencart application")
@Feature("Feature No:52 -  product page Feature")
@Story("Story No:20 - design test cases for Product page")
public class ProductInfoPageTest extends BaseTest {
	
	@BeforeClass
	public void SearchResultSetUp() {
		
		accPage =login.doAccountLogIn(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@DataProvider(name = "excelimageInfo")
	public Object[][] excelUserdata() {
		return ExcelUtil.getTestData("product_image_count");
	}
	
	@DataProvider(name = "csvimageInfo")
	public Object[][] csvProductdata() {
		return CsvUtil.getCSVData("productImageDetails");
	}
	
	@Description("Total image test for each product")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Saikat Maity")
	@Test(dataProvider = "csvimageInfo")
	public void totalImageTest(String searchKey,String productName,String imageCount) {
		int actualImageCount = accPage.doSearchProduct(searchKey).doSelectProduct(productName).getProductImageCount();
		Assert.assertEquals(actualImageCount, Integer.parseInt(imageCount));
	}
	
	@Description("Product Availability check")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Saikat Maity")
	@Test(enabled = true)
	public void productAvailabilityTest() {
		String actualAvailabilityStatus = accPage.doSearchProduct("macbook").doSelectProduct("MacBook").getProductAvailabilityStatus();
		Assert.assertTrue(actualAvailabilityStatus.contains("In Stock"));
	}
	
	
	@DataProvider(name = "productTestData")
	public Object[][] getproductTestData() {
		
		return new Object[][] {
			{"macbook","MacBook","Apple","Product 16","600","In Stock","$602.00","$500.00"},
			{"macbook","MacBook Air","Apple","Product 17","700","Out Of Stock","$1,202.00","$1,000.00"},
			{"macbook","MacBook Pro","Apple","Product 18","800","Out Of Stock","$2,000.00","$2,000.00"},
			{"htc","HTC Touch HD","HTC","Product 1","400","In Stock","$122.00","$100.00"}
		};
	}
	
	@Description("product Meta data validation..")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Saikat Maity")
	@Test(dataProvider = "productTestData")
	public void productMetaDataTest(String searchKey,String ProductName,String brand ,String productCode,String rewards,
										String Availability,String ActualPrice,String excludingTaxPrice) {
		
		Map<String, String> productMetaDetails = accPage.doSearchProduct(searchKey).doSelectProduct(ProductName).getProductMetaDetails();
		
		SoftAssert sf = new SoftAssert();
		sf.assertEquals(productMetaDetails.get("Brand"), brand);
		sf.assertEquals(productMetaDetails.get("Product Code"), productCode);
		sf.assertEquals(productMetaDetails.get("Reward Points"), rewards);
		sf.assertEquals(productMetaDetails.get("Availability"), Availability);
		sf.assertEquals(productMetaDetails.get("ActualPrice"), ActualPrice);
		sf.assertEquals(productMetaDetails.get("WithoutTaxPrice"), excludingTaxPrice);
		
		sf.assertAll();
	}

}


