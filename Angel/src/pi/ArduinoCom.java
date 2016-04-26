package pi;

import java.io.PrintWriter;
import com.fazecast.jSerialComm.SerialPort;

public class ArduinoCom {	
		
		SerialPort chosenPort;
		SerialPort[] portNames;
		boolean connectionOk = false;

		public ArduinoCom()
		{
			portNames = SerialPort.getCommPorts();
			if(portNames.length>=1)
			{
				chosenPort = SerialPort.getCommPort(portNames[0].getSystemPortName());
				init();
			}
		}
		
		public ArduinoCom(String comPort)
		{
			chosenPort = SerialPort.getCommPort(comPort);
			init();
		}
		
		public void init()
		{
			chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
			connectionOk = true;
		}
		
		public void sendString(String msg)
		{
			if(connectionOk)
			{
				if(chosenPort.openPort())
				{
					Thread thread = new Thread()
					{
						@Override 
						public void run()
						{
							try {Thread.sleep(100); } catch(Exception e) {}
							
							PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
							output.print(msg);
							output.flush();
						}
					};
					thread.start();
				}
				else 
				{
					chosenPort.closePort();
				}
			}
		}
		
		
		
		public static void main(String[] args) {
			
			// populate the drop-down box
			SerialPort[] portNames = SerialPort.getCommPorts();
			for(int i = 0; i < portNames.length; i++)
				System.out.println(portNames[i].getSystemPortName());
		}
	
}
