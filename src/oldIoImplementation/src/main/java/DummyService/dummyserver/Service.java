package DummyService.dummyserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Service implements Runnable
{
    private int port;
    public Service(int port)
    {
        this.port = port;
    }

    public void run()
    {
        ServerSocket testSocket;
        try
        {
            testSocket = new ServerSocket(port);
            System.out.println("Service start at: " + port);
            while (true)
            {
                Socket connectionSocket = testSocket.accept();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
