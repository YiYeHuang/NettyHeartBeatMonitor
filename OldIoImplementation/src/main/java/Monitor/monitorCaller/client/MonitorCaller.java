package Monitor.monitorCaller.client;

import Monitor.monitorCaller.request.RegisterReqest;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class MonitorCaller implements Runnable
{
    private String m_monitorHost;
    private String m_monitorPort;
    private String m_cid;
    private String m_sHost;
    private String m_sPort;
    private int m_pollFreq;
    private int m_graceTime;
    private Date m_blackOutStartTime;
    private Date m_blackOutEndTime;

    private Socket clientSocket;
    
    public MonitorCaller(
        String monitorHost,
        String monitorPort,
        String c_id,
        String serviceHost,
        String servicePort,
        int servicePollFreq,
        int graceTime,
        Date blackOutStartTime,
        Date blackOutEndTime)
    {
        this.m_monitorHost = monitorHost;
        this.m_monitorPort = monitorPort;
        this.m_cid = c_id;
        this.m_sHost = serviceHost;
        this.m_sPort = servicePort;
        this.m_pollFreq = servicePollFreq;
        this.m_graceTime = graceTime;
        this.m_blackOutStartTime = blackOutStartTime;
        this.m_blackOutEndTime = blackOutEndTime;
    }

    public String getName()
    {
        return m_cid;
    }

    @Override
    public void run()
    {
        try
        {
            // Connect to the TCPChatServer program 

            clientSocket = new Socket(m_monitorHost, Integer.valueOf(m_monitorPort));

            ObjectOutputStream chatOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream chatInputStream = new ObjectInputStream(clientSocket.getInputStream());

            RegisterReqest regReq = new RegisterReqest(
                    m_cid,
                    m_sHost,
                    m_sPort,
                    m_pollFreq,
                    m_graceTime,
                    m_blackOutStartTime,
                    m_blackOutEndTime);

            // Send the name to the server
            chatOutputStream.writeObject(regReq);

            // Start up a reader thread that reads messages from the server
            ResponseReader reader = new ResponseReader(this, chatInputStream);

            reader.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void disconnect()
    {
        try
        {
            clientSocket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
