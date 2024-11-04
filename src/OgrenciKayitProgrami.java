import java.io.*;
import java.util.Scanner;

public class OgrenciKayitProgrami {

    static final String DOSYA_ADI = "ogrenci_kayit.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Lütfen yapmak istediğiniz işlemi seçiniz:");
            System.out.println("1. Yeni öğrenci kaydetme");
            System.out.println("2. Öğrencileri listeleme");
            System.out.println("3. Not güncelleme");
            System.out.println("4. Çıkış");
            System.out.print("Seçiminiz: ");

            int secim = scanner.nextInt();
            scanner.nextLine(); // Dummy nextLine() to consume newline character

            switch (secim) {
                case 1:
                    yeniKayit();
                    break;
                case 2:
                    ogrencileriListele();
                    break;
                case 3:
                    notGuncelle();
                    break;
                case 4:
                    System.out.println("Programdan çıkılıyor...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Geçersiz bir seçim yaptınız. Lütfen tekrar deneyin.");
                    break;
            }
        }
    }

    public static void yeniKayit() {
        try (FileWriter writer = new FileWriter(DOSYA_ADI, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            Scanner scanner = new Scanner(System.in);

            System.out.print("Öğrencinin adı soyadı: ");
            String adSoyad = scanner.nextLine();
            System.out.print("Öğrencinin numarası: ");
            String numara = scanner.nextLine();
            System.out.print("Öğrencinin doğum yılı: ");
            String dogumYili = scanner.nextLine();
            System.out.print("Öğrencinin not ortalaması: ");
            String notOrtalamasi = scanner.nextLine();

            printWriter.println(adSoyad + ", " + numara + ", " + dogumYili + ", " + notOrtalamasi);
            System.out.println("Öğrenci başarıyla kaydedildi.");

        } catch (IOException e) {
            System.out.println("Dosya işlemleri sırasında bir hata oluştu: " + e.getMessage());
        }
    }

    public static void ogrencileriListele() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DOSYA_ADI))) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("İsmi hangi harfle başlayan öğrencileri listelemek istiyorsunuz: ");
            String harf = scanner.nextLine().toLowerCase();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] ogrenci = line.split(", ");
                if (ogrenci[0].toLowerCase().startsWith(harf)) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Dosya işlemleri sırasında bir hata oluştu: " + e.getMessage());
        }
    }

    public static void notGuncelle() {
        try {
            File tempFile = new File("temp.txt");
            File file = new File(DOSYA_ADI);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            Scanner scanner = new Scanner(System.in);
            System.out.print("Notunu güncellemek istediğiniz öğrencinin numarası: ");
            String numara = scanner.nextLine();
            System.out.print("Yeni not: ");
            String yeniNot = scanner.nextLine();

            String line;
            boolean bulundu = false;

            while ((line = reader.readLine()) != null) {
                String[] ogrenci = line.split(", ");
                if (ogrenci[1].equals(numara)) {
                    ogrenci[3] = yeniNot;
                    line = String.join(", ", ogrenci);
                    bulundu = true;
                }
                writer.write(line + "\n");
            }

            reader.close();
            writer.close();

            if (!bulundu) {
                System.out.println("Öğrenci bulunamadı.");
                return;
            }

            if (!file.delete()) {
                System.out.println("Eski dosya silinemedi.");
                return;
            }

            if (!tempFile.renameTo(file)) {
                System.out.println("Dosya adı değiştirilemedi.");
                return;
            }

            System.out.println("Not başarıyla güncellendi.");

        } catch (IOException e) {
            System.out.println("Dosya işlemleri sırasında bir hata oluştu: " + e.getMessage());
        }
    }
}
