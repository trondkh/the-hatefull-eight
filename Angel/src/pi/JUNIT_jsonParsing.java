package pi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JUNIT_jsonParsing {

	JSONReadFromFile o;
	
	@Before
	public void setUp()
	{
		o = new JSONReadFromFile();
	}
	
	@Test
	public void parsingTest()
	{
		CarData c = new CarData(123.123, new CarDataValueHolder((Object)'a'),"testing");
		String json = "{\"name\":\"test_success\",\"value\":0,\"timestamp\":1364309660.002000}";
		c = o.jsonParser(json);
		assertEquals(c.time,(double)1364309660.002000,0.0001);
//		assertEquals((long)c.value.data,'a');
		assertEquals(c.variable,"test_success");
	}

}
