package viewerController;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by TheOLGPC on 20.06.2017.
 */
public class PWMPanelController implements Initializable {

    public GridPane grid;

    public void initialize(URL location, ResourceBundle resources) {

        FXMLLoader fxmlLoader;
        for(int i = 0; i < 6; i++){
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlMinimalComponents/BarLabel.fxml"));
            AnchorPane panel = null;
            try {
                panel = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            grid.add(panel,i,1);

        }
    }

}
