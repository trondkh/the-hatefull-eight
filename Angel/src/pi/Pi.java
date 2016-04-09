package pi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import Packets.Packet;


public class Pi {
	
	int licencePlate = 1;
	
	void readShit()
	{
		ArrayList<String> allTheLines = new ArrayList<String>();
		Reader r;
		try
		{
			r = new FileReader("./pi/pi/uptown-west.json");
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
		Pi p = new Pi();
//		p.readShit();
		p.testloop();
	}

	ServerSocket serverSocket;
	Socket socket;
	
	public void testloop() 
	{
		while(true)
		{
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			testSendData();
		}
	}
	public void testSendData()
	{
		this.licencePlate++;
		
		Packet packet = new Packet(true, 0, 0, true, false, Integer.toString(licencePlate));
		try
		{
			socket = new Socket("localhost",6666);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(packet);
			System.out.println("Sending packet");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
