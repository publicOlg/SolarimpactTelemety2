package myConnectables;

import application.Main;
import communication.Connectable;
import communication.InfoPaket;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

import static oracle.jrockit.jfr.events.Bits.intValue;

/**
 * Created by TheOLGPC on 20.06.2017.
 */
public class MyBar implements Connectable,Initializable {

    public Rectangle bar;
    public Label pwmLabel;
    private int id;
    private double intialHight;
    private double maxPWM = 100;

    public MyBar(){
        id = Main.data.getIdForLabel();

    }


    public void update(String s){

        if(Double.valueOf(s) > maxPWM) maxPWM = Double.valueOf(s);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                bar.heightProperty().setValue((1-Double.valueOf(s) / maxPWM) * intialHight);
                pwmLabel.setText(s);
            }
        });
    }


    public void onMouse(MouseEvent event) {
        char sign = Main.controller.openSelectChannel(event.getScreenX(),event.getScreenY());
        if(sign != Character.MIN_VALUE){
            this.setConnection(sign);
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        pwmLabel.setTooltip(new Tooltip("not connected"));
        pwmLabel.setText("N/A");
        pwmLabel.getTooltip().setFont(Font.font("", 10));
        if(Main.data.myLabelIsSet(id))setConnection(Main.data.getMyLabelReferenc(id));
        intialHight = bar.getHeight();
        bar.heightProperty().setValue(0.5 * intialHight);
    }

    public InfoPaket setConnection(char sign){

        Main.data.setMyLabelReferences(sign,id);
        pwmLabel.getTooltip().setText((Main.model.getInfoPaketBySign(sign).getName() + " " + sign));
        return Connectable.super.setConnection(sign);
    }

    public void infoPaketDeleted() {
        Main.data.setMyLabelReferences(Character.MIN_VALUE,id);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pwmLabel.getTooltip().setText("not connected");
                pwmLabel.setText("N/A");
            }
        });
    }

}
