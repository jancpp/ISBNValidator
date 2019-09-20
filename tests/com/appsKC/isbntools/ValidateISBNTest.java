package com.appsKC.isbntools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidateISBNTest {

	@Test
	void checkAValid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0486821951");
		assertTrue(result, "first value");
		result = validator.checkISBN("1503214958");
		assertTrue(result, "second value");
	}
	
	@Test
	void checkAValid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9781503214958");
		assertTrue(result, "first value");
		result = validator.checkISBN("9781681776149");
		assertTrue(result, "second value");
	}
	
	@Test
	void checkAnInvalid10DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("0486821952");
		assertFalse(result);
	}
	
	@Test
	void checkAnInvalid13DigitISBN() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("9781681776148");
		assertFalse(result);
	}
	
	@Test
	void tenDigitISBNNumbersEndingInXAreValid() {
		ValidateISBN validator = new ValidateISBN();
		boolean result = validator.checkISBN("012000030X");
		assertTrue(result);
	}
	
	@Test
	void nineDigitISBsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		
		assertThrows(NumberFormatException.class, () -> {
			validator.checkISBN("123456789");
		});
	}
	
	@Test
	void nonNumericISBsAreNotAllowed() {
		ValidateISBN validator = new ValidateISBN();
		
		assertThrows(NumberFormatException.class, () -> {
			validator.checkISBN("helloworld");
		});
	}

}
