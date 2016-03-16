package server;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SYSTEST_ServerInterface {
	
	ServerInterface si;
	
	@Before
	public void setUp()
	{
		si = new ServerInterface();
		si.main.init();
	}

	@Test
	public void addCarSlippage()
	{
		// 
		double lat = 58.031774; 
		double lon = 7.452886;
		si.carReportsSlippage("ABC", lat, lon);
	}
}
