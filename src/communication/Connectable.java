package communication;

import application.Main;

public interface Connectable {


    public default void update(String s){
	}
	
	public default InfoPaket setConnection(char sign){
			InfoPaket infoPaket = Main.model.getInfoPaketBySign(sign);
			infoPaket.addConnectable(this);
			return Main.model.getInfoPaketBySign(sign);
	}

	public void infoPaketDeleted();

}
