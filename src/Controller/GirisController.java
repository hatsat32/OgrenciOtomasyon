package Controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GirisController implements Initializable{

    private static String urlDatabase = "jdbc:sqlite:bilgiler.sqlite";
    public TextField tf_username;
    public PasswordField pf_pass;
    public Button bt_giris;
    public Label label;

    public void girisYap() throws IOException {
        int uname = Integer.parseInt( tf_username.getText() );
        String pass = pf_pass.getText();
        boolean answer;


        while (true){
            answer = ogrenciGiris(uname, pass);
            if (answer)
                break;
            answer = adminGiris(uname, pass);
            if (answer)
                break;
            answer = ogretimUyesiGiris(uname, pass);
            if (answer)
                break;
            break;
        }

        if (answer){
            bt_giris.getScene().getWindow().hide();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/anaEkran.fxml"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/anaEkran.fxml"));
            loader.load();
            AnaController anaController = loader.getController();
            anaController.setId(uname);

            stage.setScene(new Scene(root));
            stage.show();
        }else {
            label.setText("Hatalı kullanıcıadı ve şifre.");
        }
    }


    private boolean ogrenciGiris(int ogrNo, String pass){
        String sqlOgrAraSorgu = "SELECT parola FROM ogrenciler WHERE ogrenciNo="+ogrNo;
        String tmp = null;

        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlOgrAraSorgu) ){
            tmp = rs.getString("parola");
        } catch (SQLException e) {
            //e.printStackTrace();
        }

        return pass.equals(tmp);
    }

    private boolean ogretimUyesiGiris(int perNo, String pass){
        String sqlOgrAraSorgu = "SELECT parola FROM ogretimUyeleri WHERE personelNo="+perNo;
        String tmp = null;

        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlOgrAraSorgu) ){
            tmp = rs.getString("parola");
        } catch (SQLException e) {
            //e.printStackTrace();
        }

        return pass.equals(tmp);
    }

    private boolean adminGiris(int admNo, String pass){
        String sqlOgrAraSorgu = "SELECT parola FROM admin WHERE adminNo="+admNo;
        String tmp = null;

        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlOgrAraSorgu) ){
            tmp = rs.getString("parola");
        } catch (SQLException e) {
            //e.printStackTrace();
        }

        return pass.equals(tmp);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
