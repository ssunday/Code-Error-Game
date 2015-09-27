import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Code {
	/*
	 * The x coordinate of the beginning of the bubble
	 */
	private int x;
	/*
	 * The y coordinate of the beginning of the bubble
	 */
	private int y;
	/*
	 * The lines of code that the object will display.
	 */
	private String lines;
	/*
	 * Whether the line of code is correct or not
	 */
	private boolean correct;
	/*
	 * The white rectangle that serves as a bubble surrounding the line of code
	 */
	private Rectangle2D codeBubble;
	public Code() 
	{
		x = 0;
		y = 0;
		lines = "Dummy";
		correct = true;

	}
	
	public Code(int x1,int y1, String lines1, boolean whether_correct)
	{
		x = x1;
		y = y1;
		lines = lines1;
		correct = whether_correct;
		
        codeBubble = new Rectangle2D.Double(x1,y1,lines1.length()*7,30);

	}
	/**
	 * This function returns the coordinates of the line of code
	 * @return integer array of the coordinates.
	 */
	public int[] getCoords()
	{
		int[] coords = new int [2];
		coords[0] = x;
		coords[1] = y;
		return coords;
	}
	/**
	 * Increments the y coordinate, having it 'move' down.
	 */
	public void moveY()
	{
		y += 1;
	}
	/**
	 * Resets the position of the line of code
	 * @param x1
	 * @param y1
	 * @param lines1
	 * @param whether_correct
	 */
	public void reset(int x1,int y1, String lines1, boolean whether_correct)
	{
		x = x1;
		y = y1;
		lines = lines1;
		correct = whether_correct;
        codeBubble.setRect(x1,y1,lines1.length()*7,30);

	}
	/**
	 * 
	 * @return whether the line of code is correct or not
	 */
	public boolean getCorrect()
	{
		return correct;
	}

	public Rectangle2D getRect()
	{
		return codeBubble;
	}
	
	/**
	 * Checks whether it is intersecting with the other piece of code.
	 * @param code
	 * @return true if intersecting or contains the other piece of code
	 */
	public boolean checkIntersects(Rectangle2D second_rect)
	{
		return codeBubble.intersects(second_rect) || codeBubble.contains(second_rect);
	}
	/**
	 * Checks to see if the (x,y) coordinate is within the bubble.
	 * @param x1 
	 * @param y1
	 * @return true if point is within the bubble.
	 */
	public boolean checkCollision(int x1, int y1)
	{
        return codeBubble.contains(x1,y1);
	}
	/**
	 * Draws the thing
	 * @param g
	 */
	public void draw(Graphics g)
	{
        Graphics2D g2 = (Graphics2D) g;
        codeBubble.setRect(x,y,lines.length()*7,30);
        g2.setColor(Color.white);
        g2.fill(codeBubble);
        g2.setColor(Color.BLACK);
        g2.drawString(lines,x+5,y+20);
	}
	
	
}
