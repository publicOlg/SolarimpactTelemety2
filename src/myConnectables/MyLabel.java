	package myConnectables;

	import application.Main;
	import communication.Connectable;
	import communication.InfoPaket;
	import javafx.application.Platform;
	import javafx.scene.control.Label;
	import javafx.scene.control.Tooltip;

	import java.net.URL;
	import java.util.ResourceBundle;


	public class MyLabel extends Label implements Connectable {

		private int id;


		public MyLabel(){
			super();

			this.setTooltip(new Tooltip("not connected"));
			this.setText("N/A");

			id = Main.data.getIdForLabel();
			if(Main.data.myLabelIsSet(id))setConnection(Main.data.getMyLabelReferenc(id));

			this.setOnMouseClicked( (event )->{
				char sign = Main.controller.openSelectChannel(event.getScreenX(),event.getScreenY());
				if(sign != Character.MIN_VALUE){
					setConnection(sign);

				}
			});
		}
		
		public void update(String s){
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					setText(s);
				}
			});
		}

		public InfoPaket setConnection(char sign){
			Main.data.setMyLabelReferences(sign,id);
			this.getTooltip().setText(Main.model.getInfoPaketBySign(sign).getName() + " " + sign);
			return Connectable.super.setConnection(sign);
		}

		@Override
		public void infoPaketDeleted() {
			Main.data.setMyLabelReferences(Character.MIN_VALUE,id);

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					getTooltip().setText("not connected");
					setText("N/A");
				}
			});
		}
	}
