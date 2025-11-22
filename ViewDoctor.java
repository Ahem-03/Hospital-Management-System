import javax.swing.*;
import java.awt.event.*;

public class ViewDoctor extends JFrame  implements ActionListener{
    JPanel p1;
    JLabel lh, lId, lname, lgender, lspecialization, lqualification, lexperience, lphone, lemail, laddress, lavailability;
    JTextField tfId, tfname, tfgender, tfspecialization, tfqualification, tfexperience, tfphone, tfemail, tfaddress, tfavailability;
    JButton bupdate, bsearch, bview, bmenu;

    public ViewDoctor() {
//initialization
        p1 = new JPanel();
        p1.setLayout(null);

// Header Label
        lh = new JLabel("-: City Hospital :-");
// Labels
        lId  = new JLabel("ID");
        lname = new JLabel("Name");
        lgender = new JLabel("Gender");
        lspecialization = new JLabel("Specialization");
        lqualification = new JLabel("Qualification");
        lexperience = new JLabel("Experience");
        lphone = new JLabel("Phone");
        lemail = new JLabel("Email");
        laddress = new JLabel("Address");
        lavailability = new JLabel("Availability");

// TextFields
        tfId  = new JTextField();
        tfname = new JTextField();
        tfgender = new JTextField();
        tfspecialization = new JTextField();
        tfqualification = new JTextField();
        tfexperience = new JTextField();
        tfphone = new JTextField();
        tfemail = new JTextField();
        tfaddress = new JTextField();
        tfavailability = new JTextField();

// Button
        bsearch = new JButton("search");
        bupdate = new JButton("update");
        bview = new JButton("view all");
        bmenu = new JButton("Menu");

        // Adding to panel
        add(p1);
//label
        p1.add(lId);
        p1.add(lh);
        p1.add(lname);
        p1.add(lgender);
        p1.add(lspecialization);
        p1.add(lqualification);
        p1.add(lexperience);
        p1.add(lphone);
        p1.add(lemail);
        p1.add(laddress);
        p1.add(lavailability);
//text field
        p1.add(tfId);
        p1.add(tfname);
        p1.add(tfgender);
        p1.add(tfspecialization);
        p1.add(tfqualification);
        p1.add(tfexperience);
        p1.add(tfphone);
        p1.add(tfemail);
        p1.add(tfaddress);
        p1.add(tfavailability);
//button
        p1.add(bsearch);
        p1.add(bupdate);
        p1.add(bview);
        p1.add(bmenu);

// Set bounds (x, y, width, height)
        setTitle("View Doctor");

// Header
        lh.setBounds(320, 10, 200, 30);

// Labels - compact left side
        lId.setBounds(30, 50, 100, 25);
        lname.setBounds(30, 80, 100, 25);
        lgender.setBounds(30, 115, 100, 25);
        lspecialization.setBounds(30, 150, 100, 25);
        lqualification.setBounds(30, 185, 100, 25);
        lexperience.setBounds(30, 220, 100, 25);
        lphone.setBounds(30, 255, 100, 25);
        lemail.setBounds(30, 290, 100, 25);
        laddress.setBounds(30, 325, 100, 25);
        lavailability.setBounds(30, 360, 100, 25);

// TextFields - compact
        tfId.setBounds(140, 50, 180, 25);
        tfname.setBounds(140, 80, 180, 25);
        tfgender.setBounds(140, 115, 180, 25);
        tfspecialization.setBounds(140, 150, 180, 25);
        tfqualification.setBounds(140, 185, 180, 25);
        tfexperience.setBounds(140, 220, 180, 25);
        tfphone.setBounds(140, 255, 180, 25);
        tfemail.setBounds(140, 290, 180, 25);
        tfaddress.setBounds(140, 325, 180, 25);
        tfavailability.setBounds(140, 360, 180, 25);

// Buttons - horizontal row below form
        bsearch.setBounds(320, 50, 80, 25);
        bupdate.setBounds(130, 405, 90, 30);
        bview.setBounds(230, 405, 90, 30);
        bmenu.setBounds(700, 700, 100, 40);

        bmenu.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    public  void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bmenu) {     // add patient page will open
            Main_Menu m = new Main_Menu();
            m.setVisible(true);
            m.setSize(850, 800);
            m.setLocationRelativeTo(null);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        ViewDoctor obj = new ViewDoctor();
        obj.setSize(850,800);
        obj.setLocationRelativeTo(null);
        obj.setVisible(true);
    }
}