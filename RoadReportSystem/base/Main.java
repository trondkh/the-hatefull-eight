package base;

public class Main {

	
	public static void main(String[] args) {
		//CoordinateHandler location = new CoordinateHandler(63.4157977, 10.4018315);
		CoordinateHandler location;
		if(args.length == 2) {
			location = new CoordinateHandler(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
		} else {
			location = new CoordinateHandler(63.760489, 10.070044);
		}
		
		XMLFile yr = new XMLFile(true, location.generateYrURL());
		
		
		XMLHandler xmlHandler = new XMLHandler(yr, null, location);
		xmlHandler.exampleRequest();
	}
	
}
