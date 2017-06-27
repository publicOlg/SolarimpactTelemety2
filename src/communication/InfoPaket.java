package communication;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import application.Main;

import static java.lang.Thread.sleep;

public class InfoPaket implements Runnable,Serializable {

	public boolean alive = true;
	private String info;
	private char sign;
	private boolean newInfo = false;
	private String name;
	private boolean activ = false;
	//private Timer timer = new Timer();
    private String COLOR;
	private ArrayList<Connectable> connectables = new ArrayList<>();
 
    
    public InfoPaket(String name, char sign){
    	this.name = name;
    	this.sign = sign;
    }

    public boolean removeConnectables(Connectable connectable){
    	if(connectables.contains(connectable)){
    		connectables.remove(connectable);
    		return true;
		}

		return false;
	}

    public void addConnectable(Connectable connectable){
    	this.connectables.add(connectable);
    }
    

    public String getName() {
		return name;
	}


    public String getWebColor(){
         return COLOR;
    }
    
	
    public synchronized boolean isNew(){
    	if (!newInfo){
			try {
				wait(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (newInfo){
			notify();
			return true;
		}
    	return false;
    }
    
    
	private synchronized String getInfo() {
		newInfo = false;
		return info.substring(1, info.length());
	}
	

	public synchronized void setInfo(String info) {
		this.info = info;
		newInfo = true;
		setActive();
		writeToLog(info);
		notify();
		try {
			wait(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	private void writeToLog(String s){
		Main.controller.writeLog(name + ":  " + s, getWebColor(), this);
	}
	

	public synchronized char getSign() {
		return sign;
	}
	
	
	public synchronized void setSign(char sign) {
		this.sign = sign;
	}
	

	private void setActive(){
		if(!activ)Main.controller.writeLog("SYSETM: <" + name + "> Channel opened, Sign: " + sign, Main.model.COLOR_BLACK);
		activ = true;
		/*timer.cancel();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			  public void run() {
			    activ = false;
			    writeToLog("is not longer Activ");
			  }
			}, 1000);
			*/
	}
	
	
	public boolean isActiv(){
		return activ;
	}


	@Override
	public void run() {
		COLOR = Main.model.getWebColor();
		while(alive){
			if(isNew()){
				for(Connectable con : connectables){
					con.update(getInfo());
				}
				try {
					sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

		for(Connectable con : connectables){
			con.infoPaketDeleted();
		}
	}
}
