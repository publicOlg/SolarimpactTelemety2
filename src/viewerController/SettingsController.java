package viewerController;

import javafx.scene.control.*;
import myConnectables.MyLabel;
import myConnectables.MyTextField;

/**
 * Created by TheOLGPC on 22.06.2017.
 */
public class SettingsController {

    public MyLabel xDifLabel;
    public MyLabel yDifLabel;

    public MyTextField rudderOne;
    public MyTextField rudderTwo;
    public MyTextField rudderThree;
    public MyTextField rudderFour;
    public CheckBox checkTwo;
    public CheckBox checkFour;

    public ToggleButton loggerOn;
    public MyTextField textLogger;

    public MyLabel textPilot;

    public MyLabel textHight;


    public void onDifferantialSliderMoved(){

    }



    public void onCheckRudder(){

        if(checkFour.isSelected() && checkFour.isFocused() ) {
            rudderTwo.setDisable(true);
            rudderTwo.textProperty().bind(rudderOne.textProperty());
            rudderThree.setDisable(true);
            rudderThree.textProperty().bind(rudderOne.textProperty());
            rudderFour.setDisable(true);
            rudderFour.textProperty().bind(rudderOne.textProperty());
            checkTwo.setSelected(false);

        }else if(checkTwo.isSelected()){
            rudderTwo.setDisable(false);
            rudderTwo.textProperty().unbind();
            rudderThree.setDisable(true);
            rudderThree.textProperty().bind(rudderOne.textProperty());
            rudderFour.setDisable(true);
            rudderFour.textProperty().bind(rudderTwo.textProperty());
            checkFour.setSelected(false);
        }else{
            rudderTwo.setDisable(false);
            rudderTwo.textProperty().unbind();
            rudderThree.setDisable(false);
            rudderThree.textProperty().unbind();
            rudderFour.setDisable(false);
            rudderFour.textProperty().unbind();
        }
    }


    public void onDifSend(){
        if(xDifLabel.send() && yDifLabel.send())return;
        warning();

    }

    public void onRudderSend(){
        if (rudderOne.send() && rudderTwo.send()
                && rudderThree.send() && rudderFour.send()) return;
        warning();
    }

    public void onPilotSend(){
        if (textPilot.send())return;
        warning();
    }

    public void onLoggerSend(){
        if(textLogger.send()) return;
        warning();
    }

    public void onHighttSend(){
        if (textHight.send()) return;
        warning();
    }

    private void warning(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Send Process did not work!");
        alert.setContentText("False input Value or missing Channel Information.");

        alert.showAndWait();
    }
}
