package ui;

import model.Item;
import model.User;
import service.KasirService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class KasirView extends JPanel {

    private KasirService service = new KasirService();
    private DefaultTableModel model;
    private JLabel labelTotal;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;

    private User currentUser; // 🔥 WAJIB

    public KasirView(User user) {

        this.currentUser = user; // 🔥 SIMPAN USER LOGIN

        setLayout(new BorderLayout());
        setBackground(new Color(245, 246, 250));

        Color primary = new Color(52, 152, 219);

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Kasir");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JTextField search = new JTextField();
        search.setPreferredSize(new Dimension(200, 35));

        header.add(title, BorderLayout.WEST);
        header.add(search, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // FORM
        JPanel formCard = new JPanel(new GridLayout(8,1,10,10));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JTextField nama = new JTextField();
        JTextField harga = new JTextField();
        JTextField qty = new JTextField();

        JButton tambah = new JButton("Tambah");
        JButton clear = new JButton("Reset");

        tambah.setBackground(primary);
        tambah.setForeground(Color.WHITE);

        formCard.add(new JLabel("Nama Barang"));
        formCard.add(nama);
        formCard.add(new JLabel("Harga"));
        formCard.add(harga);
        formCard.add(new JLabel("Qty"));
        formCard.add(qty);
        formCard.add(tambah);
        formCard.add(clear);

        // TABLE
        String[] kolom = {"Nama", "Harga", "Qty", "Total"};
        model = new DefaultTableModel(kolom, 0);

        table = new JTable(model);
        table.setRowHeight(30);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scroll = new JScrollPane(table);

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        tableCard.add(scroll, BorderLayout.CENTER);

        JPanel center = new JPanel(new GridLayout(1,2,20,0));
        center.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        center.setBackground(new Color(245,246,250));

        center.add(formCard);
        center.add(tableCard);

        add(center, BorderLayout.CENTER);

        // FOOTER
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton hapus = new JButton("Hapus Item");
        JButton clearAll = new JButton("Clear Semua");

        JPanel left = new JPanel();
        left.setBackground(Color.WHITE);
        left.add(clearAll);
        left.add(hapus);

        JPanel right = new JPanel();
        right.setBackground(primary);

        labelTotal = new JLabel("Total: Rp 0");
        labelTotal.setForeground(Color.WHITE);
        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));

        right.add(labelTotal);

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);

        // ================= EVENT =================

        // TAMBAH
        tambah.addActionListener(e -> {
            try {
                String n = nama.getText().trim();
                int h = Integer.parseInt(harga.getText());
                int q = Integer.parseInt(qty.getText());

                if (n.isEmpty() || h <= 0 || q <= 0) throw new Exception();

                service.tambahItem(
                        new Item(n, h, q),
                        currentUser.getId() // 🔥 FIX USER ID
                );

                loadData();

                nama.setText("");
                harga.setText("");
                qty.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!");
            }
        });

        // HAPUS
        hapus.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row >= 0) {
                String namaBarang = model.getValueAt(
                        table.convertRowIndexToModel(row), 0
                ).toString();

                service.hapusItem(namaBarang, currentUser.getId()); // 🔥 FIX
                loadData();
            }
        });

        // CLEAR INPUT
        clear.addActionListener(e -> {
            nama.setText("");
            harga.setText("");
            qty.setText("");
        });

        // CLEAR SEMUA
        clearAll.addActionListener(e -> {
            service.clearAll(currentUser.getId()); // 🔥 FIX
            loadData();
        });

        // SEARCH
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = search.getText();
                if (text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        loadData();
    }

    // ================= LOAD DATA =================
    public void loadData() {

        model.setRowCount(0);

        for (Item item : service.getAll(currentUser.getId())) { // 🔥 FIX
            model.addRow(new Object[]{
                    item.getNama(),
                    item.getHarga(),
                    item.getQty(),
                    item.getTotal()
            });
        }

        labelTotal.setText("Total: Rp " + service.getTotal(currentUser.getId())); // 🔥 FIX
    }
}
