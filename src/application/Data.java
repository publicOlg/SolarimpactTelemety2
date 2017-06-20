package application;

import communication.InfoPaket;

import javax.print.DocFlavor;
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





    // FOR  MYBUTTON
    private ArrayList<String> myButtonReferencesCode = new ArrayList<String>();
    private ArrayList<String> myButtonReferencesTxt = new ArrayList<String>();

    private int idForButton = -1;

    public int getIdForButton(){

        idForButton++;
        if(myButtonReferencesCode.size() < idForButton + 1){
            myButtonReferencesCode.add(null);
            myButtonReferencesTxt.add(null);
        }
        return idForButton;
    }

    public String getMyButtonReferenceCode(int id){
        return myButtonReferencesCode.get(id);
    }

    public String getMyButtonReferenceTxt(int id){
        return myButtonReferencesCode.get(id);
    }

    public boolean myButtonIsSet(int id) {
        if (myButtonReferencesCode.size() > id){
            if (myButtonReferencesCode.get(id) != null) {
                return true;
            }
        }
        return false;
    }

    public void setMyButtonReferenceCode(String code, int id){
        myButtonReferencesCode.set(id, code);
    }

    public void setMyButtonReferenceTxt(String txt, int id){
        myButtonReferencesTxt.set(id, txt);
    }

    public void reset(){
        idForLabel = -1;
        idForButton = -1;
    }
}
