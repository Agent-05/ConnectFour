import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(8025);

            // allow X to connect and build streams to / from X
            Socket xCon = serverSocket.accept();
            ObjectOutputStream xos = new ObjectOutputStream(xCon.getOutputStream());
            ObjectInputStream xis = new ObjectInputStream(xCon.getInputStream());

            // Lets the client know they are the Red player
            xos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_R,null));
            System.out.println("Red has Connected.");

            // Creates a Thread to listen to the Red client
            ServersListener sl = new ServersListener(xis,xos,'R');
            Thread t = new Thread(sl);
            t.start();

            // allow Blue to connect and build streams to / from Blue
            Socket oCon = serverSocket.accept();
            ObjectOutputStream oos = new ObjectOutputStream(oCon.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(oCon.getInputStream());

            // Lets the client know they are the Blue player
            oos.writeObject(new CommandFromServer(CommandFromServer.CONNECTED_AS_B,null));
            System.out.println("Blue has Connected.");

            // Creates a Thread to listen to the X client
            sl = new ServersListener(ois,oos,'B');
            t = new Thread(sl);
            t.start();


            xos.writeObject(new CommandFromServer(CommandFromServer.R_TURN,null));
            oos.writeObject(new CommandFromServer(CommandFromServer.R_TURN,null));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
