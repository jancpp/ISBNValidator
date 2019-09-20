package com.appsKC.isbntools;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

class StockManagementTests {

	@Test
	void testCanGetCorrectLocatorCode() {
		ExternalISBNDataService testWebService = new ExternalISBNDataService() {
			
			@Override
			public Book lookup(String isbn) {
				return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
			}
		};
		
		ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
			
			@Override
			public Book lookup(String isbn) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		stockManager.setDatabaseService(testDatabaseService);
		
		String isbn = "014177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}
	
	@Test
	public void databaseIsUsedIfDataIsPresent() {
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		when(databaseService.lookup("014177396")).thenReturn(new Book("014177396", "xxx", "xxx"));
		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);
		
		String isbn = "014177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		
		verify(databaseService, times(1)).lookup("014177396");
		verify(webService, times(0)).lookup(anyString());
	}
	
	@Test
	public void webServiceIsUsedIfDataIsNotPresent() {
		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);
		
		when(databaseService.lookup("014177396")).thenReturn(null);
		when(webService.lookup("014177396")).thenReturn(new Book("014177396", "xxx", "xxx"));

		
		StockManager stockManager = new StockManager();
		stockManager.setWebService(webService);
		stockManager.setDatabaseService(databaseService);
		
		String isbn = "014177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		
		verify(databaseService, times(1)).lookup("014177396");
		verify(webService, times(1)).lookup("014177396");
	}

}
