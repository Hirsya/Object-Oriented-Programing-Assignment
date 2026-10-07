import java.awt.*;
import javax.swing.*;

public class MenuGUI {

    private JFrame frame;
    private JComboBox<String> cbJenis;
    private JTextField tfNilai, tfTinggi, tfWarna;
    private JButton btnHitung;
    private JLabel lblTinggi;

    public MenuGUI() {
        frame = new JFrame("Menu Bentuk");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        cbJenis = new JComboBox<>(new String[]{"Lingkaran", "BujurSangkar", "Silinder"});
        tfNilai = new JTextField(10);
        tfTinggi = new JTextField(10);
        tfWarna = new JTextField(10);
        btnHitung = new JButton("Hitung");
        lblTinggi = new JLabel("Tinggi:");

        JPanel panelInput = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        addRow(panelInput, g, 0, new JLabel("Jenis:"), cbJenis);
        addRow(panelInput, g, 1, new JLabel("Nilai (radius/sisi):"), tfNilai);
        addRow(panelInput, g, 2, lblTinggi, tfTinggi);
        addRow(panelInput, g, 3, new JLabel("Warna:"), tfWarna);

        JPanel panelTombol = new JPanel(new FlowLayout());
        btnHitung.setPreferredSize(new Dimension(120, 35));
        panelTombol.add(btnHitung);

        frame.add(panelInput, BorderLayout.NORTH);
        frame.add(panelTombol, BorderLayout.CENTER);

        btnHitung.addActionListener(e -> hitung());
        cbJenis.addActionListener(e -> updateTinggi());

        updateTinggi();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addRow(JPanel panel, GridBagConstraints g, int row, JLabel label, JComponent field) {
        g.gridx = 0;
        g.gridy = row;
        panel.add(label, g);
        g.gridx = 1;
        g.gridy = row;
        field.setPreferredSize(new Dimension(160, 26));
        panel.add(field, g);
    }

    private void updateTinggi() {
        boolean silinder = "Silinder".equals(cbJenis.getSelectedItem());
        lblTinggi.setEnabled(silinder);
        tfTinggi.setEnabled(silinder);
    }

    private void hitung() {
        try {
            String jenis = (String) cbJenis.getSelectedItem();
            double nilai = Double.parseDouble(tfNilai.getText());
            String warna = tfWarna.getText();
            double hasil;
            String label;

            if ("Lingkaran".equals(jenis)) {
                hasil = new Lingkaran(nilai, warna).hitungLuas();
                label = "Luas Lingkaran";
            } else if ("BujurSangkar".equals(jenis)) {
                hasil = new BujurSangkar(nilai, warna).hitungLuas();
                label = "Luas Bujur Sangkar";
            } else {
                double tinggi = Double.parseDouble(tfTinggi.getText());
                hasil = new Silinder(tinggi, nilai, warna).hitungVolume();
                label = "Volume Silinder";
            }

            JOptionPane.showMessageDialog(frame,
                    label + " " + warna + " = " + String.format("%.3f", hasil),
                    "Hasil Perhitungan",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Input tidak valid! Masukkan angka.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuGUI());
    }
}
