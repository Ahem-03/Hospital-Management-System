import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.regex.Pattern;

public class AddDoctor extends JFrame implements ActionListener {
    JPanel p1;
    JLabel lh, lname, lgender, lspecialization, lqualification, lexperience, lphone, lemail, laddress, lavailability;
    JTextField tfdoctor_Id, tfname, tfspecialization, tfqualification, tfexperience, tfphone, tfemail;
    JComboBox<String> cbGender, cbAvailability;
    JTextArea taAddress;
    JButton bsave, bclear, bmenu;

     // database connection
    final static String url = "jdbc:mysql://localhost:3306/hospital_db";
    final static String user = "root";
    final static String user_password = "Ahem@0304";
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;

    public AddDoctor() {
        // initialization
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(245, 250, 255));
        add(p1);

        // Header Label
        lh = new JLabel("Add Doctor", JLabel.CENTER);
        lh.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lh.setForeground(new Color(10, 60, 120));

        // Labels
        lname = new JLabel("Name");
        lgender = new JLabel("Gender");
        lspecialization = new JLabel("Specialization");
        lqualification = new JLabel("Qualification");
        lexperience = new JLabel("Experience (yrs)");
        lphone = new JLabel("Phone");
        lemail = new JLabel("Email");
        laddress = new JLabel("Address");
        lavailability = new JLabel("Availability");

        // Inputs
        tfdoctor_Id = new JTextField();
        tfname = new JTextField();
        cbGender = new JComboBox<>(new String[] {"Select", "Male", "Female", "Other"});
        tfspecialization = new JTextField();
        tfqualification = new JTextField();
        tfexperience = new JTextField();
        tfphone = new JTextField();
        tfemail = new JTextField();
        taAddress = new JTextArea();
        taAddress.setLineWrap(true);
        taAddress.setWrapStyleWord(true);
        JScrollPane addressScroll = new JScrollPane(taAddress);

        cbAvailability = new JComboBox<>(new String[] {"Available", "Not Available", "On Leave"});

        // Buttons
        bsave = new JButton("Save");
        bclear = new JButton("Clear");
        bmenu = new JButton("Menu");

        // Styling label
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        lname.setFont(labelFont); 
        lgender.setFont(labelFont); 
        lspecialization.setFont(labelFont);
        lqualification.setFont(labelFont);
        lexperience.setFont(labelFont); 
        lphone.setFont(labelFont);
        lemail.setFont(labelFont); 
        laddress.setFont(labelFont); 
        lavailability.setFont(labelFont);

        //Styling textfiled
        tfdoctor_Id.setFont(labelFont);
        tfname.setFont(labelFont); 
        tfspecialization.setFont(labelFont); 
        tfqualification.setFont(labelFont);
        tfexperience.setFont(labelFont); 
        tfphone.setFont(labelFont); 
        tfemail.setFont(labelFont);
        cbGender.setFont(labelFont); 
        cbAvailability.setFont(labelFont); 
        taAddress.setFont(labelFont);

        bsave.setBackground(new Color(0, 123, 102)); 
        bsave.setForeground(Color.WHITE);
        bclear.setBackground(new Color(200, 200, 200));

        bmenu.setBackground(new Color(52, 105, 158)); 
        bmenu.setForeground(Color.WHITE);

        // Add to panel
        p1.add(lh);
        p1.add(lname); p1.add(tfname);
        p1.add(lgender); p1.add(cbGender);
        p1.add(lspecialization); p1.add(tfspecialization);
        p1.add(lqualification); p1.add(tfqualification);
        p1.add(lexperience); p1.add(tfexperience);
        p1.add(lphone); p1.add(tfphone);
        p1.add(lemail); p1.add(tfemail);
        p1.add(laddress); p1.add(addressScroll);
        p1.add(lavailability); p1.add(cbAvailability);
        p1.add(bsave); p1.add(bclear); p1.add(bmenu);

        // Bounds (layout kept similar to project)
        setTitle("-: Add Doctor :-");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lh.setBounds(200, 20, 450, 40);

        tfdoctor_Id.setBounds(100, 50, 160, 280);
        tfdoctor_Id.setEditable(false);
        tfdoctor_Id.setText(ViewDoctor.generateNextPatientId(tfdoctor_Id));

        lname.setBounds(100, 90, 160, 28);
        tfname.setBounds(270, 90, 430, 28);

        lgender.setBounds(100, 135, 160, 28);
        cbGender.setBounds(270, 135, 200, 28);

        lspecialization.setBounds(100, 180, 160, 28);
        tfspecialization.setBounds(270, 180, 430, 28);

        lqualification.setBounds(100, 225, 160, 28);
        tfqualification.setBounds(270, 225, 430, 28);

        lexperience.setBounds(100, 270, 160, 28);
        tfexperience.setBounds(270, 270, 200, 28);

        lphone.setBounds(100, 315, 160, 28);
        tfphone.setBounds(270, 315, 200, 28);

        lemail.setBounds(100, 360, 160, 28);
        tfemail.setBounds(270, 360, 430, 28);

        laddress.setBounds(100, 405, 160, 28);
        addressScroll.setBounds(270, 405, 430, 80);

        lavailability.setBounds(100, 500, 160, 28);
        cbAvailability.setBounds(270, 500, 200, 28);

        bsave.setBounds(270, 560, 140, 36);
        bclear.setBounds(430, 560, 100, 36);
        bmenu.setBounds(800, 700, 100, 36);

        setSize(1050, 820);
        setLocationRelativeTo(null);

        // Listeners (anonymous for Save & Clear)
        bsave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSaveFrontend();
            }
        });

        bclear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        bmenu.addActionListener(this);

        setVisible(true);
    }

    private void onSaveFrontend() {
        String name = tfname.getText().trim();
        String gender = (String) cbGender.getSelectedItem();
        String spec = tfspecialization.getText().trim();
        String qual = tfqualification.getText().trim();
        String exp = tfexperience.getText().trim();
        String phone = tfphone.getText().trim();
        String email = tfemail.getText().trim();
        String address = taAddress.getText().trim();
        String availability = (String) cbAvailability.getSelectedItem();

        // basic validation
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if ("Select".equals(gender)) {
            JOptionPane.showMessageDialog(this, "Please select gender.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!exp.matches("^\\d{1,2}$")) {
            JOptionPane.showMessageDialog(this, "Enter valid experience in years (0-99).", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!phone.isEmpty() && !phone.matches("\\d{7,15}")) {
            JOptionPane.showMessageDialog(this, "Phone must be 7-15 digits.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!email.isEmpty()) {
           // String regex = "^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
            // if (!Pattern.matches(regex, email)) {
            //     JOptionPane.showMessageDialog(this, "Enter a valid email.", "Validation", JOptionPane.WARNING_MESSAGE);
              return;
            // }
        }

        // simulate save: show summary modal
        StringBuilder sb = new StringBuilder();
        sb.append("Doctor (frontend only) saved:\n\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Gender: ").append(gender).append("\n");
        sb.append("Specialization: ").append(spec).append("\n");
        sb.append("Qualification: ").append(qual).append("\n");
        sb.append("Experience: ").append(exp).append(" yrs\n");
        sb.append("Phone: ").append(phone).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Availability: ").append(availability).append("\n");
        sb.append("Address: ").append(address).append("\n");

        JOptionPane.showMessageDialog(this, sb.toString(), "Saved", JOptionPane.INFORMATION_MESSAGE);
        
        // this will auto generate the doctor id 
            tfdoctor_Id.setText(ViewDoctor.generateNextPatientId(tfdoctor_Id));

        String doctor_Id = tfdoctor_Id.getText().trim();
        String name_2 = tfname.getText().trim();
        String gender_2 = (String) cbGender.getSelectedItem();
        String specialization_2 = tfspecialization.getText().trim();
        String qualification_2 = tfqualification.getText().trim();
        String experience_2 = String.valueOf(Integer.parseInt(tfexperience.getText().trim()));
        String phone_2 = tfphone.getText().trim();
        String email_2 = tfemail.getText().trim();
        String availability_2 = (String) cbAvailability.getSelectedItem();
        String address_2 = taAddress.getText().trim();
        
        try {
            con = DriverManager.getConnection(url, user, user_password);

            System.out.println("dtabase is connected ");
            String query = "insert into doctor_db(doctor_id, name, gender, specialization, experience, phone, email, availability, address) values ('"+doctor_Id+"','"+name_2+"', '"+gender_2+"', '"+specialization_2+"', '"+qualification_2+"','"+experience_2+"','"+phone_2+"','"+email_2+"','"+availability_2+"','"+address_2+"')";

            PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, doctor_Id);
        pst.setString(2, name_2);
        pst.setString(3, gender_2);
        pst.setString(4, specialization_2);
        pst.setString(5, qualification_2);
        pst.setString(6, experience_2);
        pst.setString(7, phone_2);
        pst.setString(8, email_2);
        pst.setString(9, address_2);
        pst.setString(10, availability_2);

           int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Record inserted.");
            } else {
                JOptionPane.showMessageDialog(this, "Insert failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println("there is a error ");
            JOptionPane.showMessageDialog(this, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearForm() {
        tfname.setText("");
        cbGender.setSelectedIndex(0);
        tfspecialization.setText("");
        tfqualification.setText("");
        tfexperience.setText("");
        tfphone.setText("");
        tfemail.setText("");
        taAddress.setText("");
        cbAvailability.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bmenu) {
            Main_Menu m = new Main_Menu();
            m.setVisible(true);
            m.setSize(1050, 820);
            m.setLocationRelativeTo(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddDoctor());
    }
}
