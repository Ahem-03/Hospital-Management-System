import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main_Menu extends JFrame  {
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
        baddp.addActionListener( new ActionListener() {
            
            // add patient page will open
            public void actionPerformed(ActionEvent e ){
                 new ViewPatient();
                setVisible(true);
                  setSize(1050,800);
                  setLocationRelativeTo(null);
                  setVisible(false);
            }
        });
    
        // baddD.addActionListener(this);
        // baptmnt.addActionListener(this);


        // framing 
        setSize(1050,820);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new Main_Menu());
    }
}