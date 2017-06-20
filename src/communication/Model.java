package communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.fazecast.jSerialComm.SerialPort;

import application.Main;
import javafx.scene.paint.Color;


public class Model {

	final int BAUDRATE = 115200;
	SerialPort serialPort;
	public final String COLOR_BLACK = "#000000";
	public final String COLOR_SEND = "#8f867b";
	public Random rand = new Random();
	
	ArrayList<Color> comColors = new ArrayList<Color>();
	int colorUsed = -1;
	
	public Com com;
	public ArrayList<InfoPaket> infoPakets = new ArrayList<InfoPaket>();
	
	
	void setColors(){
		comColors.add(Color.web("#EE3A8C"));
		comColors.add(Color.web("#B23AEE"));
		comColors.add(Color.web("#7A67EE"));
		comColors.add(Color.web("#1C86EE"));
		comColors.add(Color.web("#B2DFEE"));
		comColors.add(Color.web("#00CD66"));
		comColors.add(Color.web("#BCEE68"));
		comColors.add(Color.web("#EEEE00"));
		comColors.add(Color.web("#EEAD0E"));
		comColors.add(Color.web("#EE3B3B"));
	}
	
	/*
	public void readPorts() {
		BufferedReader in;
		String line;
		try {
			in = new BufferedReader(new FileReader(PATH));
			
		
			while((line = in.readLine()) != null)
			{
				addInfoPaket(line, in.readLine().charAt(0));
						
			}
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/


	public void addNewInfoPaket(String name, char sign){
		addInfoPaket(name, sign);
		Main.data.addNewInfoPaket(name,sign);
		/*
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(PATH, true));
			output.newLine();
			output.write(name);
			output.newLine();
			output.write(sign);
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}
	
	public InfoPaket addInfoPaket(String name, char sign){
		InfoPaket paket;
		
		new Thread(
		paket = new InfoPaket(name , sign)
		).start();
		
		infoPakets.add(paket);
		Main.controller.addConnectivityPanel(paket);
		return paket;
	}

	
	public InfoPaket getInfoPaketBySign(char sign){
		for(InfoPaket infoPaket : infoPakets){
			if(infoPaket.getSign() == sign){
				return infoPaket;
			}
		}
		return null;
	}

	
	public ArrayList<String> getPortNames(){
		ArrayList<String> names = new ArrayList<String>();;
		for(SerialPort port : SerialPort.getCommPorts()){
			names.add(port.getSystemPortName());
		}
		return names;
	}
	
	
	public String getWebColor(){
		if(colorUsed < comColors.size() - 1){
			colorUsed++;
		}else{
			colorUsed = 0;
		}
        return String.format( "#%02X%02X%02X",
                (int)( comColors.get(colorUsed).getRed() * 255 ),
                (int)( comColors.get(colorUsed).getGreen() * 255 ),
                (int)( comColors.get(colorUsed).getBlue() * 255 ) );
	}

	
	public SerialPort[] getPorts(){
		return SerialPort.getCommPorts();
	}

	
	public void connectCom(int i) {
		serialPort = getPorts()[i];
		if(serialPort.openPort()){
			Main.controller.writeLog("SYSTEM: Port opened successfully.", COLOR_BLACK);
		}else {
			Main.controller.writeLog("SYSTEM: Unable to open the port.", COLOR_BLACK);
			//disconnect();
			return;
		}
		
		serialPort.setBaudRate(BAUDRATE);
		serialPort.openPort();
		serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
				
		com = new Com(serialPort);
		com.start();

	}
	
	
	public Model(){

		infoPakets = Main.data.getAllInfoPakets();
		setColors();
	}
}
	
	
	
	
	
