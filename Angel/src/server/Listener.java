package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import Packets.Packet;

public class Listener {

	ServerSocket serverSocket;
	Socket socket;
	
	public Listener()
	{
		
	}
	
	public void run()
	{
		
	}
	
	public Packet getCarData()
	{
		Packet packet = null;
		try
		{
			serverSocket = new ServerSocket(6666);
			serverSocket.setReuseAddress(true);
			socket = serverSocket.accept();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			packet = (Packet)ois.readObject();
//			ois.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return packet;
	}
	
	public void sendCarData(Packet packet)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(packet);
			System.out.println("Sending cardata to pi...");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try {
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
