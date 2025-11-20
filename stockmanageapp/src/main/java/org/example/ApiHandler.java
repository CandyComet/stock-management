package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ApiHandler {
    private final String URL = "jdbc:mysql://localhost:3306/stock_manage?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "Blink_123";

    public ApiHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
                try (Statement st = c.createStatement()) {
                    st.executeUpdate("CREATE TABLE IF NOT EXISTS stock (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "name VARCHAR(255) NOT NULL," +
                            "price DOUBLE NOT NULL," +
                            "warehouse VARCHAR(255) NOT NULL," +
                            "quantity INT NOT NULL)");
                    st.executeUpdate("CREATE TABLE IF NOT EXISTS admins (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "username VARCHAR(100) NOT NULL," +
                            "email VARCHAR(100) NOT NULL," +
                            "phone VARCHAR(20) NOT NULL," +
                            "password VARCHAR(255) NOT NULL)");
                }

                try (PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) AS cnt FROM admins");
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt("cnt") == 0) {
                        try (PreparedStatement ins = c.prepareStatement(
                                "INSERT INTO admins (username, email, phone, password) VALUES (?,?,?,?)")) {
                            ins.setString(1, "admin");
                            ins.setString(2, "admin");
                            ins.setString(3, "0000000000");
                            ins.setString(4, "admin");
                            ins.executeUpdate();
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean saveStock(StockModel s) {
        String sql = "INSERT INTO stock (name, price, warehouse, quantity) VALUES (?, ?, ?, ?)";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getProductName());
            ps.setDouble(2, s.getProductPrice());
            ps.setString(3, s.getWarehouseName());
            ps.setInt(4, s.getProductQuantity());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<StockModel> getAll() {
        List<StockModel> list = new ArrayList<>();
        String sql = "SELECT * FROM stock ORDER BY id";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new StockModel(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("warehouse"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateStock(StockModel s) {
        String sql = "UPDATE stock SET name=?, price=?, warehouse=?, quantity=? WHERE id=?";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getProductName());
            ps.setDouble(2, s.getProductPrice());
            ps.setString(3, s.getWarehouseName());
            ps.setInt(4, s.getProductQuantity());
            ps.setInt(5, s.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStock(int id) {
        String sql = "DELETE FROM stock WHERE id=?";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<StockModel> searchByField(String field, String keyword) {
        List<StockModel> list = new ArrayList<>();
        if (field == null || keyword == null) return list;
        String sql;
        switch (field) {
            case "id":
                sql = "SELECT * FROM stock WHERE CAST(id AS CHAR) LIKE ? ORDER BY id";
                break;
            case "name":
                sql = "SELECT * FROM stock WHERE name LIKE ? ORDER BY id";
                break;
            case "warehouse":
                sql = "SELECT * FROM stock WHERE warehouse LIKE ? ORDER BY id";
                break;
            case "quantity":
                sql = "SELECT * FROM stock WHERE CAST(quantity AS CHAR) LIKE ? ORDER BY id";
                break;
            case "price":
                sql = "SELECT * FROM stock WHERE CAST(price AS CHAR) LIKE ? ORDER BY id";
                break;
            default:
                sql = "SELECT * FROM stock WHERE name LIKE ? OR warehouse LIKE ? OR CAST(id AS CHAR) LIKE ? ORDER BY id";
                break;
        }

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {
            String pattern = "%" + keyword + "%";
            if (field.equals("name") || field.equals("warehouse") || field.equals("id") ||
                    field.equals("quantity") || field.equals("price")) {
                ps.setString(1, pattern);
            } else {
                ps.setString(1, pattern);
                ps.setString(2, pattern);
                ps.setString(3, pattern);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new StockModel(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("warehouse"),
                            rs.getInt("quantity")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean saveAdmin(AdminModel a) {
        String sql = "INSERT INTO admins (username, email, phone, password) VALUES (?, ?, ?, ?)";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getPhone());
            ps.setString(4, a.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AdminModel> getAllAdmins() {
        List<AdminModel> list = new ArrayList<>();
        String sql = "SELECT * FROM admins ORDER BY id";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new AdminModel(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateAdmin(AdminModel a) {
        String sql = "UPDATE admins SET username=?, email=?, phone=?, password=? WHERE id=?";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getPhone());
            ps.setString(4, a.getPassword());
            ps.setInt(5, a.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAdmin(int id) {
        String sql = "DELETE FROM admins WHERE id=?";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateLogin(String email, String password) {
        String sql = "SELECT * FROM admins WHERE email=? AND password=?";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void SaveImageFile(File file) {
        try {
            String boundary = "----JavaBoundary" + System.currentTimeMillis();
            String bodyStart = "--" + boundary + "\r\n"
                    + "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n"
                    + "Content-Type: image/png\r\n\r\n";
            String bodyEnd = "\r\n--" + boundary + "--\r\n";

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(bodyStart.getBytes());
            Files.copy(file.toPath(), outputStream);
            outputStream.write(bodyEnd.getBytes());
            byte[] requestBody = outputStream.toByteArray();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/stock/upload-images"))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Status Code: " + response.statusCode());
            System.out.println("Response: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllImageFiles() {
        List<String> list = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:8080/stock/all-images");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) list.add(line.trim());
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}