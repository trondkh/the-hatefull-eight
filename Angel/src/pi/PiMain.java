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
import java.util.Arrays;


public class PiMain {
	
	String licencePlate = "CD49873";
	Packet packet = new Packet(true, 63.415763, 10.406500, true, false, licencePlate);
	int teller = 0;
	private Random random;
	
	List<Double> latitude = Arrays.asList();
	List<Double> longditude = Arrays.asList();
	
	
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
	
	public static void main(String[] args) {
		PiMain p = new PiMain();
//		p.readShit();
		p.loop();
	}

	ServerSocket serverSocket;
	Socket socket;
	
	public void loop() {
		while(true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driveCar();
			SendData();
			Packet packet = RecvData();
			testPrintPacket(packet);
		}
	}
	
	
	public void driveCar() {
		packet = new Packet(true, latitude.get(teller), longditude.get(teller), randomAccident(), randomAccident(), licencePlate);
		if(teller < latitude.size()){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packet;
	}
	
	public void testPrintPacket(Packet packet) {
		String str = "Airbags: " + packet.getAirbags() + " Slippness: " + packet.getSlips();
		str+= " and current road condition: " + packet.getRoadCondition() + "\n";
		System.out.println(str);
	}

    public boolean randomAccident() {
        random = new Random();
    }

    public boolean getRandomBoolean() {
        return random.nextBoolean();
    }
}
