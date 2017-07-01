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
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by TheOLGPC on 20.06.2017.
 */
public class MyHDOP implements Connectable,Initializable {

    public Circle circle;
    public Label labelHDOP;
    private int id;



    public MyHDOP(){
        id = Main.data.getIdForLabel();
    }

    public void update(String s){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(1.4 * Double.valueOf(s) + 40 < 111) circle.setRadius(1.4 * Double.valueOf(s) + 40);
                labelHDOP.setText(s);
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
        labelHDOP.setTooltip(new Tooltip("not connected"));
        labelHDOP.setText("50");
        labelHDOP.getTooltip().setFont(Font.font("", 10));
        if(Main.data.myLabelIsSet(id))setConnection(Main.data.getMyLabelReferenc(id));
        circle.setRadius(80);
    }

    public InfoPaket setConnection(char sign){

        Main.data.setMyLabelReferences(sign,id);
        labelHDOP.getTooltip().setText((Main.model.getInfoPaketBySign(sign).getName() + " " + sign));
        return Connectable.super.setConnection(sign);
    }

    public void infoPaketDeleted() {
        Main.data.setMyLabelReferences(Character.MIN_VALUE,id);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labelHDOP.getTooltip().setText("not connected");
                labelHDOP.setText("50");
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
