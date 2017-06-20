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
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by TheOLGPC on 13.06.2017.
 */
public class MyCurseGauge  implements Connectable,Initializable {

    public AnchorPane curseGauge;
    public Label labelCurse;
    private int id;



    public MyCurseGauge(){
        id = Main.data.getIdForLabel();
    }

    public void update(String s){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                curseGauge.setRotate(Double.valueOf(s));
                labelCurse.setText(s + "°");
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
        labelCurse.setTooltip(new Tooltip("not connected"));
        labelCurse.getTooltip().setFont(Font.font("", 10));
        if(Main.data.myLabelIsSet(id))setConnection(Main.data.getMyLabelReferenc(id));
    }

    public InfoPaket setConnection(char sign){

        Main.data.setMyLabelReferences(sign,id);
        labelCurse.getTooltip().setText((Main.model.getInfoPaketBySign(sign).getName() + " " + sign));
        return Connectable.super.setConnection(sign);
    }

    public void infoPaketDeleted() {
        Main.data.setMyLabelReferences(Character.MIN_VALUE,id);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labelCurse.getTooltip().setText("not connected");
                labelCurse.setText("N/A");
            }
        });
    }
}
