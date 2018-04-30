package otomasyon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/girisEkrani.fxml"));


        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        tablolariOlustur();
        launch(args);
    }

    private static void tablolariOlustur(){
        String sqlOgrenciler = "CREATE TABLE IF NOT EXISTS ogrenciler" +
                "(\n" +
                "ogrenciNo INTEGER PRIMARY KEY, \n" +
                "isim TEXT NOT NULL, \n" +
                "soyisim TEXT NOT NULL, \n" +
                "mail TEXT NOT NULL, \n" +
                "telefonNo BIGINT NOT NULL, \n" +
                "parola TEXT NOT NULL" +
                ");";
        String sqlOgretimUyeleri = "CREATE TABLE IF NOT EXISTS ogretimUyeleri" +
                "(\n" +
                "personelNo INTEGER PRIMARY KEY, \n" +
                "isim TEXT NOT NULL, \n" +
                "soyisim TEXT NOT NULL, \n" +
                "mail TEXT NOT NULL, \n" +
                "telefonNo BIGINT NOT NULL, \n" +
                "parola TEXT NOT NULL" +
                ")";
        String sqlAdmin = "CREATE TABLE IF NOT EXISTS admin" +
                "(\n" +
                "adminNo INTEGER PRIMARY KEY, \n" +
                "isim TEXT NOT NULL, \n" +
                "soyisim TEXT NOT NULL, \n" +
                "mail TEXT NOT NULL, \n" +
                "telefonNo BIGINT NOT NULL, \n" +
                "parola TEXT NOT NULL" +
                ")";
        String sqlDersListesi = "CREATE TABLE IF NOT EXISTS dersListesi\n" +
                "(\n" +
                "dersId INT PRIMARY KEY NOT NULL ,\n" +
                "dersIsmi TEXT,\n" +
                "ogrUyeId INT,\n" +
                "dersSayisi INT,\n" +
                "dersKredisi INT\n" +
                ");";
        String sqlOgrDerslerNotlar = "CREATE TABLE IF NOT EXISTS ogrDerslerNotlar\n" +
                "(\n" +
                "ogrenciNo INT PRIMARY KEY NOT NULL,\n" +
                "dersId INT,\n" +
                "vize INT,\n" +
                "final INT,\n" +
                "butunleme INT,\n" +
                "ortalama INT\n" +
                ");";

        String sqlDersProgrami = "CREATE TABLE IF NOT EXISTS dersProgrami\n" +
                "(\n" +
                "pazartesi INT,\n" +
                "sali INT,\n" +
                "carsamba INT,\n" +
                "persembe INT,\n" +
                "cuma INT" +
                ");";
        String sqlDersProg = "CREATE TABLE IF NOT EXISTS dersProg\n" +
                "(\n" +
                "gun TEXT,\n" +
                "ders1 INT,\n" +
                "ders2 INT,\n" +
                "ders3 INT,\n" +
                "ders4 INT,\n" +
                "ders5 INT,\n" +
                "ders6 INT,\n" +
                "ders7 INT,\n" +
                "ders8 INT\n" +
                ");\n" +
                "INSERT INTO dersProg (gun) VALUES ('PAZARTESİ');" +
                "INSERT INTO dersProg (gun) VALUES ('SALI');" +
                "INSERT INTO dersProg (gun) VALUES ('ÇARŞAMBA');" +
                "INSERT INTO dersProg (gun) VALUES ('PERŞEMBE');" +
                "INSERT INTO dersProg (gun) VALUES ('CUMA');";



        //database için url bilgisi
        String urlDatabase = "jdbc:sqlite:bilgiler.sqlite";

        //bilgiler için db oluşturulur.
        try {
            DriverManager.getConnection(urlDatabase);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //bilgiler için tablo oluşturulur.
        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmp = conn.createStatement() ){
            stmp.execute(sqlOgrenciler);
            stmp.execute(sqlOgretimUyeleri);
            stmp.execute(sqlAdmin);
            stmp.execute(sqlDersListesi);
            stmp.execute(sqlOgrDerslerNotlar);
//            stmp.execute(sqlDersProgrami);
//            stmp.executeUpdate(sqlDersProg);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

}
