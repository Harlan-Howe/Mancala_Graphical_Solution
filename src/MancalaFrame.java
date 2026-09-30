import javax.swing.*;
import java.awt.*;

public class MancalaFrame extends JFrame
{
    public MancalaFrame(MancalaPanel panel)
    {
        super("Mancala");
        setSize(800,300);
        getContentPane().setLayout(new GridLayout(1,1));
        getContentPane().add(panel);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
