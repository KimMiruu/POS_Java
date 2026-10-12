package ui;

import model.User;
import service.KasirService;

import javax.swing.*;
import java.awt.*;

public class HomeView {

    private User user;
    private KasirService service = new KasirService();

    public HomeView(User user) {
        this.user = user;
    }

    public void show() {

        JFrame frame = new JFrame("Dashboard");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Color sidebarColor = new Color(33, 37, 41);
        Color primary = new Color(52, 152, 219);
        Color bg = new Color(245,246,250);

        // ================= SIDEBAR =================
        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarColor);
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new GridLayout(7,1,0,10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));

        JLabel logo = new JLabel("MyApp");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnDashboard = createMenuButton("Dashboard");
        JButton btnKasir = createMenuButton("Kasir");
        JButton btnLaporan = createMenuButton("Laporan");
        JButton btnProfile = createMenuButton("Profile");
        JButton btnLogout = createMenuButton("Logout");

        sidebar.add(logo);
        sidebar.add(btnDashboard);
        sidebar.add(btnKasir);
        sidebar.add(btnLaporan);
        sidebar.add(btnProfile);
        sidebar.add(new JLabel());
        sidebar.add(btnLogout);

        frame.add(sidebar, BorderLayout.WEST);

        // ================= CONTENT =================
        CardLayout cardLayout = new CardLayout();
        JPanel content = new JPanel(cardLayout);
        content.setBackground(bg);

        // DASHBOARD
        JPanel dashboardPanel = createDashboard(bg, primary);

        // 🔥 FIX: KIRIM USER KE KASIRVIEW
        KasirView kasirPanel = new KasirView(user);

        LaporanView laporanPanel = new LaporanView(user);
        ProfileView profilePanel = new ProfileView(user);

        content.add(dashboardPanel, "dashboard");
        content.add(kasirPanel, "kasir");
        content.add(laporanPanel, "laporan");
        content.add(profilePanel, "profile");

        frame.add(content, BorderLayout.CENTER);

        // ================= NAVIGATION =================

        btnDashboard.addActionListener(e -> {
            content.remove(dashboardPanel);
            JPanel newDashboard = createDashboard(bg, primary);
            content.add(newDashboard, "dashboard");
            cardLayout.show(content, "dashboard");
        });

        btnKasir.addActionListener(e -> cardLayout.show(content, "kasir"));

        btnLaporan.addActionListener(e -> {
            laporanPanel.loadData();
            cardLayout.show(content, "laporan");
        });

        btnProfile.addActionListener(e -> cardLayout.show(content, "profile"));

        btnLogout.addActionListener(e -> {
            frame.dispose();
            new LoginView().show();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ================= DASHBOARD =================
    private JPanel createDashboard(Color bg, Color primary) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bg);

        // ❌ FIX: getNama() → getUsername()
        JLabel welcome = new JLabel("Selamat Datang, " + user.getUsername());
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));

        JPanel cards = new JPanel(new GridLayout(1,3,20,0));
        cards.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
        cards.setBackground(bg);

        // 🔥 FIX: WAJIB pakai userId
        int total = service.getTotal(user.getId());
        int jumlah = service.getAll(user.getId()).size();

        cards.add(createCard("Total Transaksi", String.valueOf(jumlah), primary));
        cards.add(createCard("Pendapatan", "Rp " + total, new Color(46,204,113)));
        cards.add(createCard("User", user.getUsername(), new Color(155,89,182)));

        panel.add(welcome, BorderLayout.NORTH);
        panel.add(cards, BorderLayout.CENTER);

        return panel;
    }

    // ================= BUTTON =================
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52,58,64));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(73,80,87));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(52,58,64));
            }
        });

        return btn;
    }

    // ================= CARD =================
    private JPanel createCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel t = new JLabel(title);
        t.setForeground(Color.WHITE);

        JLabel v = new JLabel(value);
        v.setForeground(Color.WHITE);
        v.setFont(new Font("Segoe UI", Font.BOLD, 18));

        card.add(t, BorderLayout.NORTH);
        card.add(v, BorderLayout.CENTER);

        return card;
    }
}
