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
	
	String serverIP;
	String licencePlate = "CD49873";
	Packet packet = new Packet(true, 63.415763, 10.406500, true, false, licencePlate);
	int teller = 0;
	private Random random;
	ArduinoCom arduino;
	
	ArrayList<Double> latitude = new ArrayList<Double>();
	ArrayList<Double> longditude = new ArrayList<Double>();
	
	
	public static void main(String[] args) {
		PiMain p = new PiMain(args);
//		p.readShit();
		p.loop();
	}

	ServerSocket serverSocket;
	Socket socket;
	
	
	
	public PiMain(String[] args)
	{
		if(args.length==1)
		{
			this.serverIP = args[0];
		}
		else
		{
			this.serverIP = "localhost";
//			this.serverIP = "192.168.1.102";
		}
		
		DemoCoordinateGenerator dcg = new DemoCoordinateGenerator();
		this.latitude = dcg.getLatitude();
		this.longditude = dcg.getLongditude();
		this.random = new Random();
		arduino = new ArduinoCom();
	}
	
	public void loop() {
		while(true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			driveCar();
			SendData();
			Packet packet = RecvData();
			testPrintPacket(packet);
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
	
	public void SendData() {
		try{
			socket = new Socket("localhost",6666);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(packet);
			System.out.println("Sending packet");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Packet RecvData() {
		Packet packet = null;
		try{
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			packet = (Packet)ois.readObject();
		} catch (ClassNotFoundException | IOException e){
			e.printStackTrace();
		}
		return packet;
	}
	
	public void testPrintPacket(Packet packet) {
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

	void readShit()
	{
		ArrayList<String> allTheLines = new ArrayList<String>();
		Reader r;
		try
		{
			r = new FileReader("./Angel/src/pi/uptown-west.json");
			BufferedReader br = new BufferedReader(r);
			String str;
			while((str = br.readLine())!=null)
			{
				allTheLines.add(str);
			}
			br.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String[] strings = new String[3];
		String str;
		for(int i=0;i<100;i++)
		{
			str = Integer.toString(i) + " ";
			str += allTheLines.get(i);
			System.out.println(str);
			strings = str.split(","); 
			str += strings[0].split(":")[1];
			str += " at "; 
			str += strings[1].split(":")[1];
//			System.out.println(str);
		}
	}
}
