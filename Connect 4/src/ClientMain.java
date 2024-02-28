import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientMain
{
    public static void main(String[] args)
    {
        try {
            // create an object for the TTT game
            GameData gameData = new GameData();

            // create a connection to server
            Socket socket = new Socket("T440014-2329L.oths.katyisd.org",8025);
            ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

            // determine if playing as red or blue
            CommandFromServer cfs = (CommandFromServer) is.readObject();
            TTTFrame frame;

            // Create the Frame based on which player the server says this client is
            if(cfs.getCommand() == CommandFromServer.CONNECTED_AS_R)
                frame = new TTTFrame(gameData,os,'R');//indicates Red
            else
                frame = new TTTFrame(gameData,os, 'B');//indicates Blue

            // Starts a thread that listens for commands from the server
            ClientsListener cl = new ClientsListener(is,os,frame);
            Thread t = new Thread(cl);
            t.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
