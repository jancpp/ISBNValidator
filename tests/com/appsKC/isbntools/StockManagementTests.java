package com.appsKC.isbntools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StockManagementTests {

	@Test
	void testCanGetCorrectLocatorCode() {
		ExternalISBNDataService testService = new ExternalISBNDataService() {
			
			@Override
			public Book lookup(String isbn) {
				return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
			}
		};
		
		StockManager stockManager = new StockManager();
		stockManager.setService(testService);
		
		String isbn = "014177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}

}
