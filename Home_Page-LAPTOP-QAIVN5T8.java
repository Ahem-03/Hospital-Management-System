import javax.swing.*;
import java.awt.*;
public class Home_Page extends JFrame implements Runnable{
    JLabel lheading;
    JPanel jpanel;
    public Home_Page() {
        //initializing
        jpanel = new JPanel();
        lheading = new JLabel("-: Welcome to the City Hospital :-");
        ImageIcon bgIcon = new ImageIcon("image_1.png");
        Image img = bgIcon.getImage().getScaledInstance(1250, 820, Image.SCALE_SMOOTH);
        bgIcon = new ImageIcon(img);

        JLabel backgroundLabel = new JLabel(bgIcon);
        backgroundLabel.setBounds(0, 0, 1050, 820);
        backgroundLabel.setLayout(null); // Allow adding components over it
        //set bounds
        //background image
        lheading.setBounds(50,30,300,50);
        lheading.setFont(new Font("Segoe UI", Font.BOLD,28));
        lheading.setForeground(Color.LIGHT_GRAY);
        
        
        //adding to the panel
        add(jpanel);
        //jpanel.add(lheading);
        jpanel.add(backgroundLabel);

        //framing 
        setTitle("Home Page");
        setSize(1250, 820);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }
    public void run(){
        try {
            Thread.sleep(3000);
            new Login_Page();
            setVisible(true);
            this.setVisible(false);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
         Home_Page hp =new Home_Page();
        Thread td = new Thread(hp);
        td.start();
    }
}