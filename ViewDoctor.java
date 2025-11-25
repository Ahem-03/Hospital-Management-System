import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewDoctor extends JFrame {
    JPanel leftPanel, rightPanel;
    JLabel titleLabel, filterLabel;
    JTextField searchField;
    JButton btnSearch, btnRefresh, btnAdd, btnUpdate, btnDelete, btnBack;
    JTable doctorTable;
    JScrollPane tableScroll;
    DefaultTableModel model;

    // database connection
    final static String url = "jdbc:mysql://localhost:3306/hospital_db";
    final static String user = "root";
    final static String user_password = "Ahem@0304";
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;

    public ViewDoctor() {
        setTitle("🏥 View Doctor Details");
        setSize(1050, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 250, 255));

        // LEFT PANEL
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(200, 230, 250));
        leftPanel.setBounds(0, 0, 280, 820);
        add(leftPanel);

        // Title Label
        titleLabel = new JLabel("👨‍⚕️ View Doctors");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 60, 120));
        titleLabel.setBounds(40, 40, 200, 30);
        leftPanel.add(titleLabel);

        // Filter/Search
        filterLabel = new JLabel("Search by Name/ID:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterLabel.setBounds(40, 100, 200, 25);
        leftPanel.add(filterLabel);

        // search field
        searchField = new JTextField();
        searchField.setBounds(40, 130, 200, 30);
        searchField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        leftPanel.add(searchField);

        // buttons
        btnSearch = new JButton("Search");
        btnSearch.setBounds(40, 180, 200, 35);
        btnSearch.setBackground(new Color(0, 120, 215));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnSearch);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(40, 230, 200, 35);
        btnRefresh.setBackground(new Color(0, 153, 102));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnRefresh);

        btnAdd = new JButton("Add Doctor");
        btnAdd.setBounds(40, 280, 200, 35);
        btnAdd.setBackground(new Color(52, 105, 158));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnAdd);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(40, 330, 200, 35);
        btnUpdate.setBackground(new Color(255, 193, 7));
        btnUpdate.setForeground(Color.BLACK);
        btnUpdate.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(40, 380, 200, 35);
        btnDelete.setBackground(new Color(244, 67, 54));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnDelete);

        btnBack = new JButton("Back");
        btnBack.setBounds(40, 430, 200, 35);
        btnBack.setBackground(new Color(255, 87, 87));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnBack);

        // RIGHT PANEL - TABLE
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(new Color(240, 250, 255));
        rightPanel.setBounds(280, 0, 770, 820);
        add(rightPanel);

        // Table setup
        model = new DefaultTableModel();
        model.addColumn("Doctor ID");
        model.addColumn("Name");
        model.addColumn("Gender");
        model.addColumn("Specialization");
        model.addColumn("Qualification");
        model.addColumn("Experience");
        model.addColumn("Phone");
        model.addColumn("Address");
        model.addColumn("Availability");

        doctorTable = new JTable(model);
        doctorTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        doctorTable.setRowHeight(25);
        tableScroll = new JScrollPane(doctorTable);
        tableScroll.setBounds(20, 20, 730, 780);
        rightPanel.add(tableScroll);

        // Action Listeners
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchDoctor();
            }
        });

        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDoctorData();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddDoctor();
                ViewDoctor.this.setVisible(false);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = doctorTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(ViewDoctor.this, "Please select a doctor to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String doctorId = model.getValueAt(selectedRow, 0).toString();
                JOptionPane.showMessageDialog(ViewDoctor.this, "Update functionality for Doctor ID: " + doctorId, "Update", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = doctorTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(ViewDoctor.this, "Please select a doctor to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String doctorId = model.getValueAt(selectedRow, 0).toString();
                int confirm = JOptionPane.showConfirmDialog(ViewDoctor.this, "Are you sure you want to delete Doctor ID: " + doctorId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteDoctor(doctorId);
                }
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main_Menu();
                ViewDoctor.this.setVisible(false);
            }
        });

        loadDoctorData();
        setLocationRelativeTo(null);
        setVisible(true);
    }

     //=========== this will auto generate the id ============

    public static String generateNextPatientId(JTextField txtdoctor_Id){
        String nextId = "P101";
        String query = "select doctor_id from doctor_id ORDER BY doctor_id DESC LIMIT 1";
        try {
            con = DriverManager.getConnection(url, user, user_password);
        stmt = con.createStatement();
        System.out.println("database is connected....");
        rs = stmt.executeQuery(query);

        if(rs.next()){
            String lastId = rs.getString("doctor_id");
            // Extract numeric part from "P101" -> 101
                int numericPart = Integer.parseInt(lastId.substring(1));
                // Increment and format
                nextId = "D" + (numericPart + 1);
        }
        } catch (Exception e) {
            System.out.println(" patient id problem"+ e);
        }
        //return query;
        return nextId;
        }

    private void loadDoctorData() {
        model.setRowCount(0);
        try {
            con = DriverManager.getConnection(url, user, user_password);
            stmt = con.createStatement();
            System.out.println("Database connected....");
            String query = "SELECT * FROM doctor_db";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Object[] row = {
                    rs.getString("doctor_id"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getString("specialization"),
                    rs.getString("qualification"),
                    rs.getString("experience"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("availability")
                };
                model.addRow(row);
            }
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(this, "Error loading doctor data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchDoctor() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadDoctorData();
            return;
        }
        model.setRowCount(0);
        try {
            con = DriverManager.getConnection(url, user, user_password);
            stmt = con.createStatement();
            String query = "SELECT * FROM doctor_db WHERE doctor_id LIKE %" + searchTerm + "% OR name LIKE '%" + searchTerm + "%'";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Object[] row = {
                    rs.getString("doctor_id"),
                    rs.getString("name"),
                    rs.getString("gender"),
                    rs.getString("specialization"),
                    rs.getString("qualification"),
                    rs.getString("experience"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("availability")
                };
                model.addRow(row);
            }
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    private void deleteDoctor(String doctorId) {
        try {
            con = DriverManager.getConnection(url, user, user_password);
            stmt = con.createStatement();
            String query = "DELETE FROM doctor_db WHERE doctor_id = '" + doctorId + "'";
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(this, "Doctor deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadDoctorData();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            JOptionPane.showMessageDialog(this, "Error deleting doctor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewDoctor());
    }
}