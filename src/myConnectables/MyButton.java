package myConnectables;


import application.Main;
import communication.Connectable;
import communication.InfoPaket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Created by fhopp on 30.06.2017.
 */
public class MyButton extends Button implements Connectable{
    ArrayList<Connectable> connectables = new ArrayList<>();
    private int id;
    private char sign;



    private void warning(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Send Process did not work!");
        alert.setContentText("False input Value or missing Channel Information.");

        alert.showAndWait();
    }

    public void addConnectale(Connectable connectable){
        connectables.add(connectable);
    }

    public MyButton() {
        super();

        this.setTooltip(new Tooltip("not connected"));

        id = Main.data.getIdForLabel();
        if(Main.data.myLabelIsSet(id))setConnection(Main.data.getMyLabelReferenc(id));

        setOnMouseClicked(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event) {

                if(event.isPopupTrigger()) {
                    sign = Main.controller.openSelectChannel(event.getScreenX(), event.getScreenY());
                    if (sign != Character.MIN_VALUE) {
                        setConnection(sign);
                    }
                    return;
                }

                String message = sign + "";
                for(Connectable connectable : connectables){
                    if(connectable.getValue().isEmpty()){
                        warning();
                        return;
                    }
                    message += connectable.getValue();
                }
                Main.model.com.send(message);
            }
        });

    }

    public InfoPaket setConnection(char sign){
        Main.data.setMyLabelReferences(sign,id);
        this.sign = sign;
        this.getTooltip().setText(Main.model.getInfoPaketBySign(sign).getName() + " " + sign);
        return Connectable.super.setConnection(sign);
    }

    @Override
    public void infoPaketDeleted() {
        Main.data.setMyLabelReferences(Character.MIN_VALUE,id);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getTooltip().setText("not connected");
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
