package myConnectables;

import application.Main;
import communication.Connectable;
import communication.InfoPaket;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;


public class MyLabel extends Label implements Connectable {

	private int id;
	private char sign;


	public MyLabel(){
		super();

		this.setTooltip(new Tooltip("not connected"));
		this.setText("N/A");

		id = Main.data.getIdForLabel();
		if(Main.data.myLabelIsSet(id))setConnection(Main.data.getMyLabelReferenc(id));

		this.setOnMouseClicked( (event )->{
			sign = Main.controller.openSelectChannel(event.getScreenX(),event.getScreenY());
			if(sign != Character.MIN_VALUE){
				setConnection(sign);

			}
		});
	}

	public void update(String s){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				setText(s);
			}
		});
	}

	public InfoPaket setConnection(char sign){
		Main.data.setMyLabelReferences(sign,id);
		this.sign = sign;
		this.getTooltip().setText(Main.model.getInfoPaketBySign(sign).getName() + " " + sign);
		return Connectable.super.setConnection(sign);
	}

	public boolean send(){
		if(sign != Character.MIN_VALUE && Main.model.isNumeric(getText()) && Main.model.com != null ) {
			String s = sign + "";
			int value = Integer.valueOf(getText()) + 100;

			if (value < 100 && value > 9 ){
				s += "0" + value;
			} else if (value < 10 && value != 0 ){
				s += "00" + value;
			} else if(value == 0) {
				s += "000";
			}else{
				s += value;
			}

			Main.model.com.send(s);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public String getValue() {
		if(Main.model.isNumeric(getText()) && Main.model.com != null ) {
			String s = "";
			int value = Integer.valueOf(getText()) + 100;

			if (value < 100 && value > 9 ){
				s += "0" + value;
			} else if (value < 10 && value != 0 ){
				s += "00" + value;
			} else if(value == 0) {
				s += "000";
			}else{
				s += value;
			}

			return s;
		}
		return "";
	}


	@Override
	public Connectable infoPaketDeleted() {
		Main.data.setMyLabelReferences(Character.MIN_VALUE,id);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				getTooltip().setText("not connected");
				//setText("");
			}
		});
		return this;
	}
}
