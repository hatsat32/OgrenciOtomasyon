
/********************************************************************************************
 SÜLEYMAN ERGEN
 1306150080
 bilgisayar mühendisliği
 nesneye yönelik programlama Java proje ödevi


 Proje intelliJ ultimate de yazıldı ve çalıştırıldı.
 sqlite veritabanı kullanıldı



 sisteme giriş için aşağıdaki bilgiler kullanılmalı.
 id:123
 pass:qwe

 bu bilgiler ile sisteme öğrenci öğretmen admin olarak sisteme girilebilir.
 deneme amaçlı oluşturuldu. şifresiz sisteme GİRİLEMEZ.

 database dosyası proje klasöründe. herhangi bir programla açılıp incelenebilir.


 sqlite için jdbc  proje klasörünün içinde. ide de bulunmaması durumunda hemen eklenebilir.



 *******************************************************************************************/



package beta;

import java.util.Scanner;
import static java.lang.System.out;
import java.sql.*;

import kisiler.Admin;
import kisiler.Ogrenci;
import kisiler.OgretimUyesi;



public class Beta {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

    }
    //TAMAM
    private static void ogrenciGiris(){
        String tmp = null;
        Scanner sc = new Scanner(System.in);
        System.out.print("Lütfen öğrenci numaranızı girin: ");
        int ogrNo = sc.nextInt();
        System.out.print("Lütfen şifrenizi girin: ");
        String pass = sc.next();

        String sqlOgrAraSorgu = "SELECT parola FROM ogrenciler WHERE ogrenciNo="+ogrNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlOgrAraSorgu) ){
            tmp = rs.getString("parola");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(pass.equals(tmp)){
            Ogrenci ogr = new Ogrenci(ogrNo);
            ogr.ogrenciMenu();
        }else{
            System.out.println("Giriş başarısız !!!");
        }
    }

    //TAMAM
    private static void ogretimUyesiGiris(){
        String tmp = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("Lütfen Personel numaranızı girin: ");
        int perNo = sc.nextInt();
        System.out.print("Lütfen şifrenizi girin: ");
        String pass = sc.next();

        String sqlOgrAraSorgu = "SELECT parola FROM ogretimUyeleri WHERE personelNo="+perNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase);
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlOgrAraSorgu) ){
            tmp = rs.getString("parola");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(pass.equals(tmp)){
            OgretimUyesi ogrUye = new OgretimUyesi(perNo);
            ogrUye.ogretimUyesiMenu();
        }else{
            System.out.println("Giriş başarısız !!!");
        }
    }

    //TAMAM
    private static void adminGiris(){
        String tmp = null;
        Scanner sc = new Scanner(System.in);
        System.out.print("Lütfen admin numaranızı girin: ");
        int admNo = sc.nextInt();
        System.out.print("Lütfen şifrenizi girin: ");
        String pass = sc.next();

        String sqlOgrAraSorgu = "SELECT parola FROM admin WHERE adminNo="+admNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase);
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlOgrAraSorgu) ){
            tmp = rs.getString("parola");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(pass.equals(tmp)){
            Admin adm = new Admin(admNo);
            adm.adminMenu();
        }else{
            System.out.println("Giriş başarısız !!!");
        }
    }
    private static String urlDatabase = "jdbc:sqlite:bilgiler.sqlite";
}

