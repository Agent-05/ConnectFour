import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream;

public class TTTFrame extends JFrame implements MouseListener {
    // Display message
    private String text = "";
    // the letter you are playing as
    private char player;
    // stores all the game data
    private GameData gameData = null;
    // output stream to the server
    ObjectOutputStream os;

    public TTTFrame(GameData gameData, ObjectOutputStream os, char player)
    {
        super("Connect  4");
        // sets the attributes
        this.gameData = gameData;
        this.os = os;
        this.player = player;

        // adds a KeyListener to the Frame
        addMouseListener(this);

        // makes closing the frame close the program
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set initial frame message
        if(player == 'R')
            text = "Waiting for Black to Connect";

        setSize(600,580);
        setResizable(false);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        //7 by 6 grid (6 down 7 across) yellow background with white circles evenly spaced out
        // draws the backgroundG
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(new Color(237, 202, 133));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));
        g.drawRect(20,80,getWidth()-40,getHeight()-100);

        // draws the display text to the screen
        g.setColor(Color.GRAY);
        g.setFont(new Font("Times New Roman",Font.BOLD,30));
        g.drawString(text,20,55);

        // draws the circle grid lines to the screen
        int spacing = 75;
        g.setColor(Color.WHITE);

        for(int y = 0;y < 6; y++)
        {
            for (int x = 0; x < 7; x++)
            {
                g.fillOval(spacing * x + 40, spacing * y + 100, 65, 65);
                g.setColor(Color.BLACK);

                g.drawOval(spacing * x + 40, spacing * y + 100, 65, 65);
                g.setColor(Color.WHITE);
            }
        }

        // draws the player moves to the screen
        g.setFont(new Font("Times New Roman",Font.BOLD,70));
        for(int r=0; r<gameData.getGrid().length; r++)
            for(int c=0; c<gameData.getGrid().length; c++)
                g.drawString(""+gameData.getGrid()[r][c],c*133+42,r*133+150);
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }


    public void setTurn(char turn) {
        if(turn==player)
            text = "Your turn";
        else
        {
            if(turn == 'R')
                text = "Red's turn.";
            if(turn == 'B')
                text = "Black's turn.";
        }
        repaint();
    }

    public void makeMove(int c, int r, char letter)
    {
        gameData.getGrid()[r][c] = letter;
        repaint();
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int placement = e.getX();
        if (placement >= 40 && placement <= 105 ){
            System.out.println("Col1");
        } else if (placement >= 115 && placement <= 180 ) {
            System.out.println("Col2");
        } else if (placement >= 190 && placement <= 255 ) {
            System.out.println("Col3");
        } else if (placement >= 265 && placement <= 330 ) {
            System.out.println("Col4");
        } else if (placement >= 340 && placement <= 405 ) {
            System.out.println("Col5");
        } else if (placement >= 415 && placement <= 480 ) {
            System.out.println("Col6");
        } else if (placement >= 490 && placement <= 555 ) {
            System.out.println("Col7");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
