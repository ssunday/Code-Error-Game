import javax.swing.JPanel;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Timer;
import javax.swing.JButton;

/**
 * Controls the game itself
 * 
 * @author Sarah Sunday
 * @version 25-11-2014
 */
public class GamePanel extends JPanel {
	/*
	 * An ArrayList that contains the Code objects that will be displayed on the
	 * Panel.
	 */
	private ArrayList<Code> code_list;
	/*
	 * The Array of all lines of code to be used for random Code object
	 * generation. Corresponds 1:1 with the boolean array truths.
	 */
	private String[] codeLines;
	/*
	 * A Boolean Array of whether the line of code in codeLines provokes an
	 * error or is legitimate.
	 */
	private boolean[] truths;
	/*
	 * The score the player has accumulated.
	 */
	private int score;
	/*
	 * How many lives the player has.
	 */
	private int lives;
	/*
	 * Whether the game is over or not.
	 */
	private boolean end;
	/*
	 * A random generator object to be used for generating which line of code to
	 * be used and where to put the Code object.
	 */
	private Random gen;
	/*
	 * Boolean variable used to detect whether the game over screen has been
	 * displayed, used to prevent clicking automatically restarting game
	 */
	private boolean showed;

	/**
	 * Mouse Listener class used for setting actios from user input.
	 * @author Sarah
	 *
	 */
	class myMouseListener implements MouseListener {
		public void mousePressed(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}
		
		/**
		 * When mouse is clicked, check if clicked inside a code bubble and whether it is right or wrong, then either increment score or subtract lives.
		 * If the code was right then reset the location.
		 */
		public void mouseClicked(MouseEvent e) {
			if (code_list.size() > 0) {
				for (int i = 0; i < code_list.size(); i++) {
					if (code_list.get(i).checkCollision(e.getX(), e.getY())
							&& code_list.get(i).getCorrect()) {
						incScore();
						checkCloseness(i);
					}

					else if (code_list.get(i).checkCollision(e.getX(), e.getY())
							&& !(code_list.get(i).getCorrect())) {
						wrong();
					}

				}

				if (end && showed) {
					lives = 3;
					score = 0;
					for (int i = 0; i < code_list.size(); i++)
					{
						checkCloseness(i);
					}
					end = false;
					showed = false;
				}
			}
			repaint();
		}
	}
	/**
	 * Initializes the Game panel, adding the mouse listener and setting start values.
	 */
	public GamePanel() {
		this.addMouseListener(new myMouseListener());
		score = 0;
		lives = 3;
		code_list = new ArrayList<Code>();
		codeLines = new String[9];
		truths = new boolean[9];
		gen = new Random();
		end = false;
		setUp();
		int delay = 20;
		ActionListener codeManager = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < code_list.size(); i++) {
					code_list.get(i).moveY();
					if (code_list.get(i).getCoords()[1] + 30 > getHeight()) {
						checkCloseness(i);
					}
				}
			}
		};
		showed = false;
		
		for (int i = 0; i < 5; i++)
		{
			int rand = gen.nextInt(codeLines.length);
			Code code1 = new Code(10, 10, codeLines[rand], truths[rand]);
			code_list.add(code1);
		}

		new Timer(delay, codeManager).start();

	}

	/**
	 * Sets up the truth/CodeLines arrays with their respective values.
	 */
	private void setUp() {
		codeLines[0] = "print 'yes'";
		truths[0] = true;
		codeLines[1] = "print 'no";
		truths[1] = false;
		codeLines[2] = "x = 2 + '2'";
		truths[2] = false;
		codeLines[3] = "('hello') = var1";
		truths[3] = false;
		codeLines[4] = "y = 'hello' * 5";
		truths[4] = true;
		codeLines[5] = "var2 = ('hello' , 5)";
		truths[5] = true;
		codeLines[6] = "var1 = ('hello,' 6)";
		truths[6] = false;
		codeLines[7] = "var = [1,'h:2',3]";
		truths[7] = true;
		codeLines[8] = "name = raw_input('What is your name?:)";
		truths[8] = false;

	}

	/**
	 * Handles the instance when the player selects a good piece of code.
	 * Increases score by a hundred points.
	 */
	private void incScore() {
		score += 100;
	}

	/**
	 * Handles when the player has selected an line of code that contains an
	 * error. It takes away a life and checks whether the game is over.
	 */
	private void wrong() {
		lives -= 1;
		if (lives <= 0) {
			end = true;
		}
	}

	/**
	 * 
	 * Check Closeness is used for resetting Code objects's positions and
	 * values.
	 * 
	 * @param pos
	 *            - The position in code_list which refers to a specific Code
	 *            object we are trying to test its distance to other Codes
	 */
	private void checkCloseness(int pos) {
		boolean tooclose = true;
		int rand = gen.nextInt(codeLines.length);
		int rand2 = gen.nextInt(600);
		int rand3 = gen.nextInt(200);
		while (tooclose)
		{
			tooclose = false;
			rand = gen.nextInt(codeLines.length);
			rand2 = gen.nextInt(600);
			rand3 = gen.nextInt(200);
			code_list.get(pos).reset(60 + rand2, rand3 + 60, codeLines[rand],
				truths[rand]);
			for (int j = 0; j < code_list.size(); j++)
			{
				if (j != pos && !tooclose) {
					tooclose = code_list.get(pos).checkIntersects(code_list.get(j).getRect());
					
				}
				
			}
		}
	
		repaint();
	}

	/**
	 * PaintComponent is an inherited method. It is responsible for drawing the
	 * respective elements.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLUE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		if (!end) {
			for (int i = 0; i < code_list.size(); i++) {
				code_list.get(i).draw(g);
			}
			g2.setFont(new Font("Optima", Font.PLAIN, 20));
			g2.setColor(Color.WHITE);
			g2.drawString("Score:", 30, 30);
			g2.drawString(String.valueOf(score), 90, 30);
			g2.drawString("LIVES:", getWidth() - 160, 30);
			for (int i = 0; i < lives; i++) {
				g2.drawString("X", getWidth() - 40 - 20 * (i + 1), 30);

			}
		} else if (end) {

			g2.setFont(new Font("Optima", Font.BOLD, 60));
			g2.setColor(Color.WHITE);
			g2.drawString("GAME OVER", getWidth() / 4, getHeight() / 2);
			g2.setFont(new Font("Optima", Font.PLAIN, 60));
			g2.drawString("Click anywhere to restart.", getWidth() / 4 - 120,
					getHeight() / 2 + 100);
			repaint();
			showed = true;

		}
		this.requestFocusInWindow();
		repaint();
	}

}