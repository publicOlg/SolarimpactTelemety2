package application;

import communication.InfoPaket;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by TheOLGPC on 13.06.2017.
 */
public class Data implements Serializable {




    // FOR INFOPAKETS

    private ArrayList<String> infoPaketsNames = new ArrayList<>();
    private ArrayList<Integer> infoPaketsSigns = new ArrayList<>();


    public ArrayList<InfoPaket> getAllInfoPakets(){
        ArrayList<InfoPaket> infoPakets = new ArrayList<InfoPaket>();
        for(int i = 0; i < infoPaketsNames.size(); i++){
            infoPakets.add(new InfoPaket(infoPaketsNames.get(i), (char) (int) infoPaketsSigns.get(i) ));
        }
        return infoPakets;
    }

    public void addNewInfoPaket(String name, char sign){
        infoPaketsNames.add(name);
        infoPaketsSigns.add((int)sign);
    }

    public void removeInfoPaket(int id){
        infoPaketsNames.remove(id);
        infoPaketsSigns.remove(id);
    }







    // FOR  MYLABEL
    private ArrayList<Character> myLabelReferences = new ArrayList<Character>();

    private int idForLabel = -1;

    public int getIdForLabel(){

        idForLabel++;
        if(myLabelReferences.size() < idForLabel + 1)myLabelReferences.add(null);
        return idForLabel;
    }

    public char getMyLabelReferenc(int id){
        return myLabelReferences.get(id);
    }

    public boolean myLabelIsSet(int id){

        if (myLabelReferences.size() > id) {
            if (myLabelReferences.get(id) != null && myLabelReferences.get(id) != Character.MIN_VALUE) {
                return true;
            }
        }
        return false;
    }


    public void setMyLabelReferences(char sign, int id){
        if (sign == Character.MIN_VALUE)
        {
            myLabelReferences.set(id, null);
            return;
        }
        myLabelReferences.set(id, sign);
    }





    // FOR  Sender
    private ArrayList<String> SenderReferencesCode = new ArrayList<String>();

    private int idForSender = -1;

    public int getIdForSender(){

        idForSender++;
        if(SenderReferencesCode.size() < idForSender + 1){
            SenderReferencesCode.add(null);
        }
        return idForSender;
    }

    public String getSenderReferenceCode(int id){
        return SenderReferencesCode.get(id);
    }

    public String getSenderReferenceTxt(int id){
        return SenderReferencesCode.get(id);
    }

    public boolean SenderIsSet(int id) {
        if (SenderReferencesCode.size() > id){
            if (SenderReferencesCode.get(id) != null) {
                return true;
            }
        }
        return false;
    }

    public void setSenderReferenceCode(String code, int id){
        SenderReferencesCode.set(id, code);
    }

    public void reset(){
        idForLabel = -1;
        idForSender = -1;
    }


}
