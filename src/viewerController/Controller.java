package viewerController;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

import application.Main;
import communication.InfoPaket;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class Controller implements Initializable {

	public ArrayList<InfoPaket> selectedChannels = new ArrayList<InfoPaket>();
	public ArrayList<ConnectivityPanelController> connectivityPanels = new ArrayList<ConnectivityPanelController>();
	public MotorDataController[][] motorDataControllers = new MotorDataController[2][2];


	public TextField messageText;
	public ToggleButton buttonAutoScroll;
	public ToggleButton buttonLogger;
	public ToggleButton buttonConnect;
	public TextFlow console;
	public ChoiceBox<String> comList;
	public VBox chanelTree;
	public ScrollPane consoleScroll;
	public GridPane gridPaneMotorOverview;
	public StackPane gpsPane;
	public StackPane PWMPane;
	public StackPane settingPane;
	public TextField consoleLines;
	private Stage stagePopup;
	private ChannelPopupController channelPopupController;
	private GpsDataController gpsDataController;
	private int consoleMaxLines = 500;
	
	boolean autoscroll = false;
	

	private void setUpChannelPopup(){
        stagePopup = new Stage(StageStyle.UNDECORATED);
        stagePopup.setAlwaysOnTop(true);
        stagePopup.initModality(Modality.APPLICATION_MODAL);
        stagePopup.setResizable(false);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ChannelPopup.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stagePopup.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

        channelPopupController = fxmlLoader.getController();
        channelPopupController.setStage(stagePopup);
        channelPopupController.setChannelList();
    }

	public Controller(){
        setUpChannelPopup();

		Timer timerActiv = new Timer();
		timerActiv.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  for (ConnectivityPanelController cony: connectivityPanels){
					    cony.setActivSelected();
					}
			  }
			}, 1, 500);
	}
	
	public void onNewButton(){
		
		
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add");
		dialog.setHeaderText("Add a new Channel");

		// Set the icon (must be included in the project).
//		dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Add", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField name = new TextField();
		name.setPromptText("Name");
		TextField sign = new TextField();
		sign.setPromptText("Sign (only one char)");

		grid.add(new Label("Name:"), 0, 0);
		grid.add(name, 1, 0);
		grid.add(new Label("Sign:"), 0, 1);
		grid.add(sign, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		name.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> name.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
			
			if (dialogButton == loginButtonType && sign.getText().length() == 1) {
		        return new Pair<>(name.getText(), sign.getText());
		    }
			if(dialogButton == ButtonType.CANCEL){
				 return null;
			}
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("False Input Format");
			alert.setContentText("Sign must be ONE Char!");

			alert.showAndWait();
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
		    Main.model.addNewInfoPaket(usernamePassword.getKey(), usernamePassword.getValue().charAt(0));
		});
	}

	public char openSelectChannel(Double x, Double y){

	    if (stagePopup.isShowing()){
            stagePopup.hide();
        }

        stagePopup.setX(x);
        stagePopup.setY(y);
        stagePopup.showAndWait();


	    return channelPopupController.selectedSign;
	}

	public void onSend(){
		if(messageText.getText().isEmpty() == false){
			Main.model.com.send(messageText.getText());
		}
		messageText.setText("");
	}
	
	public void setAutoscroll(){
		consoleScroll.setVvalue(1.0);
		autoscroll = !autoscroll;
	}

	public void onConnectButton(){
		
		
			if(buttonConnect.isSelected()){
				if(comList.getValue() != null){
				Main.model.connectCom(comList.getItems().indexOf(comList.getValue()));
				}
				
			}else {
				Main.model.com.disconnect();
			}
	}
	
	public void writeLog(String s, String color, InfoPaket paket){
		if(selectedChannels.contains(paket)){
			writeLog(s,color);
		}
	}
	
	public void writeLog(String s, String color){
		Calendar calendar = GregorianCalendar.getInstance();
		Text t = new Text();
		t.setText("\n   [" + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "] " + s );
		t.setStyle("-fx-fill: " + color + ";");

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				while(console.getChildren().size() > consoleMaxLines){
					console.getChildren().remove(0);
				}

				console.getChildren().add(t);
				if(autoscroll){
					consoleScroll.setVvalue(1.0);
				}

			}
		});
		
	}
	
	public void mouseOverComList(){
		String s = comList.getValue();
		comList.getItems().clear();
		for(String name: Main.model.getPortNames()) {
			comList.getItems().add(name);
		}
		comList.setValue(s);
	}

	public void fillGridPane(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MotorData.fxml"));

		try {
			VBox panel = fxmlLoader.load();
			gridPaneMotorOverview.add(panel,0,0);
			motorDataControllers[0][0]  = fxmlLoader.getController();

			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MotorData.fxml"));
			panel = fxmlLoader.load();
			gridPaneMotorOverview.add(panel,0,1);
			motorDataControllers[0][1]  = fxmlLoader.getController();
			motorDataControllers[0][1].name.setText("Motor 2");

			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MotorData.fxml"));
			panel = fxmlLoader.load();
			gridPaneMotorOverview.add(panel,1,0);
			motorDataControllers[1][0]  = fxmlLoader.getController();
			motorDataControllers[1][0].name.setText("Motor 3");

			fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MotorData.fxml"));
			panel = fxmlLoader.load();
			gridPaneMotorOverview.add(panel,1,1);
			motorDataControllers[1][1]  = fxmlLoader.getController();
			motorDataControllers[1][1].name.setText("Motor 4");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addConnectivityPanel(InfoPaket infoPaket){
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ConnectivityPanel.fxml"));
		
		try {	
			HBox panel = fxmlLoader.load();
			chanelTree.getChildren().add(panel);

		} catch (IOException e) {
			e.printStackTrace();
		}   

		channelPopupController.addInfoPaket(infoPaket);
		ConnectivityPanelController controller = fxmlLoader.getController();
		controller.setInfoPaket(infoPaket);
		connectivityPanels.add(controller);
	}

	public void setConsoleLines(){
		if(!consoleLines.getText().isEmpty() && consoleLines.getText().matches("[0-9]+"))
		consoleMaxLines = Integer.valueOf(consoleLines.getText());
	}


	public void onDelete(){
		if(selectedChannels.isEmpty())return;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText("Delete Channels");
		alert.setContentText("Are you sure about deleting this Channel? \n  \n ");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			for (InfoPaket paket : selectedChannels) {
				int i = Main.model.infoPakets.indexOf(paket);
				chanelTree.getChildren().remove(i);
				channelPopupController.removeInfoPaket(i);
				connectivityPanels.remove(i);
				Main.data.removeInfoPaket(i);
				Main.model.infoPakets.remove(i);
				paket.alive = false;
			}
			selectedChannels.clear();
		}
	}


	public void setUpThirdTab(){

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/GpsData.fxml"));
		try {
			VBox box = fxmlLoader.load();
			gpsPane.getChildren().add(box);
		} catch (IOException e) {
			e.printStackTrace();
		}

		gpsDataController  = fxmlLoader.getController();

		//gpsDataController  = fxmlLoader.getController();
		fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Settings.fxml"));
		try {
			AnchorPane pane = fxmlLoader.load();
			settingPane.getChildren().add(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}

		fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PWMPanel.fxml"));
		try {
			VBox box = fxmlLoader.load();
			PWMPane.getChildren().add(box);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void onLogger(){
		if(buttonLogger.isArmed()){
			Main.logger.newData();
			return;
		}
		Main.logger.stopLogger();
	}

	public void comSelected(){
		buttonConnect.setDisable(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		for(InfoPaket infoPaket : Main.model.infoPakets){
			addConnectivityPanel(infoPaket);
			new Thread(infoPaket).start();
		}

		fillGridPane();
		setUpThirdTab();
		consoleLines.setText(String.valueOf(consoleMaxLines));
	}
}

