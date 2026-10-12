package ui;

import model.User;

import javax.swing.*;
import java.awt.*;

public class ProfileView extends JPanel {

    public ProfileView(User user) {

        setLayout(new BorderLayout());
        setBackground(new Color(245,246,250));

        // ================= MAIN CONTAINER =================
        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(500, 450));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        // ================= AVATAR =================
        JLabel avatar = new JLabel("👤", JLabel.CENTER);
        avatar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ================= NAME (FIXED) =================
        JLabel nama = new JLabel(user.getUsername()); // 🔥 FIX
        nama.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nama.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ================= ROLE =================
        JLabel role = new JLabel(user.getRole());
        role.setOpaque(true);
        role.setBackground(new Color(52,152,219));
        role.setForeground(Color.WHITE);
        role.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        role.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ================= DIVIDER =================
        JSeparator separator = new JSeparator();

        // ================= INFO PANEL =================
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3,1,10,10));
        infoPanel.setOpaque(false);

        infoPanel.add(createInfo("Username", user.getUsername())); // 🔥 FIX
        infoPanel.add(createInfo("Email", user.getEmail()));
        infoPanel.add(createInfo("Role", user.getRole()));

        // ================= BUTTON =================
        JPanel btnPanel = new JPanel(new GridLayout(1,2,10,0));
        btnPanel.setOpaque(false);

        JButton editBtn = new JButton("Edit");
        JButton logoutBtn = new JButton("Logout");

        editBtn.setBackground(new Color(46,204,113));
        editBtn.setForeground(Color.WHITE);
        editBtn.setFocusPainted(false);

        logoutBtn.setBackground(new Color(231,76,60));
        logoutBtn.setForeground(Color.WHITE);

        btnPanel.add(editBtn);
        btnPanel.add(logoutBtn);

        // ================= EVENT =================
        editBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fitur edit belum dibuat 😄");
        });

        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logout dari sidebar ya 👈");
        });

        // ================= ADD COMPONENT =================
        card.add(avatar);
        card.add(Box.createVerticalStrut(15));
        card.add(nama);
        card.add(Box.createVerticalStrut(10));
        card.add(role);
        card.add(Box.createVerticalStrut(20));
        card.add(separator);
        card.add(Box.createVerticalStrut(20));
        card.add(infoPanel);
        card.add(Box.createVerticalStrut(20));
        card.add(btnPanel);

        container.add(card);
        add(container, BorderLayout.CENTER);
    }

    // ================= COMPONENT INFO =================
    private JPanel createInfo(String label, String value) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setForeground(Color.GRAY);

        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 14));

        panel.add(l, BorderLayout.NORTH);
        panel.add(v, BorderLayout.CENTER);

        return panel;
    }
}
