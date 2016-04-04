package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener {

	ServerSocket serverSocket;
	Socket socket;
	
	public Listener()
	{
		
	}
	
	public void run()
	{
	}
	
	public CarData getCarData()
	{
		CarData cd = null;
		try
		{
			serverSocket = new ServerSocket(6666);
			socket = serverSocket.accept();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			cd = (CarData)ois.readObject();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return cd;
	}
	
	public void sendCarData(CarData car)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(car);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
