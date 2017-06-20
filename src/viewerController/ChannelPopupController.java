package viewerController;

import application.Main;
import com.sun.xml.internal.bind.v2.model.core.ID;
import communication.InfoPaket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by TheOLGPC on 09.06.2017.
 */
public class ChannelPopupController {

    public char selectedSign = Character.MIN_VALUE;
    Stage stage;


    public ListView channelList;
    public Button setChannel;

    ObservableList<String> items = FXCollections.observableArrayList ();


    public void setStage(Stage stage){
        this.stage = stage;
    }

    public ChannelPopupController(){
    }

    public void setChannelList(){
        channelList.setItems(items);
    }

    public void addInfoPaket(InfoPaket infoPaket){
        items.add( infoPaket.getSign()+ "     " + infoPaket.getName());
    }

    public void removeInfoPaket(int id){
        items.remove(id);
    }

    public void onCancel() {
        selectedSign = Character.MIN_VALUE;
        stage.hide();
        setChannel.setDisable(true);
    }

    public void onSet() {
        selectedSign = channelList.getFocusModel().getFocusedItem().toString().charAt(0);
        stage.hide();
        setChannel.setDisable(true);
    }

    public void onMouseClickList(){
        setChannel.setDisable(false);
    }

}
