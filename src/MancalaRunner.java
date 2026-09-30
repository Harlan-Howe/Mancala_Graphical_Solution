public class MancalaRunner
{
    static void main()
    {
        // setup board
        int[] boardValues = {4,4,4,4,4,4,0,4,4,4,4,4,4,0};
        // set up GUI, giving the GUI the board
        MancalaPanel panel = new MancalaPanel(boardValues);
        MancalaFrame window = new MancalaFrame(panel);

        // set up the referee, giving it board & GUI
        MancalaGame referee = new MancalaGame(boardValues, panel);

        // give the referee to the GUI
        panel.setReferee(referee);

        // make GUI appear, starting the game.
        window.setVisible(true);
    }

}
