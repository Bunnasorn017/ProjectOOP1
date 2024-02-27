import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class ProductForm extends JFrame {
    private JLabel idLabel, nameLabel, priceLabel, qtyLabel;
    private JTextField idField, nameField, priceField, qtyField;
    private JButton addButton, updateButton, deleteButton, refreshButton;
    private JTable productTable;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    private Product product;

    public ProductForm() {
        super("Product Management");

        product = new Product();

        idLabel = new JLabel("Product ID:");
        nameLabel = new JLabel("Product Name:");
        priceLabel = new JLabel("Product Price:");
        qtyLabel = new JLabel("Product Quantity:");

        idField = new JTextField(10);
        nameField = new JTextField(20);
        priceField = new JTextField(10);
        qtyField = new JTextField(5);

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        refreshButton = new JButton("Refresh");

        productTable = new JTable();
        scrollPane = new JScrollPane(productTable);

        tableModel = new DefaultTableModel();
        productTable.setModel(tableModel);

        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");

        setLayout(new FlowLayout());

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(priceLabel);
        add(priceField);
        add(qtyLabel);
        add(qtyField);
        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(refreshButton);
        add(scrollPane);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                int price = Integer.parseInt(priceField.getText());
                int qty = Integer.parseInt(qtyField.getText());

                product.setDetails(id, name, price, qty);
                product.insertP();
                refreshTable();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                int price = Integer.parseInt(priceField.getText());
                int qty = Integer.parseInt(qtyField.getText());

                product.setDetails(id, name, price, qty);
                product.updatep();
                refreshTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                product.setDetails(id, "", 0, 0);
                product.deletep();
                refreshTable();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        ResultSet rs = product.selectAllp();
        try {
            while (rs.next()) {
                String id = rs.getString("P_id");
                String name = rs.getString("P_name");
                int price = rs.getInt("P_price");
                int qty = rs.getInt("P_qty");
                Vector<Object> row = new Vector<>();
                row.add(id);
                row.add(name);
                row.add(price);
                row.add(qty);
                tableModel.addRow(row);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProductForm();
            }
        });
    }
}
