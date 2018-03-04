package kisiler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class Kisi {
    private String isim, soyisim, mail;
    private int telefonNo, dogumTirihi;
    private String PAROLA;

    String urlDatabase = "jdbc:sqlite:bilgiler.db";

    abstract void bilgileriGuncelle(int id);
    abstract void bilgileriGoster(int id);

    abstract void parolayiDegistir(int id);

    //TAMAM
    void degistirPass(String tmp, String passEski, String sqlParolaDegistir){

        Scanner scan = new Scanner(System.in);

        if (tmp.equals(passEski)){
            System.out.print("Lütfen yeni şifrenizi girin: ");
            String yeniParola1 = scan.nextLine();
            System.out.print("Yeni şifrenizi tekrar girin: ");
            String yeniParola2 = scan.nextLine();


            if (yeniParola1.equals(yeniParola2) ){
                try (Connection conn2 = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn2.prepareStatement(sqlParolaDegistir)){
                    pstmt.setString(1, yeniParola1);
                    pstmt.executeUpdate();
                    System.out.println("Parolanız başarıyla değiştirildi.");

                }catch (SQLException e){
                    e.printStackTrace();
                }

            } else {
                System.out.println("Parolalar eşleşmiyor!!!");
            }

        }else{
            System.out.println("Hatalı giriş yaptınız!");
        }
    }
}
