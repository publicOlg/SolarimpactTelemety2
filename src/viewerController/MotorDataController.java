package viewerController;

import application.Main;
import graphicComponents.MyButton;
import javafx.scene.chart.*;
import javafx.scene.input.MouseEvent;
import myConnectables.MyLabel;
import myConnectables.MySeries;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MotorDataController {


	static ArrayList<MySeries> MySeries = new ArrayList<MySeries>();
	public MyLabel  motorVoltage;
	public MyLabel  motorNOR;
	public MyLabel  motorPower;
	public MyLabel  motorCurrent;
	public MyLabel  akkuVoltage;
	public MyLabel  akkuCurrent;
	public LineChart chart;
	public MyButton motorSwitch;
	public double xTime = 0;

	
	public void setMotorVoltage(char sign) {
		motorVoltage.setConnection(sign);
	}


	public void setMotorNOR(char sign) {
		motorNOR.setConnection(sign);
	}


	public void setMotorPower(char sign) {
		motorPower.setConnection(sign);
	}


	public void setMotorCurrent(char sign) {
		motorCurrent.setConnection(sign);
	}


	public void setAkkuVoltage(char sign) {
		akkuVoltage.setConnection(sign);
	}


	public void setAkkuCurrent(char sign) {
		akkuCurrent.setConnection(sign);
	}


	
	
	public MotorDataController(){

        Timer timerActiv = new Timer();
        timerActiv.schedule(new TimerTask() {
            @Override
            public void run() {
                xTime += 0.01;


                    ((NumberAxis)chart.getXAxis()).setLowerBound(xTime-10);
                    ((NumberAxis)chart.getXAxis()).setUpperBound(xTime);


            }
        }, 500, 10);

	}
	
	
	public void onMotorSwitch(){
	}



	public void onChartMouseClicked(MouseEvent event){

        char sign = Main.controller.openSelectChannel(event.getScreenX(),event.getScreenY());
        if(sign != Character.MIN_VALUE)addSeries(sign);

    }

	public void addSeries(char sign){
		MySeries serie  = new MySeries(sign, this);
		MySeries.add(serie);
		serie.getSerie().setName(Main.model.getInfoPaketBySign(sign).getName());
        chart.getData().add(serie.getSerie());
	}
	
	public void removeSeries(char sign){
		
	}
}
