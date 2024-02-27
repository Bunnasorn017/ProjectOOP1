import java.sql.*;

public class Customer {

    private String C_id;
    private String C_name;
    private String C_email;
    private String C_tel;

    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String dbUrl = "jdbc:mysql://localhost/hardwarecom_db";

    public void setDetails(String id, String name, String email, String tel) {
        C_id = id;
        C_name = name;
        C_email = email;
        C_tel = tel;
    }

    public void connectsql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, "root", "");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void insertCustomer() {
        try {
            connectsql();
            ps = con.prepareStatement("INSERT INTO customer VALUES (DEFAULT,?,?,?,?)");
            ps.setString(1, C_id);
            ps.setString(2, C_name);
            ps.setString(3, C_email);
            ps.setString(4, C_tel);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void updateCustomer() {
        try {
            connectsql();
            ps = con.prepareStatement("UPDATE customer SET C_name=?, C_email=?, C_tel=? WHERE C_id=?");
            ps.setString(1, C_name);
            ps.setString(2, C_email);
            ps.setString(3, C_tel);
            ps.setString(4, C_id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    } 

    public ResultSet selectAllCustomers() {
        connectsql();
        ResultSet rs = null;
        try {
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM customer");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return rs;
    }

    public void deleteCustomer(String id) {
        try {
            connectsql();
            ps = con.prepareStatement("DELETE FROM customer WHERE C_id = ?");
            ps.setString(1, id);
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
