import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientsListener implements Runnable
{
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private TTTFrame frame = null;

    public ClientsListener(ObjectInputStream is,
                           ObjectOutputStream os,
                           TTTFrame frame) {
        this.is = is;
        this.os = os;
        this.frame = frame;

    }

    @Override
    public void run() {
        try
        {
            while(true)
            {
                CommandFromServer cfs = (CommandFromServer)is.readObject();

                // processes the received command
                if(cfs.getCommand() == CommandFromServer.R_TURN)
                    frame.setTurn('R');
                else if(cfs.getCommand() == CommandFromServer.B_TURN)
                    frame.setTurn('B');
                else if(cfs.getCommand() == cfs.MOVE)
                {
                    String data = cfs.getData();
                    // pulls data for the move from the data field
                    int c = data.charAt(0) - '0';
                    int r = data.charAt(1) - '0';

                    // changes the board and redraw the screen
                    frame.makeMove(c,r,data.charAt(2));
                }
                // handles the various end game states
                else if(cfs.getCommand() == CommandFromServer.TIE)
                {
                    frame.setText("Tie game.");
                }
                else if(cfs.getCommand() == CommandFromServer.R_WINS)
                {
                    frame.setText("Red wins!");
                }
                else if(cfs.getCommand() == CommandFromServer.B_WINS)
                {
                    frame.setText("Blue wins!");
                }
                else if(cfs.getCommand() == CommandFromServer.RESET)
                {
                    frame.restart();
                }
                else if(cfs.getCommand() == CommandFromServer.RESETREQ)
                {
                    frame.req();
                    if(!frame.getLocal())
                    {
                        frame.setText("Other player wants to play again, but do you? (Press right mouse button)");
                    }
                }
                else if(cfs.getCommand() == CommandFromServer.DISCONNECT)
                {
                    frame.setText("Other Player Disconnected");
                    Thread.sleep(3000);
                    frame.setText("Ending Game...");
                    Thread.sleep(3000);
                    frame.setText("5");
                    Thread.sleep(1000);
                    frame.setText("4");
                    Thread.sleep(1000);
                    frame.setText("3");
                    Thread.sleep(1000);
                    frame.setText("2");
                    Thread.sleep(1000);
                    frame.setText("1");
                    Thread.sleep(1000);
                    frame.setText("Bye Bye");
                    Thread.sleep(1000);
                    System.exit(0);

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }



}