package pi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Packets.Packet;
import java.util.Random;


public class PiMain {
	
	String licencePlate = "CD49873";
	Packet packet = new Packet(true, 63.415763, 10.406500, true, false, licencePlate);
	int teller = 0;
	private Random random;
	ArduinoCom arduino;
	TcpCom serverCom;
	
	ArrayList<Double> latitude = new ArrayList<Double>();
	ArrayList<Double> longditude = new ArrayList<Double>();
	
	
	public static void main(String[] args) {
		PiMain p = new PiMain(args);
		p.loop();
	}

	public PiMain(String[] args)
	{
		DemoCoordinateGenerator dcg = new DemoCoordinateGenerator();
		this.latitude = dcg.getLatitude();
		this.longditude = dcg.getLongditude();
		dcg.calculateCoordinates(10, this.latitude, this.longditude);
		this.random = new Random();
		arduino = new ArduinoCom();
		serverCom = new TcpCom(args);
		Random random = new Random();
		Integer plateNumber = new Integer(random.nextInt(99999));
		this.licencePlate = "XY" + plateNumber.toString(); 
	}
	
	public void loop() {
		while(true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			driveCar();
			serverCom.sendPacket(packet);
			Packet packet = serverCom.recievePacket();
			printPacket(packet);
		}
	}
	public void driveCar() {
		packet = new Packet(true, latitude.get(teller), longditude.get(teller), getRandomBoolean(), getRandomBoolean(), licencePlate);
		if(teller < (latitude.size()-1)){
			teller ++;
		}
		else{
			teller = 0;
		}
	}
	
	public void printPacket(Packet packet) {
		String str = "Airbags: " + packet.getAirbags() + " Slippness: " + packet.getSlips();
		str+= " and current road condition: " + packet.getRoadCondition() + "\n";
		System.out.println(str);
		printToArduino(packet);
	}
	
	public void printToArduino(Packet packet)
	{
		String allOk = "No problems     Road is clear   ";
		String accident = "Accident ahead  Drive carefully";
		String accidents = "Several accidents";
		if(packet.getAirbags()<=0)
		{
			arduino.sendString(allOk);
		}
		else if(packet.getAirbags()==1)
		{
			arduino.sendString(accident);
		}
		else
		{
			arduino.sendString(accidents);
		}
			
	}

    public boolean getRandomBoolean() {
        return random.nextBoolean();
    }

	
}
