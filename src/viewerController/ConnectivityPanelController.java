package viewerController;

import communication.InfoPaket;
import application.Main;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ConnectivityPanelController {

	public CheckBox checkActiv;
	public HBox panel;
	public TextField channelSign;
	public Label channelName;
	
	boolean isselected = false;
	InfoPaket infoPaket;
	
	
	
	public ConnectivityPanelController() {
	}
	
	
	public void setInfoPaket(InfoPaket infoPaket){
		this.infoPaket = infoPaket;

		channelName.setText(infoPaket.getName());
		channelSign.setText(infoPaket.getSign() + "");
	}
	
	
	public void setActivSelected() {
			checkActiv.setSelected(infoPaket.isActiv());
	}
	
	
	public void onMouseClicked(){
		if(!isselected){
			Main.controller.selectedChannels.add(infoPaket);
			panel.setStyle("-fx-background-color: "+ infoPaket.getWebColor() + ";");
			isselected = true;
			
		}else {
			Main.controller.selectedChannels.remove(infoPaket);
			panel.setStyle("-fx-background-color: transparent;");
			isselected = false;
		}
	}
}
