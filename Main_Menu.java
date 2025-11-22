import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class Main_Menu extends JFrame implements ActionListener{
    JPanel p1;
    JLabel lh;
    JButton  baddp, baddD, baptmnt;
    public Main_Menu()
    {
        //initializing
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(235, 245, 255));
        lh      = new JLabel(" -: Appolo Hospital :- ", JLabel.CENTER);
        baddp   = new JButton(" 🧑‍🤝‍🧑 Manage patient");
        baddD   = new JButton("👨‍⚕️ Manage Doctors");
        baptmnt = new JButton(" 📅 Fix appointment");


        //  set the font for all component 
        lh.setFont(new Font("Segoe UI", Font.BOLD, 28));
        baddp.setFont(new Font("Segoe UI", Font.BOLD, 18));
        baddD.setFont(new Font("Segoe UI", Font.BOLD, 18));
        baptmnt.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        //set background for the button
        baddp.setBackground(new Color(0, 153, 153));
        baddD.setBackground(new Color(051, 153, 255));
        baptmnt.setBackground(new Color(102, 102, 255));
// adding to the panel
        add(p1);
        p1.add(lh);
        p1.add(baddp);
        p1.add(baddD);
        p1.add(baptmnt);

// set bounds (x,y,w,h)
        setTitle("Main Menu");
        lh.setBounds(0,20,1050,50);
        baddp.setBounds(300, 200, 250, 50);      // Add Patient
        baddD.setBounds(300, 340, 250, 50);      // Add Doctor
        baptmnt.setBounds(300, 480, 250, 50);	 // appointment

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


// action listener
        baddp.addActionListener(this);
        baddD.addActionListener(this);
        baptmnt.addActionListener(this);


        // framing 
        setSize(1050,820);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public  void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource()==baddp) {     // add patient page will open
            AddPatient ap = new AddPatient();
            ap.setVisible(true);
            ap.setSize(1050,800);
            ap.setLocationRelativeTo(null);
            this.setVisible(false);
        }
        
        else if (ae.getSource()==baddD) {     // add doctor page will open
            AddDoctor ad = new AddDoctor();
            ad.setVisible(true);
            ad.setSize(1050,800);
            ad.setLocationRelativeTo(null);
            this.setVisible(false);
        }
     
    }
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Main_Menu());
    }
}