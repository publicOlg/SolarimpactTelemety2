package myConnectables;

import application.Main;
import communication.Connectable;
import communication.InfoPaket;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import javax.tools.Tool;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by TheOLGPC on 13.06.2017.
 */
public class MySpeedGauge implements Connectable,Initializable {

    public Arc speedGauge;
    public Label labelSpeed;
    private int id;
    double maxSpeed = 30;

    public MySpeedGauge(){
        id = Main.data.getIdForLabel();
    }



    public void update(String s){
        if(Double.valueOf(s) > maxSpeed) maxSpeed = Double.valueOf(s);
        double angel = Double.valueOf(s) / maxSpeed * 360;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                speedGauge.setLength(angel);
                labelSpeed.setText(s);
            }
        });
    }

    public void onMouse(MouseEvent event) {
        char sign = Main.controller.openSelectChannel(event.getScreenX(),event.getScreenY());
        if(sign != Character.MIN_VALUE){
            this.setConnection(sign);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelSpeed.setTooltip(new Tooltip("not connected"));
        labelSpeed.getTooltip().setFont(Font.font("", 10));
        if(Main.data.myLabelIsSet(id))setConnection(Main.data.getMyLabelReferenc(id));
    }

    public InfoPaket setConnection(char sign){
        Main.data.setMyLabelReferences(sign,id);
        labelSpeed.getTooltip().setText((Main.model.getInfoPaketBySign(sign).getName() + " " + sign));
        return Connectable.super.setConnection(sign);
    }


    public void infoPaketDeleted() {
        Main.data.setMyLabelReferences(Character.MIN_VALUE,id);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labelSpeed.getTooltip().setText("not connected");
                labelSpeed.setText("N/A");
            }
        });
    }

    @Override
    public boolean send() {
        return false;
    }

    @Override
    public String getValue() {
        return null;
    }
}
