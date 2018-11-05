package oldImpl.Monitor.monitorServer.launch;

import oldImpl.Monitor.monitorServer.server.CentralService;
import oldImpl.Monitor.monitorServer.server.MonitorServer;

public class RunMonitor
{
    public static void main(String[] args)
    {
        try
        {

            // Start the chat application
            CentralService server = new CentralService();

            int port = 4321;

            // Start the TCP server
            MonitorServer tcpServer = new MonitorServer(server, port);

            tcpServer.start();
        }
        catch (Exception e)
        {
            System.out.println("Got exception starting up:");
            e.printStackTrace();
        }
    }
}
