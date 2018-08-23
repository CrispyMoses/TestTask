package service;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CalculationServiceTest {

	@InjectMocks
	private CalculationService service;

	@Mock
	private JsonRequestService requestService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void calculateDifference() throws Exception {
		Date date = new Date();
		when(requestService.getUsdPrice()).thenReturn("31.5");
		when(requestService.getUsdPrice(date)).thenReturn("10.5487");

		String result = service.calculateDifference(date, "124.5");

		assertEquals("2582.26153425", result);
	}
}