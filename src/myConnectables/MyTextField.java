package myConnectables;

import application.Main;
import communication.Connectable;
import communication.InfoPaket;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

/**
 * Created by TheOLGPC on 22.06.2017.
 */
public class MyTextField extends TextField implements Connectable {

    private int id;
    private char sign;

    public MyTextField(){
        super();
        this.setTooltip(new Tooltip());
        id = Main.data.getIdForSender();
        if(Main.data.myLabelIsSet(id))setConnection(Main.data.getMyLabelReferenc(id));

        this.setOnMouseClicked( (event )->{
            if(event.isPopupTrigger()){
                sign = Main.controller.openSelectChannel(event.getScreenX(),event.getScreenY());
                if(sign != Character.MIN_VALUE){
                    setConnection(sign);
                }
            }
        });
    }

    public String getSignedText(){
        if(sign != Character.MIN_VALUE) return "";
        return sign + getText();
    }


    @Override
    public void update(String s) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setText(s);
            }
        });
    }

    @Override
    public InfoPaket setConnection(char sign) {
        this.sign = sign;
        Main.data.setMyLabelReferences(sign,id);
        this.getTooltip().setText(Main.model.getInfoPaketBySign(sign).getName() + " " + sign);
        return Connectable.super.setConnection(sign);
    }

    public String getValue(){
        if(sign != Character.MIN_VALUE && Main.model.isNumeric(getText()) && Main.model.com != null ) {
            String s = "";
            int value = Integer.valueOf(getText()) + 100;

            if (value < 100 && value > 9 ){
                s += "0" + value;
            } else if (value < 10 && value != 0 ){
                s += "00" + value;
            } else if(value == 0) {
                s += "000";
            }else{
                s += value;
            }

            return s;
        }
        return "";


    }

    public boolean send(){
        if(sign != Character.MIN_VALUE && Main.model.isNumeric(getText()) && Main.model.com != null ) {
            String s = sign + "";
            int value = Integer.valueOf(getText()) + 100;

            if (value < 100 && value > 9 ){
                s += "0" + value;
            } else if (value < 10 && value != 0 ){
                s += "00" + value;
            } else if(value == 0) {
                s += "000";
            }else{
                s += value;
            }

            Main.model.com.send(s);
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public void infoPaketDeleted() {
        Main.data.setMyLabelReferences(Character.MIN_VALUE,id);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getTooltip().setText("not connected");
                setText("N/A");
            }
        });
    }

    /*
    public void initialize(URL location, ResourceBundle resources) {
        TextField text = this;

        System.out.print(23);
        this.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                text.getTooltip().show(text, event.getScreenX(), event.getScreenX());
                System.out.print(23);
            }
        });

    }
    */
}
