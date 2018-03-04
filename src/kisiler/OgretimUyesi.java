package kisiler;


import java.sql.*;
import java.util.Scanner;

public class OgretimUyesi extends Kisi {
    private int personelNo;

    private Scanner scan = new Scanner(System.in);

    //CONSTRRCTOR
    public OgretimUyesi(int personelNo) {
        this.personelNo = personelNo;
    }

    //TAMAM
    public void ogretimUyesiMenu(){
        System.out.println("\n-------------------------------");
        System.out.println("GİRİŞ BAŞARILI");
        System.out.println("Lütfen işlem seçiniz.\n" +
                "1 -> Not gir.\n" +
                "2 -> Not göster.\n"+
                "3 -> Not güncelle.\n" +
                "4 -> Bilgileri göster.\n" +
                "5 -> Bilgileri güncelle.\n" +
                "6 -> Parolayı değiştir.\n" +
                "0 -> Çıkış yap.");
        while (true){
            System.out.print("\nLütfen seçim yapın: ");
            int secim = scan.nextInt();
            if (secim == 1)         notGirme();
            else if (secim == 2)    notGorme();
            else if (secim == 3)    notGuncelleme();
            else if (secim == 4)    bilgileriGoster(personelNo);
            else if (secim == 5)    bilgileriGuncelle(personelNo);
            else if (secim == 6)    parolayiDegistir(personelNo);
            else if (secim == 0)    break;
            else System.out.println("Hatalı seçim! ");
        }
    }


    //TAMAM
    private void notGirme(){
        String  sqlNotGirmeSorgu = "UPDATE ogrDerslerNotlar SET vize=?, final=?, butunleme=? WHERE ogrenciNo=? AND dersId=?";
        String sqlDersId = "SELECT dersId FROM dersListesi WHERE ogrUyeId="+personelNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn.prepareStatement(sqlNotGirmeSorgu);
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlDersId)) {
            int ogrNo, vize,finall ,butunleme, dersId;

            dersId = rs.getInt("dersId");


            System.out.print("Lütfen ogrenci no giriniz: ");
            ogrNo = scan.nextInt();
            System.out.print("Vize notunu giriniz: ");
            vize = scan.nextInt();
            System.out.print("Final notunu giriniz: ");
            finall = scan.nextInt();
            System.out.print("Bütünleme notunu giriniz: ");
            butunleme = scan.nextInt();

            pstmt.setInt(1, vize);
            pstmt.setInt(2, finall);
            pstmt.setInt(3, butunleme);
            pstmt.setInt(4, ogrNo);
            pstmt.setInt(5, dersId);
            pstmt.executeUpdate();
            System.out.println("Notlar sisteme girildi.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //TAMAM
    private void notGorme(){

        String sqlDersId = "SELECT dersId FROM dersListesi WHERE ogrUyeId="+personelNo;

        int dersId = 0, ogrNo = 0;

        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlDersId)){
            dersId = rs.getInt("dersId");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.print("Lütfen öğrenci no giriniz: ");
        ogrNo = scan.nextInt();


        String  sqlNotGorme = "SELECT vize, final, butunleme FROM ogrDerslerNotlar WHERE dersId="+dersId+" AND ogrenciNo="+ogrNo;
        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlNotGorme)){
            System.out.println(
                    "Vize: " + rs.getInt("vize") + "\t" +
                            "Final: " + rs.getInt("final") + "\t" +
                            "Bütünleme: " + rs.getInt("butunleme")
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TAMAM
    private void notGuncelleme(){
        String  sqlNotGirmeSorgu = "UPDATE ogrDerslerNotlar SET vize=?, final=?, butunleme=? WHERE ogrenciNo=? AND dersId=?";
        String sqlDersId = "SELECT dersId FROM dersListesi WHERE ogrUyeId="+personelNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn.prepareStatement(sqlNotGirmeSorgu);
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlDersId)) {
            int ogrNo, vize,finall ,butunleme, dersId;

            dersId = rs.getInt("dersId");


            System.out.print("Lütfen ogrenci no giriniz: ");
            ogrNo = scan.nextInt();
            System.out.print("Vize notunu giriniz: ");
            vize = scan.nextInt();
            System.out.print("Final notunu giriniz: ");
            finall = scan.nextInt();
            System.out.print("Bütünleme notunu giriniz: ");
            butunleme = scan.nextInt();

            pstmt.setInt(1, vize);
            pstmt.setInt(2, finall);
            pstmt.setInt(3, butunleme);
            pstmt.setInt(4, ogrNo);
            pstmt.setInt(5, dersId);
            pstmt.executeUpdate();
            System.out.println("Notlar başarıyla güncellendi.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //TAMAM
    @Override
    void bilgileriGoster(int personelNo) {
        String sqlsorgu = "SELECT personelNo, isim, soyisim, mail, telefonNo FROM ogretimUyeleri WHERE personelNo="+personelNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase);
             Statement stmp = conn.createStatement();
             ResultSet rs = stmp.executeQuery(sqlsorgu)){

            while (rs.next()){
                System.out.println(
                        "Personel No: "+rs.getInt("personelNO") + "\n" +
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
    void bilgileriGuncelle(int personelNo) {
        String sqlsorgu = "UPDATE ogretimUyeleri SET mail=?, telefonNo=? WHERE personelNo="+personelNo;

        try(Connection conn = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn.prepareStatement(sqlsorgu) ) {
            Scanner sc = new Scanner(System.in);

            System.out.print("Mail adresinizi girin: ");
            String mail = sc.nextLine();
            System.out.print("Telefon numaranızı girin: ");
            long telefonNo = sc.nextLong();

            pstmt.setString(1, mail);
            pstmt.setLong(2, telefonNo);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println("Bilgi güncellemesi başarılı.");
    }

    //TAMAM
    @Override
    void parolayiDegistir(int personelNo) {
        String sqlOgrAraSorgu = "SELECT parola FROM ogretimUyeleri WHERE personelNo="+personelNo;
        String sqlParolaDegistir = "UPDATE ogretimUyeleri SET parola=? WHERE personelNo="+personelNo;

        Scanner scan = new Scanner(System.in);

        System.out.print("Lütfen eski şifrenizi giriniz: ");
        String tmp = scan.nextLine();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:bilgiler.db"); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlOgrAraSorgu) ){

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
//            } else {
//                System.out.println("Hatalı giriş yaptınız!");
//            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
