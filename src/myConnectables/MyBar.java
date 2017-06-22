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

    public Rectangle barUpper;
    public Rectangle barLower;
    public Label pwmLabel;
    private int id;
    private double intialHight;
    private double maxPWM = 255;

    public MyBar(){
        id = Main.data.getIdForLabel();
    }


    public void update(String s){
        Double value = Double.valueOf(s);

        if(value > maxPWM) maxPWM = Double.valueOf(s);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if(value > (maxPWM - 1) / 2) {
                    barUpper.heightProperty().setValue((1-(2*value-(maxPWM-1)) / maxPWM) * intialHight);
                    barLower.heightProperty().setValue(0);
                }else if (value < (maxPWM - 1) / 2){
                    barUpper.heightProperty().setValue(0);
                    barLower.heightProperty().setValue((value / maxPWM) * 2 * intialHight);
                }else{
                    barUpper.heightProperty().setValue(intialHight);
                    barUpper.heightProperty().setValue(intialHight);
                }

                pwmLabel.setText(Integer.toString((int) (((value-(maxPWM - 1)/2) / (maxPWM - 1)/2) * 400)));
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
        intialHight = barUpper.getHeight();
        barUpper.heightProperty().setValue(0.5 * intialHight);
        barLower.heightProperty().setValue(0);
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
