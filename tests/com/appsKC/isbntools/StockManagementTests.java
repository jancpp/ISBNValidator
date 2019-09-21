package com.appsKC.isbntools;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StockManagementTests {
	
	ExternalISBNDataService testWebService;
	StockManager stockManager;
	
	ExternalISBNDataService testDatabaseService;
	
	@BeforeEach
	public void setup() {
		testWebService = mock(ExternalISBNDataService.class);
		stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		
		testDatabaseService = mock(ExternalISBNDataService.class);
		stockManager.setDatabaseService(testDatabaseService);

	}
	
	@Test
	void testCanGetCorrectLocatorCode() {
		
		when(testWebService.lookup(anyString())).thenReturn(new Book("014177396", "Of Mice And Men", "J. Steinbeck"));
		when(testDatabaseService.lookup(anyString())).thenReturn(null);

		String isbn = "014177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}
	
	@Test
	public void databaseIsUsedIfDataIsPresent() {
		
		when(testDatabaseService.lookup("014177396")).thenReturn(new Book("014177396", "xxx", "xxx"));
		
		String isbn = "014177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		
		verify(testDatabaseService, times(1)).lookup("014177396");
		verify(testWebService, times(0)).lookup(anyString());
	}
	
	@Test
	public void webServiceIsUsedIfDataIsNotPresent() {
		
		when(testDatabaseService.lookup("014177396")).thenReturn(null);
		when(testWebService.lookup("014177396")).thenReturn(new Book("014177396", "xxx", "xxx"));
		
		String isbn = "014177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		
		verify(testDatabaseService, times(1)).lookup("014177396");
		verify(testWebService, times(1)).lookup("014177396");
	}
}
