package Controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import kisiler.Ogrenci;

public class ParolaDegistir {
    String urlDatabase = "jdbc:sqlite:bilgiler.sqlite";

    GirisController GirisController = new GirisController();


    kisiler.Ogrenci ogrenci = new Ogrenci(1306150080);

    public PasswordField tf_eskiParola, tf_yeniParola, tf_yeniParolaTekrar;
    public Label lb_top;
    public Button bt_degistir;

    public void degistir(){
        String eskiParola = tf_eskiParola.getText();
        String yeniParola = tf_yeniParola.getText();
        String yeniParolaTekrar = tf_yeniParolaTekrar.getText();


    }
}
