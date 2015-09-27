
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

/*
 * Main method to use to run the program
 */
public class CodeGameMain extends JFrame
{
	/**
	 * Initializes the frame
	 */
    public CodeGameMain()
    {
       GamePanel panel = new GamePanel();
       panel.setFocusable(true); 
       panel.requestFocusInWindow();
       this.add(panel);
       this.setTitle("Code Game");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.pack();
       this.setSize(800,500);
       this.setVisible(true);
       setResizable(false);
    }
    /**
     * Starts it off
     * @param args
     */
    public static void main(String[] args)
    {
        CodeGameMain game = new CodeGameMain();
    }
}
