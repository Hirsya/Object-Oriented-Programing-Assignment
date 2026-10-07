import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean jalan = true;

        while (jalan) {
            System.out.println("=== Menu Bentuk ===");
            System.out.println("1. Lingkaran");
            System.out.println("2. BujurSangkar");
            System.out.println("3. Silinder");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");

            String pilih = sc.nextLine().trim();
            Bentuk bentuk;

            switch (pilih) {
                case "1":
                    bentuk = new Lingkaran(bacaAngka(sc, "Radius: "), bacaWarna(sc));
                    break;
                case "2":
                    bentuk = new BujurSangkar(bacaAngka(sc, "Sisi: "), bacaWarna(sc));
                    break;
                case "3":
                    double radius = bacaAngka(sc, "Radius: ");
                    double tinggi = bacaAngka(sc, "Tinggi: ");
                    bentuk = new Silinder(tinggi, radius, bacaWarna(sc));
                    break;
                case "0":
                    jalan = false;
                    continue;
                default:
                    System.out.println("Pilihan tidak valid.\n");
                    continue;
            }

            bentuk.printInfo();
            System.out.println();
        }

        System.out.println("Selesai.");
        sc.close();
    }

    static double bacaAngka(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Harus berupa angka, coba lagi.");
            }
        }
    }

    static String bacaWarna(Scanner sc) {
        System.out.print("Warna: ");
        return sc.nextLine().trim();
    }
}
