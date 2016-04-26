package pi;

import Packets.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpCom {
	String serverIP;
	ServerSocket serverSocket;
	Socket socket;
	
	public TcpCom(String[] args)
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
	
	public void sendPacket(Packet packet)
	{
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
	
	public Packet recievePacket()
	{
		Packet packet = null;
		try{
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			packet = (Packet)ois.readObject();
		} catch (ClassNotFoundException | IOException e){
			e.printStackTrace();
		}
		return packet;
	}
	
}
