import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewPatient extends JFrame {

    JPanel leftPanel, rightPanel;
    JLabel titleLabel, filterLabel;
    JTextField searchField;
    JButton btnSearch, btnRefresh, btnBack;
    JTable patientTable;
    JScrollPane tableScroll;

    public ViewPatient() {
        setTitle("🏥 View Patient Details");
        setSize(1050, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 250, 255)); // light hospital blue

        // ==== LEFT PANEL ====
        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(200, 230, 250));
        leftPanel.setBounds(0, 0, 280, 820);
        add(leftPanel);

        // Title Label
        titleLabel = new JLabel("🩺 View Patients");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(0, 60, 120));
        titleLabel.setBounds(40, 40, 200, 30);
        leftPanel.add(titleLabel);

        // Filter/Search
        filterLabel = new JLabel("Search by Name/ID:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterLabel.setBounds(40, 100, 200, 25);
        leftPanel.add(filterLabel);

        searchField = new JTextField();
        searchField.setBounds(40, 130, 200, 30);
        searchField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        leftPanel.add(searchField);

        btnSearch = new JButton("Search");
        btnSearch.setBounds(40, 180, 200, 35);
        btnSearch.setBackground(new Color(0, 120, 215));
        btnSearch.setForeground(Color.white);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnSearch);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(40, 230, 200, 35);
        btnRefresh.setBackground(new Color(0, 153, 102));
        btnRefresh.setForeground(Color.white);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnRefresh);

        btnBack = new JButton("Back");
        btnBack.setBounds(40, 280, 200, 35);
        btnBack.setBackground(new Color(255, 87, 87));
        btnBack.setForeground(Color.white);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnBack);

        // ==== RIGHT PANEL ====
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.white);
        rightPanel.setBounds(280, 0, 770, 820);
        add(rightPanel);

        JLabel tableTitle = new JLabel("All Registered Patients");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tableTitle.setForeground(new Color(0, 70, 130));
        tableTitle.setBounds(20, 20, 300, 30);
        rightPanel.add(tableTitle);

        // ==== TABLE ====
        String[] columns = {"Patient ID", "Name", "Age", "Gender", "Disease", "Contact"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Example rows (for demo)
        model.addRow(new Object[]{"P101", "Rahul Mehta", "32", "Male", "Fever", "9876543210"});
        model.addRow(new Object[]{"P102", "Neha Sharma", "28", "Female", "Cold", "9123456789"});
        model.addRow(new Object[]{"P103", "Amit Kumar", "45", "Male", "Diabetes", "9988776655"});

        patientTable = new JTable(model);
        patientTable.setRowHeight(28);
        patientTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        patientTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        patientTable.getTableHeader().setBackground(new Color(0, 120, 215));
        patientTable.getTableHeader().setForeground(Color.white);
        patientTable.setGridColor(new Color(220, 220, 220));

        tableScroll = new JScrollPane(patientTable);
        tableScroll.setBounds(20, 70, 730, 700);
        rightPanel.add(tableScroll);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewPatient();
    }
}
