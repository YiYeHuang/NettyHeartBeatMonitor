package Monitor.monitorCaller.request;

import java.io.Serializable;

public class ServiceStatus  implements Serializable 
{
    private static final long serialVersionUID = 56431921123234L;
    private final String m_host;
    private final String m_port;
    private boolean m_isAlive;

    public ServiceStatus(
        String host,
        String port)
    {
        this.m_host = host;
        this.m_port = port;
        this.m_isAlive = false;
    }

    public boolean isAlive()
    {
        return m_isAlive;
    }

    public void setIsAlive(boolean isAlive)
    {
        this.m_isAlive = isAlive;
    }

    public String getHost()
    {
        return m_host;
    }

    public String getPort()
    {
        return m_port;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(m_host).append(":").append(m_port);
        if (m_isAlive)
        {
            sb.append(" is running");
        }
        else
        {
            sb.append(" no response");
        }

        return sb.toString();
    }
}
