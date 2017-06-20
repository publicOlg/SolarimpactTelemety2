package viewerController;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import myConnectables.MyLabel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GpsDataController implements Initializable {

    public MyLabel labelLaenge;
    public MyLabel labelBreite;
    public MyLabel LabelHoehe;
    public MyLabel labelSat;
    public MyLabel labelCurse;
    public AnchorPane curseGaugePane;
    public AnchorPane speedGaugePane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlMinimalComponents/SpeedGauge.fxml"));
        AnchorPane panelSpeed = null;
        try {
            panelSpeed = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        speedGaugePane.getChildren().add(panelSpeed);



        fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlMinimalComponents/CurseGauge.fxml"));
        AnchorPane panelCurse = null;
        try {
            panelCurse = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        curseGaugePane.getChildren().add(panelCurse);


    }
}
