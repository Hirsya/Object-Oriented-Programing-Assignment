import java.util.ArrayList;

public class Akun {

    private String nama;
    private double uang;
    private String password;
    private int transaksi;

    private static ArrayList<Akun> daftar = new ArrayList<>();

    public Akun() {
        this.nama = "Belum mengisi nama";
        this.uang = 0;
        this.password = "";
    }

    public Akun(String nama, double uang, String password) {
        this.nama = nama;
        this.uang = uang;
        this.password = password;
    }

    // Getter
    public String getNama() { return nama; }
    public double getUang() { return uang; }
    public String getPassword() { return password; }
    public int getTransaksi() { return transaksi; }

    // Setter
    public void setNama(String nama) { this.nama = nama; }
    public void setPassword(String password) { this.password = password; }
    

    // Method
    public void deposit(double uang) {
        this.uang += uang;
        this.transaksi++;
    }

    public void tarikUang(double uang) {
        this.uang -= uang;
        this.transaksi++;
    }

    public static boolean daftar(String nama, double uang, String pass) {
        for (Akun a : daftar) {
            if (a.nama.equals(nama)) return false;
        }
        daftar.add(new Akun(nama, uang, pass));
        return true;
    }

    public static Akun login(String nama, String pass) {
        for (Akun a : daftar) {
            if (a.nama.equals(nama) && a.password.equals(pass)) return a;
        }
        return null;
    }
}
