package viewerController;

import javafx.scene.control.*;

/**
 * Created by TheOLGPC on 22.06.2017.
 */
public class SettingsController {

    public Slider yDif;
    public Slider xDif;
    public Label xDifLabel;
    public Label yDifLabel;

    public TextField rudderOne;
    public TextField rudderTwo;
    public TextField rudderThree;
    public TextField rudderFour;
    public CheckBox checkTwo;
    public CheckBox checkFour;
    public Button rudderSend;

    public ToggleButton loggerOn;
    public TextField textLogger;
    public Button SendLogger;

    public Slider pilotSlider;
    public Label textPilot;
    public Button pilotSend;

    public Slider hightSlider;
    public Label textHight;
    public Button sendHight;


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

}
