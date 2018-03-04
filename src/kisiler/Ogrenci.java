package kisiler;

import java.sql.*;
import java.util.Scanner;

public class Ogrenci extends Kisi {
    private int ogrenciNo;
    private Scanner scan = new Scanner(System.in);

    //CONSTRRCTOR
    public Ogrenci(int ogrenciNo) {
        this.ogrenciNo = ogrenciNo;
    }

    //TAMAM
    public void ogrenciMenu(){
        System.out.println("\n-------------------------------");
        System.out.println("Lütfen işlem seçiniz.\n" +
                "1 -> Dersleri göster.\n" +
                "2 -> Notları göster.\n"+
                "3 -> Bilgileri göster.\n" +
                "4 -> Bilgileri güncelle.\n" +
                "5 -> Ders seç.\n" +
                "6 -> Parolayı değiştir.\n" +
                "0 -> Çıkış yap.");
        while (true){
            System.out.print("Lütfen seçim yapın: ");
            int secim = scan.nextInt();
            if (secim == 1)         dersleriGoster();
            else if (secim == 2)    notlariGoster();
            else if (secim == 3)    bilgileriGoster(ogrenciNo);
            else if (secim == 4)    bilgileriGuncelle(ogrenciNo);
            else if (secim == 5)    dersSec();
            else if (secim == 6)    parolayiDegistir(ogrenciNo);
            else if (secim == 0)    break;
            else System.out.println("Hatalı seçim! ");
        }
    }

    //TAMAM
    private void dersleriGoster(){
        String sqlDersGoster = "SELECT dersId FROM ogrDerslerNotlar WHERE ogrenciNo="+ogrenciNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlDersGoster)){
            while (rs.next()){
                System.out.println(rs.getInt("dersId"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //TAMAM
    private void notlariGoster(){
        String sqlNotlariGoster = "SELECT dersId, vize, final, butunleme FROM ogrDerslerNotlar WHERE ogrenciNo="+ogrenciNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlNotlariGoster)){
            while (rs.next()){
                System.out.println("ders: " +rs.getInt("dersId") +"   "+
                        "Vize: " + rs.getInt("vize") +"   "+
                        "final: " + rs.getInt("butunleme") +"   "+
                        "butunleme: " + rs.getInt("butunleme")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //TAMAM
    private void dersSec(){
        String sqlDersSec = "INSERT INTO ogrDerslerNotlar (ogrenciNo, dersId) VALUES (?,?)";

        try (Connection conn = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn.prepareStatement(sqlDersSec) ){
            System.out.print("DersId seçiniz: ");
            int  dersId = scan.nextInt();
            pstmt.setInt(1,ogrenciNo);
            pstmt.setInt(2,dersId);
            pstmt.executeUpdate();

            System.out.println("Ders seçimi başarılı.");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //TAMAM
    @Override
    public void bilgileriGuncelle(int ogrenciNo) {
        String sqlSorgu = "UPDATE ogrenciler SET mail=?, telefonNo=? WHERE ogrenciNo=?"+ogrenciNo;

        Scanner sc = new Scanner(System.in);
        System.out.print("Mail adresinizi girin: ");
        String mail = sc.nextLine();
        System.out.print("Telefon numaranızı girin: ");
        long telefonNo = sc.nextLong();

        try(Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sqlSorgu) ) {
            pstmt.setString(1, mail);
            pstmt.setLong(2, telefonNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Bilgi güncellemesi başarılı.");
    }

    //TAMAM
    @Override
    public void bilgileriGoster(int ogrenciNo) {
        String sqlsorgu = "SELECT ogrenciNo, isim, soyisim, mail, telefonNo FROM ogrenciler WHERE ogrenciNo="+ogrenciNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase);
             Statement stmp = conn.createStatement();
             ResultSet rs = stmp.executeQuery(sqlsorgu)){

            while (rs.next()){
                System.out.println(
                        "Öğrenci No: "+rs.getInt("ogrenciNO") + "\n" +
                                "İsim: "+rs.getString("isim") + "\n" +
                                "Soyisim: "+rs.getString("soyisim") + "\n" +
                                "Mail: "+rs.getString("mail") + "\n" +
                                "Telefon: "+rs.getString("telefonNo") + "\n"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TAMAM
    @Override
    void parolayiDegistir(int id) {
        String sqlOgrAraSorgu = "SELECT parola FROM ogrenciler WHERE ogrenciNo="+ogrenciNo;
        String sqlParolaDegistir = "UPDATE ogrenciler SET parola=? WHERE ogrenciNo="+ogrenciNo;

        Scanner scan = new Scanner(System.in);

        System.out.print("Lütfen eski şifrenizi giriniz: ");
        String tmp = scan.nextLine();

        try (Connection conn = this.connect(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlOgrAraSorgu)){
            String eskiParola = rs.getString("parola");

            stmt.close();
            rs.close();

            degistirPass(tmp, eskiParola, sqlParolaDegistir);

//            if (tmp.equals(eskiParola)){
//                System.out.print("Lütfen yeni şifrenizi girin: ");
//                String yeniParola1 = scan.nextLine();
//                System.out.print("Yeni şifrenizi tekrar girin: ");
//                String yeniParola2 = scan.nextLine();
//
//
//                if (yeniParola1.equals(yeniParola2) ){
//                    try (Connection conn2 = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn2.prepareStatement(sqlParolaDegistir)){
//                        pstmt.setString(1, yeniParola1);
//                        pstmt.executeUpdate();
//                        System.out.println("Parolanız başarıyla değiştirildi.");
//
//                    }catch (SQLException e){
//                        e.printStackTrace();
//                    }
//
//                } else {
//                    System.out.println("Parolalar eşleşmiyor!!!");
//                }
//
//            }else{
//                System.out.println("Hatalı giriş yaptınız!");
//            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //TAMAM
    private Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(urlDatabase);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}

