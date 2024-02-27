import java.sql.*;

import javax.swing.JTextField;
public class Product {

  private String P_id;
  private String P_name;
  private int P_price;
  private int P_qty;

    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private String dbUrl = "jdbc:mysql://localhost/hardwarecom_db";

    public void setDetails(String id, String name, int price, int qty) {
      P_id = id;
      P_name = name;
      P_price = price;
      P_qty = qty;
    }

    public String getid() {
      return P_id;
    }

    public String getname() {
      return P_name;
    }

    public int getprice() {
      return P_price;
    }

    public int getqty() {
      return P_qty;
    }

    public Object[] convert2row() {
      Object[] row = {P_id, P_name, P_price, P_qty};
      return row;
    }

    public void connectsql() {
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(dbUrl, "root", "");
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }

    public void insertP() {
      try {
        connectsql();
        ps = con.prepareStatement("INSERT INTO product VALUES (DEFAULT,?,?,?,?)");
        ps.setString(1, P_id);
        ps.setString(2, P_name);
        ps.setString(3, "" + P_price);
        ps.setString(4, "" + P_qty);
        ps.executeUpdate();
        ps.close();
        con.close();
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }

    public void updatep() {
      try {
        connectsql();
        ps = con.prepareStatement("UPDATE product SET P_name=?, P_price=?, P_qty=? WHERE P_id = ?");
        ps.setString(1, P_id);
        ps.setString(2, P_name);
        ps.setString(3, "" + P_price);
        ps.setString(4, "" + P_qty);
        ps.executeUpdate();
        ps.close();
        con.close();
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }

    public ResultSet selectAllp() {
      connectsql();
      ResultSet rs = null;
      try {
          st = con.createStatement();
          rs = st.executeQuery("SELECT * FROM product");
      } catch (SQLException e) {
          System.out.println(e.toString());
      }
      return rs;
  }

    public void deletep() {
      try {
        connectsql();
        ps = con.prepareStatement("DELETE FROM product WHERE P_id = ?");
        ps.executeUpdate();
        ps.close();
        con.close();
      } catch (Exception e) {
        System.out.println(e.toString());
      }
    }
}