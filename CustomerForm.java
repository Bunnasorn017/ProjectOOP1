import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CustomerForm extends JFrame {
    private JTextField idField, nameField, emailField, telField;
    private JButton addButton, updateButton, deleteButton;
    private JTextArea displayArea;
    private Customer customer;

    public CustomerForm() {
        super("Customer Form");

        customer = new Customer();

        setLayout(new FlowLayout());

        idField = new JTextField(10);
        nameField = new JTextField(10);
        emailField = new JTextField(10);
        telField = new JTextField(10);

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);

        add(new JLabel("ID: "));
        add(idField);
        add(new JLabel("Name: "));
        add(nameField);
        add(new JLabel("Email: "));
        add(emailField);
        add(new JLabel("Tel: "));
        add(telField);
        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(new JScrollPane(displayArea));

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String email = emailField.getText();
                String tel = telField.getText();

                customer.setDetails(id, name, email, tel);
                customer.insertCustomer();
                displayAllCustomers();
                displayArea.setText("Customer added successfully.");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String email = emailField.getText();
                String tel = telField.getText();

                customer.setDetails(id, name, email, tel);
                customer.updateCustomer();
                displayAllCustomers();
                displayArea.setText("Customer updated successfully.");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();

                customer.deleteCustomer(id);
                displayAllCustomers();
                displayArea.setText("Customer deleted successfully.");
            }
        });

        displayAllCustomers();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    private void displayAllCustomers() {
        ResultSet rs = customer.selectAllCustomers();
        try {
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getString("C_id")).append(", ");
                sb.append("Name: ").append(rs.getString("C_name")).append(", ");
                sb.append("Email: ").append(rs.getString("C_email")).append(", ");
                sb.append("Tel: ").append(rs.getString("C_tel")).append("\n");
            }
            displayArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CustomerForm customerForm = new CustomerForm();
    }
}
