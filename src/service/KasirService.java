package service;

import config.Database;
import model.Item;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class KasirService {

    public void tambahItem(Item item, int userId) {
        try {
            Connection conn = Database.connect();

            String sql = "INSERT INTO transaksi(nama_barang, harga, qty, total, user_id) VALUES(?,?,?,?,?)";

            var ps = conn.prepareStatement(sql);
            ps.setString(1, item.getNama());
            ps.setInt(2, item.getHarga());
            ps.setInt(3, item.getQty());
            ps.setInt(4, item.getTotal());
            ps.setInt(5, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Item> getAll(int userId) {

        List<Item> list = new ArrayList<>();

        try {
            Connection conn = Database.connect();

            String sql = "SELECT * FROM transaksi WHERE user_id=?";

            var ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            var rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Item(
                        rs.getString("nama_barang"),
                        rs.getInt("harga"),
                        rs.getInt("qty")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void hapusItem(String nama, int userId) {
        try {
            Connection conn = Database.connect();

            String sql = "DELETE FROM transaksi WHERE nama_barang=? AND user_id=? LIMIT 1";

            var ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setInt(2, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearAll(int userId) {
        try {
            Connection conn = Database.connect();

            String sql = "DELETE FROM transaksi WHERE user_id=?";

            var ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotal(int userId) {

        int total = 0;

        try {
            Connection conn = Database.connect();

            String sql = "SELECT SUM(total) as total FROM transaksi WHERE user_id=?";

            var ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            var rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }
}
