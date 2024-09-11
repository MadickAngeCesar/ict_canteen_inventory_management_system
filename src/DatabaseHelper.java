import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/inventoryDB";
    private static final String USER = "root";
    private static final String PASS = "admin";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection to MySQL has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void addItem(String name, String category, String supplier, String item_id, int quantity, double price) {
        String sql = "INSERT INTO Items(name, item_id, category, supplier, quantity, price) VALUES(?,?,?,?,?,?)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, item_id);
            pstmt.setString(3, category);
            pstmt.setString(4, supplier);
            pstmt.setInt(5, quantity);
            pstmt.setDouble(6, price);
            pstmt.executeUpdate();
            System.out.println("Item added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet searchItem(String name) {
        String sql = "SELECT id, name, item_id, category, quantity, price, supplier FROM Items WHERE name LIKE ?";
        
        try {
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
