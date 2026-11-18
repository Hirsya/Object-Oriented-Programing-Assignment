import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AstralMessege {
    public static void main(String[] args){
        JFrame frame = new JFrame();     
        frame.setTitle("Astral Messanger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1000,650);
        frame.setLocationRelativeTo(null);
        
        frame.setIconImage(loadIcon("icon frame.jpg", 32, 32).getImage());
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new BorderLayout(10,10));

        Image logoWatermark = loadIcon("logo.png", 160, 160).getImage();
        JPanel panelKiri = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
                int x = (getWidth() - 160) / 2;
                int y = (getHeight() - 160) / 2 - 20;
                g2.drawImage(logoWatermark, x, y, 160, 160, this);
                g2.dispose();
            }
        };
        panelKiri.setBackground(Color.white);
        panelKiri.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        JPanel panelKanan = new JPanel();
        panelKanan.setLayout(new BoxLayout(panelKanan, BoxLayout.Y_AXIS));
        panelKanan.setBackground(Color.white);
        panelKanan.setPreferredSize(new Dimension(200, 0));
        panelKanan.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            new EmptyBorder(15, 12, 15, 12)
        ));
        ((JPanel) frame.getContentPane()).setBorder(new EmptyBorder(12,12,12,12));

        JPanel chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        chatListPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(chatListPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        JTextField inputPesan = new JTextField();
        inputPesan.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputPesan.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        JPanel typingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        typingPanel.setOpaque(false);

        JLabel lblTypingAvatar = new JLabel(loadIcon("icon frame.jpg", 32, 32));
        lblTypingAvatar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        JLabel lblTypingText = new JLabel("Aha ..... (typing)");
        lblTypingText.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblTypingText.setForeground(Color.DARK_GRAY);

        typingPanel.add(lblTypingAvatar);
        typingPanel.add(lblTypingText);
        typingPanel.setVisible(false);

        JPanel panelBawah = new JPanel();
        panelBawah.setLayout(new BoxLayout(panelBawah, BoxLayout.Y_AXIS));
        panelBawah.setOpaque(false);
        panelBawah.add(typingPanel);
        panelBawah.add(Box.createVerticalStrut(5));
        panelBawah.add(inputPesan);     
        
        tambahBalonChat(chatListPanel, "Aha", "icon frame.jpg", "Ohh so you finally talk to me now?", false);
        final String[] karakterAktif = {"Aha"};
        final String[] avatarAktif = {"icon frame.jpg"};

        JButton btnAha = createContactButton("Aha", "icon frame.jpg");
        btnAha.addActionListener(e -> {
            karakterAktif[0] = "Aha";
            avatarAktif[0] = "icon frame.jpg";
            chatListPanel.removeAll();
            tambahBalonChat(chatListPanel, "Aha", "icon frame.jpg", "Ohh so you finally talk to me now?", false);
        });
        JButton btnSilverWolf = createContactButton("Silver Wolf", "siwol.jpg");
        btnSilverWolf.addActionListener(e -> {
            karakterAktif[0] = "Silver Wolf";
            avatarAktif[0] = "siwol.jpg";
            chatListPanel.removeAll();
            tambahBalonChat(chatListPanel, "Silver Wolf", "siwol.jpg", "Sup? you're at collage right now?", false);
        });
        JButton btnYaoGuang = createContactButton("Yao Guang", "yaoguang.jpg");
        btnYaoGuang.addActionListener(e -> {
            karakterAktif[0] = "Yao Guang";
            avatarAktif[0] = "yaoguang.jpg";
            chatListPanel.removeAll();
            tambahBalonChat(chatListPanel, "Yao Guang", "yaoguang.jpg", "Im sorry miss this only a silly project", false);
        });

        inputPesan.addActionListener(e -> {
            String pesan = inputPesan.getText().trim();
            if (!pesan.isEmpty()) {
                tambahBalonChat(chatListPanel, "Hirsya", "avatar.jpg", pesan, true);
                inputPesan.setText("");
                String namaPengirim = karakterAktif[0];
                String fileAvatar = avatarAktif[0];
                lblTypingAvatar.setIcon(loadIcon(fileAvatar, 32, 32));
                lblTypingText.setText(namaPengirim.toLowerCase() + " ..... (typing)");
                typingPanel.setVisible(true);
                SwingUtilities.invokeLater(() -> {
                    JScrollBar vertical = scrollPane.getVerticalScrollBar();
                    vertical.setValue(vertical.getMaximum());
                });
                javax.swing.Timer timerBalasan = new javax.swing.Timer(1200, evt -> {
                    typingPanel.setVisible(false);

                    String balasan;
                    if (namaPengirim.equalsIgnoreCase("Aha")) {
                        balasan = "Hahaha! Now that is hilarious! Tell me more!";
                    } else if (namaPengirim.equalsIgnoreCase("Silver Wolf")) {
                        balasan = "Hold on, I'm in the middle of a raid. I'll text you back later.";
                    } else {
                        balasan = "Understood. Your message has been received.";
                    }

                    tambahBalonChat(chatListPanel, namaPengirim, fileAvatar, balasan, false);

                    SwingUtilities.invokeLater(() -> {
                        JScrollBar vertical = scrollPane.getVerticalScrollBar();
                        vertical.setValue(vertical.getMaximum());
                    });
                });
                timerBalasan.setRepeats(false);
                timerBalasan.start(); 
            }
        });
        
        panelKiri.add(scrollPane, BorderLayout.CENTER);
        panelKiri.add(panelBawah, BorderLayout.SOUTH);
        panelKanan.add(btnAha);
        panelKanan.add(Box.createVerticalStrut(10));
        panelKanan.add(btnSilverWolf);
        panelKanan.add(Box.createVerticalStrut(10));
        panelKanan.add(btnYaoGuang);
        
        frame.add(panelKiri, BorderLayout.CENTER);
        frame.add(panelKanan, BorderLayout.EAST);
        frame.setVisible(true);
    }
    
    public static ImageIcon loadIcon(String fileName, int width, int height) {
    ImageIcon originalIcon = new ImageIcon("Assets/" + fileName);
    Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    return new ImageIcon(scaledImage);
    }   

    public static JButton createContactButton(String namaKarakter, String namaFileIcon) {
    JButton btn = new JButton(namaKarakter, loadIcon(namaFileIcon, 36, 36));
    btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55)); 
    btn.setHorizontalAlignment(SwingConstants.LEFT);       
    btn.setIconTextGap(15);                                   
    btn.setFont(new Font("SansSerif", Font.BOLD, 14));
    btn.setBackground(new Color(245, 246, 248));
    btn.setForeground(new Color(40, 45, 55));
    btn.setFocusPainted(false);                              
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));            
    btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 185, 195), 1, true),
            new EmptyBorder(8, 12, 8, 12)
    ));
    return btn;
    }

    public static void tambahBalonChat(JPanel chatListPanel, String namaPengirim, String fileAvatar, String isiPesan, boolean isUser) {
        JPanel barisPesan = new JPanel(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT, 10, 6));
        barisPesan.setOpaque(false);
        barisPesan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        JPanel kotakTeks = new JPanel();
        kotakTeks.setLayout(new BoxLayout(kotakTeks, BoxLayout.Y_AXIS));
        kotakTeks.setOpaque(false);

        JLabel lblNama = new JLabel(namaPengirim);
        lblNama.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblNama.setForeground(Color.DARK_GRAY);
        lblNama.setAlignmentX(isUser ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT);

        JLabel lblIsi = new JLabel(isiPesan);
        lblIsi.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblIsi.setOpaque(true);
        lblIsi.setBackground(isUser ? new Color(225, 212, 180) : Color.WHITE);
        lblIsi.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));
        lblIsi.setAlignmentX(isUser ? Component.RIGHT_ALIGNMENT : Component.LEFT_ALIGNMENT);

        kotakTeks.add(lblNama);
        kotakTeks.add(Box.createVerticalStrut(3));
        kotakTeks.add(lblIsi);

        JLabel lblAvatar = new JLabel(loadIcon(fileAvatar, 38, 38));
        lblAvatar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        if (isUser) {
            barisPesan.add(kotakTeks);
            barisPesan.add(lblAvatar);
        } else {
            barisPesan.add(lblAvatar);
            barisPesan.add(kotakTeks);
        }
        chatListPanel.add(barisPesan);
        chatListPanel.revalidate();
        chatListPanel.repaint();
    }
}