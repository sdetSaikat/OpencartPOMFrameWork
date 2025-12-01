package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.pages.SearchResultsPage;

public class SearchResultsTest extends BaseTest {
	
	
	@BeforeClass
	public void SearchResultSetUp() {
		
		accPage =login.doAccountLogIn(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@Test
	public void searchResultsCountTest() {
		SearchResultsPage resultsPage = accPage.doSearchProduct("macbook");
		int resultsCount = resultsPage.getsearchResultsCount();
		Assert.assertEquals(resultsCount, 3);
	}
	
	
	
	@Test(enabled = true,description = "This is to test the list of products available for each searchkey")
	public void searchedProductListsTest() {
		
		List<String> expectedListOfProducts = List.of("MacBook","MacBook Air","MacBook Pro");
		SearchResultsPage resultsPage = accPage.doSearchProduct("macbook");
		List<String> actualproductNames = resultsPage.getProductNames();
		Assert.assertEquals(expectedListOfProducts, actualproductNames);
	}

}
