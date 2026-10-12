package ui;

import service.AuthService;

import javax.swing.*;
import java.awt.*;

public class RegisterView {

    private AuthService auth = new AuthService();

    public void show() {

        JFrame frame = new JFrame("Register");
        frame.setSize(400, 520);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color bg = new Color(240,242,245);
        Color primary = new Color(46,204,113);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(bg);

        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(320, 420));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(25,25,25,25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,0,8,0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        // ================= TITLE =================
        JLabel title = new JLabel("Register");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        card.add(title, gbc);

        // ================= EMAIL =================
        gbc.gridy++;
        card.add(new JLabel("Email"), gbc);

        JTextField email = new JTextField();
        email.setPreferredSize(new Dimension(0, 35));
        gbc.gridy++;
        card.add(email, gbc);

        // ================= USERNAME =================
        gbc.gridy++;
        card.add(new JLabel("Username"), gbc);

        JTextField username = new JTextField();
        username.setPreferredSize(new Dimension(0, 35));
        gbc.gridy++;
        card.add(username, gbc);

        // ================= PASSWORD =================
        gbc.gridy++;
        card.add(new JLabel("Password"), gbc);

        JPasswordField password = new JPasswordField();
        password.setPreferredSize(new Dimension(0, 35));
        gbc.gridy++;
        card.add(password, gbc);

        // ================= BUTTON REGISTER =================
        JButton register = new JButton("Register");
        register.setBackground(primary);
        register.setForeground(Color.WHITE);
        register.setFocusPainted(false);

        gbc.gridy++;
        gbc.insets = new Insets(15,0,5,0);
        card.add(register, gbc);

        // ================= BACK BUTTON =================
        JButton back = new JButton("Kembali ke Login");
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setForeground(primary);

        gbc.gridy++;
        gbc.insets = new Insets(5,0,0,0);
        card.add(back, gbc);

        root.add(card);
        frame.add(root);

        // ================= ACTION =================
        register.addActionListener(e -> {

            String em = email.getText().trim();
            String user = username.getText().trim();
            String pass = new String(password.getPassword());

            if (em.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Semua field wajib diisi!");
                return;
            }

            // validasi sederhana email
            if (!em.contains("@")) {
                JOptionPane.showMessageDialog(frame, "Format email tidak valid!");
                return;
            }

            if (auth.register(em, user, pass)) {
                JOptionPane.showMessageDialog(frame, "Register berhasil!");
                frame.dispose();
                new LoginView().show();
            } else {
                JOptionPane.showMessageDialog(frame, "Email sudah digunakan / gagal!");
            }
        });

        back.addActionListener(e -> {
            frame.dispose();
            new LoginView().show();
        });

        frame.setVisible(true);
    }
}
