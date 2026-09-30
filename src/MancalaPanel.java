import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MancalaPanel extends JPanel implements MouseListener
{
    private final int[] board;
    private final Font numberFont, spaceFont;
    private int whoseTurn;
    private MancalaGame referee;
    private String message;

    private final boolean DRAW_SPACES = true; // handy for debugging; confusing in regular game?

    public MancalaPanel(int[] board)
    {
        super();
        if (board.length != 14)
            throw new RuntimeException("There was a problem. This board is the wrong size.");
        this.board = board;
        this.setBackground(Color.lightGray);
        numberFont = new Font("Times",Font.BOLD,24);
        spaceFont = new Font("Times",Font.PLAIN,10);
        whoseTurn = 0;
        message = "Start of game";
        addMouseListener(this);
        referee = null;
    }

    public void setReferee(MancalaGame ref)
    {
        referee = ref;
    }

    public int getWhoseTurn() {
        return whoseTurn;
    }

    /**
     * changes the color of the bar at the bottom of the window and changes the message
     * to say this it is Blue or Red's turn.
     * @param whoseTurn - must be 0 or 1
     */
    public void setWhoseTurn(int whoseTurn) {
        this.whoseTurn = whoseTurn;
        if (whoseTurn == 0)
            setMessage("It is Blue's turn to move.");
        else if (whoseTurn == 1)
            setMessage("It is Red's turn to move.");
        else
            throw new RuntimeException("I was told to set whose turn to "+whoseTurn);
    }

    /**
     * changes the message to be another string, but leaves the color alone.
     * If you wish to change the color and manually change the message, call setWhoseTurn() first,
     * then call setMessage() immediately afterward to override the "player's turn" message.
     * @param m - the string to display at the bottom of the window.
     */
    public void setMessage(String m)
    {
        message = m;
        repaint();
    }

    /**
     * refresh the appearance of the screen. You should never call this method directly; call repaint() instead.
     * @param g the <code>Graphics</code> object used to repaint this panel.
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // draw red spaces
        g.setColor(new Color(64,0,0));
        g.fillRoundRect(5,10,90,190,30,30);
        for (int i=1; i<=6; i++)
        {
            g.fillRoundRect(5+100*i, 10, 90, 90, 30, 30);
        }
        // draw blue spaces
        g.setColor(new Color(0,0,64));
        for (int i=1; i<=6; i++)
        {
            g.fillRoundRect(5+100*i, 110, 90, 90, 30, 30);
        }
        g.fillRoundRect(705,10,90,190,30,30);
        // draw content numbers
        g.setFont(numberFont);
        g.setColor(new Color(255,255,255));
        drawNumberCenteredAt(g, board[13], 50, 105);
        drawNumberCenteredAt(g, board[6], 750, 105 );
        for (int i=1; i<=6; i++)
        {
            drawNumberCenteredAt(g, board[i-1], 50+100*i, 155);
            drawNumberCenteredAt(g, board[13-i], 50+100*i, 55);
        }
        // draw spaces numbers
        if (DRAW_SPACES)
        {
            g.setFont(spaceFont);
            g.setColor(new Color(255,255,0));
            drawNumberCenteredAt(g, 13, 20, 25);
            drawNumberCenteredAt(g, 6, 720, 25 );
            for (int i=1; i<=6; i++)
            {
                drawNumberCenteredAt(g, i-1, 20+100*i, 125);
                drawNumberCenteredAt(g, 13-i, 20+100*i, 25);
            }
        }
        // draw message
        if (getWhoseTurn() == 0)
            g.setColor(new Color(0,0,64));
        else
            g.setColor(new Color(64,0,0));
        g.fillRect(0,210,800,40);
        g.setFont(numberFont);
        g.setColor(Color.WHITE);
        drawStringCenteredAt(g, message, 400,230);
    }

    /**
     * a utility to draw an integer centered at a given coordinate.
     * @param g - the Graphics object for drawing
     * @param n - the int to display
     * @param x - the center x location to draw
     * @param y - the center y location to draw
     * Note: I made this method private because only this class should call it (not AP standard).
     */
    private void drawNumberCenteredAt(Graphics g, int n, int x, int y)
    {
        drawStringCenteredAt(g, ""+n, x, y);
    }

    /**
     * a utility to draw a String centered at a given coordinate.
     * @param g - the Graphics object for drawing
     * @param s - the String to display
     * @param x - the center x location to draw
     * @param y - the center y location to draw
     * Note: I made this method private because only this class should call it (not AP standard).
     */
    private void drawStringCenteredAt(Graphics g, String s, int x, int y)
    {
        int width = g.getFontMetrics().stringWidth(s);
        int height = g.getFont().getSize();
        g.drawString(s,x-width/2,y+height/2);
    }

    /**
     * The user has just clicked in the window at (px, py). If this is in a bin,
     * tell the referee which bin it was via "handleUserSelection()."
     * @param px
     * @param py
     */
    public void handleSelection(int px, int py)
    {
        if (py>200)
            return;
        int index = -1;
        if (px<100)
            index = 13;
        else if (px>700)
            index = 6;
        else
        {
            index = (px - 100)/100;
            if (py<100)
                index = 12-index;
        }
        referee.handleUserSelection(index);
    }

    // ----------------------------------------------------------------------------------------------------------------
    // The Remainder of this class is required methods for responding to the mouse, most of which do nothing.
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        handleSelection(e.getX(), e.getY());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
