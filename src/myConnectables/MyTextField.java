package myConnectables;

import application.Main;
import communication.Connectable;
import communication.InfoPaket;

import java.awt.*;

/**
 * Created by TheOLGPC on 22.06.2017.
 */
public class MyTextField extends TextField implements Connectable {

    private int id;

    public MyTextField(){
        super();

        id = Main.data.getIdForSender();
        //if(Main.data.SenderIsSet(id))setConnection(Main.data.getSenderReferenceCode(id));
    }

    @Override
    public void update(String s) {

    }

    @Override
    public InfoPaket setConnection(char sign) {
        return null;
    }

    @Override
    public void infoPaketDeleted() {

    }
}
