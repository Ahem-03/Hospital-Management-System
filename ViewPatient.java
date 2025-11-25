import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ViewPatient extends JFrame implements ActionListener{

    JPanel leftPanel, rightPanel;
    JLabel titleLabel, filterLabel;
    JTextField searchField;
    JButton btnSearch, btnRefresh, btnBack;
    JTable patientTable;
    JScrollPane tableScroll;
    DefaultTableModel model;
    //======these componats are for Addpatient Panel=========== 
    JPanel addPanel;
    JTextField txtPatientId, txtName, txtAge, txtDisease, txtContact;
    JRadioButton rbmale, rbfemale;
    JButton btnSave, btnClear,btnAdd,  btnBack_2;

    // database connection
    final static String url = "jdbc:mysql://localhost:3306/hospital_db";
    final static String user = "root";
    final static String user_password = "Ahem@0304";
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;

    public ViewPatient() {
        setTitle("🏥 View Patient Details");
        setSize(1050, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 250, 255)); // light hospital blue

        // = LEFT PANEL 
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

        // search field
        searchField = new JTextField();
        searchField.setBounds(40, 130, 200, 30);
        searchField.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)));
        leftPanel.add(searchField);

        // buttons 
        btnSearch = new JButton("Search");
        btnSearch.setBounds(40, 180, 200, 35);
        btnSearch.setBackground(new Color(0, 120, 215));
        btnSearch.setForeground(Color.white);
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnSearch);
        btnSearch.addActionListener(this);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(40, 230, 200, 35);
        btnRefresh.setBackground(new Color(0, 153, 102));
        btnRefresh.setForeground(Color.white);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnRefresh);
        btnRefresh.addActionListener( this);
            btnRefresh.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e ){
                    loadPatientData();
                }
            });  // actionPerformed(ActionEvent e) Invoked when an action occurs.
        btnBack = new JButton("Back");
        btnBack.setBounds(40, 330, 200, 35);
        btnBack.setBackground(new Color(255, 87, 87));
        btnBack.setForeground(Color.white);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e ){
                 new Main_Menu();
                  setVisible(true);
                  setSize(1050,820);
                  setLocationRelativeTo(null);
                  ViewPatient.this.setVisible(false);
            }
        });  // actionPerformed(ActionEvent e) Invoked when an action occurs.

        btnAdd  = new JButton("Add patient");           
        btnAdd.setBounds(40, 280, 200, 35);
        btnAdd.setBackground(new Color(52, 105, 158));
        btnAdd.setForeground(Color.white);
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 14));
        leftPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            @Override
        public void actionPerformed(ActionEvent e){
            new AddPatient();
            setLocationRelativeTo(null);
            ViewPatient.this.setVisible(false);
        }
        }); 

        // ==== RIGHT PANEL ====
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.white);
        rightPanel.setBounds(280, 0, 770, 820);
        add(rightPanel);

        JLabel tableTitle = new JLabel("All Registered Patients", JLabel.CENTER);
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tableTitle.setForeground(new Color(0, 70, 130));
        tableTitle.setBounds(0, 20, 300, 30);
        rightPanel.add(tableTitle);

        //table initializing 
        String[] column = {"Patient ID", "Name", "Age", "Gender", "Disease", "Contact"};
        model = new DefaultTableModel(column,0);
        
        // creating a table 
        patientTable = new JTable(model);
        patientTable.setRowHeight(28);
        patientTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        patientTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        patientTable.getTableHeader().setBackground(new Color(0, 120, 215));
        patientTable.getTableHeader().setForeground(Color.white);

        tableScroll = new JScrollPane(patientTable);
        tableScroll.setBounds(20, 70, 730, 700);
        rightPanel.add(tableScroll);

          // Load data from database
        loadPatientData();

        setVisible(true);
    }
    //=========== this will auto generate the id ============

    public static String generateNextPatientId(JTextField txtPatientId){
        String nextId = "P105";
        String query = "select patient_id from Patients ORDER BY patient_id DESC LIMIT 1";
        try {
            con = DriverManager.getConnection(url, user, user_password);
        stmt = con.createStatement();
        System.out.println("database is connected....");
        rs = stmt.executeQuery(query);

        if(rs.next()){
            String lastId = rs.getString("patient_id");
            // Extract numeric part from "P101" -> 101
                int numericPart = Integer.parseInt(lastId.substring(1));
                // Increment and format
                nextId = "P" + (numericPart + 1);
        }
        } catch (Exception e) {
            System.out.println(" patient id problem"+ e);
        }
        //return query;
        return nextId;
        }

    // this method will auro matically shows the all patients 
   public void loadPatientData(){

    model.setRowCount(0);
    try{
        con = DriverManager.getConnection(url, user, user_password);
        stmt = con.createStatement();
        System.out.println("database is connected....");
        String query  = "select * from patients";
        rs = stmt.executeQuery(query);
        
        while (rs.next()) {
            Object[] row ={
                rs.getString("patient_id"),
                rs.getString("name"),
                rs.getString("age"),
                rs.getString("gender"),
                rs.getString("disease"),
                rs.getString("contact")
            };
            model.addRow(row);
        }
    }catch(Exception ee){
        System.out.println("problem"+ee);
    }
   }

   // ========== form here we are adding the button event =========
   public void actionPerformed(ActionEvent e){   

        // =================== searching button ============ 
         if (e.getSource()==btnSearch) {
             model.setRowCount(0);
            String id = searchField.getText(); 
            try {
                String query_2 = "select * from patients where patient_id = '"+id+"' or name = '"+id+"'";
                System.out.println("query is running...");
                rs = stmt.executeQuery(query_2);
                if (rs.next()) {
                    Object[] row={
                        rs.getString("patient_id"),
                        rs.getString("name"),
                        rs.getString("age"),
                        rs.getString("gender"),
                        rs.getString("disease"),
                        rs.getString("contact"),
                    };
                    model.addRow(row);
                }
            } catch (Exception ee) {
                System.out.println("Error in search button : ");
            }
        }
   }


   // ====================================== AddPatient record ===============================================//


   
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("driver connnected");
            
        } catch (Exception e) {
            System.out.println("problem"+ e);
        }
        SwingUtilities.invokeLater(()-> new ViewPatient());
    }
}
