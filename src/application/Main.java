package application;
	
import communication.Model;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viewerController.Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Queue;


public class Main extends Application {
	
	
	static public Controller controller;
	static public Model model;
	static public Data data;
	static public Logger logger;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FileInputStream fin = new FileInputStream("theData.dat");
			ObjectInputStream ois = new ObjectInputStream(fin);
			data = (Data) ois.readObject();
			ois.close();
		}
		catch (Exception e) {
				data = new Data();
				e.printStackTrace();
		}

		data.reset();
		model = new Model();

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Viewer.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root);
			controller = fxmlLoader.getController();
			
			primaryStage.setTitle("SolarImpact  Telemetry");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		logger = new Logger();
		logger.start();

		primaryStage.setOnCloseRequest(e -> {

			try {
				FileOutputStream fout = new FileOutputStream("theData.dat");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(data);
				oos.close();
			}
			catch (Exception d) { d.printStackTrace(); }
			logger.stopLogger();
	        Platform.exit();
	        System.exit(0);
	    });
	}

	
	
	public static void main(String[] args) {
		launch(args);
	}
}
