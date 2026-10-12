package ui;

import javax.swing.*;
import java.awt.*;

import model.User;
import service.AuthService;

public class LoginView {

    private AuthService auth = new AuthService();

    public void show() {

        JFrame frame = new JFrame("Login");
        frame.setSize(400, 480);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color bg = new Color(240,242,245);
        Color primary = new Color(52,152,219);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(bg);

        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(320, 380));
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
        JLabel title = new JLabel("Login");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 0;
        card.add(title, gbc);

        // ================= EMAIL =================
        gbc.gridy++;
        card.add(new JLabel("Email"), gbc);

        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(0, 35));
        gbc.gridy++;
        card.add(emailField, gbc);

        // ================= PASSWORD =================
        gbc.gridy++;
        card.add(new JLabel("Password"), gbc);

        JPasswordField password = new JPasswordField();
        password.setPreferredSize(new Dimension(0, 35));
        gbc.gridy++;
        card.add(password, gbc);

        // ================= BUTTON LOGIN =================
        JButton login = new JButton("Login");
        login.setBackground(primary);
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);

        gbc.gridy++;
        gbc.insets = new Insets(15,0,5,0);
        card.add(login, gbc);

        // ================= REGISTER LINK =================
        JButton register = new JButton("Belum punya akun? Register");
        register.setBorderPainted(false);
        register.setContentAreaFilled(false);
        register.setForeground(primary);

        gbc.gridy++;
        gbc.insets = new Insets(5,0,0,0);
        card.add(register, gbc);

        root.add(card);
        frame.add(root);

        // ================= ACTION =================
        login.addActionListener(e -> {

            String email = emailField.getText().trim();
            String pass = new String(password.getPassword());

            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Isi semua field!");
                return;
            }

            User user = auth.login(email, pass); // 🔥 FIX

            if (user != null) {
                JOptionPane.showMessageDialog(frame, "Login berhasil!");
                frame.dispose();
                new HomeView(user).show();
            } else {
                JOptionPane.showMessageDialog(frame, "Email atau password salah!");
            }
        });

        register.addActionListener(e -> {
            frame.dispose();
            new RegisterView().show();
        });

        frame.setVisible(true);
    }
}
