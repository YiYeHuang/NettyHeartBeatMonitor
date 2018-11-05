package oldImpl.Monitor.monitorServer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MonitorServer extends Object implements Runnable
{
    protected ServerSocket serverSocket;

    protected CentralService server;

    protected Thread myThread;

    private int m_port;

    public MonitorServer(CentralService server, int port) throws IOException
    {
        m_port = port;
        serverSocket = new ServerSocket(m_port);

        this.server = server;
    }

    public void run()
    {
        while (true)
        {
            try
            {
                // Accept a new connection
                Socket newConn = serverSocket.accept();
                System.out.println("Current Client number: " + server.getCurrentUserNumber());
                System.out.println("Incoming socket: " + newConn.getInetAddress() + " " + newConn.getLocalAddress());
                // Create a client to handle the connection
                CallerService newClient = new CallerService(server, newConn);

                // Register and run
                newClient.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void start()
    {
        System.out.println("Monitor start at: " + m_port);
        myThread = new Thread(this);
        myThread.start();
    }

    public void stop()
    {
        myThread.stop();
        myThread = null;
    }
}
