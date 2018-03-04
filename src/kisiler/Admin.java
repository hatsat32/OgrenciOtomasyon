package kisiler;

import java.sql.*;
import java.util.Scanner;

public class Admin extends Kisi {

    private int adminNo;
    private Scanner scan = new Scanner(System.in);
    //CONSTRRCTOR
    public Admin(int adminNo) {
        this.adminNo = adminNo;
    }

    //TAMAM
    public void adminMenu(){
        System.out.println("\n-------------------------------");
        System.out.println("GİRİŞ BAŞARILI");
        System.out.println("Lütfen işlem seçiniz.\n" +
                "1 -> Ders ekle.\n" +
                "2 -> Öğrenci ekle.\n"+
                "3 -> Öğretmen ekle.\n" +
                "4 -> Bilgileri göster.\n" +
                "5 -> Bilgileri güncelle.\n" +
                "6 -> Parolayı değiştir.\n" +
//                "7 -> ders programi otomatik hazırla.\n" +
                "0 -> Çıkış yap.");
        while (true){
            System.out.print("\nLütfen seçim yapın: ");
            int secim = scan.nextInt();
            if (secim == 1)         dersEkle();
            else if (secim == 2)    ogrenciEkle();
            else if (secim == 3)    ogretimUyesiEkle();
            else if (secim == 4)    bilgileriGoster(adminNo);
            else if (secim == 5)    bilgileriGuncelle(adminNo);
            else if (secim == 6)    parolayiDegistir(adminNo);
//            else if (secim == 7)    dersProgramiHazirla();
            else if (secim == 0)    break;
            else System.out.println("Hatalı seçim! ");
        }
    }


    //TAMAM
    @Override
    void bilgileriGoster(int id) {
        String sqlsorgu = "SELECT adminNo, isim, soyisim, mail, telefonNo FROM admin WHERE adminNo="+adminNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase);
             Statement stmp = conn.createStatement();
             ResultSet rs = stmp.executeQuery(sqlsorgu) ){

            while (rs.next()){
                System.out.println(
                        "Admin No: "+rs.getInt("adminNO") + "\n" +
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
    void bilgileriGuncelle(int adminNo) {
        String sqlSorgu = "UPDATE admin SET isim=?, soyisim=?, mail=?, telefonNo=? WHERE adminNo=?"+adminNo;
        //isim soyisim mail telefon
        Scanner sc = new Scanner(System.in);
        System.out.print("İsminizi girin: ");
        String isim = sc.nextLine();
        System.out.print("Soyisminizi girin: ");
        String soyisim = sc.nextLine();
        System.out.print("Mail adresinizi girin: ");
        String mail = sc.nextLine();
        System.out.print("Telefon numaranızı girin: ");
        long telefonNo = sc.nextLong();

        try(Connection conn = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn.prepareStatement(sqlSorgu) ) {
            pstmt.setString(1, isim);
            pstmt.setString(2, soyisim);
            pstmt.setString(3, mail);
            pstmt.setLong(4, telefonNo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Bilgi güncellemesi başarılı.");
    }


    //TAMAM
    @Override
    void parolayiDegistir(int id) {
        String sqlOgrAraSorgu = "SELECT parola FROM admin WHERE adminNo="+adminNo;
        String sqlParolaDegistir = "UPDATE admin SET parola=? WHERE adminNo="+adminNo;

        Scanner scan = new Scanner(System.in);

        System.out.print("Lütfen eski şifrenizi giriniz: ");
        String tmp = scan.nextLine();

        try (Connection conn = DriverManager.getConnection(urlDatabase); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlOgrAraSorgu)){
            String eskiParola = rs.getString("parola");

            stmt.close();
            rs.close();

            degistirPass(tmp, eskiParola, sqlParolaDegistir);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //TAMAM
    private void dersEkle(){
        String sqlDersEkle = "INSERT INTO dersListesi(dersId, dersIsmi, ogrUyeId, dersSayisi, dersKredisi) VALUES (?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn.prepareStatement(sqlDersEkle)){
            System.out.print("ders Id giriniz: ");
            int dersId = scan.nextInt();
            System.out.print("ders ismi giriniz:");
            String dersIsmi = scan.next();
            System.out.print("ogretim uyesi id giriniz: ");
            int ogrUyeId = scan.nextInt();
            System.out.print("ders sayısını giriniz: ");
            int dersSayisi = scan.nextInt();
            System.out.print("ders kredisiniz giriniz: ");
            int dersKredisi = scan.nextInt();

            pstmt.setInt(1,dersId);
            pstmt.setString(2, dersIsmi);
            pstmt.setInt(3, ogrUyeId);
            pstmt.setInt(4, dersSayisi);
            pstmt.setInt(5, dersKredisi);
            pstmt.executeUpdate();

            System.out.println("Ders ekleme işlemi başarılı.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TAMAM
    private void ogrenciEkle(){
        int ogrenciNo = 0;
        String isim, soyisim, mail, parola;
        long telefonNo;

        String sqlOgrenciEkle = "INSERT INTO ogrenciler(ogrenciNo, isim, soyisim, mail, telefonNo, parola) VALUES (?,?,?,?,?,?)";
        String sqlIsUniq = "SELECT ogrenciNo FROM ogrenciler WHERE ogrenciNo="+ogrenciNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn.prepareStatement(sqlOgrenciEkle);
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlIsUniq)){



            System.out.print("Öğrenci no : ");
            ogrenciNo = scan.nextInt();

            if (!rs.next() ){
                System.out.print("İsim       : ");
                isim = scan.next();
                System.out.print("Soyisim    : ");
                soyisim = scan.next();
                System.out.print("Mail       : ");
                mail = scan.next();
                System.out.print("telefon no : ");
                telefonNo = scan.nextLong();
                System.out.print("Parola     : ");
                parola = scan.next();

                pstmt.setInt(1,ogrenciNo);
                pstmt.setString(2, isim);
                pstmt.setString(3, soyisim);
                pstmt.setString(4, mail);
                pstmt.setLong(5, telefonNo);
                pstmt.setString(6, parola);
                pstmt.executeUpdate();
                System.out.println("Öğrenci ekleme başarılı.");

            } else {
                System.out.println("Bu Öğrenci numarası zaten var !");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //TAMAM
    private void ogretimUyesiEkle(){
        int personelNo = 0;
        String sqlOgrUyeEkle = "INSERT INTO ogretimUyeleri(personelNo, isim, soyisim, mail, telefonNo, parola) VALUES (?,?,?,?,?,?)";
        String sqlIsUniq = "SELECT personelNo FROM ogretimUyeleri WHERE personelNo="+personelNo;


        String isim, soyisim, mail, parola;
        long telefonNo;

        try (Connection conn = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn.prepareStatement(sqlOgrUyeEkle);
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlIsUniq)){


            System.out.print("Öğretim üye no : ");
            personelNo = scan.nextInt();


            if (!rs.next()){
                System.out.print("İsim           : ");
                isim = scan.next();
                System.out.print("Soyisim        : ");
                soyisim = scan.next();
                System.out.print("Mail           : ");
                mail = scan.next();
                System.out.print("telefon no     : ");
                telefonNo = scan.nextLong();
                System.out.print("Parola         : ");
                parola = scan.next();

                pstmt.setInt(1,personelNo);
                pstmt.setString(2, isim);
                pstmt.setString(3, soyisim);
                pstmt.setString(4, mail);
                pstmt.setLong(5, telefonNo);
                pstmt.setString(6, parola);
                pstmt.executeUpdate();
                System.out.println("Öğretim üyesi ekleme başarılı.");

            } else {
                System.out.println("Böyle bir Öğretim üyesi  zaten var !");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //TODO: TAMAMLANACAK
//    private void dersProgramiHazirla(){
//          String sqlDersProgramiYap = "INSERT INTO dersProgrami (pazartesi, sali, carsamba, persembe, cuma) VALUES (?,?,?,?,?)";
//
//          try (Connection conn = DriverManager.getConnection(urlDatabase); PreparedStatement pstmt = conn.prepareStatement(sqlDersProgramiYap)) {
//              System.out.println("Pazartesi günü için ders id girin");
//
//              System.out.println("Salı günü için ders id girin");
//              System.out.println("Çarşamba günü için ders id girin");
//              System.out.println("Perşembe günü için ders id girin");
//              System.out.println("Cuma günü için ders id girin");
//          } catch (SQLException e) {
//               e.printStackTrace();
//          }
//    }

}
