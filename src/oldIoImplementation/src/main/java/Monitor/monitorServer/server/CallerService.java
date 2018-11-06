package Monitor.monitorServer.server;

import Monitor.monitorServer.request.RegisterReqest;
import Monitor.monitorServer.request.ServiceStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class CallerService extends Object implements Runnable
{
    private CentralService server;

    private Socket clientSock;

    private ObjectInputStream inStream;

    private ObjectOutputStream outStream;

    private String clientName;

    private Thread myThread;

    private ServiceMetadata m_serviceMeta;

    private boolean m_initServerCheckWithInGraceTime;

    public CallerService(CentralService server, Socket clientSock) throws IOException
    {
        this.server = server;
        this.clientSock = clientSock;
        this.m_initServerCheckWithInGraceTime = true;

        // get data streams to the socket
        inStream = new ObjectInputStream(clientSock.getInputStream());
        outStream = new ObjectOutputStream(clientSock.getOutputStream());

        try
        {
            RegisterReqest registerReq = (RegisterReqest) inStream.readObject();
            clientName  = registerReq.getRid();

            System.out.println(
                    "Client: " + registerReq.getRid() +
                    " Registered, IP:" + clientSock.getInetAddress() +
                    " Registerd Service " + registerReq.getHost() + ":" + registerReq.getPort());


            m_serviceMeta = server.registerCaller(registerReq.getRid(), this, registerReq);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // TODO tell client register failure
        }
    }

    public void disconnect()
    {
        try
        {
            clientSock.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            stop();
        }
    }

    /**
     * The monitor should allow callers to define a grace time that applies to all
     * services being monitored. If a service is not responding, the monitor will
     * wait for the grace time to expire before notifying any clients. If the
     * service goes back on line during this grace time, no notification will be
     * sent. If the grace time is less than the polling frequency, the monitor
     * should schedule extra checks of the service.
     */
    public void initialCheck() throws IOException, InterruptedException
    {
        if (m_initServerCheckWithInGraceTime)
        {
            if (m_serviceMeta.getGraceTime() < m_serviceMeta.getPollFreq())
            {
                boolean connection = ping();
                if (connection != server.getServiceStatus(m_serviceMeta.getURL()))
                {
                    notify(connection);
                    server.updateStatus(m_serviceMeta.getURL(), connection);
                }
            }
            else
            {
                Thread.sleep(m_serviceMeta.getGraceTime());
            }
            
            // turn off grace time check
            m_initServerCheckWithInGraceTime = false;
        }
    }

    public boolean ping()
    {
        try
        {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(m_serviceMeta.getHost(), Integer.valueOf(m_serviceMeta.getPort())), 10000);
            socket.close();
            return true;
        }
        catch (IOException ex)
        {
            return false;
        }
    }

    public void notify(boolean status) throws IOException
    {
        ServiceStatus toClient = new ServiceStatus(m_serviceMeta.getHost(), m_serviceMeta.getPort());
        toClient.setIsAlive(status);
        outStream.writeObject(toClient);
    }

    public void run()
    {
        while (true)
        {
            Date now = new Date();
            // out side black out time
            while (null == m_serviceMeta.getBlackOutStartTime() ||
                   null == m_serviceMeta.getBlackOutEndTime() ||
                   now.compareTo(m_serviceMeta.getBlackOutStartTime()) < 0 ||
                   now.compareTo(m_serviceMeta.getBlackOutEndTime()) > 0)
            {   
                try
                {
                    initialCheck();
                    // regular poll schedule
                    Thread.sleep(m_serviceMeta.getPollFreq());
                    boolean connection = ping();
                    if (connection != server.getServiceStatus(m_serviceMeta.getURL()))
                    {
                        notify(connection);
                        server.updateStatus(m_serviceMeta.getURL(), connection);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    server.removeCaller(clientName);
                    return;
                }
            }
        }
    }

    public void start()
    {
        myThread = new Thread(this);
        myThread.start();
    }

    public void stop()
    {
        myThread.stop();
        myThread = null;
    }
}
