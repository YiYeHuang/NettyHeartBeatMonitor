package Monitor.monitorServer.server;

import java.util.Date;

public final class ServiceMetadata
{
    private final String m_host;
    private final String m_port;
    private final int m_pollFreq;
    private int m_graceTime;
    private Date m_blackOutStartTime;
    private Date m_blackOutEndTime;

    public ServiceMetadata(
        String host,
        String port,
        int pollFreq,
        int graceTime,
        Date blackOutStartTime,
        Date blackOutEndTime)
    {
        this.m_host = host;
        this.m_port = port;
        if (pollFreq < 1000)
        {
            m_pollFreq = 1000;
        }
        else
        {
            this.m_pollFreq = pollFreq;
        }
        this.m_graceTime = graceTime;
        this.m_blackOutStartTime = blackOutStartTime;
        this.m_blackOutEndTime = blackOutEndTime;
    }

    public String getHost()
    {
        return m_host;
    }

    public String getPort()
    {
        return m_port;
    }

    public String getURL()
    {
        return m_host + ":" + m_port;
    }

    public int getPollFreq()
    {
        return m_pollFreq;
    }

    public int getGraceTime()
    {
        return m_graceTime;
    }

    public Date getBlackOutStartTime()
    {
        return m_blackOutStartTime;
    }

    public Date getBlackOutEndTime()
    {
        return m_blackOutEndTime;
    }
}
