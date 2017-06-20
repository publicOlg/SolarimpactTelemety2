package graphicComponents;


        import application.Main;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.TextInputDialog;
        import javafx.scene.control.Tooltip;

        import java.util.Optional;

/**
 * Created by TheOLGPC on 13.06.2017.
 */
public class MyButton extends Button {

    private String code = "";
    private int id;


    public MyButton(){
        super();

        id = Main.data.getIdForButton();
        if(Main.data.myButtonIsSet(id)){
            code = Main.data.getMyButtonReferenceCode(id);
            setText(Main.data.getMyButtonReferenceTxt(id));
        }

        this.setTooltip(new Tooltip("Code: " + code));

        this.setOnMouseClicked( (event )->{
            if(event.isPopupTrigger()){

                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Change Button Code");
                dialog.setHeaderText("Insert a new Button Code");
                dialog.setContentText("Code:");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(name -> setCode(name));
                return;
            }

               sendCode();

        });

    }



    public void setCode(String code){
        this.code = code;
        Main.data.setMyButtonReferenceCode(code,id);
        this.setTooltip(new Tooltip("Code: " + code));
    }

    private void sendCode(){
        if( code != "" ){
            if(Main.model.com != null)Main.model.com.send(code);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No code inserted!");
            alert.setContentText("Try to insert a code for action.");
            alert.showAndWait();
        }
    }
}
