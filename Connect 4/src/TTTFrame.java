import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
    BufferedImage buffer = new BufferedImage(600,800,BufferedImage.TYPE_4BYTE_ABGR);
    public boolean localCheck = false;
    public boolean globalCheck = false;
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


        WindowListener wl = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    System.out.println("Hi");
                    os.writeObject(new CommandFromClient(CommandFromClient.DISCONNECT, ""));
                    Thread.sleep(1000);
                }
                catch (Exception a){
                    a.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };
        addWindowListener(wl);
    }

    public void paint(Graphics gg)
    {
        //7 by 6 grid (6 down 7 across) yellow background with white circles evenly spaced out
        // draws the backgroundG

        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setColor(new Color(237, 202, 133));
        g2.fillRect(0,0,getWidth(),getHeight());
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));
        g2.drawRect(20,80,getWidth()-40,getHeight()-100);

        // draws the display text to the screen
        g2.setColor(Color.GRAY);
        g2.setFont(new Font("Times New Roman",Font.BOLD,30));
        g2.drawString(text,20,55);

        // draws the circle grid lines to the screen
        int spacing = 75;
        g2.setColor(Color.WHITE);

        for(int y = 0;y < 6; y++)
        {
            for (int x = 0; x < 7; x++)
            {
                if (gameData.getGrid()[y][x] == 'B'){
                    g2.setColor(new Color(91, 162, 217));
                    g2.fillOval(spacing * x + 40, spacing * y + 100, 65, 65);
                } else if (gameData.getGrid()[y][x] == 'R') {
                    g2.setColor(new Color(237, 45, 45));
                    g2.fillOval(spacing * x + 40, spacing * y + 100, 65, 65);
                } else {
                    g2.fillOval(spacing * x + 40, spacing * y + 100, 65, 65);
                }
                g2.setColor(Color.BLACK);

                g2.drawOval(spacing * x + 40, spacing * y + 100, 65, 65);
                g2.setColor(Color.WHITE);
            }
        }
        gg.drawImage(buffer,0,0,null);
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
                text = "Blue's turn.";
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
        if(e.getButton() == 3)
        {
            if(!localCheck && ((gameData.isWinner(gameData.getGrid(), 'B') || gameData.isWinner(gameData.getGrid(), 'R') || gameData.isCat()) || globalCheck))//did I already want to reset?
            {
                reset();
                localCheck = true;//now it shows I did already request
            }
        }
        else {
            int placement = e.getX();
            int column = -1;
            int row = -1;
            if (placement >= 40 && placement <= 105) {
                column = 0;
                row = rower(gameData.getGrid(), column);
            } else if (placement >= 115 && placement <= 180) {
                column = 1;
                row = rower(gameData.getGrid(), column);
            } else if (placement >= 190 && placement <= 255) {
                column = 2;
                row = rower(gameData.getGrid(), column);
            } else if (placement >= 265 && placement <= 330) {
                column = 3;
                row = rower(gameData.getGrid(), column);
            } else if (placement >= 340 && placement <= 405) {
                column = 4;
                row = rower(gameData.getGrid(), column);
            } else if (placement >= 415 && placement <= 480) {
                column = 5;
                row = rower(gameData.getGrid(), column);
            } else if (placement >= 490 && placement <= 555) {
                column = 6;
                row = rower(gameData.getGrid(), column);
            }
            if (column != -1 && row != -1) {
                try {
                    os.writeObject(new CommandFromClient(CommandFromClient.MOVE, "" + column + row + player));
                    System.out.println("Work");
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }
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
    //to find the lowest row that the circle should drop on
    public static int rower(char[][] arr, int col){
        for (int i = arr.length-1; i >= 0; i--){
            if (arr[i][col] == ' '){
                return i;
            }
        }
        return -1;
    }

    public boolean getLocal(){
        return localCheck;
    }

    public void restart(){
        gameData.reset();
        globalCheck = false;
        localCheck = false;
    }

    public void reset(){
        try{
            if(globalCheck)
            {
                os.writeObject(new CommandFromClient(CommandFromClient.RESTART, ""));//restart if someone already requested
            }

            else
                os.writeObject(new CommandFromClient(CommandFromClient.RESTARTREQ, ""));//otherwise make sure that the request gets filed
        }
                catch(Exception b)
        {
            b.printStackTrace();
        }
    }

    public void req(){
        globalCheck = true;
    }

}
