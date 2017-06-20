package communication;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import com.fazecast.jSerialComm.*;

import application.Main;


public class Com extends Thread {
	SerialPort serialPort;
	boolean run = false;
	PrintWriter out;
	InputStream in;
	int time;
	final String COLOR_DEFAULT = "#333399";

	
	Com(SerialPort serialPort){
		this.serialPort = serialPort;
		run = true;
		out = new PrintWriter(serialPort.getOutputStream());
		in = serialPort.getInputStream();
		time = 0;
	}
	
	public void disconnect() {
		run = false;
		try {
			serialPort.closePort();
		} catch (NullPointerException e) {
			e.printStackTrace(System.out);
		} 
		Main.controller.writeLog("SYSTEM: Disconnected", Main.model.COLOR_BLACK);
		Main.controller.buttonConnect.setSelected(false);
	}
	
	public void send(String s){

		Main.controller.writeLog("SEND: " + s, Main.model.COLOR_SEND);
		out.print(s + (char)10);
		out.flush();
	}
	
	public void run(){
		int i;
		StringBuilder text = new StringBuilder();
		
		while(run){
			try {
				
				i = in.read();
				if(i == 10){
					fullMessage(text.toString());
					text.delete(0,text.length());
				}if(i> 31 & i < 200){
					text.append ((char)i);
				}

			} catch (IOException e) {
				e.printStackTrace(System.out);
				disconnect();
			} 
		}
	}
	
	
	void fullMessage(String text){
		if(text.isEmpty()){
			return;
		}
		InfoPaket info = Main.model.getInfoPaketBySign(text.charAt(0));
		if(info != null){
		 info.setInfo(text);
		} else {
			Main.controller.writeLog("DEFAULT: " + text, COLOR_DEFAULT);
		}
	}

}
