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


public class PiMain {
	
	int licencePlate = 1;
	String serverIP;
	
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
		PiMain p = new PiMain(args);
//		p.readShit();
		p.testloop();
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
	}
	
	public void testloop() 
	{
		while(true)
		{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			testSendData();
			Packet packet = testRecvData();
			testPrintPacket(packet);
		}
	}
	public void testSendData()
	{
		this.licencePlate++;
		
		Packet packet = new Packet(true, 63.415763, 10.406500, true, false, Integer.toString(licencePlate));
		try
		{
			socket = new Socket(serverIP,6666);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(packet);
			System.out.println("Sending packet");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Packet testRecvData()
	{
		Packet packet = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			packet = (Packet)ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packet;
	}
	
	public void testPrintPacket(Packet packet)
	{
		String str = "Airbags: " + packet.getAirbags() + " Slippness: " + packet.getSlips();
		str+= " and current road condition: " + packet.getRoadCondition() + "\n";
		System.out.println(str);
	}

}
