package application;


import communication.InfoPaket;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by fhopp on 30.06.2017.
 */
public class Logger extends Thread{
    PrintWriter writer;
    long rate = 500;
    boolean running = false;
    long time = 0;

    public void run(){
        while (true){
            if(running){
                writer.print(line(Long.toString(time)));

                for(InfoPaket infoPaket : Main.model.infoPakets){
                    line(infoPaket.getInfo());
                }
                writer.println("\"\"");
                time += rate;
            }
            try {
                sleep(rate);
            } catch (InterruptedException e) {
            }
        }
    }

    private String line(String value) {
        return "\"" + value + "\";";
    }

    public void newData(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        try {
            writer = new PrintWriter("logger/" + timeStamp + ".csv","UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        running = true;
        time = 0;

        writer.print(line(""));
        for(InfoPaket infoPaket : Main.model.infoPakets){
            writer.print(line(infoPaket.getName()));
        }
        writer.println("\"\"");
    }


    public void stopLogger(){
        if(writer != null)writer.close();
        running = false;
    }

}
