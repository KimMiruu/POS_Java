package service;

import config.Database;
import model.User;

import java.sql.Connection;

public class AuthService {

    public User login(String email, String password) {

        try {
            Connection conn = Database.connect();

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            var ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            var rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        "User"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean register(String email, String username, String password) {

        try {
            Connection conn = Database.connect();

            String checkSql = "SELECT * FROM users WHERE email=?";
            var checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, email);

            var rs = checkPs.executeQuery();

            if (rs.next()) return false;

            String sql = "INSERT INTO users(email, username, password) VALUES(?,?,?)";

            var ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, password);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
