package ui;

import model.Item;
import model.User;
import service.KasirService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LaporanView extends JPanel {

    private KasirService service = new KasirService();
    private DefaultTableModel model;
    private JLabel totalLabel;
    private JLabel jumlahLabel;
    private JTable table;

    private User currentUser; // 🔥 WAJIB

    public LaporanView(User user) {

        this.currentUser = user;

        setLayout(new BorderLayout());
        setBackground(new Color(245,246,250));

        Color primary = new Color(52,152,219);

        // ================= HEADER =================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));

        JLabel title = new JLabel("Laporan Transaksi");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // ================= SUMMARY =================
        JPanel summary = new JPanel(new GridLayout(1,2,20,0));
        summary.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        summary.setBackground(new Color(245,246,250));

        totalLabel = new JLabel();
        jumlahLabel = new JLabel();

        summary.add(createCard("Total Pendapatan", totalLabel, primary));
        summary.add(createCard("Jumlah Transaksi", jumlahLabel, new Color(46,204,113)));

        add(summary, BorderLayout.NORTH);

        // ================= TABLE =================
        String[] kolom = {"Barang", "Harga", "Qty", "Total"};
        model = new DefaultTableModel(kolom, 0);

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(table);

        // ================= ACTION BUTTON =================
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionPanel.setBackground(Color.WHITE);

        JButton btnHapus = new JButton("Hapus");
        JButton btnClear = new JButton("Clear Semua");
        JButton btnPrint = new JButton("Print");

        actionPanel.add(btnHapus);
        actionPanel.add(btnClear);
        actionPanel.add(btnPrint);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230,230,230)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        tableCard.add(actionPanel, BorderLayout.NORTH);
        tableCard.add(scroll, BorderLayout.CENTER);

        add(tableCard, BorderLayout.CENTER);

        // ================= EVENT =================

        // 🔥 HAPUS ITEM (USER FILTER)
        btnHapus.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                String nama = model.getValueAt(row, 0).toString();

                service.hapusItem(nama, currentUser.getId()); // 🔥 FIX

                loadData();

            } else {
                JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            }
        });

        // 🔥 CLEAR SEMUA (USER ONLY)
        btnClear.addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin hapus semua data?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                service.clearAll(currentUser.getId()); // 🔥 FIX
                loadData();
            }
        });

        // 🔥 PRINT
        btnPrint.addActionListener(e -> {
            try {
                boolean printed = table.print();
                if (printed) {
                    JOptionPane.showMessageDialog(this, "Berhasil print!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal print!");
            }
        });

        loadData();
    }

    // ================= LOAD DATA =================
    public void loadData() {

        model.setRowCount(0);

        int total = 0;

        for (Item item : service.getAll(currentUser.getId())) { // 🔥 FIX USER ID

            model.addRow(new Object[]{
                    item.getNama(),
                    item.getHarga(),
                    item.getQty(),
                    item.getTotal()
            });

            total += item.getTotal();
        }

        totalLabel.setText("Rp " + total);
        jumlahLabel.setText(String.valueOf(service.getAll(currentUser.getId()).size())); // 🔥 FIX
    }

    public void refresh() {
        loadData();
    }

    // ================= CARD =================
    private JPanel createCard(String title, JLabel valueLabel, Color color) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel t = new JLabel(title);
        t.setForeground(Color.WHITE);

        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        card.add(t, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
