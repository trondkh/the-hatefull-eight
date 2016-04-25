package pi;

import java.io.PrintWriter;
import com.fazecast.jSerialComm.SerialPort;

public class ArduinoCom {	
		
		static SerialPort chosenPort;

		public static void main(String[] args) {
			
			// populate the drop-down box
			SerialPort[] portNames = SerialPort.getCommPorts();
			for(int i = 0; i < portNames.length; i++)
				System.out.println(portNames[i].getSystemPortName());
			
			chosenPort = SerialPort.getCommPort(portNames[0].getSystemPortName());
			chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

			if(chosenPort.openPort()) {
				// create a new thread for sending data to the arduino
				Thread thread = new Thread(){
					@Override public void run() {
						// wait after connecting, so the bootloader can finish
						try {Thread.sleep(100); } catch(Exception e) {}

						// enter an infinite loop that sends text to the arduino
						PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
						while(true) {
							output.print("Welcome to Angel");
							output.print("Snows in hell   ");
							output.flush();
							try {Thread.sleep(100); } catch(Exception e) {}
						}
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
