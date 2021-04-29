package hu.nive.ujratervezes.jurassic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JurassicPark {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public JurassicPark(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    List<String> checkOverpopulation() {
        List<String> overpopulated = new ArrayList<>();
        String sql =
                "SELECT breed, expected, actual " +
                        "FROM dinosaur " +
                        "WHERE actual > expected " +
                        "ORDER BY breed";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String breedName = rs.getString("breed");
                overpopulated.add(breedName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return overpopulated;
    }

    private Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

}
