package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class AnaController {
    public Button bt_bilgiGoster, bt_bilgiGuncelle, bt_dersGoster, bt_notGoster, bt_parolaDegistir;
    public BorderPane mainBorderPane;

    private static int ogrNo;


    public void setId(int uname){
        ogrNo = uname;
        bt_bilgiGoster.setText(String.valueOf(ogrNo));
        System.out.println(ogrNo);
    }


    public void bilgileriGoster() throws IOException {
        System.out.println(ogrNo);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("dene1.fxml"));
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("../FXML/bilgileriGoster.fxml"));
        mainBorderPane.setCenter(anchorPane);

    }

    public void bilgileriGuncelle(){

    }

    public void dersleriGoster(){

    }

    public void notlariGoster(){

    }

    public void parolaDegistir() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("../FXML/parolaDegistir.fxml"));
        mainBorderPane.setCenter(anchorPane);
    }

}
