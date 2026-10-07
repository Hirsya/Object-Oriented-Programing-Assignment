import java.awt.*;
import javax.swing.*;

public class Bank {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private Akun aktif;
    private JTextField loginNama, regNama, regUang;
    private JPasswordField loginPass, regPass;
    private JLabel dashNama, dashUang, dashTransaksi;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Bank().start());
    }

    private void start() {
        cards.add(panel("Login Akun", buatLogin()), "login");
        cards.add(panel("Daftar Akun", buatDaftar()), "register");
        cards.add(panel("Dasbor Akun", buatDasbor()), "dashboard"); 

        JFrame frame = new JFrame("Bank Sederhana");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.add(cards);
        frame.setVisible(true);
    }

    private JPanel panel(String judul, JPanel isi) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.insets = new Insets(0, 0, 15, 0);
        p.add(new JLabel(judul), c);
        c.insets = new Insets(5, 5, 5, 5);
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        p.add(isi, c);
        return p;
    }

    private JPanel buatLogin() {
        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));
        loginNama = new JTextField();
        loginPass = new JPasswordField();
        JButton login = new JButton("Login");
        JButton daftar = new JButton("Daftar");
        login.addActionListener(e -> {
            Akun a = Akun.login(loginNama.getText().trim(), new String(loginPass.getPassword()));
            if (a == null) {
                JOptionPane.showMessageDialog(null, "Nama atau password salah.");
                return;
            }
            aktif = a;
            refreshDasbor();
        });
        daftar.addActionListener(e -> cardLayout.show(cards, "register"));
        p.add(new JLabel("Nama:")); p.add(loginNama);
        p.add(new JLabel("Password:")); p.add(loginPass);
        p.add(login); p.add(daftar);
        return p;
    }

    private JPanel buatDaftar() {
        JPanel p = new JPanel(new GridLayout(4, 2, 5, 5));
        regNama = new JTextField();
        regPass = new JPasswordField();
        regUang = new JTextField();
        JButton daftar = new JButton("Daftar");
        JButton kembali = new JButton("Login");
        daftar.addActionListener(e -> {
            String nama = regNama.getText().trim();
            String pass = new String(regPass.getPassword());
            String uangStr = regUang.getText().trim();
            if (nama.isEmpty() || pass.isEmpty() || uangStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field wajib diisi.");
                return;
            }
            double uang;
            try {
                uang = Double.parseDouble(uangStr);
                if (uang < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Uang awal harus angka positif.");
                return;
            }
            if (!Akun.daftar(nama, uang, pass)) {
                JOptionPane.showMessageDialog(null, "Nama akun sudah terdaftar.");
                return;
            }
            JOptionPane.showMessageDialog(null, "Akun berhasil dibuat. Silakan login.");
            regNama.setText(""); regPass.setText(""); regUang.setText("");
            cardLayout.show(cards, "login");
        });
        kembali.addActionListener(e -> cardLayout.show(cards, "login"));
        p.add(new JLabel("Nama:")); p.add(regNama);
        p.add(new JLabel("Password:")); p.add(regPass);
        p.add(new JLabel("Uang Awal:")); p.add(regUang);
        p.add(daftar); p.add(kembali);
        return p;
    }

    private JPanel buatDasbor() {
        JPanel p = new JPanel(new GridLayout(5, 2, 5, 5));
        dashNama = new JLabel("-");
        dashUang = new JLabel("-");
        dashTransaksi = new JLabel("-");
        JButton deposit = new JButton("Deposit");
        JButton tarik = new JButton("Tarik Uang");
        JButton keluar = new JButton("Keluar");
        deposit.addActionListener(e -> transaksi(true));
        tarik.addActionListener(e -> transaksi(false));
        keluar.addActionListener(e -> {
            loginNama.setText(""); loginPass.setText("");
            aktif = null;
            cardLayout.show(cards, "login");
        });
        p.add(new JLabel("Nama Akun:")); p.add(dashNama);
        p.add(new JLabel("Jumlah Uang:")); p.add(dashUang);
        p.add(new JLabel("Jumlah Transaksi:")); p.add(dashTransaksi);
        p.add(deposit); p.add(tarik);
        p.add(keluar);
        return p;
    }

    private void transaksi(boolean isDeposit) {
        if (aktif == null) return;
        String input = JOptionPane.showInputDialog(isDeposit ? "Jumlah deposit:" : "Jumlah tarik uang:");
        if (input == null || input.trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Transaksi Gagal/Dibatalkan.");
            return;
        }
        try {
            double nilai = Double.parseDouble(input.trim());
            if (nilai <= 0) throw new NumberFormatException();
            if (!isDeposit) {
                if (nilai > aktif.getUang()) {
                    JOptionPane.showMessageDialog(null, "Saldo tidak cukup.");
                    return;
                }
                aktif.tarikUang(nilai);
                JOptionPane.showMessageDialog(null, "Penarikan berhasil.");
            } else {
                aktif.deposit(nilai);
                JOptionPane.showMessageDialog(null, "Deposit berhasil.");
            }
            refreshDasbor();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Masukkan angka positif yang valid.");
        }
    }

    private void refreshDasbor() {
        dashNama.setText(aktif.getNama());
        dashUang.setText("Rp " + String.format("%,.2f", aktif.getUang()));
        dashTransaksi.setText(String.valueOf(aktif.getTransaksi()));
        cardLayout.show(cards, "dashboard");
    }
}
