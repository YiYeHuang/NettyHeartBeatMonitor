package oldImpl.MonitorCaller.monitorCaller.request;

import java.io.Serializable;
import java.util.Date;

/**
 * While PRC is great for communicate, I cannot figure out a way to let server 
 * talk to client in heart beat way.
 * Therefore, have to use customized Serialize
 */
public final class RegisterReqest implements Serializable 
{
    private static final long serialVersionUID = 392334319211234L;
    private String m_rid;
    private String m_sHost;
    private String m_sPort;
    private int m_pollFreq;
    private int m_graceTime;
    private Date m_blackOutStartTime;
    private Date m_blackOutEndTime;

    public RegisterReqest(
        String id,
        String hostName, 
        String port,
        int pollFreq,
        int graceTime,
        Date blackOutStartTime,
        Date blackOutEndTime)
    {
        this.m_rid = id;
        this.m_sHost = hostName;
        this.m_sPort = port;
        this.m_pollFreq = pollFreq;
        this.m_graceTime = graceTime;
        this.m_blackOutStartTime = blackOutStartTime;
        this.m_blackOutEndTime = blackOutEndTime;
        
        if (m_blackOutStartTime != null && 
            m_blackOutEndTime != null &&
            m_blackOutStartTime.compareTo(blackOutEndTime) > 0)
        {
            this.m_blackOutStartTime = null;
            this.m_blackOutEndTime = null;
        }
    }

    public String getRid()
    {
        return m_rid;
    }

    public String getHost()
    {
        return m_sHost;
    }

    public String getPort()
    {
        return m_sPort;
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

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(m_rid).append("|")
        .append(m_sHost).append("|")
        .append(m_sPort).append("|")
        .append(m_pollFreq).append(" milli sec");
        return sb.toString();
    }
}
