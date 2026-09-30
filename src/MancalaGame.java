import javax.swing.*;

public class MancalaGame
{
    private int[] board;
    private MancalaPanel GUI;
    private int whoseTurn;
    private final int BLUES_TURN = 0;
    private final int REDS_TURN = 1;
    private final int BLUES_STORE = 6;
    private final int REDS_STORE = 13;

    public MancalaGame(int[] board, MancalaPanel GUI)
    {
        this.board = board;
        this.GUI = GUI;
        whoseTurn = BLUES_TURN;
        this.GUI.setWhoseTurn(whoseTurn);
        resetBoard();
    }

    /**
     * resets the board to the starting condition, useful for the start of the game and replays.
     */
    public void resetBoard()
    {
        for (int i=0; i<14; i++)
            board[i] = 4;
        board[BLUES_STORE] = 0;
        board[REDS_STORE] = 0;
        GUI.setWhoseTurn(BLUES_TURN);
    }

    /**
     * the user just clicked one of the bins; it's up to you to do something about this.
     * @param whichBin - the index (0-13) of the bin clicked.
     */
    public void handleUserSelection(int whichBin)
    {
        System.out.println("I was just told to handle a click in bin "+whichBin+"!");

        if (whoseTurn == BLUES_TURN && whichBin<BLUES_STORE && board[whichBin] > 0)
            makeMove(whichBin);
        else if (whoseTurn == REDS_TURN && whichBin<REDS_STORE && whichBin > BLUES_STORE && board[whichBin] > 0)
            makeMove(whichBin);
        else
            GUI.setMessage("That was not a legal move. Try again.");

        GUI.repaint();
    }

    public void makeMove(int whichBin)
    {
        int hand = board[whichBin];
        board[whichBin] = 0;
        int loc = whichBin;
        while (hand>0)
        {
            loc = (loc+1)%14;
            if (whoseTurn == BLUES_TURN && loc == REDS_STORE)
                continue;
            if (whoseTurn == REDS_TURN && loc == BLUES_STORE)
                continue;
            board[loc] ++;
            hand --;
        }
        if (whoseTurn == BLUES_TURN && loc < BLUES_STORE && board[loc] == 1)
        {
            int captured = 1;
            board[loc] = 0;
            captured += board[12-loc];
            board[12-loc] = 0;
            board[BLUES_STORE]+= captured;
        }
        if (whoseTurn == REDS_TURN && loc > BLUES_STORE && loc < REDS_STORE && board[loc] == 1)
        {
            int captured = 1;
            board[loc] = 0;
            captured += board[12-loc];
            board[12-loc] = 0;
            board[REDS_STORE]+= captured;
        }
        if (isGameOver())
        {
            System.out.println("Game over.");
            if (board[BLUES_STORE]>board[REDS_STORE])
            {
                GUI.setWhoseTurn(BLUES_TURN);
                GUI.setMessage("Blue wins!");
            }
            else if (board[BLUES_STORE]<board[REDS_STORE])
            {
                GUI.setWhoseTurn(REDS_TURN);
                GUI.setMessage("Red wins!");
            }
            else
                GUI.setMessage("It's a tie!");
            requestPlayAgain();
            resetBoard();
            return;


        }
        if ((whoseTurn == 0 && loc == BLUES_STORE)  ||
                (whoseTurn == 1 && loc == REDS_STORE))
        {
            GUI.setMessage("Move again.");
        }
        else
        {
            whoseTurn = 1 - whoseTurn;
            GUI.setWhoseTurn(whoseTurn);
        }
    }

    private boolean isGameOver()
    {
        int lowerCount = 0;
        int upperCount = 0;
        for (int i=0; i<6; i++)
        {
            lowerCount+= board[i];
            upperCount+= board[i+7];
        }
        if (lowerCount > 0 && upperCount > 0)
            return false;

        board[BLUES_STORE]+= lowerCount;
        board[REDS_STORE]+= upperCount;
        for (int i=0; i<6; i++)
        {
            board[i] = 0;
            board[i+7] = 0;
        }
        return true;
    }

    /**
     * completely optional method - shows a dialog box asking the player if he/she wants to play again.
     * if not, ends the program.
     */
    public void requestPlayAgain()
    {
        int response = JOptionPane.showConfirmDialog(GUI,"Play again?","Play again",JOptionPane.YES_NO_OPTION);
        if (response != JOptionPane.YES_OPTION)
        {
            System.exit(1);
        }
    }
}
