package myConnectables;

import communication.InfoPaket;
import communication.Connectable;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import viewerController.MotorDataController;
public class MySeries implements Connectable {


    InfoPaket paket;

	Series<Number, Number> serie = new XYChart.Series();
    MotorDataController controller;
    double lastUpdate = 0;
    int i = 0;

    @Override
    public void update(String s) {

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(i > 100){
                        serie.getData().remove(0);
                        i--;
                    }
                    if(controller.xTime > lastUpdate + 0.01){
                        serie.getData().add(new XYChart.Data(controller.xTime, Double.valueOf(s)));
                        lastUpdate = controller.xTime;
                        i++;
                    }
                }
            });
    }

    public Series getSerie(){
        return serie;
    }

    public MySeries(char sign , MotorDataController controller) {
        paket  = setConnection(sign);
        this.controller = controller;
    }


    public Connectable infoPaketDeleted() {
        return null;
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
